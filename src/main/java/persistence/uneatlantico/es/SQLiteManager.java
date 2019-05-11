package persistence.uneatlantico.es;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import entities.uneatlantico.es.Database;
import util.uneatlantico.es.PathResolver;

public class SQLiteManager {
	
	/**
	 * Default name for the SQLite Database the application will use
	 */
	private final static String Default_Filename = "db.db";
	private String filename;
	
	public SQLiteManager (String filename)
	{
		if (!(filename.length()>0))
		{
			this.filename = Default_Filename;
		}
		if (!(filename.endsWith(".db") || filename.endsWith(".sqlite")))
		{
			this.filename = filename + ".db";
		}
	}
	
	/**
	 * Obtains the URL to access the SQLite database
	 * @return  URL used to connect to the SQLite database
	 */
	public String getDatabaseUrl()
	{
		String url = "jdbc:sqlite:";
		url += PathResolver.getCurrentDirectory();
		url += this.filename;
		return url;
	}
	
	/**
	 * Obtains the connection to the SQLite Database
	 * @return  the Connection
	 * @throws SQLException  if the connection is not possible
	 */
	public Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(getDatabaseUrl());
	}
	
	/**
	 * Initializes the SQLite Database if it doesn't exist
	 */
	public void createDatabase()
	{		
		Connection conn = null;
		Statement stmt = null;
        try  {
        	conn = DriverManager.getConnection(getDatabaseUrl());
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                
                System.out.println(getDatabaseUrl());
                stmt = conn.createStatement();

                StringBuilder sql = new StringBuilder();
                sql.append ("CREATE TABLE IF NOT EXISTS BBDD (");
                sql.append ("  ID          INTEGER NOT NULL PRIMARY KEY, ");
                sql.append ("  NOMBRE      varchar(255) NOT NULL, ");
                sql.append ("  DESCRIPCION varchar(255), ");
                sql.append ("  SERVIDOR    varchar(255));");

                sql.append ("CREATE TABLE TABLAS (");
                sql.append ("  ID          INTEGER NOT NULL PRIMARY KEY, ");
                sql.append ("  NOMBRE      varchar(255) NOT NULL, ");
                sql.append ("  DESCRIPCION varchar(255), ");
                sql.append ("  ID_BBDD     integer(10) NOT NULL, ");
                sql.append ("  FOREIGN KEY(ID_BBDD) REFERENCES BBDD(ID));");
                
                sql.append ("CREATE TABLE COLUMNAS (");
                sql.append ("  ID          INTEGER NOT NULL PRIMARY KEY, ");
                sql.append ("  NOMBRE      varchar(255) NOT NULL, ");
                sql.append ("  TIPO_DATO   varchar(255) NOT NULL, ");
                sql.append ("  TAM_DATO    integer(10) NOT NULL, ");
                sql.append ("  DESCRIPCION varchar(255), ");
                sql.append ("  ID_TABLA    integer(10) NOT NULL, ");
                sql.append ("  FOREIGN KEY(ID_TABLA) REFERENCES TABLAS(ID));");
                
                sql.append ("CREATE UNIQUE INDEX BBDD_ID ");
                sql.append ("  ON BBDD (ID);");

                stmt.executeUpdate(sql.toString());

                stmt.close();

                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	public void SaveToDatabase(Database db)
	{
		
	}
}
