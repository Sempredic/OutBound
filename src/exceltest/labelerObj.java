/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

/**
 *
 * @author Vince
 */
public class labelerObj {
    
    static FileInputStream fis;
    Code128Bean labelBarcode;
    XWPFDocument doc;
    String label;
    String year;
    final int dpi = 160;
    BitmapCanvasProvider canvas;
    
    
    labelerObj(String label,String year)throws Exception{
        
        fis = null;
        labelBarcode = new Code128Bean();
        doc = null;
        this.label = label;
        this.year = year;
        
    }
    
    labelerObj(){doc=null;}
    
    public void generateDevice(String text)throws Exception{
            
        XWPFDocument doc = new XWPFDocument(OPCPackage.open("Data\\labelLayout.docx"));

        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                  
                for (XWPFTableCell cell : row.getTableCells()) {
                    if(cell.getColor()==null){
                        cell.removeParagraph(0);
                        
                        XWPFParagraph p = cell.addParagraph();
                        p.setAlignment(ParagraphAlignment.CENTER);

                        XWPFRun run = p.createRun();

                        run.setText(text);
                        run.setFontSize(10);
                    }
                          
                }
            }
        }
        
        doc.write(new FileOutputStream(new File("Data\\label.doc")));

        File file = new File("Data\\label.doc");
        
        Runtime.getRuntime().exec(new String[]{"rundll32", "shell32.dll,ShellExec_RunDLL", file.getAbsolutePath()});
 
    }

    public void generateTech()throws Exception{
        Code128Bean label = new Code128Bean();

        //Configure the barcode generator
        label.setModuleWidth(UnitConv.in2mm(2.8f / dpi));
        label.setBarHeight(5);
        label.doQuietZone(false);
        label.setFontSize(1.5);

        //Open output file
        File outputFile = new File("Data\\Barcode.jpg");

        FileOutputStream out = new FileOutputStream(outputFile);

        BitmapCanvasProvider canvas = new BitmapCanvasProvider(
            out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

        //Generate the barcode
        label.generateBarcode(canvas,this.label);

        //Signal end of generation
        canvas.finish();

        fis = new FileInputStream("Data\\Barcode.jpg");

        XWPFDocument doc = new XWPFDocument(OPCPackage.open("Data\\labelLayout.docx"));

        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                  
                for (XWPFTableCell cell : row.getTableCells()) {
                    if(cell.getColor()==null){
                        cell.removeParagraph(0);
                        
                        XWPFParagraph p = cell.addParagraph();
                        p.setAlignment(ParagraphAlignment.CENTER);

                        XWPFRun run = p.createRun();

                        fis = new FileInputStream("Data\\Barcode.jpg");

                        run.addPicture(fis, XWPFDocument.PICTURE_TYPE_JPEG,null, Units.toEMU(105), Units.toEMU(25));
                        run.setText("/"+year);
                        run.setFontSize(8);
                    }
                          
                }
            }
        }
        
        doc.write(new FileOutputStream(new File("Data\\label.doc")));

        File file = new File("Data\\label.doc");
        
        Runtime.getRuntime().exec(new String[]{"rundll32", "shell32.dll,ShellExec_RunDLL", file.getAbsolutePath()});
 
    }
    
}
