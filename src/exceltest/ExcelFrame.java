/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
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
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Stack;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFFont;
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
    DefaultTableModel infoTableModel;
    DefaultTableModel blankModel;
    HashMap<String,Integer> multiMap;
    static int multiCounter;
    String[] multiColumn;
    Object[][] multiDataTable;
    String[] infoColumn;
    Object[][] infoDataTable;
    static XSSFWorkbook workbook;
    static CellStyle style;
    static CellStyle headStyle;
    static CellStyle titleStyle;
    static XSSFSheet sheet;
    static XSSFSheet sheet2;
    private DateFormat sdf;
    Date date;
    boolean done;
    String[] devNames;
    JTextField techName;
    JTextField techNumber;
    optionsFrame oFrame;
    String lastHour;
    Stack listStack;

    
    
    public ExcelFrame(Table table){
        this.curTable = table; 
        
        initMultiTable();
        initInfoTable();
        initComponents(); 
     
        tableModel = (DefaultTableModel)theTable.getModel();
        blankModel = (DefaultTableModel)theTable.getModel();
        mTableModel = (DefaultTableModel)mTable.getModel();
        multiMap = new HashMap<String,Integer>();
        sdf = new SimpleDateFormat("MM'_'dd'_'yyyy");
        date = new Date();
        done = false;
        techNumber = new JTextField("");
        techName = new JTextField("");
        oFrame = new optionsFrame();
        lastHour = " ";
        listStack = new Stack();
        
        
        initTableStyle();
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
    
    private void initInfoTable(){
        int rosterSize = curTable.getRosterNum().size();

        infoColumn = new String [] {
                "Tech#","Name","Quota","Avg/Hr","EOD Est"};
        infoDataTable = new Object[rosterSize][infoColumn.length];
        
        for(int i=0;i<infoDataTable.length;i++){
            for(int j=0;j<infoDataTable[0].length;j++){
                infoDataTable[i][j]= "";  
            }
        }
        for(int i=0;i<infoDataTable.length;i++){
            infoDataTable[i][0]= curTable.getRosterNum().get(i);
            infoDataTable[i][1]= curTable.getRosterNames().get(i);
        }   
    }
    
    private void initTableStyle(){
        DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
        DefaultTableCellRenderer colRender = new DefaultTableCellRenderer();
        DefaultTableCellRenderer colRenderer = new DefaultTableCellRenderer();
        colRenderer.setHorizontalAlignment(JLabel.CENTER);

        colRender.setBackground(Color.DARK_GRAY);
        colRender.setHorizontalAlignment(JLabel.CENTER);
        colRender.setForeground(Color.WHITE);
        
        centerRender.setHorizontalAlignment(JLabel.CENTER);

         for(int x=0;x<theTable.getColumnCount();x++){
            theTable.getColumnModel().getColumn(x).setCellRenderer(centerRender);
        }
         
        for(int i = 0;i<mTable.getColumnCount();i++){
            mTable.getColumnModel().getColumn(i).setCellRenderer(colRenderer);
        }

         theTable.getColumn("Tech Total").setCellRenderer(colRender);
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
        snapShotButton = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        mTable = new javax.swing.JTable();
        dTableList = new java.awt.List();
        exportButton = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        tablePanel = new javax.swing.JScrollPane();
        theTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        quotaButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        infoTable = new javax.swing.JTable();
        quotaTextField = new javax.swing.JTextField();
        eQuotaLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        quotaLabel = new javax.swing.JLabel();
        hourLabel = new javax.swing.JLabel();
        multiScanLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        saveMenuItem = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        addMenuItem = new javax.swing.JMenuItem();
        optionsMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));
        setResizable(false);

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

        mTable.setBackground(new java.awt.Color(51, 51, 51));
        mTable.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));
        mTable.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        mTable.setForeground(new java.awt.Color(255, 255, 255));
        mTable.setModel(new javax.swing.table.DefaultTableModel(multiDataTable,
            multiColumn));
    mTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    mTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    mTable.setEnabled(false);
    mTable.setGridColor(new java.awt.Color(255, 255, 255));
    mTable.setShowHorizontalLines(false);
    jScrollPane2.setViewportView(mTable);

    dTableList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    dTableList.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
    dTableList.setMultipleMode(true);

    exportButton.setText("Export");
    exportButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            exportButtonActionPerformed(evt);
        }
    });

    theTable.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
    theTable.setModel(new javax.swing.table.DefaultTableModel(curTable.getDataTable(),
        curTable.getcolumnTable())
    );
    theTable.setColumnSelectionAllowed(true);
    theTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    theTable.setEnabled(false);
    theTable.setGridColor(new java.awt.Color(0, 0, 0));
    theTable.setRowSelectionAllowed(false);
    tablePanel.setViewportView(theTable);
    theTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

    jTabbedPane1.addTab("Scan", tablePanel);

    quotaButton.setText("Confirm");
    quotaButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            quotaButtonActionPerformed(evt);
        }
    });

    infoTable.setModel(new javax.swing.table.DefaultTableModel(infoDataTable,infoColumn));
    infoTable.setEnabled(false);
    jScrollPane1.setViewportView(infoTable);

    eQuotaLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    eQuotaLabel.setText("Enter Quota");

    jLabel1.setText("Current Quota");

    quotaLabel.setBackground(new java.awt.Color(255, 255, 255));
    quotaLabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
    quotaLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    quotaLabel.setText("0");

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(47, 47, 47)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(quotaButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(quotaTextField)
                    .addComponent(eQuotaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(quotaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGap(62, 62, 62))
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(quotaLabel)
                    .addGap(78, 78, 78)
                    .addComponent(eQuotaLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(quotaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(quotaButton)
                    .addGap(0, 0, Short.MAX_VALUE))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
            .addContainerGap())
    );

    jTabbedPane1.addTab("Info", jPanel1);

    hourLabel.setText("Select Hour");

    multiScanLabel.setBackground(new java.awt.Color(204, 204, 204));
    multiScanLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
    multiScanLabel.setText("MULTI SCAN COUNTER");

    jMenu1.setText("File");

    saveMenuItem.setText("Save ");
    jMenu1.add(saveMenuItem);

    jMenuBar1.add(jMenu1);

    jMenu3.setText("Tools");

    addMenuItem.setText("Add New Tech");
    addMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            addMenuItemMouseClicked(evt);
        }
    });
    addMenuItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            addMenuItemActionPerformed(evt);
        }
    });
    jMenu3.add(addMenuItem);

    optionsMenuItem.setText("Options...");
    optionsMenuItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            optionsMenuItemActionPerformed(evt);
        }
    });
    jMenu3.add(optionsMenuItem);

    jMenuBar1.add(jMenu3);

    setJMenuBar(jMenuBar1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jToggleButton1)
                        .addComponent(snapShotButton))))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2)
                .addComponent(jTabbedPane1))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(31, 31, 31)
                            .addComponent(dTableList, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(52, 52, 52)
                            .addComponent(exportButton)))
                    .addContainerGap(26, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hourLabel)
                    .addGap(45, 45, 45))))
        .addGroup(layout.createSequentialGroup()
            .addGap(379, 379, 379)
            .addComponent(multiScanLabel)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                    .addComponent(jToggleButton1))
                .addGroup(layout.createSequentialGroup()
                    .addGap(29, 29, 29)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(hourLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(dTableList, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(exportButton)))))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(multiScanLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(snapShotButton))
            .addContainerGap(63, Short.MAX_VALUE))
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
                JLabel center = new JLabel("Tech Number Not Found",JLabel.CENTER);
                JOptionPane.showMessageDialog(this,
                    center,
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
            }else if(devFieldName.getText().toUpperCase().equals("CLEAR")){
                manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusPreviousComponent();
                techFieldName.setEditable(true);
                techFieldName.setText("");
                devFieldName.setEnabled(false);
                devFieldName.setText("");
            }else{
                //custom title, no icon
                JLabel center = new JLabel("Device Type Not Found",JLabel.CENTER);
                JOptionPane.showMessageDialog(this,
                    center,
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
        DateFormat hour = new SimpleDateFormat("hh:mm aa");
        Date tableDate = new Date();
        String timeID = hour.format(tableDate);
        
        if(!curTable.isDTListEmpty()){
            
            if(!curTable.checkDTExists(timeID)){
  
                dTableList.add(curTable.updateTableViaList(getModel(),dTableList.getItems()));
      
            }else{
                JLabel center = new JLabel("Current Time Already Exists",JLabel.CENTER);
                JOptionPane.showMessageDialog(this,
                        center,
                        "Error",
                        JOptionPane.PLAIN_MESSAGE);
            }   
        }else{
            dTableList.add(curTable.updateTable(getModel()));
        }
        
        updateInfoTable();
    }//GEN-LAST:event_snapShotButtonActionPerformed

    private void updateInfoTable(){
        
        int row = 0;
        int col = 0;
        int value = 0;
        int eodValue =0;
        int estCol = 0;
        
        col = getInfoModel().findColumn("Avg/Hr");
        estCol = getInfoModel().findColumn("EOD Est");
        
        for(Map.Entry<String,String> entry:curTable.getTechAvgList().entrySet()){
            row = getRow(getInfoModel(),entry.getKey());
            value = Integer.valueOf(entry.getValue())/dTableList.getItemCount();
            eodValue = value*10;
            
            getInfoModel().setValueAt(value,row,col);
            getInfoModel().setValueAt(eodValue,row,estCol);
        }
        
    }
    
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
        makeProdTable(getModel());
        StringBuilder location = new StringBuilder("C:\\Users\\Public\\OutBoundProd_");
        location.append(String.valueOf(sdf.format(date)));
        location.append(".xlsx");
        
        //OUTPUT
        try (FileOutputStream outputStream = new FileOutputStream(location.toString())){
            workbook.write(outputStream);
            outputStream.close();
            
            JOptionPane.showMessageDialog(this,"File Created","Written Successfully", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_exportButtonActionPerformed

    private void addMenuItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMenuItemMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_addMenuItemMouseClicked

    private void addMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMenuItemActionPerformed
        // TODO add your handling code here:
        String[] newTechRow = new String [9];
        String[] newInfoRow = new String[getInfoModel().getColumnCount()];
        JPanel p = new JPanel(new BorderLayout(5,5));

        JPanel labels = new JPanel(new GridLayout(0,1,2,2));
        labels.add(new JLabel("Tech Number", SwingConstants.RIGHT));
        labels.add(new JLabel("Tech Name", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0,1,2,2));

        controls.add(techNumber);
        controls.add(techName);
        p.add(controls, BorderLayout.CENTER);
        
        int option = JOptionPane.showConfirmDialog(this, p, "Create Tech", JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            
            if(techNumber.getText().length()==4){
                if(techName.getText().length()<=10){
                    if(!curTable.getRosterNum().contains(techNumber.getText())){
                        for(int i=0;i<newTechRow.length;i++){
                            newTechRow[i] = "0";
                        }
                        
                        for(int i=0;i<newInfoRow.length;i++){
                            newInfoRow[i] = "0";
                        }
                        
                        newTechRow[0] = techNumber.getText();
                        newInfoRow[0] = techNumber.getText();
                        
                        if(techName.getText().length()==0){
                            curTable.addToRoster(techNumber.getText(),"**");
                            newTechRow[1] = "**";
                            newInfoRow[1] = "**";
                        }else{
                          
                            curTable.addToRoster(techNumber.getText(),techName.getText());
                            newTechRow[1] = techName.getText();
                            newInfoRow[1] = techName.getText();
                        }
                        
                        newInfoRow[getCol(getInfoModel(),"Quota")]=quotaLabel.getText();
                        
                        tableModel.insertRow(0,newTechRow);
                        getInfoModel().insertRow(0, newInfoRow);
                        techNumber.setText("");
                        techName.setText("");
                    }else{
                        JOptionPane.showMessageDialog(this,"Tech Already Exists","Try Again", JOptionPane.WARNING_MESSAGE);
                        techNumber.setText("");
                        techName.setText("");
                    }
                }else{
                    JOptionPane.showMessageDialog(this,"Tech Name Exceeds [10]Char Max","Try Again", JOptionPane.WARNING_MESSAGE);
                    techNumber.setText("");
                    techName.setText("");
                }  
            }else{
                JOptionPane.showMessageDialog(this,"Tech must be 4 characters","Try Again", JOptionPane.WARNING_MESSAGE);
                    techNumber.setText("");
                    techName.setText("");
            }
        }
   
    }//GEN-LAST:event_addMenuItemActionPerformed

    private void optionsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsMenuItemActionPerformed
        // TODO add your handling code here:
        oFrame.setVisible(true);
    }//GEN-LAST:event_optionsMenuItemActionPerformed

    private void quotaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quotaButtonActionPerformed
        // TODO add your handling code here:
        int col,row=0;
        String quota = quotaTextField.getText();
        
        if(isInteger(quota)){
            
            col = getCol(getInfoModel(),"Quota");
            
            if(quota.length()<=4){
                
                quotaLabel.setText(quota);
                
                for(String tech:curTable.getRosterNum()){
                    row = getRow(getInfoModel(),tech);
                    getInfoModel().setValueAt(quota, row, col);
                    quotaTextField.setText("");
                }
            }else{
                JOptionPane.showMessageDialog(this,"Quota Exceeds [4]Char Max","Try Again", JOptionPane.WARNING_MESSAGE);
                quotaTextField.setText("");
            }
        
        }else{
            JOptionPane.showMessageDialog(this,"Not An Integer","Try Again", JOptionPane.WARNING_MESSAGE);
            quotaTextField.setText("");
        }
        
        
    }//GEN-LAST:event_quotaButtonActionPerformed
    
    private boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
    private DefaultTableModel getInfoModel(){
        return (DefaultTableModel)infoTable.getModel();
    }
    public void makeTables(String[] selectedList){
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Prod Tables");
        style = workbook.createCellStyle();
        headStyle = workbook.createCellStyle();
        titleStyle = workbook.createCellStyle();
        //String hr = String.valueOf(hour.format(tableDate));
        int counter = 0;
        int colNum = 1;
        int rowNum = 1;
        int curCell = 0;
        LinkedHashMap<String,String[][]>list = new LinkedHashMap<String,String[][]>();
        String[] devNames = {"Tech","Name","Classic","Nano","Shuffle","Touch","Pad","Phone","     Tech Total    "};  
        
        
        for(String item:selectedList){
            //System.out.println(item);
            list.put(item,curTable.getDataTableFromList(item));
        }
        //Create set from HashMap
        Set hashSet = list.entrySet();
        //Create Iterator from the Set
        Iterator it = hashSet.iterator();

        while(it.hasNext()){
            
            Map.Entry me = (Map.Entry)it.next();
            
            CellRangeAddress range = new CellRangeAddress(
                    rowNum,rowNum,colNum,devNames.length);
            sheet.addMergedRegion(range);
            
            // Creates the cell
            Row title = sheet.createRow(rowNum);
            Cell titleCell = title.createCell(colNum);
            titleCell.setCellValue("HOUR " + me.getKey());
            setStyleFontWhite(titleStyle);
            cellFillBlack(titleStyle);
            
            titleCell.setCellStyle(titleStyle);
            
            CellUtil.setAlignment(titleCell,HorizontalAlignment.CENTER);
            
            rowNum++;
            
            Row header = sheet.createRow(rowNum++);
            
            for(int col = 0;col<devNames.length;col++){
                
                Cell headerCell = header.createCell(colNum++);
                headerCell.setCellValue(devNames[col]);  
                headStyle.setAlignment(HorizontalAlignment.CENTER);
                cellBorderBlack(headStyle);
                cellFillHGrey(headStyle);
                setStyleFontWhite(headStyle);
                headerCell.setCellStyle(headStyle);
                
            }
              
            colNum = 1;
                    
            for(String[] tableRow:(String[][])me.getValue()){
                
                Row row = sheet.createRow(rowNum++);
                
                for(String tCell:tableRow){
                    curCell++;
                    Cell cell = row.createCell(colNum++);
                    cell.setCellValue(tCell);
                    
                    if(tableRow[0].equals("Total Dev") && (curCell == tableRow.length)){
                        setTotalDevCell(cell);
                    }else{
                        cellBorderBlack(style);
                        cellFillLGrey(style);
                        setStyleFontBold(style);
                        cell.setCellStyle(style); 
                    }
 
                }
                colNum=1;   
                curCell=0;
            }
            rowNum++;

            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(9);
        }   

    }
    
    public void makeProdTable(DefaultTableModel curModel){
        sheet2 = workbook.createSheet("Outbound Prod");
        int colNum = 1;
        int rowNum = 1;
        int curCell = 0;
        
        String[] devNames = {"Tech","Name","Classic","Nano","Shuffle","Touch","Pad","Phone","     Tech Total    "};  
        
        CellRangeAddress range = new CellRangeAddress(
                    rowNum,rowNum,colNum,devNames.length);
            sheet2.addMergedRegion(range);
            
            // Creates the cell
            Row title = sheet2.createRow(rowNum);
            Cell titleCell = title.createCell(colNum);
            titleCell.setCellValue("OUTBOUND PRODUCTION");
            setStyleFontWhite(titleStyle);
            cellFillBlack(titleStyle);
            
            titleCell.setCellStyle(titleStyle);
            
            CellUtil.setAlignment(titleCell,HorizontalAlignment.CENTER);
            
            rowNum++;
            
            Row header = sheet2.createRow(rowNum++);
            
            for(int col = 0;col<devNames.length;col++){
                
                Cell headerCell = header.createCell(colNum++);
                headerCell.setCellValue(devNames[col]);  
                headStyle.setAlignment(HorizontalAlignment.CENTER);
                cellBorderBlack(headStyle);
                cellFillHGrey(headStyle);
                setStyleFontWhite(headStyle);
                headerCell.setCellStyle(headStyle);
                
            }
              
            colNum = 1;
            
            for(String[] tableRow:curTable.toStringArray(curModel)){
                
                Row row = sheet2.createRow(rowNum++);
                
                for(String tCell:tableRow){
                    curCell++;
                    Cell cell = row.createCell(colNum++);
                    cell.setCellValue(tCell);
                    
                    if(tableRow[0].equals("Total Dev") && (curCell == tableRow.length)){
                        setTotalDevCell(cell);
                    }else{
                        cellBorderBlack(style);
                        cellFillLGrey(style);
                        setStyleFontBold(style);
                        cell.setCellStyle(style); 
                    }
 
                }
                colNum=1;   
                curCell=0;
            }
            rowNum++;

            sheet2.autoSizeColumn(2);
            sheet2.autoSizeColumn(9);
                    
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
    
    public static void setStyleFontWhite(CellStyle style) {
        XSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

    }
    
    public static void setStyleFontBold(CellStyle style) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

    }
    
    public static void cellFillLGrey(CellStyle style) {
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    }
    
    public static void setTotalDevCell(Cell cell) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        setStyleFontWhite(style);
        cell.setCellStyle(style);
    }
    
    public static void cellFillBlack(CellStyle style) {
        style.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
   

    }
    
     public static void cellFillHGrey(CellStyle style) {
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
   

    }

    public static void cellBorderBlack(CellStyle style) {

        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

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
    private javax.swing.JMenuItem addMenuItem;
    private java.awt.List dTableList;
    private javax.swing.JTextField devFieldName;
    private javax.swing.JLabel deviceField;
    private javax.swing.JLabel eQuotaLabel;
    private javax.swing.JButton exportButton;
    private javax.swing.JLabel hourLabel;
    public javax.swing.JTable infoTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JTable mTable;
    private javax.swing.JLabel multiScanLabel;
    private javax.swing.JMenuItem optionsMenuItem;
    private javax.swing.JButton quotaButton;
    private javax.swing.JLabel quotaLabel;
    private javax.swing.JTextField quotaTextField;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JButton snapShotButton;
    private javax.swing.JScrollPane tablePanel;
    private javax.swing.JLabel techField;
    private javax.swing.JTextField techFieldName;
    private javax.swing.JTable theTable;
    // End of variables declaration//GEN-END:variables
}
