/**
 * 
 */
package dataaccess.uneatlantico.es;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  Check: <br> <a href="https://www.databasejournal.com/features/mssql/article.php/3587906/System-Tables-and-Catalog-Views.htm">databasejournal.com</a>
 *	<br>
 *	<a href="https://docs.microsoft.com/en-us/sql/connect/jdbc/building-the-connection-url?view=sql-server-2017">docs.microsoft.com</a>
 *
 * @author Alberto Gutiérrez Arroyo
 * 
 */
public class MSSQLServerDatabase implements IDatabase {
	
	private Connection connection;

	public void getConnection(String connection, String username, String password) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			this.connection = DriverManager.getConnection(connection, username, password);
		} catch(SQLException sqle) {
			System.err.println("Error SQL");
			sqle.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("Error de driver");
			e.printStackTrace();
		}
	}
	
	public List<String> getTableNames() {
		List<String> tableNames = new ArrayList<String>();
		Statement statement;
		try {
			statement = this.connection.createStatement();
			String queryString = "select * from sysobjects where type='u'";
	        ResultSet rs = statement.executeQuery(queryString);
	        while (rs.next()) {
	        	tableNames.add(rs.getString(1));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableNames;
	}

	public List<String> getTableColumnNames(String tableName) {
		DatabaseMetaData metadata;
		List<String> tableNames = new ArrayList<String>();
		
		try {
			metadata = connection.getMetaData();
			ResultSet resultSet = metadata.getColumns(null, null, tableName, null);
		    while (resultSet.next()) {
		      String name = resultSet.getString("COLUMN_NAME");
		      String type = resultSet.getString("TYPE_NAME");
		      int size = resultSet.getInt("COLUMN_SIZE");

//		      System.out.println("Column name: [" + name + "]; type: [" + type + "]; size: [" + size + "]");
		      tableNames.add(name);
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableNames;
	}

	public Map<String, Map<String, String>> getTableAndColumnNames() {
		// TODO Auto-generated method stub
		return null;
	}

	

	

}
