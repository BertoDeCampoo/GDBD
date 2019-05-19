package es.uneatlantico.gdbd.dataaccess;

import es.uneatlantico.gdbd.entities.*;

/**
 * Implementation of MySQL database connector
 * @author Alberto Gutiérrez Arroyo
 */
public class MySQLDatabase implements IDatabase {

	/**
	 * JDBC MySQL base path to create connections
	 */
	private static final String Subprotocol = "jdbc:mysql://";
	/**
	 * JDBC driver class
	 */
	private static final String Driver = "com.mysql.jdbc.Driver";
	/**
	 * Default MySQL port
	 */
	private static final int Default_Port = 3306;
	private String server;
	private String selectedDatabase;
	private String username;
	private int port;
	private char[] password;

	public String getSelectedDatabase() {
		return this.selectedDatabase;
	}

	/**
	 * Default constructor of MySQL Database Connector. As the port is not defined, it will use the default
	 * MySQL port (3306)
	 * @param server database server address
	 * @param database name of the database on the server
	 * @param username user name to login on the database server
	 * @param password password for the given username
	 */
	public MySQLDatabase(String server, String database, String username, char[] password) {
		// TODO - implement MySQLDatabase.MySQLDatabase
		throw new UnsupportedOperationException();
	}

	/**
	 * Constructor of MySQL Database Connector (It admits a custom port)
	 * @param server database server address
	 * @param port port the connection will use
	 * @param username user name to login on the database server
	 * @param password password for the given username
	 */
	public MySQLDatabase(String server, int port, String username, char[] password) {
		// TODO - implement MySQLDatabase.MySQLDatabase
		throw new UnsupportedOperationException();
	}

	public java.sql.Connection getConnection() throws java.sql.SQLException {
		// TODO - implement MySQLDatabase.getConnection
		throw new UnsupportedOperationException();
	}

	public java.util.List<String> getTableNames() {
		// TODO - implement MySQLDatabase.getTableNames
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param databaseName
	 */
	@Override
	public java.util.List<Table> getTables(String databaseName) {
		// TODO - implement MySQLDatabase.getTables
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param tableName
	 */
	@Override
	public java.util.List<Column> getColumns(String tableName) {
		// TODO - implement MySQLDatabase.getColumns
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param databaseName
	 */
	@Override
	public Database getDatabaseInformation(String databaseName) {
		// TODO - implement MySQLDatabase.getDatabaseInformation
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param database
	 */
	@Override
	public boolean selectDatabase(String database) {
		// TODO - implement MySQLDatabase.selectDatabase
		throw new UnsupportedOperationException();
	}

	@Override
	public java.util.List<String> getDatabases() {
		// TODO - implement MySQLDatabase.getDatabases
		throw new UnsupportedOperationException();
	}

}