package es.uneatlantico.gdbd.dataaccess;

import es.uneatlantico.gdbd.entities.*;

/**
 * Check: <br> <a href="https://www.databasejournal.com/features/mssql/article.php/3587906/System-Tables-and-Catalog-Views.htm">databasejournal.com</a>
 * <br>
 * <a href="https://docs.microsoft.com/en-us/sql/connect/jdbc/building-the-connection-url?view=sql-server-2017">docs.microsoft.com</a>
 * 
 * @author Alberto Gutiérrez Arroyo
 */
public class MSSQLServerDatabase implements IDatabase {

	/**
	 * JDBC Microsoft SQL Server/SQL Server Express subprotocol to create JDBC connections
	 */
	private static final String Subprotocol = "jdbc:sqlserver://";
	/**
	 * JDBC driver class
	 */
	private static final String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	/**
	 * Default Microsoft SQL Server port
	 */
	private static final int Default_Port = 1433;
	private String server;
	private String selectedDatabase;
	private String username;
	private int port;
	private char[] password;

	public String getSelectedDatabase() {
		return this.selectedDatabase;
	}

	/**
	 * Constructor of Microsoft SQL Server/SQL Server Express Database Connector (It admits a custom port)
	 * @param server server address. It can be a DNS, an IP Address or a localhost (127.0.0.1 for local PC)
	 * @param port port used to stablish connection with the server
	 * @param username user name to login on the database server
	 * @param password password for the given username
	 */
	public MSSQLServerDatabase(String server, int port, String username, char[] password) {
		// TODO - implement MSSQLServerDatabase.MSSQLServerDatabase
		throw new UnsupportedOperationException();
	}

	/**
	 * Constructor of Microsoft SQL Server/SQL Server Express Database Connector
	 * @param server server address. It can be a DNS, an IP Address or a localhost (127.0.0.1 for local PC)
	 * @param username user name to login on the database server
	 * @param password password for the given username
	 */
	public MSSQLServerDatabase(String server, String username, char[] password) {
		// TODO - implement MSSQLServerDatabase.MSSQLServerDatabase
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param database
	 */
	@Override
	public boolean selectDatabase(String database) {
		// TODO - implement MSSQLServerDatabase.selectDatabase
		throw new UnsupportedOperationException();
	}

	@Override
	public java.sql.Connection getConnection() throws java.sql.SQLException {
		// TODO - implement MSSQLServerDatabase.getConnection
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param databaseName
	 */
	@Override
	public java.util.List<Table> getTables(String databaseName) {
		// TODO - implement MSSQLServerDatabase.getTables
		throw new UnsupportedOperationException();
	}

	public java.util.List<Table> getTablesOld() {
		// TODO - implement MSSQLServerDatabase.getTablesOld
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param tableName
	 */
	@Override
	public java.util.List<Column> getColumns(String tableName) {
		// TODO - implement MSSQLServerDatabase.getColumns
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param databaseName
	 */
	public Database getDatabaseInformation(String databaseName) {
		// TODO - implement MSSQLServerDatabase.getDatabaseInformation
		throw new UnsupportedOperationException();
	}

	public java.util.List<String> getDatabases() {
		// TODO - implement MSSQLServerDatabase.getDatabases
		throw new UnsupportedOperationException();
	}

}