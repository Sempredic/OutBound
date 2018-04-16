/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import static exceltest.DatabaseObj.areasList;
import java.io.PrintWriter;

/**
 *
 * @author Vince
 */
public class errorLogger {
    
    static public PrintWriter errorWriter;
    
    public errorLogger()throws Exception{
        errorWriter = new PrintWriter("errorLogger.txt", "UTF-8");
    }
    
    private void writeToFileSave(){
 
   
    }
    
}
