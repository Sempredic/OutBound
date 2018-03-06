/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import java.util.ArrayList;

/**
 *
 * @author 311015
 */
public class cellArea {
    
    private ArrayList<String> deviceType;
    private String name;
    
    cellArea(String name, String[] deviceTypes){
        this.name = " ";
        deviceType = new ArrayList<String>();
        
        this.name = name;
        
        for(String device:deviceTypes){
            deviceType.add(device);
        }
    }
    
    cellArea(String name){
        this.name = " ";
        deviceType = new ArrayList<String>();
        
        this.name = name;
        
    }
    
    cellArea(){
        this.name = " ";
        deviceType = new ArrayList<String>();
        
    }
    
    ArrayList<String> getDeviceTypes(){
        return this.deviceType;
    }
    
    String getAreaName(){
        return this.name;
    }
    
    public void addDeviceType(String device){
        deviceType.add(device);
    }
    
    public void updateAreaName(String name){
        this.name = name;
    }
    
}
