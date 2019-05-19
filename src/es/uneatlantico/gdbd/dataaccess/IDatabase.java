package es.uneatlantico.gdbd.dataaccess;

import es.uneatlantico.gdbd.entities.*;

/**
 * @author Alberto Gutiérrez Arroyo
 */
public interface IDatabase {

	/**
	 * Getter for the <code>database</code>. It should return the name of the database to work with from this server
	 * @return  the current selected database on the server
	 */
	String getSelectedDatabase();

	/**
	 * Obtains a list with the databases on this server
	 * @return  a list with the databases on the server
	 */
	java.util.List<String> getDatabases();

	/**
	 * Obtains the connection to the database
	 * @return the connection to the database
	 * @throws SQLException  if the database can't be reached
	 */
	java.sql.Connection getConnection() throws java.sql.SQLException;

	/**
	 * Selects the database with the given name on the server
	 * @param database name of the database to use
	 * @return <code>true</code> if the database can be selected<br> <code>false</code> if there is no database with the given name on the server
	 */
	boolean selectDatabase(String database);

	/**
	 * Obtains the name of the tables on the given database
	 * @param databaseName the name of the database from wich to get the tables
	 * @return the name of the tables of the database
	 */
	java.util.List<Table> getTables(String databaseName);

	/**
	 * Obtains the columns of the given table
	 * @param tableName name of the table from which to retrieve the columns
	 * @return a list with the names of the columns of the selected database
	 */
	java.util.List<Column> getColumns(String tableName);

	/**
	 * Obtains the name of the tables and the columns of each table
	 * @param databaseName the name of the database from which to retrieve information
	 * @return a Database entity with all the information of the current database
	 */
	Database getDatabaseInformation(String databaseName);

}