package es.uneatlantico.gdbd.dataaccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import es.uneatlantico.gdbd.entities.Column;
import es.uneatlantico.gdbd.entities.Database;
import es.uneatlantico.gdbd.entities.Table;

/**
 * Implementation of PostgreSQL database connector
 * @author Alberto Guti�rrez Arroyo
 *
 */
public class PostgreSQLDatabase implements IDatabase {
	
	/**
	 * JDBC PostgreSQL subprotocol to create JDBC connections
	 */
	private final static String Subprotocol = "jdbc:postgresql://";
	/**
	 * JDBC driver class
	 */
	private final static String Driver = "org.postgresql.Driver";
	/**
	 * Default PostgreSQL Server port
	 */
	private final static int Default_Port = 5432;	
	private String server, selectedDatabase, username;
	private int port;
	private char[] password;
	
	/**
	 * Constructor of PostgreSQL Database Connector
	 * @param server  server address. It can be a DNS, an IP Address or a localhost (127.0.0.1 for local PC)
	 * @param port  port used to stablish connection with the server
	 * @param username  user name to login on the database server
	 * @param password  password for the given username
	 */
	public PostgreSQLDatabase(String server, int port, String username, char[] password)
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
	public boolean setName(String database) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Table> getTables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Column> getColumns(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Database getDatabaseInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getDatabasesOnThisServer() {
		// TODO Auto-generated method stub
		return null;
	}

}
