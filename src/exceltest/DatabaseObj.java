
package exceltest;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Vince
 */
public class DatabaseObj {
    
    
    static Connection conn;
    static boolean status;
    static Statement stmt;
    static PreparedStatement preparedStatement;
    static ArrayList<String> areasList;
    static ArrayList<String> devicesList;
    static ArrayList<String> employeesList;
    String dbLocation;
    
    public DatabaseObj(){
        
        stmt = null;
        preparedStatement = null;
        areasList = new ArrayList<String>();
        devicesList = new ArrayList<String>();
        employeesList = new ArrayList<String>();
        dbLocation = "C:\\Users\\Vince\\Downloads\\DB.accdb";
        
        try{
            //STEP 2: Register JDBC driver
          Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            //STEP 3: Open a connection
          System.out.println("Connecting to database: " + dbLocation + "...");
          conn = DriverManager.getConnection("jdbc:ucanaccess://" + dbLocation);
          
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
    
    static ArrayList<String> getEmployeeList(){
        return employeesList;
    }
    
    static ArrayList<String> getAreaList(){
        return areasList;
    }
    
    static ArrayList<String> getDevicesList(){
        return devicesList;
    }
    
    static void closeConnection() throws Exception{
        conn.close();
    }
    
    static String getStatus(){
       boolean temp;
       temp = getStatusBoolean();

       return temp?"Connected":"Disconnected"; 
    }
    
    static boolean getStatusBoolean(){
        
        int timeout =0;
        
        try{
            System.out.println("Getting Database Status...");
    
            timeout = conn.getNetworkTimeout();
          
            status = true;
        } catch(Exception e){
            
            status = false;
        }
        
        return status;
    }
    
    static void executeUpdateTotalsQuery(int total,int EntryID)throws Exception{
   
        String SQL = "UPDATE cellEntries SET cellEntries.[Total Completed] = [Total Completed]+?\n" +
                     "WHERE (((cellEntries.ID)=?))";

        preparedStatement = conn.prepareStatement(SQL);
        preparedStatement.setInt(1,total);
        preparedStatement.setInt(2,EntryID);
   
        preparedStatement .executeUpdate();

    }
    
    static boolean executeCellEntryExistsQ(String Date,String CellArea,String Shift)throws Exception{
        
        boolean exists = false;
        String SQL = "SELECT cellEntries.DateOfEntry, areas.AreaName, areas.Shift\n" +
                     "FROM areas RIGHT JOIN cellEntries ON areas.ID = cellEntries.CellID\n" +
                     "WHERE (((cellEntries.DateOfEntry)=#" + Date + "#) AND ((areas.AreaName)=\"" + CellArea + "\")\n" +
                     "AND ((areas.Shift)=" + Shift + "))";
     
        stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery(SQL);
        
        if(!rs.next()){
            exists = false;
        }else{
            exists = true;
        }
        
        return exists;
    }
    
    static int executeGetCellIDQ(String Date,String CellArea,String Shift)throws Exception{
        int areaID = 0;
        String selectSQL = "SELECT areas.ID\n" +
                           "FROM areas\n" +
                           "WHERE (((areas.AreaName)=\"" + CellArea + "\") AND ((areas.Shift)="+ Shift + "))";
        
        stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery(selectSQL);
        
        while(rs.next()){
            areaID = rs.getInt("ID");
        }
        
        return areaID;
    }
    
    static int executeGetEntryIDQ(String Date,int CellID)throws Exception{
        int entryID = 0;
        String selectSQL = "SELECT cellEntries.ID\n" +
                           "FROM cellEntries\n" +
                           "WHERE (((cellEntries.DateOfEntry)=#" + Date + "#) AND ((cellEntries.CellID)="+ CellID + "))";
        
        stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery(selectSQL);
        
        while(rs.next()){
            entryID = rs.getInt("ID");
        }
        
        return entryID;
    }
    
    static void executeGetEmployeeIDQ(ArrayList<Object> techIDList,LinkedHashMap<String,String> roster)throws Exception{
        
        String SQL = " ";

        for(Map.Entry<String,String> entry:roster.entrySet()){
            
            SQL = "SELECT employees.ID\n" +
                   "FROM employees\n" +
                   "WHERE (((employees.TechID)=\"" + entry.getKey() + "\"));";

            ResultSet rs = stmt.executeQuery(SQL);
            
            while(rs.next()){
                techIDList.add(rs.getInt("ID"));
            }
        }

    }
    
    static ArrayList<Integer> executeTechProdEntriesAppendQ(LinkedHashMap<String,String> entryMap,ArrayList<Object> techIDList)throws Exception{
       
        String prodID = entryMap.get("EntryID");
        String Date = entryMap.get("Date");
        String SQL = " ";
        ArrayList<Integer> generatedKeys = new ArrayList<Integer>();
        
        for(Object employeeID:techIDList){
            SQL = "INSERT INTO techProdEntries ( prodID,[Date of Entry],employeeID )\n" +
                     "VALUES (" + prodID + ",#" + Date + "#," + employeeID + ")";
             
            stmt.executeUpdate(SQL);
            ResultSet rs = stmt.getGeneratedKeys();
            
            while(rs.next()){
                generatedKeys.add(rs.getInt(1));
            }
        }
        
        return generatedKeys;
        
    }
    
    static LinkedHashMap<String,String> executeCellEntryAppendQ(String Date,String CellArea,String Shift)throws Exception{
        LinkedHashMap<String,String> cellEntryInfo = new LinkedHashMap<String,String>();
        int areaID = 0;
        int cellEntryID = 0;
        
        areaID = executeGetCellIDQ(Date,CellArea,Shift);
        
        cellEntryInfo.put("Date",Date);
        cellEntryInfo.put("AreaID", String.valueOf(areaID));
        cellEntryInfo.put("AreaName",CellArea);
        cellEntryInfo.put("Shift",Shift);
        
        String appendSQL = "INSERT INTO cellEntries ( DateOfEntry, CellID )\n" +
                           "VALUES (#" + Date + "#," + areaID + ")";
        
        stmt.executeUpdate(appendSQL);
        
        ResultSet rs = stmt.getGeneratedKeys();
            
        while(rs.next()){
            cellEntryID = rs.getInt(1);
        }
        
        cellEntryInfo.put("EntryID",String.valueOf(cellEntryID));
  
        return cellEntryInfo;
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
        String SQL = "SELECT *\n" +
                     "FROM techProdEntries";
        try{
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(SQL);
            ResultSetMetaData rsmd = rs.getMetaData();
            
            for(int i=5;i<rsmd.getColumnCount()+1;i++){
                
                dev = rsmd.getColumnName(i);
                
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
