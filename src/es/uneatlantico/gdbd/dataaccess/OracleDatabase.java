package es.uneatlantico.gdbd.dataaccess;

import es.uneatlantico.gdbd.entities.*;

/**
 * Implementation of Oracle database connector
 * @author Alberto Gutiérrez Arroyo
 */
public class OracleDatabase implements IDatabase {

	/**
	 * JDBC Oracle subprotocol to create JDBC connections
	 */
	private static final String Subprotocol = "jdbc:oracle:";
	/**
	 * JDBC driver class
	 */
	private static final String Driver = "oracle.jdbc.OracleDriver";
	/**
	 * Default Oracle database port
	 */
	private static final int Default_Port = 1521;
	private String server;
	private String selectedDatabase;
	private String username;
	private int port;
	private char[] password;

	public String getSelectedDatabase() {
		return this.selectedDatabase;
	}

	/**
	 * Constructor of Oracle Database Connector
	 * @param server server address. It can be a DNS, an IP Address or a localhost (127.0.0.1 for local PC)
	 * @param port port used to stablish connection with the server
	 * @param username user name to login on the database server
	 * @param password password for the given username
	 */
	public OracleDatabase(String server, int port, String username, char[] password) {
		// TODO - implement OracleDatabase.OracleDatabase
		throw new UnsupportedOperationException();
	}

	@Override
	public java.sql.Connection getConnection() throws java.sql.SQLException {
		// TODO - implement OracleDatabase.getConnection
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param database
	 */
	@Override
	public boolean selectDatabase(String database) {
		// TODO - implement OracleDatabase.selectDatabase
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param databaseName
	 */
	@Override
	public java.util.List<Table> getTables(String databaseName) {
		// TODO - implement OracleDatabase.getTables
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param tableName
	 */
	@Override
	public java.util.List<Column> getColumns(String tableName) {
		// TODO - implement OracleDatabase.getColumns
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param databaseName
	 */
	@Override
	public Database getDatabaseInformation(String databaseName) {
		// TODO - implement OracleDatabase.getDatabaseInformation
		throw new UnsupportedOperationException();
	}

	@Override
	public java.util.List<String> getDatabases() {
		// TODO - implement OracleDatabase.getDatabases
		throw new UnsupportedOperationException();
	}

}