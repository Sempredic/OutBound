/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import javax.swing.table.*;
import table.Table;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 *
 * @author Vince
 */
public class ExcelFrame extends javax.swing.JFrame {

    /**
     * Creates new form ExcelFrame
     */
    
    static Table curTable;
    Table multiTable;
    final int DEFAULT_INC = 6;
    KeyboardFocusManager manager;
    AbstractButton abstractButton;
    boolean multiSelected;
    DefaultTableModel tableModel;
    DefaultTableModel mTableModel;
    HashMap<String,Integer> multiMap;
    static int multiCounter;
    String[] multiColumn;
    Object[][] multiDataTable;
    static XSSFWorkbook workbook;
    static CellStyle style;
    static XSSFSheet sheet;
    private DateFormat sdf;
    Date date;
    boolean done;
    String[] devNames;
    
    
    
    public ExcelFrame(Table table){
        
        
        this.curTable = table; 
        multiCounter = 0;
        initMultiTable();
        initComponents();
        tableModel = (DefaultTableModel)theTable.getModel();
        mTableModel = (DefaultTableModel)mTable.getModel();
        multiMap = new HashMap<String,Integer>();
        //Create Workbook and Sheet
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Outbound Production");
        style = workbook.createCellStyle();
        sdf = new SimpleDateFormat("MM'_'dd'_'yyyy");
        date = new Date();
        done = false;
        
    }
    
    private void initMultiTable(){
        multiColumn = new String [] {
                "Device Type","Amount"};
        multiDataTable = new Object[][]{
            {"Classic",0},
            {"Nano",0},
            {"Shuffle",0},
            {"Touch",0},
            {"Pad",0},
            {"Phone",0}
        };
    }
    
    DefaultTableModel getModel(){
        
        return tableModel;
    }
    
    DefaultTableModel getMultiModel(){
        
        return mTableModel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        techFieldName = new javax.swing.JTextField();
        devFieldName = new javax.swing.JTextField();
        techField = new javax.swing.JLabel();
        deviceField = new javax.swing.JLabel();
        tablePanel = new javax.swing.JScrollPane();
        theTable = new javax.swing.JTable();
        snapShotButton = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        mTable = new javax.swing.JTable();
        dTableList = new java.awt.List();
        exportButton = new javax.swing.JButton();
        hourLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        techFieldName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                techFieldNameKeyPressed(evt);
            }
        });

        devFieldName.setEnabled(false);
        devFieldName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                devFieldNameFocusGained(evt);
            }
        });
        devFieldName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                devFieldNameActionPerformed(evt);
            }
        });
        devFieldName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                devFieldNameKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                devFieldNameKeyTyped(evt);
            }
        });

        techField.setText("Tech");

        deviceField.setText("Device");

        theTable.setModel(new javax.swing.table.DefaultTableModel(curTable.getDataTable(),
            curTable.getcolumnTable())
    );
    theTable.setColumnSelectionAllowed(false);
    tablePanel.setViewportView(theTable);
    theTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

    snapShotButton.setText("SnapShot");
    snapShotButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            snapShotButtonActionPerformed(evt);
        }
    });

    jToggleButton1.setText("Multi Scan");
    jToggleButton1.addChangeListener(new javax.swing.event.ChangeListener() {
        public void stateChanged(javax.swing.event.ChangeEvent evt) {
            jToggleButton1StateChanged(evt);
        }
    });
    jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jToggleButton1ActionPerformed(evt);
        }
    });

    mTable.setModel(new javax.swing.table.DefaultTableModel(multiDataTable,
        multiColumn));
jScrollPane2.setViewportView(mTable);

dTableList.setMultipleMode(true);

exportButton.setText("Export");
exportButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        exportButtonActionPerformed(evt);
    }
    });

    hourLabel.setText("Hour");

    jMenu1.setText("File");
    jMenuBar1.add(jMenu1);

    jMenu2.setText("Edit");
    jMenuBar1.add(jMenu2);

    setJMenuBar(jMenuBar1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(hourLabel)
            .addGap(102, 102, 102))
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(59, 59, 59)
                    .addComponent(techField))
                .addGroup(layout.createSequentialGroup()
                    .addGap(55, 55, 55)
                    .addComponent(deviceField))
                .addGroup(layout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(devFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(techFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createSequentialGroup()
                    .addGap(31, 31, 31)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(snapShotButton)
                        .addComponent(jToggleButton1))))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
                .addComponent(tablePanel))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exportButton)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(dTableList, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(21, Short.MAX_VALUE))))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(29, 29, 29)
                    .addComponent(hourLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(dTableList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(exportButton))
                        .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createSequentialGroup()
                    .addGap(106, 106, 106)
                    .addComponent(techField)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(techFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(deviceField)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(devFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jToggleButton1)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(snapShotButton, javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(46, 46, 46))
    );

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void devFieldNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_devFieldNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_devFieldNameActionPerformed

    private void techFieldNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_techFieldNameKeyPressed
        // TODO add your handling code here:
        int keyCode = evt.getKeyCode();
        if(keyCode == KeyEvent.VK_ENTER){
            if(curTable.getRosterNum().contains(techFieldName.getText())){
                devFieldName.setEnabled(true);
                techFieldName.setEditable(false);
                manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }else{
                //custom title, no icon
                JOptionPane.showMessageDialog(this,
                    "Tech Number Not Found",
                    "Error",
                    JOptionPane.PLAIN_MESSAGE);
                techFieldName.setText("");
            }  
            
        }
    }//GEN-LAST:event_techFieldNameKeyPressed

    private void devFieldNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_devFieldNameKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_devFieldNameKeyTyped

    private void devFieldNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_devFieldNameKeyPressed
        // TODO add your handling code here:
        int keyCode = evt.getKeyCode();
        
        if(keyCode == KeyEvent.VK_ENTER && !multiSelected){
            if(tableModel.findColumn(devFieldName.getText())!=-1){
                toTable();
                commitMTable();
                techFieldName.setEditable(true);
                manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusPreviousComponent();
                techFieldName.setText("");
                devFieldName.setEnabled(false);
            }else{
                //custom title, no icon
                JOptionPane.showMessageDialog(this,
                    "Device Type Not Found",
                    "Error",
                    JOptionPane.PLAIN_MESSAGE);
                devFieldName.setText("");
            }
            
            
        }else if(keyCode == KeyEvent.VK_ENTER && multiSelected){
            if(devFieldName.getText().equals("")&&!multiMap.isEmpty()){
                //toMulti();
                commitMTable();
                techFieldName.setEditable(true);
                manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusPreviousComponent();
                techFieldName.setText("");
                devFieldName.setEnabled(false);
            }
             else if(tableModel.findColumn(devFieldName.getText())!=-1){
                toMulti();
                
                   
            }else{
                //custom title, no icon
                System.out.println(multiMap.isEmpty());
                JOptionPane.showMessageDialog(this,
                    "Device Type Not Found",
                    "Error",
                    JOptionPane.PLAIN_MESSAGE);
                devFieldName.setText("");
            }
        }   
    }//GEN-LAST:event_devFieldNameKeyPressed

    private void snapShotButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_snapShotButtonActionPerformed
        // TODO add your handling code here:
        
        dTableList.add(Integer.toString(curTable.updateTable(getModel())));
        
    }//GEN-LAST:event_snapShotButtonActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
        abstractButton = (AbstractButton) evt.getSource();
        multiSelected = abstractButton.getModel().isSelected();
        
        
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jToggleButton1StateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jToggleButton1StateChanged

    private void devFieldNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_devFieldNameFocusGained
        // TODO add your handling code here:
    
                
    }//GEN-LAST:event_devFieldNameFocusGained

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
        // TODO add your handling code here:
        makeTables(dTableList.getSelectedItems());
        
        //OUTPUT
        try (FileOutputStream outputStream = new FileOutputStream("OutBoundProd " + String.valueOf(sdf.format(date)) + ".xlsx")) {
            workbook.write(outputStream);
            outputStream.close();
            System.out.println("Written successully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
}
    }//GEN-LAST:event_exportButtonActionPerformed
    
    public static void makeTables(String[] selectedList){
        int counter = 0;
        int colNum = 1;
        int rowNum = 1;
        ArrayList<String[][]>list = new ArrayList<String[][]>();
        
        for(String item:selectedList){
            System.out.println(item);
            list.add(curTable.getDataTableFromList(Integer.parseInt(item)));
        }
        
        Iterator<String[][]> it = list.iterator();
    
        
        while(it.hasNext()){
            for(String[] tableRow:it.next()){
                
                Row row = sheet.createRow(rowNum++);
                
                for(String tCell:tableRow){
                   
                    Cell cell = row.createCell(colNum++);

                    cell.setCellValue(tCell);
                    //cell.setCellStyle(style);
                    //cellBorderBlack(style);
                    //cellFillBlue(style);
                    cell.setCellStyle(style);
                    
                }
                
                colNum=1;              
            }
            rowNum++;
        }
        
    }
    
    private void toMulti(){

        String device = devFieldName.getText();
        devFieldName.setText("");
        
        if(!multiMap.containsKey(device)){
            multiMap.put(device, 1);
        }else{
            int currentVal = multiMap.get(device);
            multiMap.replace(device, currentVal+1);
        } 
        
        if(!multiMap.isEmpty()){
            for(Map.Entry<String,Integer> entry:multiMap.entrySet()){

                String dev = entry.getKey();          
                int value = entry.getValue();   
                int col = 1;
                int row = getRow(mTableModel,dev);

                setMultiTableValues(value,row,col);
            }
        }
        
    }
    
    private void commitMTable(){
        
        if(!multiMap.isEmpty()){
            int row = getRow(tableModel,techFieldName.getText());
            
            for(Map.Entry<String,Integer> entry:multiMap.entrySet()){
                
                String device = entry.getKey();
                
                int value = entry.getValue();                
                int col = getCol(tableModel,device);

                int oldValue = Integer.parseInt(tableModel.getValueAt(row, col).toString());
                int newValue = oldValue + value;
                
                setTableValues(newValue,row,col);
            }
        }
        
        for(int i=0;i<multiDataTable.length;i++){
            setMultiTableValues(0,i,1);
        }
        
        multiMap.clear();
            
    }
    
    private void toTable(){
        
        String device = devFieldName.getText();
        devFieldName.setText("");
        
        int col = getCol(tableModel,device);
        int row = getRow(tableModel,techFieldName.getText());
            
        int oldValue = Integer.parseInt(tableModel.getValueAt(row, col).toString());
        int newValue = oldValue + DEFAULT_INC;
           
        setTableValues(newValue,row,col);    
        
    }
    
    private void setTableValues(int val, int row, int col){
        
        tableModel.setValueAt(val, row, col);    
        updateTotalDev(tableModel);
        updateTotalTech(tableModel);
    }
    
    private void setMultiTableValues(int val, int row, int col){
        
        mTableModel.setValueAt(val, row, col);    
   
    }
    
    private void updateTotalDev(DefaultTableModel model){
        String[] devNames = {"Classic","Nano","Shuffle","Touch","Pad","Phone"};
        
        int col =0;
        int devRow =0;
        int sum =0;
        
        devRow = getRow(model,"Total Dev");
               
        for(String dev:devNames){
            sum = 0;
            col = getCol(model,dev);

            for(int row =0;row<model.getRowCount()-1;row++){
                sum += Integer.parseInt(model.getValueAt(row, col).toString());
            }
            
            model.setValueAt(sum, devRow, col);
            
        }
    }
    
    private void updateTotalTech(DefaultTableModel model){
        String[] devNames = {"Classic","Nano","Shuffle","Touch","Pad","Phone"};   
        int totCol,sum,value;
        int tttSum = 0;

        totCol = getCol(model,"Tech Total");
        
        for(String tech:curTable.getRosterNum()){
            int row = getRow(model,tech);
            sum = 0;
            for(String dev:devNames){
                int col = getCol(model,dev);
                value = Integer.parseInt(model.getValueAt(row, col).toString());
                sum += value;
            }
            
            tttSum += sum;
            
            model.setValueAt(sum,row,totCol);
        }
        
        updateTTT(tttSum,model);
 
    }
    
    private void updateTTT(int sum,DefaultTableModel model){
        
        int totCol = getCol(model,"Tech Total");
        int totRow = getRow(model,"Total Dev");
        
        model.setValueAt(sum, totRow, totCol);
             
    }
    private int getCol(DefaultTableModel model, String deviceName){
        return model.findColumn(deviceName);
    }
    
    private int getRow(DefaultTableModel model, String value){
  
        String rowValue = "";
       
        for(int i=0;i<model.getRowCount();i++){

           rowValue = (String)model.getValueAt(i, 0);
           if(rowValue.equals(value)){
               return i;
           }
        }
          
        return 0;
    }
    
    DefaultTableModel getTableModel(){
        DefaultTableModel tbModel = (DefaultTableModel)theTable.getModel();
        
        return tbModel;
    }
    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ExcelFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExcelFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExcelFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExcelFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        

        /* Create and display the form */
        
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.List dTableList;
    private javax.swing.JTextField devFieldName;
    private javax.swing.JLabel deviceField;
    private javax.swing.JButton exportButton;
    private javax.swing.JLabel hourLabel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JTable mTable;
    private javax.swing.JButton snapShotButton;
    private javax.swing.JScrollPane tablePanel;
    private javax.swing.JLabel techField;
    private javax.swing.JTextField techFieldName;
    private javax.swing.JTable theTable;
    // End of variables declaration//GEN-END:variables
}
