
package DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbConnect {
    
    public Connection getConnection() throws ClassNotFoundException{
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); //com.mysql.jdbc.Driver
            //con = DriverManager.getConnection("jdbc:mysql://10.11.200.81:3306/logdb", "mconnect", "Admin@123");
            //con = DriverManager.getConnection("jdbc:mysql://10.11.200.146:3306/studentdb", "muser", "muser");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Admin01");
            return con;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            System.out.println(ex);
        }
        return null;
    }
    
    public void disconnectDb(Connection con)
    {
        try{
            con.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        DbConnect dbConnect = new DbConnect();
        try{
            Connection con = dbConnect.getConnection();
            System.out.println(con);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        
    }
}
