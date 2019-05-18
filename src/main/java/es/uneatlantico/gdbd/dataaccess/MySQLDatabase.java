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

import es.uneatlantico.gdbd.entities.Column;
import es.uneatlantico.gdbd.entities.Database;
import es.uneatlantico.gdbd.entities.Table;

/**
 * Implementation of MySQL database connector
 * @author Alberto Gutiérrez Arroyo
 *
 */
public class MySQLDatabase implements IDatabase {
	
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
	private String server, selectedDatabase, username;
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
			System.err.println("Driver error");
		}
		return null;
	}

	public List<String> getTableNames() {
		List<String> tableNames = new ArrayList<String>();
		Statement statement;
		try {
			statement = getConnection().createStatement();
			String queryString = "SELECT table_name FROM information_schema.tables where table_schema='" + selectedDatabase + "'";
	        ResultSet rs = statement.executeQuery(queryString);
	        while (rs.next()) {
	        	tableNames.add(rs.getString(1));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableNames;
	}

	@Override
	public List<Table> getTables(String databaseName) {
		List<Table> tables = new ArrayList<Table>();
		List<Column> columns;
		Table table;
		String tableName;
		
		Statement tablesStatement;
		Connection connection;
		try {
			connection = getConnection();
			tablesStatement = connection.createStatement();
			String queryString = "SELECT table_name FROM information_schema.tables where table_schema='" + databaseName + "'";
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
			e.printStackTrace();
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
			ResultSet resultSet = metadata.getColumns(selectedDatabase, null, tableName, null);
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
			e.printStackTrace();
		}
		return columns;
	}

	@Override
	public Database getDatabaseInformation(String databaseName) {
		List<Table> tables = getTables(databaseName);
		Database db = new Database(selectedDatabase, server, "MySQL", tables);
		return db;
	}

	@Override
	public boolean selectDatabase(String database)
	{
		List<String> databases = getDatabases();
		for (String currentDatabase : databases)
		{
			if (currentDatabase.equals(database))
			{
				this.selectedDatabase = database;
				return true;
			}
		}
		return false;	
	}

	@Override
	public String getSelectedDatabase() {
		return this.selectedDatabase;
	}

	@Override
	public List<String> getDatabases() {
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
			return new ArrayList<String>();
		}
		return databases;
	}

}
