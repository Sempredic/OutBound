/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import java.util.LinkedHashMap;
import javax.swing.JFrame;
import table.TableManager;

/**
 *
 * @author 311015
 */
public class sceneManager implements Runnable{
    
    
    static LinkedHashMap<String,String> rosterList;
        
    static TableManager tManager;
    mainFrame mFrame;
    ExcelFrame exFrame;
    
    private static sceneManager instance;
        
    
    public sceneManager(){
        rosterList = new LinkedHashMap<String,String>();
    }
    
    public static sceneManager getManager(){
        if (instance == null) {
         instance = new sceneManager();
      }

        return instance;
    }
    
    public void run(){
        
    }
    
    public void setExcelFrame(){
        exFrame = new ExcelFrame(tManager.getTable(0));
        
    }
    
    public static void setRoster(LinkedHashMap<String,String> rosterList){
        
        sceneManager.rosterList = rosterList;
        
    }
    
    public void setMainFrame(mainFrame mFrame){
        this.mFrame = mFrame;
    }
    
    public void runFrame(JFrame frame){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame nFrame = new JFrame();
                nFrame = frame;
                frame.setVisible(true);
                
            }
        });
    }
    
    public static void prepExcelFrame(){
        
        
        //setRoster(rosterList);
        sceneManager.getManager().setTable();
        sceneManager.getManager().setExcelFrame();
        sceneManager.getManager().runFrame(sceneManager.getManager().exFrame);
    }
    
    public void runMainFrame(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                mFrame.setVisible(true);
             
            }
           
        });
        
        
    }
    
    public mainFrame getMainFrame(){
        return mFrame;
    }
    public void setTable(){
        tManager = new TableManager(rosterList);
        //add hour table with pre chosen roster
        tManager.addTable();
        
    }
    
    public static void displayRoster(){
        System.out.println(sceneManager.rosterList);
    }
   
    
}
