/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import java.util.ArrayList;

/**
 *
 * @author Vince
 */
public class techObject {
    
    String techID;
    ArrayList<String> devType_s;
    int prodRow;
    int prodCol;
    
    public techObject(){
        techID = null;
        devType_s = new ArrayList<String>();
        prodRow = 0;
        prodCol =0;
    }
    
    public void setTechID(String techID){
        this.techID = techID;
    }
    
    public void addTechDevice(String device){
        devType_s.add(device);
    }
    public void addTechDevices(String[]devices){
        
        for(String dev:devices){
            devType_s.add(dev);
        }
    }
    public void setTechRC(int row, int col){
        prodRow = row;
        prodCol = col;
    }
    
    public int getDeviceCount(){
        return devType_s.size();
    }
    
    public ArrayList<String> getDevices(){
        return devType_s;
    }
    
    public String getTechID(){
        return this.techID;
    }
   
    
    
}
