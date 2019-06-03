/**
 * 
 */
package es.uneatlantico.gdbd.dataaccess;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.uneatlantico.gdbd.entities.Column;
import es.uneatlantico.gdbd.entities.Database;
import es.uneatlantico.gdbd.entities.Table;

/**
 * Implementation of MySQL database connector
 * @author Alberto Gutiérrez Arroyo
 *
 */
public class MySQLDatabase implements IDatabase {
	
	private static final Logger logger = LogManager.getLogger(MySQLDatabase.class); 
	/**
	 * JDBC MySQL base path to create connections
	 */
	private final static String Subprotocol = "jdbc:mysql://";
	/**
	 * JDBC driver class
	 */
	private final static String Driver = "com.mysql.jdbc.Driver";
	/**
	 * Default MySQL port
	 */
	private final static int Default_Port = 3306;
	private String server, name, username;
	private int port;
	private char[] password;	

	/**
	 * Default constructor of MySQL Database Connector. As the port is not defined, it will use the default
	 * MySQL port (3306)
	 * @param server  database server address
	 * @param database  name of the database on the server
	 * @param username  user name to login on the database server
	 * @param password  password for the given username
	 */
	public MySQLDatabase(String server, String database, String username, char[] password)
	{
		this (server, Default_Port, username, password);
	}
	
	/**
	 * Constructor of MySQL Database Connector (It admits a custom port)
	 * @param server  database server address
	 * @param port  port the connection will use
	 * @param username  user name to login on the database server
	 * @param password  password for the given username
	 */
	public MySQLDatabase(String server, int port, String username, char[] password)
	{
		this.server = server.trim();
		if (port <= 0 || port >= 65535)
			this.port = Default_Port;
		else
			this.port = port;
		this.username = username.trim();
		this.password = password;
	}
	
	public Connection getConnection() throws SQLException {
		String url;
		try {
			Class.forName(Driver);
			url = Subprotocol + server + ":" + Integer.toString(port);// + "/" + selectedDatabase;
			return DriverManager.getConnection(url, username, new String (password));
		} catch (ClassNotFoundException e) {
			logger.log(Level.FATAL, e.getLocalizedMessage());
		}
		return null;
	}

	@Override
	public List<Table> getTables() throws SQLException {
		List<Table> tables = new ArrayList<Table>();
		List<Column> columns;
		Table table;
		String tableName;
		
		Statement tablesStatement;
		Connection connection = getConnection();
		tablesStatement = connection.createStatement();
		String queryString = "SELECT table_name FROM information_schema.tables where table_schema='" + this.name + "'";
        ResultSet rs = tablesStatement.executeQuery(queryString);
        while (rs.next()) {
        	tableName = rs.getString(1);
        	columns = getColumns(tableName);
        	
        	table = new Table(tableName, columns);
        	tables.add(table);
        }
        tablesStatement.close();
        connection.close();
		return tables;
	}

	@Override
	public List<Column> getColumns(String tableName) {
		String nombre, tipo_dato;
		int tam_dato;
		DatabaseMetaData metadata;
		Connection connection;
		Column column;
		List<Column> columns = new ArrayList<Column>();
		
		try {
			connection = getConnection();
			metadata = connection.getMetaData();
			ResultSet resultSet = metadata.getColumns(name, null, tableName, null);
		    while (resultSet.next()) {
		    	nombre = resultSet.getString("COLUMN_NAME");
		    	tipo_dato = resultSet.getString("TYPE_NAME");
		    	tam_dato = resultSet.getInt("COLUMN_SIZE");

		      column = new Column(nombre, tipo_dato, tam_dato);
		      columns.add(column);
		    }
		    resultSet.close();
		    connection.close();
		} catch (SQLException e) {
			logger.log(Level.ERROR, e.getLocalizedMessage());
		}
		return columns;
	}

	@Override
	public Database getDatabaseInformation() throws SQLException {
		List<Table> tables = getTables();
		Database db = new Database(name, server, "MySQL", tables);
		return db;
	}

	@Override
	public List<String> getDatabasesOnThisServer() throws SQLException {
		List<String> databases = new ArrayList<String>();
		
		Connection con = getConnection();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet rs = meta.getCatalogs();
		while (rs.next()) {
			databases.add(rs.getString("TABLE_CAT"));
		}
		rs.close();
		con.close();
		return databases;
	}

	/**
	 * Sets the name for the database. The constructor of Database will only setup the connection so you should
	 * indicate which is the database name (More than one database could be active on that connection)
	 * @param name  name of the database to use
	 * @return  <code>True</code> if a database with that name exists on that server <br>
	 * 			<code>False</code> if a database with that name doesn't exists on that server
	 */
	@Override
	public boolean setName(String name) {
		List<String> databases;
		try {
			databases = getDatabasesOnThisServer();
		} catch (SQLException e) {
			return false;
		}
		for (String currentDatabase : databases)
		{
			if (currentDatabase.equals(name))
			{
				this.name = name;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
}
