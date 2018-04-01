
package exceltest;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Vince
 */
public class DatabaseObj {
    
    
    static Connection conn;
    static boolean status;
    static Statement stmt;
    static ArrayList<String> areasList;
    static ArrayList<String> devicesList;
    static ArrayList<String> employeesList;
    
    public DatabaseObj(){
        
        stmt = null;
        areasList = new ArrayList<String>();
        devicesList = new ArrayList<String>();
        employeesList = new ArrayList<String>();
        
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
        
       if(status){             
          readFileSave();
          getCellAreasQuery();
          getDevicesQuery();
          getEmployeesQuery();
          writeToFileSave();
       }
    }
    
    static void closeConnection() throws Exception{
        conn.close();
    }
    
    static String getStatus(){
       return status?"Connected":"Disconnected"; 
    }
    
    static boolean getStatusBoolean(){
        return status;
    }
    
    static void executeUpdateTotalsQuery(int total)throws Exception{
        String SQL = "UPDATE cellEntries\n" +
                     "SET cellEntries.[Total Completed] = " + total + "\n" +
                     "WHERE (((cellEntries.DateOfEntry)=Date()))";
 
        stmt = conn.createStatement();

        stmt.executeUpdate(SQL);  
    }
    
    static void getCellAreasQuery(){
        
        String area = " ";
        String SQL = "SELECT areas.AreaName\n" +
                     "FROM areas\n" +
                     "GROUP BY areas.AreaName";     
        try{ 
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(SQL);

            while(rs.next()){

                area = rs.getString("AreaName");

                if(!areasList.contains(area)){
                    areasList.add(area);
                }
            }
        }catch(Exception e){
            
        }
    }
    
    static void getDevicesQuery(){
        
        String dev = " ";
        String SQL = "SELECT devices.[Device Name]\n" +
                     "FROM devices;";
        try{
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(SQL);

            while(rs.next()){
                dev = rs.getString("Device Name");

                if(!devicesList.contains(dev)){
                    devicesList.add(dev);
                }
            }
        }catch(Exception e){
            
        }
    }
    
    static void getEmployeesQuery(){
        
        String techID = " ";
        String name = " ";
        String SQL = "SELECT employees.TechID, [First Name]+Left([Last Name],1)+\".\" AS Name\n" +
                     "FROM employees";
        try{
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(SQL);

            while(rs.next()){
      
                techID = rs.getString("TechID"); 
                name = rs.getString("Name");

                if(!employeesList.contains(techID + " " + name)){
                    employeesList.add(techID + " " + name);
                }
            }
        }catch(Exception e){
            
        }
    }
    
    private void readFileSave(){

        try{
            File tmpDir = new File("areasList.txt");
            File tmpDir2 = new File("devicesList.txt");
            File tmpDir3 = new File("employeesList.txt");
         
            boolean exists = tmpDir.exists();
            boolean exists2 = tmpDir2.exists();
            boolean exists3 = tmpDir3.exists();
            
            //if file doesn't exists create new Blank one
            if(exists == false || exists2 == false||exists3 == false){
                writeToFileSave();
            }else{
                //populate empty lists
                for(String area:Files.readAllLines(Paths.get("areasList.txt"))){
                    if(!areasList.contains(area)){
                       areasList.add(area);
                    }
                }

                for(String dev:Files.readAllLines(Paths.get("devicesList.txt"))){
                    if(!devicesList.contains(dev)){
                       devicesList.add(dev);
                    }
                }
                
                for(String employee:Files.readAllLines(Paths.get("employeesList.txt"))){
                    if(!employeesList.contains(employee)){
                       employeesList.add(employee);
                    }
                }
            }   
            
        }catch(Exception e){
            
        }
        
    }
    
    private void writeToFileSave(){
  
        try{
            PrintWriter writer = new PrintWriter("areasList.txt", "UTF-8");
            PrintWriter writer2 = new PrintWriter("devicesList.txt", "UTF-8");
            PrintWriter writer3 = new PrintWriter("employeesList.txt", "UTF-8");
            
            for(String area:areasList){
                writer.println(area);
            }
            
            for(String dev:devicesList){
                writer2.println(dev);
            }
            
            for(String employee:employeesList){
                writer3.println(employee);
            }
            
            writer.close();
            writer2.close();
            writer3.close();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
   
    }
}
