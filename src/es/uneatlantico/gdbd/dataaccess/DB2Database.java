package es.uneatlantico.gdbd.dataaccess;

import es.uneatlantico.gdbd.entities.*;

/**
 * Implementation of IBM DB2 database connector
 * @author Alberto Gutiérrez Arroyo
 */
public class DB2Database implements IDatabase {

	/**
	 * JDBC DB2 subprotocol to create JDBC connections
	 */
	private static final String Subprotocol = "jdbc:db2://";
	/**
	 * JDBC driver class
	 */
	private static final String Driver = "com.ibm.db2.jcc.DB2Driver";
	/**
	 * Default Microsoft SQL Server port
	 */
	private static final int Default_Port = 50000;
	private String server;
	private String selectedDatabase;
	private String username;
	private int port;
	private char[] password;

	public String getSelectedDatabase() {
		return this.selectedDatabase;
	}

	/**
	 * Constructor of IBM DB2 Database Connector (It admits a custom port)
	 * @param server server address. It can be a DNS, an IP Address or a localhost (127.0.0.1 for local PC)
	 * @param port port used to stablish connection with the server
	 * @param username user name to login on the database server
	 * @param password password for the given username
	 */
	public DB2Database(String server, int port, String username, char[] password) {
		// TODO - implement DB2Database.DB2Database
		throw new UnsupportedOperationException();
	}

	@Override
	public java.sql.Connection getConnection() throws java.sql.SQLException {
		// TODO - implement DB2Database.getConnection
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param database
	 */
	@Override
	public boolean selectDatabase(String database) {
		// TODO - implement DB2Database.selectDatabase
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param databaseName
	 */
	@Override
	public java.util.List<Table> getTables(String databaseName) {
		// TODO - implement DB2Database.getTables
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param tableName
	 */
	@Override
	public java.util.List<Column> getColumns(String tableName) {
		// TODO - implement DB2Database.getColumns
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param databaseName
	 */
	@Override
	public Database getDatabaseInformation(String databaseName) {
		// TODO - implement DB2Database.getDatabaseInformation
		throw new UnsupportedOperationException();
	}

	@Override
	public java.util.List<String> getDatabases() {
		// TODO - implement DB2Database.getDatabases
		throw new UnsupportedOperationException();
	}

}