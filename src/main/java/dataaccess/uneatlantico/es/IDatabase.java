/**
 * 
 */
package dataaccess.uneatlantico.es;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import entities.uneatlantico.es.Column;
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
	 * Obtains the columns of the given table
	 * @param tableName  name of the table from which to retrieve the columns
	 * @return a list with the names of the columns of the selected database
	 */
	public List<Column> getColumns(String tableName);
	
	/**
	 * Obtains the name of the columns on the given table of the selected database
	 * @return the name of the tables of the database
	 */
	public List<Table> getTables();
	
	/**
	 * Obtains the name of the tables and the columns of each table
	 * @return a map containing a String with the name of the table and another map with the name and type of the column
	 */
	//public Map<String, Map<String,String>> getTableAndColumnNames();

}
