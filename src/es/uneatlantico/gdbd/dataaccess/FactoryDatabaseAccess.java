package es.uneatlantico.gdbd.dataaccess;

/**
 * @author Alberto Gutiérrez Arroyo
 */
public class FactoryDatabaseAccess {

	/**
	 * It will return a database implementation with the given parameters
	 * @param databaseType type of the database. It must be defined on the enum type <code>DBMS</code>
	 * @param server database server
	 * @param port database TCP port the connection will use
	 * @param username username needed to get access to the database
	 * @param password password for the given username authentication
	 * @return The database instance
	 */
	public static IDatabase getDatabaseAccess(DBMS databaseType, String server, int port, String username, char[] password) {
		// TODO - implement FactoryDatabaseAccess.getDatabaseAccess
		throw new UnsupportedOperationException();
	}

}