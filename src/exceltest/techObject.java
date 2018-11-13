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
    String labelerID;
    ArrayList<String> devType_s;
    int prodRow;
    int prodCol;
    String caseID;
    
    public techObject(){
        techID = null;
        labelerID = null;
        devType_s = new ArrayList<String>();
        prodRow = 0;
        prodCol =0;
        caseID = null;
    }
    
    public void setTechID(String techID){
        this.techID = techID;
    }
    
    public void setLabelerID(String labelerID){
        this.labelerID = labelerID;
    }
    
    public void setCaseID(String caseID){
        this.caseID=caseID;
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
    
    public String getCaseID(){
        return this.caseID;
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
    
    public String getLabelerID(){
        return this.labelerID;
    }
   
    
    
}
