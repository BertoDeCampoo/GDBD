package es.uneatlantico.gdbd.persistence;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.uneatlantico.gdbd.entities.Column;
import es.uneatlantico.gdbd.entities.Database;
import es.uneatlantico.gdbd.entities.Table;
import es.uneatlantico.gdbd.util.PathResolver;
import es.uneatlantico.gdbd.util.StringDotsRemover;

public class SQLiteManager {
	
	private static final Logger logger = LogManager.getLogger(SQLiteManager.class); 
	
	private final static String Driver = "org.sqlite.JDBC";
	/**
	 * Default name for the SQLite Database the application will use
	 */
	public final static String Default_Filename = "db.db";
	private String filename;
	
	public SQLiteManager (String filename)
	{
		if (!(filename.length()>0))
		{
			this.filename = Default_Filename;
		}
		if (!(filename.endsWith(".db") || filename.endsWith(".sqlite")))
		{
			filename = StringDotsRemover.cleanString(filename);
			filename += ".db";
		}
		this.filename = filename;
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
		try {
			Class.forName(Driver);
			return DriverManager.getConnection(getDatabaseUrl());
		} catch (ClassNotFoundException e) {
			logger.log(Level.FATAL, e.getLocalizedMessage());
		}
		return null;
	}
	
	/**
	 * Initializes the SQLite Database if it doesn't exist
	 */
	public void initializeDatabase()
	{		
		Connection conn = null;
		Statement stmt = null;
        try  {
        	conn = getConnection();
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                
                System.out.println(getDatabaseUrl());
                stmt = conn.createStatement();

                StringBuilder sql = new StringBuilder();
                sql.append ("CREATE TABLE IF NOT EXISTS BBDD (");
                sql.append ("  ID          INTEGER NOT NULL PRIMARY KEY, ");
                sql.append ("  NOMBRE      varchar(255) NOT NULL, ");
                sql.append ("  DESCRIPCION varchar(255), ");
                sql.append ("  SERVIDOR    varchar(255), ");
                sql.append ("  TIPO        varchar(255) NOT NULL);");

                sql.append ("CREATE TABLE IF NOT EXISTS TABLAS (");
                sql.append ("  ID          INTEGER NOT NULL PRIMARY KEY, ");
                sql.append ("  NOMBRE      varchar(255) NOT NULL, ");
                sql.append ("  DESCRIPCION varchar(255), ");
                sql.append ("  ID_BBDD     integer(10) NOT NULL, ");
                sql.append ("  FOREIGN KEY(ID_BBDD) REFERENCES BBDD(ID));");
                
                sql.append ("CREATE TABLE IF NOT EXISTS COLUMNAS (");
                sql.append ("  ID          INTEGER NOT NULL PRIMARY KEY, ");
                sql.append ("  NOMBRE      varchar(255) NOT NULL, ");
                sql.append ("  TIPO_DATO   varchar(255) NOT NULL, ");
                sql.append ("  TAM_DATO    integer(10) NOT NULL, ");
                sql.append ("  DESCRIPCION varchar(255), ");
                sql.append ("  ID_TABLA    integer(10) NOT NULL, ");
                sql.append ("  FOREIGN KEY(ID_TABLA) REFERENCES TABLAS(ID));");
                
                sql.append ("CREATE UNIQUE INDEX IF NOT EXISTS BBDD_ID ");
                sql.append ("  ON BBDD (ID);");
                	
                stmt.executeUpdate(sql.toString());
                
                stmt.close();
                System.out.println("SQLite database created successfully!");

                conn.close();
            }
        } catch (SQLException e) {
            logger.log(Level.FATAL, e.getLocalizedMessage());
        }
	}
	
	/**
	 * Creates a new database entry on the SQLite database
	 * @param db  Database entity (Contains the metadata of the database)
	 * @throws SQLException if the database can't be added
	 */
	public void addNewDatabase(Database db) throws SQLException
	{
		createDatabase(db);
	}
	
	/**
	 * Auxiliary function to add the database entity on the SQLite database
	 * @param db  database to add to the list of registered databases
	 * @throws SQLException  if the information cannot be added to the SQLite database
	 */
	private void createDatabase(Database db) throws SQLException
	{
		Connection connection;
		int nextAvailableID;
		
    	connection = DriverManager.getConnection(getDatabaseUrl());
    	// Obtain next free ID for BBDD
    	nextAvailableID = getNextAvailableID("BBDD");
    	
    	String sql = "INSERT INTO BBDD (ID,NOMBRE,SERVIDOR,TIPO) VALUES(?,?,?,?)";
    	
        PreparedStatement pstmt = connection.prepareStatement(sql);
        
        pstmt.setInt(1, nextAvailableID);
        pstmt.setString(2, db.getNombre());
        pstmt.setString(3, db.getServidor());
        pstmt.setString(4, db.getTipo());
        pstmt.executeUpdate();
        
        pstmt.close();
        connection.close();
        
        for (Table table : db.getTables())
		{
			createTable(table, nextAvailableID);
		}
        
	}
	
	/**
	 * Auxiliar function to add a new table entity on the SQLite database
	 * @param table  table to add (Metadata)
	 * @param ID_BBDD  database parent of this table
	 */
	private void createTable(Table table, int ID_BBDD)
	{
		Connection connection;
		int nextAvailableID = 0;
		String sql;
		
		try  {
        	connection = DriverManager.getConnection(getDatabaseUrl());
        	// Obtain next free ID for TABLAS
        	nextAvailableID = getNextAvailableID("TABLAS");
        				
			sql = "INSERT INTO TABLAS (ID,NOMBRE,ID_BBDD) VALUES(?,?,?)";
        	
            PreparedStatement pstmt = connection.prepareStatement(sql);
            
            pstmt.setInt(1, nextAvailableID);
            pstmt.setString(2, table.getNombre());
            pstmt.setInt(3, ID_BBDD);
            pstmt.executeUpdate();
            
            pstmt.close();
            connection.close();
            
            // for each column on the table, add it to the SQLite database
			for (Column column : table.getColumns())
			{
				createColumn(column, nextAvailableID);
			}
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
	}
	
	/**
	 * Auxiliar function to add a new column entity on the SQLite database 
	 * @param column  column to add
	 * @param ID_TABLA  ID of the parent table of this column
	 */
	private void createColumn(Column column, int ID_TABLA)
	{
		Connection connection;
		int nextID = 0;
		String sql;
		
		try  {
        	connection = DriverManager.getConnection(getDatabaseUrl());
        	// Obtain last ID used on the database
        	nextID = getNextAvailableID("COLUMNAS");
						
			sql = "INSERT INTO COLUMNAS (ID,NOMBRE,TIPO_DATO,TAM_DATO,ID_TABLA) VALUES(?,?,?,?,?)";
        	
            PreparedStatement pstmt = connection.prepareStatement(sql);
            
            pstmt.setInt(1, nextID);
            pstmt.setString(2, column.getNombre());
            pstmt.setString(3, column.getTipo_dato());
            pstmt.setInt(4, column.getTam_dato());
            pstmt.setInt(5, ID_TABLA);
            pstmt.executeUpdate();
            
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
	}
	
	/**
	 * Obtains the next available ID (Primary key) for the given table
	 * @param tableName  table to obtain the available ID
	 * @return  next free ID on the given table
	 */
	private int getNextAvailableID(String tableName)
	{
		Connection connection;
		Statement stmt;
		int lastID = 0;
		String sql;
		
    	try {
			connection = DriverManager.getConnection(getDatabaseUrl());
			// Obtain last ID used on the database by the given table
			sql = "SELECT ID FROM " + tableName + " ORDER BY ID DESC LIMIT 1";
			
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				lastID = Integer.parseInt(rs.getString(1));
			}				
			stmt.close();
			lastID++;
			connection.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
    	return lastID;
	}
	
	/**
	 * Gets the list of servers stored on the database
	 * @return	the servers on the database
	 */
	public List<String> getServers()
	{
		List<String> servers = new ArrayList<String>();
		
		Connection connection;
		Statement stmt;
		try {
    		connection = this.getConnection();
            
    		stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT DISTINCT SERVIDOR FROM 'BBDD'");
            while(rs.next())	
            {
            	servers.add(rs.getString(1));
            }
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
    	return servers;
	}
	
	/** 
	 * Obtains a DefaultTableModel (Used by JTables) for the given server
	 * @param server  name of the server
	 * @return	the DefaultTableModel
	 */
	public DefaultTableModel getDatabases(String server)
	{
		Connection connection;
		DefaultTableModel tableModelDatabases;

		try {
			connection = getConnection();

			Statement stmt = connection.createStatement();
			String sqlQuery = "SELECT ID, NOMBRE AS 'Nombre de la base de datos' FROM 'BBDD' WHERE SERVIDOR = '" + server + "'";
			
			ResultSet rs = stmt.executeQuery(sqlQuery);
			
			// It creates and displays the table
			tableModelDatabases = es.uneatlantico.gdbd.util.BuildTableModel.buildTableModel(rs);
			rs.close();
			stmt.close();
		} catch (Exception e) {
			tableModelDatabases = new DefaultTableModel();
			logger.log(Level.ERROR, e.getLocalizedMessage());
		}
		return tableModelDatabases;
	}
	
	/**
	 * Obtains a DefaultTableModel (Used by JTables) containing the tables of the given database
	 * @param databaseID  identifier of the database
	 * @return  the DefaultTableModel
	 */
	public DefaultTableModel getTables(int databaseID)
	{
		Connection connection;
		DefaultTableModel tableModelTables;

		try {
			connection = getConnection();
			Statement stmt = connection.createStatement();
			String sqlQuery = "SELECT ID, NOMBRE AS 'Nombre de la tabla' FROM TABLAS WHERE ID_BBDD = '" + databaseID + "'";
			logger.log(Level.DEBUG, sqlQuery);

			ResultSet rs = stmt.executeQuery(sqlQuery);

			// It creates and displays the table
			tableModelTables = es.uneatlantico.gdbd.util.BuildTableModel.buildTableModel(rs);
			
			rs.close();
			stmt.close();
		} catch (Exception e) {
			tableModelTables = new DefaultTableModel();
			logger.log(Level.ERROR, e.getLocalizedMessage());
		}
		return tableModelTables;
	}
	
	/**
	 * Obtains a DefaultTableModel (Used by JTables) containing the columns of the the given table
	 * @param databaseID  identifier of the table
	 * @return  the DefaultTableModel
	 */
	public DefaultTableModel getColumns(int tableID)
	{
		Connection connection;
		DefaultTableModel tableModelColumns;
		
		try {
			connection = getConnection();
			Statement stmt = connection.createStatement();
			String sqlQuery = "SELECT ID, NOMBRE AS 'Nombre de la columna' FROM COLUMNAS WHERE ID_TABLA = '" + tableID + "'";
			logger.log(Level.DEBUG, sqlQuery);

			ResultSet rs = stmt.executeQuery(sqlQuery);

			// It creates and displays the table
			tableModelColumns = es.uneatlantico.gdbd.util.BuildTableModel.buildTableModel(rs);
			
			rs.close();
			stmt.close();
		} catch (Exception e) {
			tableModelColumns = new DefaultTableModel();
			logger.log(Level.ERROR, e.getLocalizedMessage());
		}
		return tableModelColumns;
	}
}
