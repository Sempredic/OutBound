/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
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
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObject;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;


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
    
    public void generateDevice(ArrayList<String> text)throws Exception{
            
        XWPFDocument doc = new XWPFDocument(OPCPackage.open("Data\\labelLayout.docx"));

        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                  
                for (XWPFTableCell cell : row.getTableCells()) {
                    if(cell.getColor()==null){
                        cell.removeParagraph(0);
                        
                        XWPFParagraph p = cell.addParagraph();
                        
                        p.setAlignment(ParagraphAlignment.CENTER);
                       
                        XWPFRun run = p.createRun();
                        run.setFontSize(8);
                        
                        for(String txt:text){
                            
                            run.setText(txt);
                            run.addBreak();
                        }     
                    }
                          
                }
            }
        }
        
        doc.write(new FileOutputStream(new File("Data\\label.doc")));

        File file = new File("Data\\label.doc");
        
        Runtime.getRuntime().exec(new String[]{"rundll32", "shell32.dll,ShellExec_RunDLL", file.getAbsolutePath()});
 
    }

    public void generateTech()throws Exception{
        Code39Bean label = new Code39Bean();

        //Configure the barcode generator
        label.setModuleWidth(UnitConv.in2mm(2.8f / dpi));
        label.setBarHeight(5);
        label.doQuietZone(false);
        label.setFontSize(2);

        //Open output file
        File outputFile = new File("Data\\Barcode.png");

        FileOutputStream out = new FileOutputStream(outputFile);
        
        
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(
            out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

        //Generate the barcode
        label.setFontSize(3);
        label.generateBarcode(canvas,this.label);

        //Signal end of generation
        canvas.finish();
        
        ConvertTransparent(outputFile.getPath());

        fis = new FileInputStream("Data\\Barcode.jpg.copy.png");

        XWPFDocument doc = new XWPFDocument(OPCPackage.open("Data\\labelLayout.docx"));

        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                  
                for (XWPFTableCell cell : row.getTableCells()) {
                    if(cell.getColor()==null){
                        
                        CTDrawing drawing;
                        CTAnchor anchor;
                        
                        cell.removeParagraph(0);
                        
                        XWPFParagraph p = cell.addParagraph();
                        p.setAlignment(ParagraphAlignment.CENTER);

                        XWPFRun run = p.createRun();

                        fis = new FileInputStream("Data\\Barcode.png");
                        //run.setText("/        /"+year);
                        run.addPicture(fis, XWPFDocument.PICTURE_TYPE_PNG,"Barcode.jpg.copy.png", Units.toEMU(105), Units.toEMU(25));
 
                        
                        
                        drawing = run.getCTR().getDrawingArray(0);

                        anchor = getAnchorWithGraphic(drawing, "Barcode.jpg.copy.png", true);

                        drawing.setAnchorArray(new CTAnchor[]{anchor});
                        drawing.removeInline(0);
                        run = p.createRun();
                        run.setFontSize(8);  
                        run.addBreak();
                        run.addBreak();
                        run.setText("                                         /         /"+year);
                        
                    }
                          
                }
            }
        }
        
        doc.write(new FileOutputStream(new File("Data\\label.doc")));

        File file = new File("Data\\label.doc");
        
        Runtime.getRuntime().exec(new String[]{"rundll32", "shell32.dll,ShellExec_RunDLL", file.getAbsolutePath()});
 
    }
    
    public static void ConvertTransparent(final String arguments) throws Exception
   {

      final String inputFileName = arguments;
      final int decimalPosition = inputFileName.lastIndexOf(".");
      final String outputFileName = inputFileName + ".copy.png";

      System.out.println("Copying file " + inputFileName + " to " + outputFileName);

      final File in = new File(inputFileName);
      final BufferedImage source = ImageIO.read(in);

      final int color = source.getRGB(0, 0);

      final Image imageWithTransparency = makeColorTransparent(source, new Color(color));

      final BufferedImage transparentImage = imageToBufferedImage(imageWithTransparency);

      final File out = new File(outputFileName);
      ImageIO.write(transparentImage, "PNG", out);
   }

   /**
    * Convert Image to BufferedImage.
    *
    * @param image Image to be converted to BufferedImage.
    * @return BufferedImage corresponding to provided Image.
    */
   private static BufferedImage imageToBufferedImage(final Image image)
   {
      final BufferedImage bufferedImage =
         new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
      final Graphics2D g2 = bufferedImage.createGraphics();
      g2.drawImage(image, 0, 0, null);
      g2.dispose();
      return bufferedImage;
    }
   
   public static Image makeColorTransparent(final BufferedImage im, final Color color)
   {
      final ImageFilter filter = new RGBImageFilter()
      {
         // the color we are looking for (white)... Alpha bits are set to opaque
         public int markerRGB = color.getRGB() | 0xFFFFFFFF;

         public final int filterRGB(final int x, final int y, final int rgb)
         {
            if ((rgb | 0xFF000000) == markerRGB)
            {
               // Mark the alpha bits as zero - transparent
               return 0x00FFFFFF & rgb;
            }
            else
            {
               // nothing to do
               return rgb;
            }
         }
      };

      final ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
      return Toolkit.getDefaultToolkit().createImage(ip);
   }
   
    private static CTAnchor getAnchorWithGraphic(CTDrawing drawing /*inline drawing*/ , 
      String drawingDescr, boolean behind) throws Exception {

      CTGraphicalObject graphicalobject =  drawing.getInlineArray(0).getGraphic();
      long width = drawing.getInlineArray(0).getExtent().getCx();
      long height = drawing.getInlineArray(0).getExtent().getCy();

      String anchorXML = 
       "<wp:anchor xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" "
      +"simplePos=\"0\" relativeHeight=\"0\" behindDoc=\""+((behind)?1:0)+"\" locked=\"0\" layoutInCell=\"1\" allowOverlap=\"1\">"
      +"<wp:simplePos x=\"0\" y=\"0\"/>"
      +"<wp:positionH relativeFrom=\"column\"><wp:align>center</wp:align></wp:positionH>"
      +"<wp:positionV relativeFrom=\"paragraph\"><wp:align>top</wp:align></wp:positionV>"
      +"<wp:extent cx=\""+width+"\" cy=\""+height+"\"/>"
      +"<wp:effectExtent l=\"0\" t=\"0\" r=\"0\" b=\"0\"/><wp:wrapNone/>"
      +"<wp:docPr id=\"1\" name=\"Drawing 0\" descr=\""+drawingDescr+"\"/><wp:cNvGraphicFramePr/>"
      +"</wp:anchor>";

      drawing = CTDrawing.Factory.parse(anchorXML);
      CTAnchor anchor = drawing.getAnchorArray(0);
      anchor.setGraphic(graphicalobject);
      return anchor;  
    }
    
}
