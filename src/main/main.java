/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import exceltest.ExcelFrame;
import java.util.LinkedHashMap;
import table.TableManager;

/**
 *
 * @author Vince
 */
public class main {
    
    public static void main(String args[]){
        
        LinkedHashMap<String,String> rosterList = new LinkedHashMap<String,String>();
        
        
        rosterList.put("2277", "A");   
        rosterList.put("2273", "B");
        rosterList.put("1234", "C");
        rosterList.put("0000", "DICK MAN");
        
        
 
        
        TableManager tManager = new TableManager(rosterList);
        //add hour table with pre chosen roster
        tManager.addTable();
        
        ExcelFrame frame = new ExcelFrame(tManager.getTable(0));
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frame.setVisible(true);
            }
});
    }
}
