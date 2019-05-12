package dataaccess.uneatlantico.es;

/**
 * @author Alberto Gutiérrez Arroyo
 *
 */
public class FactoryDatabaseAccess {
	
	/**
	 * It will return a database implementation with the given parameters
	 * @param databaseType  type of the database. It must be defined on the enum type <code>DBMS</code>
	 * @param server  database server
	 * @param port  database TCP port the connection will use
	 * @param database  name of the database in the database server
	 * @param username  username needed to get access to the database
	 * @param password  password for the given username authentication
	 * @return  The database instance
	 */
	public static IDatabase GetDatabaseAccess(DBMS databaseType, String server, int port, String database, String username, char[] password)
	{
		if (databaseType == DBMS.MSSQL)
			return new MSSQLServerDatabase(server, port, database, username, password);
		if (databaseType == DBMS.MySQL)
			return new MySQLDatabase (server, port, database, username, password);
		return null;
	}

}
