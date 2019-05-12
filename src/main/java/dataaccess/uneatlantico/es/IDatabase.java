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
 * @author Alberto Gutiérrez Arroyo
 *
 */
public interface IDatabase {
		
	/**
	 * Obtains the connection to the database
	 * @return  the connection to the database
	 * @throws SQLException  if the database can't be reached
	 */
	public Connection getConnection() throws SQLException;
	
	/**
	 * Selects the database with the given name on the server
	 * @param database  name of the database to use
	 * @return  <code>true</code> if the database can be selected<br>
	 * <code>false</code> if there is no database with the given name on the server
	 */
	public boolean selectDatabase(String database);
	
	/**
	 * Getter for the <code>database</code>. It should return the name of the database to work with from this server
	 * @return  the current selected database on the server
	 */
	public String getSelectedDatabase();
	
	/**
	 * Obtains the name of the tables on the given database
	 * @param  databaseName  the name of the database from wich to get the tables
	 * @return the name of the tables of the database
	 */
	public List<Table> getTables(String databaseName);
	
	/**
	 * Obtains the columns of the given table
	 * @param tableName  name of the table from which to retrieve the columns
	 * @return a list with the names of the columns of the selected database
	 */
	public List<Column> getColumns(String tableName);
	
	/**
	 * Obtains the name of the tables and the columns of each table
	 * @param databaseName  the name of the database from which to retrieve information
	 * @return  a Database entity with all the information of the current database
	 */
	public Database getDatabaseInformation(String databaseName);
	
	/**
	 * Obtains a list with the databases on this server
	 * @return  a list with the databases on the server
	 */
	public List<String> getDatabases();
}
