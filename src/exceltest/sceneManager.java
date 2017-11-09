/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import java.util.LinkedHashMap;
import table.TableManager;

/**
 *
 * @author 311015
 */
public class sceneManager{
    
    
    LinkedHashMap<String,String> rosterList;
        
    TableManager tManager;
    mainFrame mFrame;
    ExcelFrame exFrame;
    
    private static sceneManager instance;
        
    
    public sceneManager(){
        
    }
    
    public sceneManager getManager(){
        if (instance == null) {
         instance = new sceneManager();
      }

        return instance;
    }
    
    public void setExcelFrame(){
        this.exFrame = new ExcelFrame(tManager.getTable(0));
        this.exFrame = exFrame;
    }
    
    public void setRoster(LinkedHashMap<String,String> rosterList){
        this.rosterList = rosterList;
        
    }
    
    public void setMainFrame(mainFrame mFrame){
        this.mFrame = mFrame;
    }
    
    public void runExcelFrame(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                exFrame.setVisible(true);
               
            }
        });
    }
    
    public void runMainFrame(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                mFrame.setVisible(true);
            }
        });
    }
    
    public void setTable(){
        tManager = new TableManager(rosterList);
        //add hour table with pre chosen roster
        tManager.addTable();
        
    }
    
    public void displayRoster(){
        System.out.println(rosterList);
    }
   
    
}
