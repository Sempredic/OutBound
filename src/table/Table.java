/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table;

import java.awt.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
    String[] blankTable;
    Date tableDate;
    DateFormat hour;
    
    
    
    public Table(HashMap<String,String> roster){
        this.roster = roster;
        dataTableID=" ";
       
        tRosterNames = new ArrayList<String>();
        tRosterTechNum = new ArrayList<String>(); 
        dataTableList = new LinkedHashMap<String,String[][]>();

        setRosterNames();
        setRosterTechNum();
        initTableData(); 
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
    
    public boolean isDTListEmpty(){
        return dataTableList.isEmpty();
    }
    
    private void initTableData(){
        columnTable = new String [] {
                "Tech #","Name","Classic", "Nano", "Shuffle", "Touch","Pad",
                    "Phone", "Tech Total"
            };
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
                        dataTable[row][col]= "Total Dev";
                   }else if(col == 1){
                       dataTable[row][col] = " ";
                   }else{
                       dataTable[row][col] = "0";
                   } 
                }
                //System.out.print(dataTable[row][col]+" ");
            }
            //System.out.println();
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
        
        hour = new SimpleDateFormat("hh:mm aa");
        tableDate = new Date();
        dataTableID = hour.format(tableDate);
        
        
        
        for(int i=0;i<numRow;i++){
            for(int j=0;j<numCol;j++){
                table[i][j] = tModel.getValueAt(i, j).toString();
                tempTable[i][j] = 0;
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
                table[i][j] = String.valueOf(value);    
            } 
        } 
        
        dataTableList.put(dataTableID, table);
        
        return dataTableID;
    }
   
    
    public String[][] getDataTableFromList(String dataTableID){
        return dataTableList.get(dataTableID);
    }
    
}
