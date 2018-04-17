/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Vince
 */
public class Table{
    
    ArrayList<String> tRosterNames;
    ArrayList<String> tRosterTechNum;
    LinkedHashMap<String,String[][]> dataTableList;
    HashMap<String,String> roster;
    String dataTableID;
    String[][] table;
    Object[][] dataTable;
    String[] columnTable;
    String[][] saveTable;
    Date tableDate;
    DateFormat hour;
    HashMap<String,String>techAvgList;
    ArrayList<String> newAreaList;
    LinkedHashMap<String,String> databaseEntryInfo;


    public Table(HashMap<String,String> roster,ArrayList<String> areaList,LinkedHashMap<String,String> databaseEntryInfo,boolean dbExist){
        
        this.roster = roster;
        dataTableID= " ";
       
        tRosterNames = new ArrayList<String>();
        tRosterTechNum = new ArrayList<String>(); 
        dataTableList = new LinkedHashMap<String,String[][]>();
        techAvgList= new HashMap<>();
        newAreaList = areaList;
        saveTable = table;
        this.databaseEntryInfo = databaseEntryInfo;

        if(dbExist){
        
            initExistingDBTable();
            
        }else{
            setRosterNames();
            setRosterTechNum();
            initTableData();
        }   
    }
    
    public Table(HashMap<String,String> roster,LinkedHashMap<String,String> databaseEntryInfo,Object[][]saveTable){
        
        ArrayList<String> deviceList = new ArrayList<String>();
        
        this.roster = roster;
        dataTableID= " ";
        this.databaseEntryInfo = databaseEntryInfo;

       
        tRosterNames = new ArrayList<String>();
        tRosterTechNum = new ArrayList<String>(); 
        dataTableList = new LinkedHashMap<String,String[][]>();
        techAvgList= new HashMap<>();
        
        setRosterNames();
        setRosterTechNum();
        
        columnTable = new String[saveTable[0].length];
 
        for(int i = 0;i<saveTable[0].length;i++){
            columnTable[i] = (String)saveTable[0][i];
            if((i>1)&&(i<saveTable[0].length-1)){
                deviceList.add((String)saveTable[0][i]);
            }
        }
        
        newAreaList = deviceList;

        dataTable = new Object[tRosterTechNum.size()+1][columnTable.length];
        
        for(int row=0;row<tRosterTechNum.size()+1;row++){
            for(int col=0;col<columnTable.length;col++){
              
                dataTable[row][col] = saveTable[row+1][col];
            }
        }

    }
    
    public void addToRoster(String techNumber,String techName){
        tRosterNames.add(techName);
        tRosterTechNum.add(techNumber);
    }
    
    public Object[][] getDataTable(){
        return dataTable;
    }
    public String[] getcolumnTable(){
        return columnTable;
    }
    
    public int getEntryID(){
      
        if(databaseEntryInfo.get("EntryID")== " "){
            return 0;
        }
        return Integer.parseInt(databaseEntryInfo.get("EntryID"));
    }
    
    public LinkedHashMap<String,String> getDBEntryInfo(){
        return databaseEntryInfo;
    }
    
    public ArrayList<String> getAreaDevices(){
        return newAreaList;
    }
    
    public boolean isDTListEmpty(){
        return dataTableList.isEmpty();
    }
    
    private void initTableData(){
        

        columnTable = new String[newAreaList.size()+3];
     
        columnTable[0] = "Tech#";
        columnTable[1] = "Name";
        
        for(int i = 0;i<newAreaList.size();i++){
            columnTable[i+2] = newAreaList.get(i);
        }
        
        columnTable[columnTable.length-1] = "TechTotal";
        
        dataTable = new Object[tRosterTechNum.size()+1][columnTable.length];
        
        for(int row=0;row<tRosterTechNum.size()+1;row++){
            for(int col=0;col<columnTable.length;col++){
                if(row<tRosterTechNum.size()){
                   if(col == 0){
                        dataTable[row][col]= tRosterTechNum.get(row);
                   }else if(col == 1){
                       dataTable[row][col] = tRosterNames.get(row);
                   }else{
                       dataTable[row][col] = "0";
                   } 
                }else{
                    if(col == 0){
                        dataTable[row][col]= "TotalDev";
                   }else if(col == 1){
                       dataTable[row][col] = " ";
                   }else{
                       dataTable[row][col] = "0";
                   } 
                }
            }
        }
    }
    
    private void initExistingDBTable(){
        
        ArrayList<ArrayList> dataTableList = new ArrayList();
        ArrayList<String> deviceList = new ArrayList();
        this.roster.clear();
       
        try{
            
            dataTableList = DatabaseObj.executeGetTechProdEntries(newAreaList, getEntryID());
            
            for(int i=0;i<dataTableList.size();i++){
                this.roster.put((String)dataTableList.get(i).get(0),DatabaseObj.getEmployeeNameQ((String)dataTableList.get(i).get(0)));
            }
            setRosterNames();
            setRosterTechNum();
            
            columnTable = new String[newAreaList.size()+3];
     
            columnTable[0] = "Tech#";
            columnTable[1] = "Name";

            for(int i = 0;i<newAreaList.size();i++){
                columnTable[i+2] = newAreaList.get(i);
            }

            columnTable[columnTable.length-1] = "TechTotal";

            dataTable = new Object[tRosterTechNum.size()+1][columnTable.length];

            for(int row=0;row<tRosterTechNum.size()+1;row++){
                for(int col=0;col<columnTable.length;col++){
                   if(row<tRosterTechNum.size()){
                   
                       if(col == 0){
                           dataTable[row][col] = tRosterTechNum.get(row);
                       }else if(col == 1){
                           dataTable[row][col] = tRosterNames.get(row);
                       }else if(col == columnTable.length-1){
                           dataTable[row][col] = "0";
                       }else{
                           dataTable[row][col] = dataTable[row][col] = dataTableList.get(row).get(col-1);
                       }
                    }else{
                        if(col == 0){
                            dataTable[row][col]= "TotalDev";
                       }else if(col == 1){
                           dataTable[row][col] = " ";
                       }else{
                           dataTable[row][col] = "0";
                       } 
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e.toString());
            errorLogger.writeToLogger(e.toString());
        }
    }

    private void setRosterNames(){
        for(String key:roster.keySet()){
            tRosterNames.add(roster.get(key));
        } 
    }
    
    private void setRosterTechNum(){
        for(String key:roster.keySet()){
            tRosterTechNum.add(key);
        }
    }
    
    public ArrayList<String> getRosterNames(){
        
        return tRosterNames;  
    }
    
    public ArrayList<String> getRosterNum(){
        
        return tRosterTechNum;  
    }
    
    public boolean checkDTExists(String timeID){
        if(dataTableList.containsKey(timeID)){
            return true;
        }else{
            return false;
        }
    }
    
    public String updateTable(DefaultTableModel tModel){
        int numCol = tModel.getColumnCount();
        int numRow = tModel.getRowCount();
        this.table = new String[numRow][numCol];
        hour = new SimpleDateFormat("hh:mm aa");
        tableDate = new Date();
        dataTableID = hour.format(tableDate);
        
        for(int i=0;i<numRow;i++){
            for(int j=0;j<numCol;j++){
                table[i][j] = tModel.getValueAt(i, j).toString();
            }
        } 
        
        dataTableList.put(dataTableID, table);
        this.saveTable = table;
        
        updateTechAvgList(tModel);
        
        return dataTableID;
    }
    
    public String[][] toStringArray(DefaultTableModel tModel){
        int numCol = tModel.getColumnCount();
        int numRow = tModel.getRowCount();
        String[][] newTable = new String[numRow][numCol];
        
        for(int i=0;i<numRow;i++){
            for(int j=0;j<numCol;j++){
                newTable[i][j] = tModel.getValueAt(i, j).toString();
            }
        }
        
        return newTable;
        
    }
    
    public String updateTableViaList(DefaultTableModel tModel,String[] timeList){

        int numCol = tModel.getColumnCount();
        int numRow = tModel.getRowCount();
        
        Integer[][] tempTable = new Integer[numRow][numCol];
        this.table = new String[numRow][numCol];
        String[][] dummyTable = new String[numRow][numCol];
        
        hour = new SimpleDateFormat("hh:mm aa");
        tableDate = new Date();
        dataTableID = hour.format(tableDate);
         
        for(int i=0;i<numRow;i++){
            for(int j=0;j<numCol;j++){
                table[i][j] = tModel.getValueAt(i, j).toString();
                tempTable[i][j] = 0;
                dummyTable[i][j] = tModel.getValueAt(i, j).toString();
            }
        }

        for(String time:timeList){
            int curRow = getDataTableFromList(time).length;
            int curCol = getDataTableFromList(time)[0].length;
            int offSet = numRow-curRow;
            
            String[][]newTable = new String[curRow][curCol];
            
            newTable = getDataTableFromList(time);
            
            for(int i=0;i<curRow;i++){
                for(int j=2;j<curCol;j++){
                    tempTable[i+offSet][j] += Integer.valueOf(newTable[i][j]);
                }
            }
        }

        for(int i=0;i<numRow;i++){
            for(int j=2;j<numCol;j++){
                int v1 = Integer.valueOf(table[i][j]);
                int v2 = tempTable[i][j];
                int value = v1-v2;

                value=Math.abs(value);
                
                if(value >= 0){
                    table[i][j] = String.valueOf(value);
                }else{
                    table[i][j] = "0";
                }
                    
            } 
        } 
        
        dataTableList.put(dataTableID, table);
        
        updateTechAvgList(tModel);
        
        return dataTableID;
    }
    
    public void updateTechAvgList(DefaultTableModel tModel){
        
        for(String tech:getRosterNum()){
            int row = getTechRow(tech);
            int col = tModel.findColumn("TechTotal");
            String value = table[row][col];

            if(!techAvgList.containsKey(tech)){
                techAvgList.put(tech, value);
            }else{
                int newVal = Integer.valueOf(techAvgList.get(tech)) + Integer.valueOf(value);
                techAvgList.replace(tech, String.valueOf(newVal));
            }
        }
    }
    
    public HashMap<String,String> getTechAvgList(){
        return techAvgList;
    }
    
    private int getTechRow(String tech){
        int result = 0;
        
        for(int i=0;i<table.length;i++){
            if(table[i][0].equals(tech)){
                result = i;
            }
        }
        
        return result;
    }
    
    private int getDataTableTechRow(String tech){
        int row = 0;
        
        for(int i=0;i<dataTable.length;i++){
            if(dataTable[i][0].equals(tech)){
                row = i;
            }
        }
        
        return row;
    }
    
    private int getDataTableDeviceCol(String device){
        int col = 0;
        
        for(int i=0;i<dataTable[0].length;i++){
            if(dataTable[0][i].equals(device)){
                col = i;
            }
        }      
        return col;
    }
   
    
    public String[][] getDataTableFromList(String dataTableID){
        return dataTableList.get(dataTableID);
    }
    
    public void updateTableSave(DefaultTableModel tModel){
        int numCol = tModel.getColumnCount();
        int numRow = tModel.getRowCount();
        this.table = new String[numRow+1][numCol];
        
        for(int i=0;i<numRow+1;i++){
            for(int j=0;j<numCol;j++){
                if(i==0){
                    table[i][j] = tModel.getColumnName(j);
                }else{
                    table[i][j] = tModel.getValueAt(i-1, j).toString();
                }
                
            }
        } 
        
        this.saveTable = table;

    }
    
    public void writeSaveTable(){
        //String tName = new String();
        
        try{
            
            PrintWriter writer = new PrintWriter("Data\\save.txt", "UTF-8");
            writer.println(table.length + "," + table[0].length);
            
            for(int i=0;i<table.length;i++){
                for(int j=0;j<table[0].length;j++){
                    
                    if(table[i][j]!=" "){
                        writer.print(table[i][j]+" ");
                    }else{
                        writer.print(table[i][j]);
                    }
                    
                }
                writer.println();
            }
            
            writer.close();
        }catch(Exception e){
            //System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Save Error",
                    "Error",
                    JOptionPane.PLAIN_MESSAGE);
            errorLogger.writeToLogger(e.toString());
        }
    }
    
}
