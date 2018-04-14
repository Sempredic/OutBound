
package exceltest;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.text.DecimalFormat;
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
    static ArrayList<Object> curRosterList;
    static String dbLocation;
    
    public DatabaseObj(){
        
        stmt = null;
        preparedStatement = null;
        areasList = new ArrayList<String>();
        devicesList = new ArrayList<String>();
        employeesList = new ArrayList<String>();
        curRosterList = new ArrayList<Object>();
        dbLocation = "C:\\Users\\Vince\\Downloads\\DB.accdb";
        
        try{
          ///////////////////////////CONNECTION////////////////////////////////
            //STEP 2: Register JDBC driver
          Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            //STEP 3: Open a connection
          System.out.println("Connecting to database: " + dbLocation + "...");
          conn = DriverManager.getConnection("jdbc:ucanaccess://" + dbLocation);
          
          status = true;
       } catch(Exception e){
          //////////////////////////NO CONNECTION/////////////////////////////////
          status = false;
          System.out.println(e.toString());
       }
        
       if(status){             
          readFileSave();
          getCellAreasQuery();
          getDevicesQuery();
          getEmployeesQuery();
          writeToFileSave();
       }else{
           readFileSave();
       }
    }
    
    static void setCurRosterList(ArrayList<Object> techIDList){
       curRosterList = techIDList;
    }
    
    static String getDatabaseLocation(){
        return dbLocation;
    }
    
    static ArrayList<Object> getCurRosterList(){
        return curRosterList;
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
 
            timeout = conn.getNetworkTimeout();
          
            status = true;
        } catch(Exception e){
            
            status = false;
        }
        
        return status;
    }
    
    static int getEmployeeID(String tech)throws Exception{
        int ID = 0;
        String SQL = "SELECT employees.ID\n" +
                     "FROM employees\n" +
                     "WHERE (((employees.TechID)=\"" + tech + "\"))";
        
        
        ResultSet rs = stmt.executeQuery(SQL);
        
        while(rs.next()){
            ID = rs.getInt("ID");
        }
        
        return ID;
    }
    
    static String getEmployeeNameQ(String tech)throws Exception{
        String name = " ";
        String SQL = "SELECT [First Name]+Left([Last Name],1)+\".\" AS Name\n" +
                     "FROM employees\n" +
                     "WHERE (((employees.TechID)=\"" + tech + "\"))";
        
        
        ResultSet rs = stmt.executeQuery(SQL);
        
        while(rs.next()){
            name = rs.getString("Name");
        }
        
        return name;
    }
    
    static void executeUpdateTechProdQ(String tech, String device, int total, int entryID)throws Exception{
        
        int ID = getEmployeeID(tech);
        String SQL = "UPDATE techProdEntries \n" +
                     "SET techProdEntries.[" + device + "] = ?\n" +
                     "WHERE (((techProdEntries.prodID)=?)) AND (((techProdEntries.EmployeeID)=?))";

        preparedStatement = conn.prepareStatement(SQL);
        
        preparedStatement.setInt(1,total);
        preparedStatement.setInt(2,entryID);
        preparedStatement.setInt(3,ID);
   
        preparedStatement .executeUpdate();
        
    }
    
    static void executeUpdateTotalsQuery(int total,int EntryID)throws Exception{
   
        String SQL = "UPDATE cellEntries SET cellEntries.[Total Completed] = ?\n" +
                     "WHERE (((cellEntries.ID)=?))";

        preparedStatement = conn.prepareStatement(SQL);
        preparedStatement.setInt(1,total);
        preparedStatement.setInt(2,EntryID);
   
        preparedStatement .executeUpdate();

    }
    
    static boolean executeTechProdEntryEmployeeExistsQ(String tech, int entryID)throws Exception{
        int ID = 0;
        boolean exists = false;
        
        String SQL = "SELECT techProdEntries.ID\n" +
                     "FROM employees LEFT JOIN techProdEntries ON employees.ID = techProdEntries.EmployeeID\n" +
                     "WHERE (((employees.TechID)=\""+tech+"\") AND ((techProdEntries.prodID)="+entryID+"));";
        
        stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery(SQL);
        
        if(!rs.next()){
            exists = false;
        }else{
            exists = true;
        }
        
        return exists;
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
    
    static ArrayList executeGetTechProdEntries(ArrayList devices,int entryID)throws Exception{
        ArrayList<ArrayList> tableList = new ArrayList();
        
        StringBuilder builder = new StringBuilder();
        
        for(Object dev:devices){
            builder.append(",techProdEntries." + dev);
        }
        
        String SQL = "SELECT employees.TechID" + builder + ", techProdEntries.prodID\n" +
                     "FROM employees LEFT JOIN techProdEntries ON employees.ID = techProdEntries.EmployeeID\n" +
                     "WHERE (((techProdEntries.prodID)=" + entryID + "))";
        
        stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery(SQL);
        
        while(rs.next()){
            
            ArrayList rowList = new ArrayList();
            
            rowList.add(rs.getString("TechID"));
            
            for(Object dev:devices){
                rowList.add(rs.getInt((String)dev));
            }
            
            tableList.add(rowList);
        }

        return tableList;
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
    
    static int executeTechProdEntriesAppendQ(LinkedHashMap<String,String> entryMap,String tech)throws Exception{
       
        String prodID = entryMap.get("EntryID");
        String Date = entryMap.get("Date");
        String SQL = " ";
        int generatedKey = 0;
        int employeeID = getEmployeeID(tech);
        
       
        SQL = "INSERT INTO techProdEntries ( prodID,[Date of Entry],employeeID )\n" +
                 "VALUES (" + prodID + ",#" + Date + "#," + employeeID + ")";

        stmt.executeUpdate(SQL);
        ResultSet rs = stmt.getGeneratedKeys();

        while(rs.next()){
            generatedKey = rs.getInt(1);
        }
         
        return generatedKey;
        
    }
    
    static void executeFailEntriesAppendQ(int entryID,String tech,String device,String failType)throws Exception{
       
        int employeeID = getEmployeeID(tech);
        
        String SQL = "INSERT INTO failEntries ( prodID,EmployeeID,Device,[Fail Type] )\n" +
                 "VALUES (?,?,?,?)";

        preparedStatement = conn.prepareStatement(SQL);
        
        preparedStatement.setInt(1, entryID);
        preparedStatement.setInt(2, employeeID);
        preparedStatement.setString(3, device);
        preparedStatement.setString(4, failType);

        preparedStatement.executeUpdate();

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
    
    static ArrayList executeGetFailRecordsQ(int entryID)throws Exception{
        ArrayList<ArrayList> tableList = new ArrayList();
        
        String SQL = "SELECT employees.[First Name], employees.[Last Name], failEntries.Device, failEntries.[Fail Type]\n" +
                     "FROM failEntries LEFT JOIN employees ON failEntries.EmployeeID = employees.ID\n" +
                     "WHERE (((failEntries.prodID)="+entryID+"))"
                   + "ORDER BY employees.[First Name]";
        
        stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(SQL);
        
        while(rs.next()){
            ArrayList row = new ArrayList();
            
            row.add(rs.getString("First Name"));
            row.add(rs.getString("Last Name"));
            row.add(rs.getString("Device"));
            row.add(rs.getString("Fail Type"));
            
            tableList.add(row);
        }
        
        return tableList;
    }
    
    static ArrayList executeGetTechProdRecordsQ(int entryID)throws Exception{
        
        ArrayList<ArrayList> tableList = new ArrayList();
        int iPhone,iPad,iTouch,Classic,Shuffle,Nano,aTablet,aPhone,Wearables =0;
        
        String SQL = "SELECT employees.TechID, employees.[First Name], employees.[Last Name], techProdEntries.iPhone, "
                   + "techProdEntries.iPad, techProdEntries.iTouch, techProdEntries.Classic, techProdEntries.Shuffle, techProdEntries.Nano, techProdEntries.[Android Phone], techProdEntries.[Android Tablet], techProdEntries.Wearables\n" +
                     "FROM employees LEFT JOIN techProdEntries ON employees.ID = techProdEntries.EmployeeID\n" +
                     "WHERE (((techProdEntries.prodID)="+entryID+"))";
        
        stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(SQL);
        
        while(rs.next()){
            ArrayList row = new ArrayList();
            
            row.add(rs.getString("TechID"));
            row.add(rs.getString("First Name"));
            row.add(rs.getString("Last Name"));
            iPhone = rs.getInt("iPhone");
            iPad = rs.getInt("iPad");
            iTouch = rs.getInt("iTouch");
            Classic = rs.getInt("Classic");
            Shuffle = rs.getInt("Shuffle");
            Nano = rs.getInt("Nano");
            aPhone = rs.getInt("Android Phone");
            aTablet = rs.getInt("Android Tablet");
            Wearables = rs.getInt("Wearables");
            row.add(iPhone);
            row.add(iPad);
            row.add(iTouch);
            row.add(Classic);
            row.add(Shuffle);
            row.add(Nano);
            row.add(aPhone);
            row.add(aTablet);
            row.add(Wearables);
            row.add((iPhone+iPad+iTouch+Classic+Shuffle+Nano+aPhone+aTablet+Wearables));
            
            tableList.add(row);
        }
        
        return tableList;
    }
    
    static ArrayList executeGetCellRecordsQ(String month,String day,String year,String area, String shift)throws Exception{
        
        ArrayList<ArrayList> tableList = new ArrayList();
        StringBuilder builder = new StringBuilder();
        String date = new String();
        String dateOfEntry = new String();
        int totComp = 0;
        int netGoal = 0;
        int failCount = 0;
        
        if(day != "ALL"){
            date = month + "/" + day + "/" + year;
            dateOfEntry = "(((cellEntries.DateOfEntry)=#"+date+"#))";
        }else{
            date = month;
            dateOfEntry = "(((Month([cellEntries].[DateOfEntry]))="+date+"))";
        }
        
        if(area != null){
            if(area != "All"){
                builder.append(" AND (((areas.AreaName)=\""+area+"\"))");
                if(shift!="All"){
                    builder.append(" AND (((areas.Shift)="+shift+"))");
                }
            }else{
                builder.append("");
            }
            
        }else{
            builder.append("");
        }
        
        String sql = "SELECT cellEntries.ID, cellEntries.DateOfEntry, areas.AreaName, areas.Shift, cellEntries.[Total Completed], areas.[Net Goal Total], failCountQuery.CountOfprodID\n" +
                     "FROM (areas RIGHT JOIN cellEntries ON areas.ID = cellEntries.CellID) LEFT JOIN failCountQuery ON cellEntries.ID = failCountQuery.ID\n" +
                     "WHERE "+ dateOfEntry + builder + "\n" +
                     "ORDER BY cellEntries.DateOfEntry";

        stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);
        
        while(rs.next()){
            
            ArrayList row = new ArrayList();
            
            row.add(rs.getInt("ID"));
            row.add(rs.getDate("DateOfEntry"));
            row.add(rs.getString("AreaName"));
            row.add(rs.getString("Shift"));
            
            totComp = rs.getInt("Total Completed");
            netGoal = rs.getInt("Net Goal Total");
            failCount = rs.getInt("CountOfprodID");
            
            row.add(totComp);
            row.add(netGoal);
            row.add((((double)totComp/netGoal)*100));
            
            if(failCount != 0){
                row.add((((double)failCount/totComp)*100));
            }else{
                row.add(0);
            }

            tableList.add(row);
        }
        
        return tableList;
        
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
                if(getStatusBoolean()){
                    areasList.clear();
                    devicesList.clear();
                    employeesList.clear();
                }else{
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
