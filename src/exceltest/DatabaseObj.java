
package exceltest;

import java.sql.*;

/**
 *
 * @author Vince
 */
public class DatabaseObj {
    
    
    Connection conn;
    static boolean status;
    
    
    public DatabaseObj(){
        
        try{
            //STEP 2: Register JDBC driver
          Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            //STEP 3: Open a connection
          System.out.println("Connecting to a selected database...");
          conn = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Vince\\Downloads\\DB.accdb");
        
          status = true;
       } catch(Exception e){
          status = false;
       }
    }
    
    static String getStatus(){
       return status?"Connected":"Disconnected"; 
    }
    
    static void executeQuery(){
        System.out.println("EXECUTED");
    }
}
