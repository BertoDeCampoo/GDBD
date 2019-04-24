package gdbd;

import java.sql.*;

public class ConnectMSSQLServer
{
   public void dbConnect(String db_connect_string,
            String db_userid,
            String db_password)
   {
      try {
    	  // TODO: Mirar https://www.databasejournal.com/features/mssql/article.php/3587906/System-Tables-and-Catalog-Views.htm
    	  // https://docs.microsoft.com/en-us/sql/connect/jdbc/building-the-connection-url?view=sql-server-2017
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         Connection conn = DriverManager.getConnection(db_connect_string,
                  db_userid, db_password);
         System.out.println("connected");
         Statement statement = conn.createStatement();
         String queryString = "select * from sysobjects where type='u'";
         ResultSet rs = statement.executeQuery(queryString);
         while (rs.next()) {
            System.out.println(rs.getString(1));
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}