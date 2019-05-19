package es.uneatlantico.gdbd.dataaccess;

import es.uneatlantico.gdbd.entities.*;

/**
 * Implementation of PostgreSQL database connector
 * @author Alberto Gutiérrez Arroyo
 */
public class PostgreSQLDatabase implements IDatabase {

	/**
	 * JDBC PostgreSQL subprotocol to create JDBC connections
	 */
	private static final String Subprotocol = "jdbc:postgresql://";
	/**
	 * JDBC driver class
	 */
	private static final String Driver = "org.postgresql.Driver";
	/**
	 * Default PostgreSQL Server port
	 */
	private static final int Default_Port = 5432;
	private String server;
	private String selectedDatabase;
	private String username;
	private int port;
	private char[] password;

	public String getSelectedDatabase() {
		return this.selectedDatabase;
	}

	/**
	 * Constructor of PostgreSQL Database Connector
	 * @param server server address. It can be a DNS, an IP Address or a localhost (127.0.0.1 for local PC)
	 * @param port port used to stablish connection with the server
	 * @param username user name to login on the database server
	 * @param password password for the given username
	 */
	public PostgreSQLDatabase(String server, int port, String username, char[] password) {
		// TODO - implement PostgreSQLDatabase.PostgreSQLDatabase
		throw new UnsupportedOperationException();
	}

	@Override
	public java.sql.Connection getConnection() throws java.sql.SQLException {
		// TODO - implement PostgreSQLDatabase.getConnection
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param database
	 */
	@Override
	public boolean selectDatabase(String database) {
		// TODO - implement PostgreSQLDatabase.selectDatabase
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param databaseName
	 */
	@Override
	public java.util.List<Table> getTables(String databaseName) {
		// TODO - implement PostgreSQLDatabase.getTables
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param tableName
	 */
	@Override
	public java.util.List<Column> getColumns(String tableName) {
		// TODO - implement PostgreSQLDatabase.getColumns
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param databaseName
	 */
	@Override
	public Database getDatabaseInformation(String databaseName) {
		// TODO - implement PostgreSQLDatabase.getDatabaseInformation
		throw new UnsupportedOperationException();
	}

	@Override
	public java.util.List<String> getDatabases() {
		// TODO - implement PostgreSQLDatabase.getDatabases
		throw new UnsupportedOperationException();
	}

}