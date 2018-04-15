/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import exceltest.DatabaseFrame;
import exceltest.DatabaseObj;
import exceltest.initFrame;
import exceltest.mainFrame;
import exceltest.techLabeler;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 *
 * @author Vince
 */
public class main {
    
    public static void main(String args[]){
        
        DatabaseObj DB = new DatabaseObj();
        
        initFrame init = new initFrame();
        init.run(); 

    }
}
