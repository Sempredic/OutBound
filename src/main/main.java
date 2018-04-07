/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import exceltest.DatabaseObj;
import exceltest.mainFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
//import exceltest.sceneManager;

/**
 *
 * @author Vince
 */
public class main {
    
    public static void main(String args[]){
          //STEP 3: Open a connection
        String[] options = {"Outbound Tool","Database Viewer(login required)"};
        
        String selection = (String)JOptionPane.showInputDialog(
                            null,
                            new JLabel("Applications", SwingConstants.CENTER),
                            "Select Application",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            options,
                            null);
        
        
        if(selection != null){
            switch(selection){
                case "Outbound Tool":
                    DatabaseObj DB = new DatabaseObj();
                    mainFrame frame = new mainFrame();
                    frame.run();
                    break;
                case "Database Viewer(login required)":
                    System.out.println("Launching Database Viewer");
                    break;
            }
        }

    }
}
