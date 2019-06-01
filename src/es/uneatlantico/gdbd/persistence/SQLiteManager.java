package es.uneatlantico.gdbd.persistence;

import es.uneatlantico.gdbd.entities.*;
import es.uneatlantico.gdbd.util.Configuration;

public class SQLiteManager {

	private static final String Driver = "org.sqlite.JDBC";
	/**
	 * Default name for the SQLite Database the application will use
	 */
	public static final String Default_Filename = Configuration.getSQLiteFilename();
	private String filename;

	/**
	 * 
	 * @param filename
	 */
	public SQLiteManager(String filename) {
		// TODO - implement SQLiteManager.SQLiteManager
		throw new UnsupportedOperationException();
	}

	/**
	 * Obtains the URL to access the SQLite database
	 * @return URL used to connect to the SQLite database
	 */
	public String getDatabaseUrl() {
		// TODO - implement SQLiteManager.getDatabaseUrl
		throw new UnsupportedOperationException();
	}

	/**
	 * Obtains the connection to the SQLite Database
	 * @return the Connection
	 * @throws SQLException  if the connection is not possible
	 */
	public java.sql.Connection getConnection() throws java.sql.SQLException {
		// TODO - implement SQLiteManager.getConnection
		throw new UnsupportedOperationException();
	}

	/**
	 * Initializes the SQLite Database if it doesn't exist
	 */
	public void initializeDatabase() {
		// TODO - implement SQLiteManager.initializeDatabase
		throw new UnsupportedOperationException();
	}

	/**
	 * Creates a new database entry on the SQLite database
	 * @param db Database entity (Contains the metadata of the database)
	 */
	public void addNewDatabase(Database db) {
		// TODO - implement SQLiteManager.addNewDatabase
		throw new UnsupportedOperationException();
	}

	/**
	 * Auxiliar function to add the database entity on the SQLite database
	 * @param db
	 */
	private void createDatabase(Database db) {
		// TODO - implement SQLiteManager.createDatabase
		throw new UnsupportedOperationException();
	}

	/**
	 * Auxiliar function to add a new table entity on the SQLite database
	 * @param table table to add (Metadata)
	 * @param ID_BBDD database parent of this table
	 */
	private void createTable(Table table, int ID_BBDD) {
		// TODO - implement SQLiteManager.createTable
		throw new UnsupportedOperationException();
	}

	/**
	 * Auxiliar function to add a new column entity on the SQLite database
	 * @param column column to add
	 * @param ID_TABLA ID of the parent table of this column
	 */
	private void createColumn(Column column, int ID_TABLA) {
		// TODO - implement SQLiteManager.createColumn
		throw new UnsupportedOperationException();
	}

	/**
	 * Obtains the next available ID (Primary key) for the given table
	 * @param tableName table to obtain the available ID
	 * @return next free ID on the given table
	 */
	private int getNextAvailableID(String tableName) {
		// TODO - implement SQLiteManager.getNextAvailableID
		throw new UnsupportedOperationException();
	}

}