/**
 * 
 */
package dataaccess.uneatlantico.es;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @author Alberto Guti�rrez Arroyo
 *
 */
public interface IDatabase {
		
	/**
	 * Connects to the database
	 * @param connection
	 * @param username
	 * @param password
	 */
	public void getConnection(String connection, String username, String password);
	
	/**
	 * Obtains the name of the tables of the selected database
	 * @return the name of the tables of the selected database
	 */
	public List<String> getTableNames();
	
	/**
	 * Obtains the name of the columns on the given table of the selected database
	 * @param tableName  the name of the table from which to get the columns
	 * @return the name of the columns of the table
	 */
	public List<String> getTableColumnNames(String tableName);
	
	/**
	 * Obtains the name of the tables and the columns of each table
	 * @return a map containing a String with the name of the table and another map with the name and type of the column
	 */
	public Map<String, Map<String,String>> getTableAndColumnNames();

}