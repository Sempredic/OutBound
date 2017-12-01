/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table;

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
    
    private void initTableData(){
        columnTable = new String [] {
                "Tech #","Name","Classic", "Nano", "Shuffle", "Touch","Pad","Phone", "Tech Total"
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
    
    public String[][] getDataTableFromList(String dataTableID){
        return dataTableList.get(dataTableID);
    }
    
}
