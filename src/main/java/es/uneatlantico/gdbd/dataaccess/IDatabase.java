/**
 * 
 */
package es.uneatlantico.gdbd.dataaccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import es.uneatlantico.gdbd.entities.Column;
import es.uneatlantico.gdbd.entities.Database;
import es.uneatlantico.gdbd.entities.Table;

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
	 * Sets the name for the database.
	 * @param name  name of the database
	 * @return  <code>True</code> if a database with that name exists on that server
	 * 			<code>False</code> if a database with that name doesn't exists on that server
	 */
	public boolean setName(String name);
	
	/**
	 * Getter for the <code>database</code>. It should return the name of the database to work with from this server
	 * @return  the current selected database on the server
	 */
	public String getName();
	
	/**
	 * Obtains the name of the tables on the given database
	 * @param  databaseName  the name of the database from wich to get the tables
	 * @return the name of the tables of the database
	 * @throws SQLException  if it is not possible to get the tables of the selected database
	 */
	public List<Table> getTables() throws SQLException;
	
	/**
	 * Obtains the columns of the given table
	 * @param tableName  name of the table from which to retrieve the columns
	 * @return a list with the names of the columns of the selected database
	 * @throws SQLException  if it isn't possible to obtain the columns
	 */
	public List<Column> getColumns(String tableName) throws SQLException;
	
	/**
	 * Obtains the name of the tables and the columns of each table
	 * @return  a Database entity with all the information of the current database
	 * @throws SQLException  if it isn't possible to get the database information
	 */
	public Database getDatabaseInformation() throws SQLException;
	
	/**
	 * Obtains a list with the databases on this server
	 * @return  a list with the databases on the server
	 * @throws SQLException  if the databases can't be obtained
	 */
	public List<String> getDatabasesOnThisServer() throws SQLException;
}
