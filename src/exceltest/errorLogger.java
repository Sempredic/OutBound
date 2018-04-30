/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import static java.time.OffsetTime.now;
import java.util.Date;

/**
 *
 * @author Vince
 */
public class errorLogger {
    
    static private PrintWriter logWriter;
    static private Date date;
    
    public errorLogger(){
 
        date = new Date();
        
        File file = new File("Data\\errorLogger.txt");
        
        try{
            
            if(!file.exists()){
                file.createNewFile();
            }
            
            logWriter = new PrintWriter(new FileOutputStream(file,true)); 
        }catch(Exception e){
            System.out.println(e.toString());
            errorLogger.writeToLogger(e.toString());
        }
        
    }
    
    static public void writeToLogger(String text){
        logWriter.println(text + "     " + DateFormat.getDateTimeInstance().format(date));
        logWriter.close();
    }
    
}
