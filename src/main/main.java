/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import exceltest.DatabaseObj;
import exceltest.errorLogger;
import exceltest.initFrame;

/**
 *
 * @author Vince
 */
public class main {
    
    public static void main(String args[]){
        
        errorLogger logger = new errorLogger();

        DatabaseObj DB = new DatabaseObj();
        
        initFrame init = new initFrame();
        init.run(); 
  

    }
}
