/**
 * 
 */
package dataaccess.uneatlantico.es;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import entities.uneatlantico.es.Column;
import entities.uneatlantico.es.Database;
import entities.uneatlantico.es.Table;

/**
 * Implementation of Oracle database connector
 * @author Alberto Gutiérrez Arroyo
 *
 */
public class OracleDatabase implements IDatabase {
	
	/**
	 * JDBC Oracle subprotocol to create JDBC connections
	 */
	private final static String Subprotocol = "jdbc:oracle:";
	/**
	 * JDBC driver class
	 */
	private final static String Driver = "oracle.jdbc.OracleDriver";
	/**
	 * Default Oracle database port
	 */
	private final static int Default_Port = 1521;	
	private String server, selectedDatabase, username;
	private int port;
	private char[] password;
	
	/**
	 * Constructor of Oracle Database Connector
	 * @param server  server address. It can be a DNS, an IP Address or a localhost (127.0.0.1 for local PC)
	 * @param port  port used to stablish connection with the server
	 * @param username  user name to login on the database server
	 * @param password  password for the given username
	 */
	public OracleDatabase(String server, int port, String username, char[] password)
	{
		this.server = server.trim();
		if (port >= 65535)
			this.port = Default_Port;
		else if (port > 0 && port < 65535)
			this.port = port;
		this.username = username.trim();
		this.password = password;
	}

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean selectDatabase(String database) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSelectedDatabase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Table> getTables(String databaseName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Column> getColumns(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Database getDatabaseInformation(String databaseName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getDatabases() {
		// TODO Auto-generated method stub
		return null;
	}

}
