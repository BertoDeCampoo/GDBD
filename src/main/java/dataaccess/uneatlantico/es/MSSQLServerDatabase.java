/**
 * 
 */
package dataaccess.uneatlantico.es;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  Check: <br> <a href="https://www.databasejournal.com/features/mssql/article.php/3587906/System-Tables-and-Catalog-Views.htm">databasejournal.com</a>
 *	<br>
 *	<a href="https://docs.microsoft.com/en-us/sql/connect/jdbc/building-the-connection-url?view=sql-server-2017">docs.microsoft.com</a>
 *
 * @author Alberto Gutiérrez Arroyo
 * 
 */
public class MSSQLServerDatabase implements IDatabase {
	
	/**
	 * JDBC Microsoft SQL Server/SQL Server Express subprotocol to create JDBC connections
	 */
	private final static String Subprotocol = "jdbc:sqlserver://";
	/**
	 * JDBC driver class
	 */
	private final static String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	/**
	 * Default MySQL port
	 */
	private final static int Default_Port = 1433;	
	private String server, database, username;
	private int port;
	private char[] password;
	
	/**
	 * Constructor of Microsoft SQL Server/SQL Server Express Database Connector
	 * @param server  server address. It can be a DNS, an IP Address or a localhost (127.0.0.1 for local PC)
	 * @param database  instance name of the database
	 * @param username  user name to login on the database server
	 * @param password  password for the given username
	 */
	public MSSQLServerDatabase(String server, String database, String username, char[] password)
	{
		this (server, -1, database, username, password);
	}
	
	/**
	 * Constructor of Microsoft SQL Server/SQL Server Express Database Connector (It admits a custom port)
	 * @param server  server address. It can be a DNS, an IP Address or a localhost (127.0.0.1 for local PC)
	 * @param port  port used to stablish connection with the server
	 * @param database  instance name of the database
	 * @param username  user name to login on the database server
	 * @param password  password for the given username
	 */
	public MSSQLServerDatabase(String server, int port, String database, String username, char[] password)
	{
		this.server = server.trim();
		if (port >= 65535)
			this.port = Default_Port;
		else if (port > 0 && port < 65535)
			this.port = port;
		this.database = database.trim();
		this.username = username.trim();
		this.password = password;
	}
	
	public Connection getConnection() throws SQLException {
		//MSSQLServerDatabase("jdbc:sqlserver://DESKTOP-M9PG788\\SQLEXPRESS", "sa", pass);
		String url;
		try {
			Class.forName(Driver);
			url = Subprotocol + server;
			if (port > 0)
				url += ":" + Integer.toString(port);
			if (database != "")
				url += "\\" + database;
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
			String queryString = "select * from sysobjects where type='u'";
	        ResultSet rs = statement.executeQuery(queryString);
	        while (rs.next()) {
	        	tableNames.add(rs.getString(1));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableNames;
	}

	public List<String> getTableColumnNames(String tableName) {
		DatabaseMetaData metadata;
		List<String> tableNames = new ArrayList<String>();
		
		try {
			metadata = getConnection().getMetaData();
			ResultSet resultSet = metadata.getColumns(null, null, tableName, null);
		    while (resultSet.next()) {
		      String name = resultSet.getString("COLUMN_NAME");
//		      String type = resultSet.getString("TYPE_NAME");
//		      int size = resultSet.getInt("COLUMN_SIZE");

//		      System.out.println("Column name: [" + name + "]; type: [" + type + "]; size: [" + size + "]");
		      tableNames.add(name);
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableNames;
	}

	public Map<String, Map<String, String>> getTableAndColumnNames() {
		// TODO Auto-generated method stub
		return null;
	}

	

	

}
