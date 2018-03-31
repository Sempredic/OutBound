/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import exceltest.DatabaseObj;
import exceltest.mainFrame;
//import exceltest.sceneManager;

/**
 *
 * @author Vince
 */
public class main {
    
    public static void main(String args[]){
          //STEP 3: Open a connection
        DatabaseObj DB = new DatabaseObj();
        mainFrame frame = new mainFrame();
        frame.run();
  
    }
}
