/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import exceltest.DatabaseFrame;
import exceltest.DatabaseObj;
import exceltest.mainFrame;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
//import exceltest.sceneManager;

/**
 *
 * @author Vince
 */
public class main {
    
    public static void main(String args[]){
        
        DatabaseObj DB = new DatabaseObj();
        
        String[] options = {"Outbound Tool","Database Tool (login required)"};
        
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
                    mainFrame frame = new mainFrame();
                    frame.run();
                    break;
                case "Database Tool (login required)":
                    DatabaseFrame dbFrame = new DatabaseFrame();
                    dbFrame.run();
                    break;
            }
        }

    }
}
