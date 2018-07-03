
package exceltest;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Vince
 */
public class DatabaseObj {
    
    
    static Connection conn;
    static Connection skuConn;
    static boolean status;
    static boolean skuStatus;
    static Statement stmt;
    static Statement skuStmt;
    static PreparedStatement preparedStatement;
    static PreparedStatement skuPreparedStatement;
    static ArrayList<String> areasList;
    static ArrayList<String> devicesList;
    static ArrayList<String> employeesList;
    static ArrayList<Object> curRosterList;
    static String dbLocation;
    static String skuLocation;
    static DateFormat dateFormat;
    static Date theDate;
    
    public DatabaseObj(){
        
        stmt = null;
        skuStmt = null;
        skuPreparedStatement = null;
        preparedStatement = null;
        areasList = new ArrayList<String>();
        devicesList = new ArrayList<String>();
        employeesList = new ArrayList<String>();
        curRosterList = new ArrayList<Object>();
        dbLocation = readLocSave();
        skuLocation = readSKULocSave();
        dateFormat = new SimpleDateFormat("hh:mm a");
	theDate = new Date();
        
        connectToDatabase();
        connectToSKUDatabase();
        
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
    
    static void connectToDatabase(){
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
          errorLogger.writeToLogger(e.toString());
       }
    }
    
    static void connectToSKUDatabase(){
        try{
          ///////////////////////////CONNECTION////////////////////////////////
            //STEP 2: Register JDBC driver
          Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            //STEP 3: Open a connection
          System.out.println("Connecting to database: " + skuLocation + "...");
  
          skuConn = DriverManager.getConnection("jdbc:ucanaccess://" + skuLocation);
          skuStatus = true;
       } catch(Exception e){
          //////////////////////////NO CONNECTION/////////////////////////////////
          skuStatus = false;
          System.out.println(e.toString());
          errorLogger.writeToLogger(e.toString());
       }
    }
    
    static void reEstablishConnection(){
        
        dbLocation = readLocSave();
        
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
          errorLogger.writeToLogger(e.toString());
       }
    }
    
    static void reEstablishSKUConnection(){
        
        skuLocation = readLocSave();
        
        try{
          ///////////////////////////CONNECTION////////////////////////////////
            //STEP 2: Register JDBC driver
          Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            //STEP 3: Open a connection
          System.out.println("Connecting to database: " + skuLocation + "...");
          skuConn = DriverManager.getConnection("jdbc:ucanaccess://" + skuLocation);
          
          skuStatus = true;
       } catch(Exception e){
          //////////////////////////NO CONNECTION/////////////////////////////////
          skuStatus = false;
          System.out.println(e.toString());
          errorLogger.writeToLogger(e.toString());
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
    
    static String getSKUStatus(){
       boolean temp;
       temp = getSKUStatusBoolean();

       return temp?"Connected":"Disconnected"; 
    }
    
    static boolean getSKUStatusBoolean(){
        
        int timeout =0;
        
        try{
 
            timeout = skuConn.getNetworkTimeout();
          
            skuStatus = true;
        } catch(Exception e){
            
            skuStatus = false;
        }
        
        return skuStatus;
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
    
    static void executeAppendToSKUTableQ(String SKU,String deviceName,String deviceType){
        
        try{
            String skuSQL = "INSERT INTO skuTable(SKU,DeviceName,DeviceType)\n"+
                        "VALUES ?,?,?";
            
            preparedStatement = conn.prepareStatement(skuSQL);

            preparedStatement.setString(1, SKU);
            preparedStatement.setString(2, deviceName);
            preparedStatement.setString(3, deviceType);

            preparedStatement.executeUpdate();
            
        }catch(Exception e){
            System.out.println(e.toString());
            errorLogger.writeToLogger(e.toString());
        }
        
    }

    static String getDeviceNameFromSKUQ(String SKU){
        String name = " ";
        String value = null;
        int skuNumber = 0;
        String deviceName = " ";
        
        String initSQL = "SELECT skuTable.DeviceType\n"+
                         "FROM skuTable\n"+
                         "WHERE skuTable.SKU = ?";
        
        String SQL = "SELECT *\n" +
                     "FROM SmartphoneTable\n" +
                     "WHERE [SmartphoneTable].[F5]=? Or [SmartphoneTable].[F6]=? Or [SmartphoneTable].[F8] =? Or [SmartphoneTable].[F9]=? Or [SmartphoneTable].[F10]=? "
                      + "Or [SmartphoneTable].[F11]=? Or [SmartphoneTable].[F13]=? Or [SmartphoneTable].[F14]=? Or [SmartphoneTable].[F15]=? Or [SmartphoneTable].[F16]=? "+
                        "Or [SmartphoneTable].[F17]=? Or [SmartphoneTable].[F18]=? Or [SmartphoneTable].[F19]=? Or [SmartphoneTable].[F20]=? Or [SmartphoneTable].[F21]=? "+
                        "Or [SmartphoneTable].[F22]=? Or [SmartphoneTable].[F23]=? Or [SmartphoneTable].[F24]=?";
        
        String TabletsSQL = "SELECT *\n"+
                            "FROM TabletsTable\n" +
                            "WHERE TabletsTable.F4=? Or TabletsTable.F5=? Or TabletsTable.F7=? Or TabletsTable.F8=? Or TabletsTable.F9=? Or TabletsTable.F10=?";
        
        String MP3SQL = "SELECT *\n"+
                        "FROM MP3Table\n"+
                        "WHERE [MP3Table].[F4]=? Or [MP3Table].[F5]=? Or [MP3Table].[F8]=? Or [MP3Table].[F9]=? Or [MP3Table].[F10]=? Or [MP3Table].[F12]=? Or [MP3Table].[F13]=? Or [MP3Table].[F14]=? Or [MP3Table].[F15]=? Or [MP3Table].[F16]=? Or [MP3Table].[F17]=? Or [MP3Table].[F18]=? Or [MP3Table].[F19]=? Or [MP3Table].[F20]=? Or [MP3Table].[F21]=? Or [MP3Table].[F22]=? Or [MP3Table].[F23]=? Or [MP3Table].[F24]=? Or [MP3Table].[F25]=? Or [MP3Table].[F26]=?";

        if(isInteger(SKU)){
            skuNumber = Integer.parseInt(SKU);
        }

        try{
            
            preparedStatement = conn.prepareStatement(initSQL);

            preparedStatement.setString(1, SKU);

            ResultSet r = preparedStatement.executeQuery();
            
            while(r.next()){
          
                value = r.getString("DeviceType");
 
            }
            
            if(value==null){
                
                skuPreparedStatement = skuConn.prepareStatement(SQL);

                skuPreparedStatement.setString(1, SKU);
                skuPreparedStatement.setString(2, SKU);
                skuPreparedStatement.setString(3, SKU);
                skuPreparedStatement.setInt(4, skuNumber);
                skuPreparedStatement.setInt(5, skuNumber);
                skuPreparedStatement.setInt(6, skuNumber);
                for(int i=7;i<19;i++){
                    if(i==13){
                        skuPreparedStatement.setInt(i, skuNumber);
                    }else if(i==24){
                        skuPreparedStatement.setInt(i, skuNumber);
                    }else{
                        skuPreparedStatement.setString(i, SKU);
                    }
                            
                }
                

                ResultSet rs = skuPreparedStatement.executeQuery();

                while(rs.next()){

                    value = rs.getString("F3");

                    if(value!=null){

                        name = "phone";
                        deviceName = value;

                        executeAppendToSKUTableQ(SKU,deviceName,name);
                    }   
                }

                if(value==null){
                    skuPreparedStatement = skuConn.prepareStatement(TabletsSQL);

                    skuPreparedStatement.setString(1, SKU);
                    skuPreparedStatement.setString(2, SKU);
                    skuPreparedStatement.setString(3, SKU);
                    skuPreparedStatement.setInt(4, skuNumber);
                    skuPreparedStatement.setInt(5, skuNumber);
                    skuPreparedStatement.setInt(6, skuNumber);

                    ResultSet rs2 = skuPreparedStatement.executeQuery();

                    while(rs2.next()){

                        value = rs2.getString("F2");

                        if(value!=null){

                            name = "pad";
                            deviceName = value;

                            executeAppendToSKUTableQ(SKU,deviceName,name);
                        }   
                    }
                }

                if(value==null){
                    skuPreparedStatement = skuConn.prepareStatement(MP3SQL);

                    skuPreparedStatement.setInt(1, skuNumber);
                    skuPreparedStatement.setString(2, SKU);
                    skuPreparedStatement.setInt(3, skuNumber);
                    skuPreparedStatement.setInt(4, skuNumber);
                    skuPreparedStatement.setInt(5, skuNumber);

                    for(int i=6;i<21;i++){
                        if(i!=19){
                            skuPreparedStatement.setString(i, SKU);
                        }else{
                            skuPreparedStatement.setInt(i, skuNumber);
                        }
                    }

                    ResultSet rs3 = skuPreparedStatement.executeQuery();

                    while(rs3.next()){

                        value = rs3.getString("F2");

                        if(value!=null){

                            if(value.toLowerCase().contains("touch")){
                                name = "touch";
                            }else if(value.toLowerCase().contains("nano")){
                                name = "nano";
                            }else if(value.toLowerCase().contains("classic")){
                                name = "classic";
                            }else if(value.toLowerCase().contains("shuffle")){
                                name = "shuffle";
                            }
               
                            deviceName = value;

                            executeAppendToSKUTableQ(SKU,deviceName,name);
                        }   
                    }
                }
            }else{
                name = value;
            }
            
        
        }catch(Exception e){
            System.out.println(e.toString());
            errorLogger.writeToLogger(e.toString());
        }
        
        return name;
    }
    
    static boolean isInteger(String string){
        boolean result = false;
        
        try{
            int value = Integer.parseInt(string);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    static int executeCaseEntryAppendQ(LinkedHashMap<String,String> entryMap,String techID,String caseID,HashMap<String,Integer>multiMap)throws Exception{
       
        int cellEntryID = Integer.valueOf(entryMap.get("EntryID"));
        String Date = entryMap.get("Date");
        String SQL = " ";
        StringBuilder builder = new StringBuilder();
        StringBuilder stmtBuilder = new StringBuilder();
        ArrayList<Integer> devValues = new ArrayList<Integer>();
        int builderCount = 0;
        int generatedKey = 0;
        int employeeID = getEmployeeID(techID);
        int totalUnits = 0;
        
        for(Map.Entry<String,Integer>entry:multiMap.entrySet()){
            totalUnits+=entry.getValue();
            
            if(entry.getValue()>0){
                builder.append(","+entry.getKey());
                builderCount++;
                devValues.add(entry.getValue());
            }  
        }
        
        for(int i=0;i<builderCount;i++){
            stmtBuilder.append(",?");
        }
       
        SQL = "INSERT INTO caseEntries (CellPID,DateOfEntry,Time,EmployeeID,CaseID,TotalUnits,UserID)\n" +
              "VALUES(?,#"+Date+"#,?,?,?,?,?)";

        preparedStatement = conn.prepareStatement(SQL);
        
        preparedStatement.setInt(1, cellEntryID);
        preparedStatement.setString(2, dateFormat.format(theDate));
        preparedStatement.setInt(3, employeeID);
        preparedStatement.setString(4, caseID);
        preparedStatement.setInt(5, totalUnits);
        preparedStatement.setString(6,System.getProperty("user.name"));
       
        preparedStatement.executeUpdate();

        ResultSet rs = preparedStatement.getGeneratedKeys();
        
        while(rs.next()){
            generatedKey = rs.getInt(1);
        }
        
        SQL = "INSERT INTO caseProdEntries(CasePID"+builder+")\n" +
              "VALUES("+generatedKey+stmtBuilder+")";

        preparedStatement = conn.prepareStatement(SQL);
        
        for(int count=1;count<=devValues.size();count++){
            preparedStatement.setInt(count, devValues.get(count-1));
        }

        preparedStatement.executeUpdate();
        
        return generatedKey;
        
    }
    
    static String executeCaseEntryExistsQ(LinkedHashMap<String,String> entryMap,String caseID)throws Exception{
        
        int cellEntryID = (isInteger(entryMap.get("EntryID")))?Integer.valueOf(entryMap.get("EntryID")):0;
        String SQL="";
        String entryKey=null;
   
        SQL = "SELECT caseEntries.ID\n" +
              "FROM caseEntries\n" +
              "WHERE caseEntries.CaseID = ? AND caseEntries.CellPID = ?";
    
        preparedStatement = conn.prepareStatement(SQL);
        
        preparedStatement.setString(1, caseID);
        preparedStatement.setInt(2,cellEntryID);
        
        ResultSet rs = preparedStatement.executeQuery();
        
        while(rs.next()){
            entryKey = rs.getString("ID");
        }
        
        return entryKey;
        
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
    
    static void executeUpdateAreaGoalQ(int EntryID,int Goal)throws Exception{
   
        String SQL = "UPDATE cellEntries\n" +
                     "SET cellEntries.AreaGoal = ?\n" +
                     "WHERE cellEntries.ID = ?";

        preparedStatement = conn.prepareStatement(SQL);
        preparedStatement.setInt(1,Goal);
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
    
    static String executeGetCellEntryInfoQ(String entryName)throws Exception{
        
        StringBuilder builder = new StringBuilder();
        
        String SQL = "SELECT cellEntries.*,areas.*\n" +
                     "FROM areas RIGHT JOIN cellEntries ON areas.ID = cellEntries.CellID\n" +
                     "WHERE cellEntries.EntryName = ?";
        preparedStatement = conn.prepareStatement(SQL);
        
        preparedStatement.setString(1, entryName);
        
        ResultSet rs = preparedStatement.executeQuery();
        
        while(rs.next()){
            builder.append("Entry Name: " + rs.getString("EntryName") + "\n");
            builder.append("\n");
            builder.append("Entry Date: " + rs.getDate("DateOfEntry") + "\n");
            builder.append("\n");
            builder.append("Area Name: " + rs.getString("AreaName") + "\n");
            builder.append("\n");
            builder.append("Shift: " + rs.getInt("Shift") + "\n");
            builder.append("\n");
            builder.append("Total Completed: " + rs.getInt("Total Completed") + "\n");
        }
        
        return builder.toString();
        
    }
    
    static ArrayList<ArrayList> executeCellEntriesQ(String Date,String CellArea,String Shift)throws Exception{
        
        boolean exists = false;
        ArrayList<ArrayList> theList = new ArrayList<ArrayList>();
        
        String SQL = "SELECT cellEntries.*,areas.*\n" +
                     "FROM areas RIGHT JOIN cellEntries ON areas.ID = cellEntries.CellID\n" +
                     "WHERE cellEntries.DateOfEntry = #"+Date+"# AND areas.AreaName=\"" + CellArea + "\" AND areas.Shift=" + Shift;
     
        stmt = conn.createStatement();
    
        ResultSet rs = stmt.executeQuery(SQL);
        
        while(rs.next()){
            ArrayList list = new ArrayList();
            list.add(rs.getInt("cellEntries.ID"));
            list.add(rs.getString("EntryName"));
            list.add(rs.getDate("DateOfEntry"));
            list.add(rs.getString("AreaName"));
            list.add(rs.getInt("Shift"));
            list.add(rs.getInt("Total Completed"));
            theList.add(list);
        }
        
        return theList;
    }
    
    static ArrayList executeGetTechProdEntries(ArrayList devices,int entryID)throws Exception{
        ArrayList<ArrayList> tableList = new ArrayList();
        
        StringBuilder builder = new StringBuilder();
        
        for(Object dev:devices){
            builder.append(",techProdEntries.[" + dev + "]");
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
    
    static void executeAddDeviceColumnQ(String device)throws Exception{
        
        String techEntries = "ALTER TABLE techProdEntries ADD COLUMN ["+device+ "] INTEGER";
        String techUpdate =" UPDATE techProdEntries SET techProdEntries.["+device+"] = 0";
        String caseEntries = "ALTER TABLE caseProdEntries ADD COLUMN ["+device+ "] INTEGER";
        String caseUpdate =" UPDATE caseProdEntries SET caseProdEntries.["+device+"] = 0";
        
        stmt.executeUpdate(techEntries);
        stmt.executeUpdate(techUpdate);
        stmt.executeUpdate(caseEntries);
        stmt.executeUpdate(caseUpdate);
   
    }
    
    static void executeRemoveDeviceColumnQ(String device)throws Exception{
        
        String caseEntries = "ALTER TABLE caseProdEntries DROP COLUMN " + device;   
        String techEntries = "ALTER TABLE techProdEntries DROP COLUMN " + device;
        
        stmt.executeUpdate(caseEntries);
        stmt.executeUpdate(techEntries);
        
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
    
    static void executeDeleteEmployeeEntryQ(int entryID)throws Exception{
       
        String SQL = "DELETE *\n" +
                     "FROM employees\n" +
                     "WHERE (((employees.ID)=?))";

        preparedStatement = conn.prepareStatement(SQL);
        
        preparedStatement.setInt(1, entryID);
        
        preparedStatement.executeUpdate();
    }
    
    static ArrayList executeGetCaseInfoQ(int caseID)throws Exception{
        
        ArrayList list = new ArrayList();
        int employeeID;
        
        String SQL = "SELECT caseEntries.EmployeeID, caseProdEntries.*\n" +
                     "FROM caseEntries LEFT JOIN caseProdEntries ON caseEntries.ID = caseProdEntries.CasePID\n" +
                     "WHERE (((caseEntries.ID)=?))";
        
        preparedStatement = conn.prepareStatement(SQL);

        preparedStatement.setInt(1, caseID);

        ResultSet rs = preparedStatement.executeQuery();
        
        while(rs.next()){
            
            list.add(rs.getInt("EmployeeID"));
            
            for(String item:devicesList){
                list.add(rs.getInt(item));
            } 
        }
        
        return list;
    }
    
    static ArrayList executeDeleteCaseEntryQ(String caseID)throws Exception{
        
        int ID= Integer.valueOf(caseID);
        ArrayList rsList = executeGetCaseInfoQ(ID);
        ArrayList theList = new ArrayList();
        LinkedHashMap queryMap = new LinkedHashMap<String,Integer>();
        int techID = (int)rsList.get(0);
        
        for(int i=0;i<devicesList.size();i++){
            queryMap.put(devicesList.get(i), rsList.get(i+1));
        }
        
        theList.add(techID);
        theList.add(queryMap);

        String SQL = "DELETE *\n" +
                     "FROM caseEntries\n" +
                     "WHERE (((caseEntries.ID)=?))";

        preparedStatement = conn.prepareStatement(SQL);

        preparedStatement.setInt(1, ID);

        preparedStatement.executeUpdate();
        
        return theList;

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
    
    static void executeEmployeesAppendQ(String techID,String FName,String LName,String Position)throws Exception{
       
        String SQL = "INSERT INTO employees ( TechID,[First Name],[Last Name],Position )\n" +
                 "VALUES (?,?,?,?)";

        preparedStatement = conn.prepareStatement(SQL);
        
        preparedStatement.setString(1, techID);
        preparedStatement.setString(2, FName);
        preparedStatement.setString(3, LName);
        preparedStatement.setString(4, Position);

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
        StringBuilder builder = new StringBuilder();
        int sum = 0;
        
        for(String device:devicesList){
            builder.append(", techProdEntries.[" + device + "] ");
        }
        
        String SQL = "SELECT employees.TechID, employees.[First Name], employees.[Last Name]" + builder +
                     "FROM employees LEFT JOIN techProdEntries ON employees.ID = techProdEntries.EmployeeID\n" +
                     "WHERE (((techProdEntries.prodID)="+entryID+"))";
        
        stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(SQL);
        
        while(rs.next()){
            
            ArrayList row = new ArrayList();
            sum=0;
            
            row.add(rs.getString("TechID"));
            row.add(rs.getString("First Name"));
            row.add(rs.getString("Last Name"));
            
            for(String dev:devicesList){
                sum += rs.getInt(dev);
                row.add(rs.getInt(dev));
            }

            row.add(sum);
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
        
//        String sql = "SELECT cellEntries.ID, cellEntries.DateOfEntry, areas.AreaName, areas.Shift, cellEntries.[Total Completed], areas.[Net Goal Total], failCountQuery.CountOfprodID\n" +
//                     "FROM (areas RIGHT JOIN cellEntries ON areas.ID = cellEntries.CellID) LEFT JOIN failCountQuery ON cellEntries.ID = failCountQuery.ID\n" +
//                     "WHERE "+ dateOfEntry + builder + "\n" +
//                     "ORDER BY cellEntries.DateOfEntry";

        String sql = "SELECT cellEntries.ID, cellEntries.DateOfEntry, areas.AreaName, areas.Shift, cellEntries.[Total Completed], cellEntries.AreaGoal, failCountQuery.CountOfprodID\n" +
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
            netGoal = rs.getInt("AreaGoal");
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
    
    static ArrayList executeGetEmployeesQ()throws Exception{
        ArrayList<ArrayList> employeeList = new ArrayList<ArrayList>();
        String name = " ";
        String SQL = "SELECT employees.ID, employees.TechID, employees.[First Name], employees.[Last Name]\n" +
                     "FROM employees;";
        
        ResultSet rs = stmt.executeQuery(SQL);
        
        while(rs.next()){
            
            ArrayList row = new ArrayList();
            
            row.add(rs.getInt("ID"));
            row.add(rs.getString("TechID"));
            row.add(rs.getString("First Name"));
            row.add(rs.getString("Last Name"));
            
            employeeList.add(row);
        }
        
        return employeeList;
    }
    
    static ArrayList getAreaInfoFromIDQ(int areaID){
        
        ArrayList infoArray = new ArrayList();
        String SQL = "SELECT areas.AreaName, areas.Shift\n" +
                     "FROM areas\n" +
                     "WHERE areas.ID = ?";
        ResultSet rs;
        try{
            
            preparedStatement = conn.prepareStatement(SQL);
            preparedStatement.setInt(1, areaID);
            
            rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                infoArray.add(rs.getString("AreaName"));
                infoArray.add(rs.getInt("Shift"));
            }
            
        }catch(Exception e){
            System.out.println("Area "+e.toString());
        }

        return infoArray;
    }
    
    static ArrayList executeGetCasesQ(String field, String condition, String value)throws Exception{
        
        ArrayList<ArrayList> casesList = new ArrayList<ArrayList>();
        StringBuilder conditionBuilder = new StringBuilder();
        
        switch(field){
            case "DateOfEntry":
                value = "#"+value+"#";
                break;
            case "CaseID":
                value = "\""+value+"\"";
                break;
            case "UserID":
                value = "\""+value+"\"";
                break;
            case "EmployeeID":
                value = String.valueOf(getEmployeeID(value));
                break;
        }
        
        conditionBuilder.append(field + " " + condition + " " + value);

        String SQL = "SELECT *\n" +
                     "FROM caseEntries\n" +
                     "WHERE caseEntries." + conditionBuilder;
        
        stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery(SQL);
        
        while(rs.next()){
            
            ArrayList row = new ArrayList();
            
            row.add(rs.getInt("ID"));
            row.add(rs.getDate("DateOfEntry"));
            row.add(rs.getString("Time"));
            row.add(getTechIDQ(rs.getInt("EmployeeID")));
            row.add(rs.getInt("CaseID"));
            row.add(rs.getInt("TotalUnits"));
            row.add(rs.getInt("UserID"));
            
            casesList.add(row);
        }
        
        return casesList;
    }
    
    static ArrayList executeGetAreasQ(String fromDate,String toDate)throws Exception{
        
        ArrayList<ArrayList> areasList = new ArrayList<ArrayList>();

        String SQL = "SELECT cellEntries.ID, cellEntries.DateOfEntry, cellEntries.CellID, cellEntries.AreaGoal\n" +
                     "FROM cellEntries\n" +
                     "WHERE ((([cellEntries].[DateOfEntry]) Between #"+fromDate+"# And #"+toDate+"#))";

        stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery(SQL);
        
        while(rs.next()){
            
            ArrayList row = new ArrayList();
            ArrayList info = new ArrayList();
            
            row.add(rs.getInt("ID"));
            row.add(rs.getDate("DateOfEntry"));
            info = getAreaInfoFromIDQ(rs.getInt("CellID"));
            row.add(info.get(0));
            row.add(info.get(1));
            row.add(rs.getInt("AreaGoal"));
            
            areasList.add(row);
        }
        
        return areasList;
    }
    
    static ArrayList executeGetEntryDevicesQ(int entryID)throws Exception{
        
        ArrayList<String> deviceList = new ArrayList<String>();
        
        StringBuilder builder = new StringBuilder();
        
        for(String device:devicesList){
            builder.append(", techProdEntries.["+device+"]");
        }
   
        String SQL = "SELECT cellEntries.ID" + builder + "\n" +
                     "FROM cellEntries LEFT JOIN techProdEntries ON cellEntries.ID = techProdEntries.prodID\n" +
                     "WHERE (((cellEntries.ID)="+entryID+"))";

        ResultSet rs = stmt.executeQuery(SQL);
        
        while(rs.next()){
            
            for(String device:devicesList){
                
                int dev = rs.getInt(device);
                
                if(dev>0){
                    if(!deviceList.contains(device)){
                        deviceList.add(device);
                    }    
                }
            } 
        }
        
        return deviceList;
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
            errorLogger.writeToLogger(e.toString());
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
            errorLogger.writeToLogger(e.toString());
        }
    }
    
    static String getTechIDQ(int EmployeeID){
        
        String techID = new String();
        
        try{
            
            String SQL = "SELECT employees.TechID\n" +
                         "FROM employees\n" +
                         "WHERE employees.ID = ?";

            preparedStatement = conn.prepareStatement(SQL);

            preparedStatement.setInt(1, EmployeeID);

            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                techID = rs.getString("TechID");
            }
     
        }catch(Exception e){
            System.out.println(e.toString());
            errorLogger.writeToLogger(e.toString());
        }
        return techID;
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
            errorLogger.writeToLogger(e.toString());
        }
    }
    
    static public String readLocSave(){
        
        String location = " ";
        
        try{
            
            File tmpDir = new File("Data\\dbLocation.txt");
            boolean exists = tmpDir.exists();

            if(exists){
                for(String loc:Files.readAllLines(Paths.get("Data\\dbLocation.txt"))){
                    location = loc;
                }
            }else{
                
            }
        }catch(Exception e){
            System.out.println(e.toString());
            errorLogger.writeToLogger(e.toString());
        }
        
                    
        return location;
    }
    
    static public String readSKULocSave(){
        
        String location = " ";
        
        try{
            
            File tmpDir = new File("Data\\skuLocation.txt");
            boolean exists = tmpDir.exists();

            if(exists){
                for(String loc:Files.readAllLines(Paths.get("Data\\skuLocation.txt"))){
                    location = loc;
                }
            }else{
                tmpDir.createNewFile();
            }
        }catch(Exception e){
            System.out.println(e.toString());
            errorLogger.writeToLogger(e.toString());
        }
              
        return location;
    }
    
    private void readFileSave(){

        try{
            File tmpDir = new File("Data\\areasList.txt");
            File tmpDir2 = new File("Data\\devicesList.txt");
            File tmpDir3 = new File("Data\\employeesList.txt");
         
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
                    for(String area:Files.readAllLines(Paths.get("Data\\areasList.txt"))){
                        if(!areasList.contains(area)){
                           areasList.add(area);
                        }
                    }

                    for(String dev:Files.readAllLines(Paths.get("Data\\devicesList.txt"))){
                        if(!devicesList.contains(dev)){
                           devicesList.add(dev);
                        }
                    }

                    for(String employee:Files.readAllLines(Paths.get("Data\\employeesList.txt"))){
                        if(!employeesList.contains(employee)){
                           employeesList.add(employee);
                        }
                    }
                }
                
            }   
            
        }catch(Exception e){
            System.out.println(e.toString());
            errorLogger.writeToLogger(e.toString());
        }
        
    }
    
    private void writeToFileSave(){
  
        try{
            PrintWriter writer = new PrintWriter("Data\\areasList.txt", "UTF-8");
            PrintWriter writer2 = new PrintWriter("Data\\devicesList.txt", "UTF-8");
            PrintWriter writer3 = new PrintWriter("Data\\employeesList.txt", "UTF-8");
            
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
            errorLogger.writeToLogger(e.toString());
        }
   
    }
}
