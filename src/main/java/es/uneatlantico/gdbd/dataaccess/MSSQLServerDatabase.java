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
 *  Check: <br> <a href="https://www.databasejournal.com/features/mssql/article.php/3587906/System-Tables-and-Catalog-Views.htm">databasejournal.com</a>
 *	<br>
 *	<a href="https://docs.microsoft.com/en-us/sql/connect/jdbc/building-the-connection-url?view=sql-server-2017">docs.microsoft.com</a>
 *
 * @author Alberto Gutiérrez Arroyo
 * 
 */
public class MSSQLServerDatabase implements IDatabase {
	
	private static final Logger logger = LogManager.getLogger(MSSQLServerDatabase.class); 
	/**
	 * JDBC Microsoft SQL Server/SQL Server Express subprotocol to create JDBC connections
	 */
	private final static String Subprotocol = "jdbc:sqlserver://";
	/**
	 * JDBC driver class
	 */
	private final static String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	/**
	 * Default Microsoft SQL Server port
	 */
	private final static int Default_Port = 1433;	
	private String server, name, username;
	private int port;
	private char[] password;
	
	/**
	 * Constructor of Microsoft SQL Server/SQL Server Express Database Connector (It admits a custom port)
	 * @param server  server address. It can be a DNS, an IP Address or a localhost (127.0.0.1 for local PC)
	 * @param port  port used to stablish connection with the server
	 * @param username  user name to login on the database server
	 * @param password  password for the given username
	 */
	public MSSQLServerDatabase(String server, int port, String username, char[] password)
	{		
		this.server = server.trim();
		if (port >= 65535)
			this.port = Default_Port;
		else
			this.port = port;
		this.username = username.trim();
		this.password = password;
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		//MSSQLServerDatabase("jdbc:sqlserver://DESKTOP-M9PG788\\SQLEXPRESS", "sa", pass);
		String url;
		try {
			Class.forName(Driver);
			url = Subprotocol + server;
			if (port > 0)
				url += ":" + Integer.toString(port);
			return DriverManager.getConnection(url, username, new String (password));
		} catch (ClassNotFoundException e) {
			logger.log(Level.FATAL, e.getLocalizedMessage());
		}
		return null;
	}
	
	@Override
	public List<Table> getTables()
	{
		List<Table> tables = new ArrayList<Table>();
		List<Column> columns;
		Table table;
		String tableName;
		
		Statement tablesStatement;
		Connection connection;
		try {
			connection = getConnection();
			tablesStatement = connection.createStatement();
//			String queryString = "SELECT * FROM SYSOBJECTS WHERE TYPE='u'";
			String queryString = "SELECT TABLE_NAME " + 
					"FROM INFORMATION_SCHEMA.TABLES " + 
					"WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_CATALOG='" + this.name + "'";
	        ResultSet rs = tablesStatement.executeQuery(queryString);
	        while (rs.next()) {
	        	tableName = rs.getString(1);
	        	columns = getColumns(tableName);
	        	
	        	table = new Table(tableName, columns);
	        	tables.add(table);
	        }
	        tablesStatement.close();
	        connection.close();
		} catch (SQLException e) {
			logger.log(Level.ERROR, e.getLocalizedMessage());
			return new ArrayList<Table>();
		}
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
			ResultSet resultSet = metadata.getColumns(null, null, tableName, null);
		    while (resultSet.next()) {
		    	nombre = resultSet.getString("COLUMN_NAME");
		    	tipo_dato = resultSet.getString("TYPE_NAME");
		    	tam_dato = resultSet.getInt("COLUMN_SIZE");

		      column = new Column(nombre, tipo_dato, tam_dato);
		      columns.add(column);
		    }
		    connection.close();
		} catch (SQLException e) {
			logger.log(Level.ERROR, e.getLocalizedMessage());
			return new ArrayList<Column>();
		}
		return columns;
	}

	public Database getDatabaseInformation()
	{
		List<Table> tables = getTables();
		Database db = new Database(name, server, "Microsoft SQL Server", tables);
		return db;
	}
	
	public List<String> getDatabasesOnThisServer()
	{
		List<String> databases = new ArrayList<String>();
		
		Connection con = null;
	    try {
    		con = getConnection();
			DatabaseMetaData meta = con.getMetaData();
			ResultSet rs = meta.getCatalogs();
			while (rs.next()) {
				databases.add(rs.getString("TABLE_CAT"));
			}
			rs.close();
			con.close();
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getLocalizedMessage());
			return new ArrayList<String>();
		}
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
		List<String> databases = getDatabasesOnThisServer();
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
