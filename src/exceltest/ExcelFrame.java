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
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import javax.swing.table.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
    int inQuota;
    Stack<techObject>lastActionStack;
    int actionMapID;
    int SHIFT_HOURS;
    ArrayList existingTechList;
    LinkedHashMap<String,String> existingRosterList;
    int num=0;
    Thread thread1,thread2,thread3;
    static boolean is_iDevice;
    static ArrayList<String> deviceNames;
    JFileChooser fileChooser;
    FileNameExtensionFilter filter;
    static String databaseConn;
    ArrayList<String> excelColumn;
    
    
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
        inQuota = 0;
        lastActionStack = new Stack<techObject>();
        actionMapID = 0;
        SHIFT_HOURS = 10;
        existingRosterList = new LinkedHashMap<String,String>();
        existingTechList = new ArrayList();
        deviceNames = curTable.getAreaDevices();
        excelColumn = new ArrayList<String>();
        
        initTableStyle();
        initExistingTechs();
        
        progressBar.setStringPainted(true);
        progressBar.setIndeterminate(false);
        progressBar.setString("Ready");
        
        String[] fileTypes = {"xlsx","xls"};
        filter = new FileNameExtensionFilter(
                "Microsoft Excel Workbook (.xlsx,.xls)", fileTypes);
            
        fileChooser = new JFileChooser();
        
        updateDatabaseStatus();
        
        updateTotalDev(tableModel);
        updateTotalTech(tableModel);
        
        excelColumn.add("Tech#");
        excelColumn.add("Name");
        for(String dev:curTable.getAreaDevices()){
            excelColumn.add(dev);
        }
        excelColumn.add("TechTotal");
    }
    
    ////////////////////////////////////////////////////////////////////////////DATABASE/////////////////
    private static void updateDatabaseStatus(){
        databaseConn = DatabaseObj.getStatus();
        switch(databaseConn){
            case "Connected":
                dbStatus.setText(databaseConn);
                dbStatus.setForeground(Color.BLUE);
                break;
            case "Disconnected":
                dbStatus.setText(databaseConn);
                dbStatus.setForeground(Color.RED);
                break;
        }
    }
    
    private void createDBtechProdEntries(){
        
    }
    

    private void initMultiTable(){
        multiColumn = new String [] {
                "Device Type","Amount"};
        multiDataTable = new Object[curTable.getAreaDevices().size()][2];
        
        for(int i=0;i<multiDataTable.length;i++){
            for(int j=0;j<multiDataTable[0].length;j++){
                multiDataTable[i][j]= curTable.getAreaDevices().get(i);
                if(j==1){
                    multiDataTable[i][j]= 0;
                }    
            }
        }
    }
    
    
    private void initInfoTable(){
        
        int rosterSize = curTable.getRosterNum().size();

        infoColumn = new String [] {
                "Tech#","Name","Quota","Avg/Hr","EOD Est"};
        infoDataTable = new Object[rosterSize+1][infoColumn.length];
        
        for(int i=0;i<infoDataTable.length;i++){
            for(int j=0;j<infoDataTable[0].length;j++){
                infoDataTable[i][j]= "0";  
            }
        }
        
        for(int row =0;row<curTable.getRosterNum().size()+1;row++){
            if(row<curTable.getRosterNum().size()){
                infoDataTable[row][0]= curTable.getRosterNum().get(row);
                infoDataTable[row][1]= curTable.getRosterNames().get(row);
            }else{
                infoDataTable[row][0]= "TOTAL";
                infoDataTable[row][1]= " ";
                infoDataTable[row][2]= " ";
                
            }
        }

    }
    
    private void initTableStyle(){
        
        DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
        DefaultTableCellRenderer colRenderer = new DefaultTableCellRenderer();
        customTableEditor editor = new customTableEditor(tableModel);
       
        colRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRender.setHorizontalAlignment(JLabel.CENTER);
        
        newTableRenderer renderer = new newTableRenderer(getModel(),centerRender);
        multiTableRenderer mRenderer = new multiTableRenderer(mTableModel,colRenderer);
        
        for(int x=0;x<theTable.getColumnCount();x++){
            theTable.getColumnModel().getColumn(x).setCellRenderer(renderer);
            theTable.getColumnModel().getColumn(x).setCellEditor(editor);
        }
         
        for(int i = 0;i<mTable.getColumnCount();i++){
            mTable.getColumnModel().getColumn(i).setCellRenderer(mRenderer);
        }
        
        infoTable.getTableHeader().setReorderingAllowed(false);
        theTable.getTableHeader().setReorderingAllowed(false);
        mTable.getTableHeader().setReorderingAllowed(false);
        
        infoTable.getTableHeader().setResizingAllowed(false);
        theTable.getTableHeader().setResizingAllowed(false);
        mTable.getTableHeader().setResizingAllowed(false);
    }
    
    private void initExistingTechs(){
         try{
             File tmpDir = new File("Data\\employeesList.txt");
             boolean exists = tmpDir.exists();
             
             if(exists){
                for(String name:Files.readAllLines(Paths.get("Data\\employeesList.txt"))){
                    String[] li = {" "," "};
                    li = name.split(" ");
                
                    if(!existingRosterList.containsKey(li[0])){
                        existingRosterList.put(li[0],li[1]);
                        existingTechList.add(li[0]); 
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            errorLogger.writeToLogger(e.toString());
        }
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

        jScrollPane2 = new javax.swing.JScrollPane();
        mTable = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        tablePanel = new javax.swing.JScrollPane();
        theTable = new javax.swing.JTable()
        ;
        jPanel1 = new javax.swing.JPanel();
        quotaButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        infoTable = new javax.swing.JTable();
        quotaTextField = new javax.swing.JTextField();
        eQuotaLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        quotaLabel = new javax.swing.JLabel();
        multiScanLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        snapShotButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jToggleButton1 = new javax.swing.JToggleButton();
        techField = new javax.swing.JLabel();
        multiLable = new javax.swing.JLabel();
        techFieldName = new javax.swing.JTextField();
        devFieldName = new javax.swing.JTextField();
        deviceField = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        hourLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        dTableList = new java.awt.List();
        jPanel5 = new javax.swing.JPanel();
        exportMergeButton = new javax.swing.JButton();
        exportButton = new javax.swing.JButton();
        dbStatusLabel = new javax.swing.JLabel();
        dbStatus = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lastScanDetailArea = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        editMenu = new javax.swing.JMenu();
        undoMenuItem = new javax.swing.JMenuItem();
        editModeMenuItem = new javax.swing.JCheckBoxMenuItem();
        jMenu3 = new javax.swing.JMenu();
        addExistingTechMenuItem = new javax.swing.JMenuItem();
        optionsMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Outbound Tool");
        setBackground(new java.awt.Color(204, 204, 204));
        setMinimumSize(new java.awt.Dimension(1007, 718));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        mTable.setBackground(new java.awt.Color(51, 51, 51));
        mTable.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        mTable.setForeground(new java.awt.Color(255, 255, 255));
        mTable.setModel(new javax.swing.table.DefaultTableModel(multiDataTable,
            multiColumn));
    mTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    mTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    mTable.setEnabled(false);
    mTable.setGridColor(new java.awt.Color(255, 255, 255));
    mTable.setShowHorizontalLines(false);
    jScrollPane2.setViewportView(mTable);

    theTable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
    theTable.setModel(new javax.swing.table.DefaultTableModel(curTable.getDataTable(),
        curTable.getcolumnTable())
    );
    theTable.setColumnSelectionAllowed(true);
    theTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    theTable.setEnabled(false
    );
    theTable.setGridColor(new java.awt.Color(255, 255, 255));
    theTable.setRowHeight(22);
    theTable.setRowSelectionAllowed(false);
    tablePanel.setViewportView(theTable);
    theTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

    jTabbedPane1.addTab("Scan", tablePanel);

    jPanel1.setBackground(new java.awt.Color(204, 204, 204));

    quotaButton.setText("Confirm");
    quotaButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            quotaButtonActionPerformed(evt);
        }
    });

    infoTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    infoTable.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
    infoTable.setModel(new javax.swing.table.DefaultTableModel(infoDataTable,infoColumn));
    infoTable.setEnabled(false);
    infoTable.setRowHeight(20);
    jScrollPane1.setViewportView(infoTable);

    eQuotaLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    eQuotaLabel.setText("Enter Quota");

    jLabel1.setText("Current Quota");

    quotaLabel.setBackground(new java.awt.Color(255, 255, 255));
    quotaLabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
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
                .addComponent(quotaButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(quotaTextField)
                .addComponent(eQuotaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(quotaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(48, 48, 48)
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

    multiScanLabel.setBackground(new java.awt.Color(204, 204, 204));
    multiScanLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
    multiScanLabel.setText("MULTI SCAN COUNTER");

    jPanel2.setBackground(new java.awt.Color(204, 204, 204));
    jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

    snapShotButton.setText("SnapShot");
    snapShotButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            snapShotButtonActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGap(33, 33, 33)
            .addComponent(snapShotButton)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(snapShotButton)
            .addContainerGap())
    );

    jPanel3.setBackground(new java.awt.Color(204, 204, 204));
    jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

    jToggleButton1.setText("Multi Scan");
    jToggleButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
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
    jToggleButton1.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            jToggleButton1KeyPressed(evt);
        }
    });

    techField.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    techField.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    techField.setText("Tech");

    multiLable.setBackground(new java.awt.Color(255, 255, 255));
    multiLable.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
    multiLable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    multiLable.setText("OFF");
    multiLable.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

    deviceField.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    deviceField.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    deviceField.setText("Device");

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(multiLable, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(9, 9, 9)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(techFieldName)
                        .addComponent(techField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(devFieldName)
                        .addComponent(deviceField, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(techField)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(techFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(deviceField)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(devFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(23, 23, 23)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addComponent(multiLable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );

    jPanel4.setBackground(new java.awt.Color(204, 204, 204));
    jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

    hourLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    hourLabel.setText("Select Hour(s)");

    progressBar.setForeground(new java.awt.Color(102, 153, 255));
    progressBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

    dTableList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    dTableList.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
    dTableList.setMultipleMode(true);

    exportMergeButton.setText("Export/Merge");
    exportMergeButton.setEnabled(false);
    exportMergeButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            exportMergeButtonActionPerformed(evt);
        }
    });

    exportButton.setText("Export");
    exportButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            exportButtonActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
    jPanel5.setLayout(jPanel5Layout);
    jPanel5Layout.setHorizontalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(exportButton, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                .addComponent(exportMergeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );
    jPanel5Layout.setVerticalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(exportButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(exportMergeButton)
            .addContainerGap())
    );

    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addComponent(hourLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
                .addComponent(dTableList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(hourLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(dTableList, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(24, 24, 24)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
    );

    dbStatusLabel.setForeground(new java.awt.Color(255, 0, 0));
    dbStatusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    dbStatusLabel.setText(" ");

    dbStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    dbStatus.setText(" ");

    lastScanDetailArea.setEditable(false);
    lastScanDetailArea.setBackground(new java.awt.Color(233, 233, 233));
    lastScanDetailArea.setColumns(15);
    lastScanDetailArea.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
    lastScanDetailArea.setLineWrap(true);
    lastScanDetailArea.setRows(5);
    jScrollPane3.setViewportView(lastScanDetailArea);

    jMenu1.setText("File");
    jMenuBar1.add(jMenu1);

    editMenu.setText("Edit");

    undoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
    undoMenuItem.setText("Undo");
    undoMenuItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            undoMenuItemActionPerformed(evt);
        }
    });
    editMenu.add(undoMenuItem);

    editModeMenuItem.setText("Enable Edit Mode");
    editModeMenuItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            editModeMenuItemActionPerformed(evt);
        }
    });
    editMenu.add(editModeMenuItem);

    jMenuBar1.add(editMenu);

    jMenu3.setText("Tools");

    addExistingTechMenuItem.setText("Add Existing Tech");
    addExistingTechMenuItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            addExistingTechMenuItemActionPerformed(evt);
        }
    });
    jMenu3.add(addExistingTechMenuItem);

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
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(multiScanLabel)
                    .addGap(274, 274, 274))
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dbStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dbStatusLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2))))
            .addGap(18, 18, 18)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(11, 11, 11))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(111, 111, 111)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(11, 11, 11)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(29, 29, 29)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(multiScanLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                    .addGap(29, 29, 29)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(dbStatus)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(dbStatusLabel)
            .addContainerGap())
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
            
            techFieldName.setText(techFieldName.getText().trim());
            
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
            
            devFieldName.setText(devFieldName.getText().toLowerCase().trim());
            
            if(tableModel.findColumn(devFieldName.getText())!=-1){
                toTable();
                if(!multiMap.isEmpty()){
                    commitMTable();
                }
                techFieldName.setEditable(true);
                manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusPreviousComponent();
                techFieldName.setText("");
                devFieldName.setEnabled(false);
            }else if(devFieldName.getText().toUpperCase().equals("CLEAR")){
                if(!multiMap.isEmpty()){
                    multiMap.clear();
                }
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
            
            devFieldName.setText(devFieldName.getText().toLowerCase().trim());
            
            if(devFieldName.getText().equals("")&&!multiMap.isEmpty()){
                //toMulti();
                commitMTable();
                techFieldName.setEditable(true);
                manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusPreviousComponent();
                techFieldName.setText("");
                devFieldName.setEnabled(false);
                if(oFrame.getCheckBoxStatus()){
                    if(multiSelected){
                        multiSelected = false;
                        jToggleButton1.setSelected(false);
                        multiLable.setText("OFF");
                    }
                }
            }else if(tableModel.findColumn(devFieldName.getText())!=-1){
                toMulti();
            }else if(devFieldName.getText().toUpperCase().equals("CLEAR")){
                
                for(int i=0;i<multiDataTable.length;i++){
                    setMultiTableValues(0,i,1);
                }
        
                multiMap.clear();
                manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusPreviousComponent();
                techFieldName.setEditable(true);
                techFieldName.setText("");
                devFieldName.setEnabled(false);
                devFieldName.setText("");    
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
        JLabel cen = new JLabel("Are You Sure?",JLabel.CENTER);
        Object[] options = {"Yes",
                    "No"};
        
        int n = JOptionPane.showOptionDialog(this,
            cen,
            "Continue",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[1]);
        
        if(n==JOptionPane.YES_OPTION){
            if(!curTable.isDTListEmpty()){
            
            if(!curTable.checkDTExists(timeID)){
                
                dTableList.add(curTable.updateTableViaList(getModel(),dTableList.getItems()));
                curTable.writeSaveTable();
      
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
            
            lastActionStack.clear();
            lastScanDetailArea.setText(" ");
        }   
        
    }//GEN-LAST:event_snapShotButtonActionPerformed

    private void updateInfoTable(){
        
        SHIFT_HOURS = oFrame.getShiftHours();
        
        int row = 0;
        int col = 0;
        int totalRow = 0;
        int value = 0;
        int eodValue =0;
        int estCol = 0;
        int eodTotal = 0;
        int avgTotal = 0;
        
        col = getInfoModel().findColumn("Avg/Hr");
        estCol = getInfoModel().findColumn("EOD Est");
        totalRow = getRow(getInfoModel(),"TOTAL");

        for(Map.Entry<String,String> entry:curTable.getTechAvgList().entrySet()){
            
            row = getRow(getInfoModel(),entry.getKey());
            value = Integer.valueOf(entry.getValue())/dTableList.getItemCount();
            eodValue = value*SHIFT_HOURS;
            
            eodTotal+=eodValue;
            avgTotal+=value;
            
            getInfoModel().setValueAt(value,row,col);
            getInfoModel().setValueAt(eodValue,row,estCol);
        }
        
        getInfoModel().setValueAt(avgTotal,totalRow,col);
        getInfoModel().setValueAt(eodTotal,totalRow,estCol);
        
    }
    
    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
        abstractButton = (AbstractButton) evt.getSource();
        multiSelected = abstractButton.getModel().isSelected();
        
        if(multiSelected){
            multiLable.setText("ON");         
        }else{
            multiLable.setText("OFF");
        }
        
        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.focusPreviousComponent();
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jToggleButton1StateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jToggleButton1StateChanged

    private void devFieldNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_devFieldNameFocusGained
        // TODO add your handling code here:
    
                
    }//GEN-LAST:event_devFieldNameFocusGained

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
        // TODO add your handling code here:
        
        if(dTableList.getSelectedItems().length>0){
            
            Object[] options = {"Yes", "No"};
            
            int n = JOptionPane.showOptionDialog(this,
                "Export Selected Time(s)?",
                "Continue",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

            if(n == JOptionPane.YES_OPTION){
                
                thread1 = new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            startMaker();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ExcelFrame.class.getName()).log(Level.SEVERE, null, ex);
                            errorLogger.writeToLogger(ex.toString());
                        }
                    }

                });

                thread2 = new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            startProgBar();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ExcelFrame.class.getName()).log(Level.SEVERE, null, ex);
                            errorLogger.writeToLogger(ex.toString());
                        }
                    }

                });

                thread3 = new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            startTableWriter();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ExcelFrame.class.getName()).log(Level.SEVERE, null, ex);
                            errorLogger.writeToLogger(ex.toString());
                        }
                    }

                });
                    
                thread2.start();
                thread3.start();
                thread1.start();
            }
            
        }else{
            JOptionPane.showMessageDialog(this,"    No Hour(s) Selected","Try Again", JOptionPane.WARNING_MESSAGE);
        }
        
        
        
    }//GEN-LAST:event_exportButtonActionPerformed

    private void startMaker() throws InterruptedException{
        
        exportButton.setEnabled(false);
        undoMenuItem.setEnabled(false);
        techFieldName.setEnabled(false);
        devFieldName.setEnabled(false);
        addExistingTechMenuItem.setEnabled(false);

        Thread.sleep(1000);
        
        synchronized(this){
            
            makeTables(dTableList.getSelectedItems());
            makeProdTable(getModel());
            notify();
            
        }
       
    }
    
    private void startProgBar() throws InterruptedException{
        
       progressBar.setIndeterminate(true);
       progressBar.setString("Exporting");
    }
    
    private void startTableWriter() throws InterruptedException{
 
        StringBuilder location = new StringBuilder();
        
        synchronized(this){
            
            wait();
  
            progressBar.setIndeterminate(false);
            progressBar.setString("Ready");
            
            File areaFolder = new File("Production\\"+curTable.getAreaName());
            
            if(areaFolder.exists()){
                location.append("Production\\"+curTable.getAreaName()+"\\"+"OutBoundProd_");   
            }else{
                location.append("Production\\Offline\\"+"OutBoundProd_");
            }
            
            location.append("S"+curTable.getShiftInfo()+"_");
            location.append(String.valueOf(sdf.format(date)));
            location.append(".xlsx");

            try (FileOutputStream outputStream = new FileOutputStream(location.toString())){ 

                workbook.write(outputStream);
                outputStream.close(); 
                JOptionPane.showMessageDialog(this,"             File Created","Written Successfully", JOptionPane.WARNING_MESSAGE);

            } catch (Exception e) {
                
                System.out.println(e.toString());
                exportButton.setEnabled(true);
                undoMenuItem.setEnabled(true);
                techFieldName.setEnabled(true);
                devFieldName.setEnabled(true);
                addExistingTechMenuItem.setEnabled(true);
                errorLogger.writeToLogger(e.toString());
            }  
        }
        
        exportButton.setEnabled(true);
        undoMenuItem.setEnabled(true);
        techFieldName.setEnabled(true);
        devFieldName.setEnabled(true);
        addExistingTechMenuItem.setEnabled(true);
        
    }
    
    
    private void optionsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsMenuItemActionPerformed
        // TODO add your handling code here:
        oFrame.setVisible(true);
        
    }//GEN-LAST:event_optionsMenuItemActionPerformed

    private void updateInfoQuota(String Quota){
        int col,row=0;
        col = getCol(getInfoModel(),"Quota");
        
        inQuota = Integer.valueOf(Quota)/curTable.getRosterNum().size();
                  
        for(String tech:curTable.getRosterNum()){
            row = getRow(getInfoModel(),tech);
            getInfoModel().setValueAt(inQuota, row, col);
            quotaTextField.setText("");
        }
    }
    
    private void quotaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quotaButtonActionPerformed
        // TODO add your handling code here:
        
        String quota = quotaTextField.getText();
        
        if(isInteger(quota)){

            if(quota.length()<=4){
                
                if(curTable.getRosterNum().size()!=0){
                    quotaLabel.setText(quota);
                    updateInfoQuota(quota);
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

    private void undoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoMenuItemActionPerformed
        // TODO add your handling code here:
        
        int c=0;
        int r=0;
        int newV=0;
        
        if(lastActionStack.size()>0){
            
            if(lastActionStack.peek().getDeviceCount()>0){
                
                techObject curTech = lastActionStack.pop();
                
                if(lastActionStack.size()>0){
                    lastScanDetailArea.setText("Last Scan Details: \n" + "\n" +
                                    "Tech: " +lastActionStack.peek().getTechID()+"\n"
                                    + "Count: " + lastActionStack.peek().getDeviceCount());
                }else{
                    lastScanDetailArea.setText(" ");
                }
                
                
                for(String dev:curTech.getDevices()){
                    c = getCol(tableModel, dev);
                    r = getRow(tableModel, curTech.getTechID());
                    
                    if((Integer)tableModel.getValueAt(r, c)>0){
                        newV = (Integer) tableModel.getValueAt(r, c) - 1;
                        setTableValues(newV, r, c);
                    } 
                }
               
            }
        }else{
            lastScanDetailArea.setText(" ");
        }
    }//GEN-LAST:event_undoMenuItemActionPerformed

    private void addExistingTechMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addExistingTechMenuItemActionPerformed
        // TODO add your handling code here:
        String[] newTechRow = new String [9];
        String[] newInfoRow = new String[getInfoModel().getColumnCount()];
        
        Object[] options = existingTechList.toArray();
        String tech = (String)JOptionPane.showInputDialog(
                            this,
                            new JLabel("Select Existing Tech", SwingConstants.CENTER),
                            "Add Tech",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            options,
                            null);

        //If a string was returned, say so.
        if ((tech != null) && (tech.length() > 0)) {
            if(!curTable.getRosterNum().contains(tech)){
                for(int i=0;i<newTechRow.length;i++){
                    newTechRow[i] = "0";
                }

                for(int i=0;i<newInfoRow.length;i++){
                    newInfoRow[i] = "0";
                }

                newTechRow[0] = tech;
                newInfoRow[0] = tech;

                if(existingRosterList.containsKey(tech)){
                  
                    curTable.addToRoster(tech,existingRosterList.get(tech));
                    newTechRow[1] = existingRosterList.get(tech);
                    newInfoRow[1] = existingRosterList.get(tech);
                }else{
                    
                    JOptionPane.showMessageDialog(this,"Unknown Error","Try Again", JOptionPane.WARNING_MESSAGE);
                }
                
                tableModel.insertRow(0,newTechRow);
                getInfoModel().insertRow(0, newInfoRow);
                updateInfoQuota(quotaLabel.getText());
                    
            }else{
                
                JOptionPane.showMessageDialog(this,"Tech Already Exists","Try Again", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_addExistingTechMenuItemActionPerformed

    private void jToggleButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jToggleButton1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton1KeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_formKeyPressed

    private void editModeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editModeMenuItemActionPerformed
        // TODO add your handling code here:
        if(editModeMenuItem.isSelected()){
            JOptionPane.showMessageDialog(this,"Warning: Table Is Now Editable, Use Only If Necessary","Warning", JOptionPane.WARNING_MESSAGE);
            theTable.setEnabled(true);
        }else{
            
            theTable.setEnabled(false);
        }
    }//GEN-LAST:event_editModeMenuItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(this,"          Are You Sure?", "Exiting",
                JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
            try{
                DatabaseObj.closeConnection();    
            }catch(Exception e){}
            
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    private void exportMergeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportMergeButtonActionPerformed
        // TODO add your handling code here:
         
        File file;
        
        fileChooser.setCurrentDirectory(new java.io.File("."));
        fileChooser.setDialogTitle("Select File");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(filter);
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
          file = fileChooser.getSelectedFile();
          readFileMerge(file);
        }else {
          System.out.println("No Selection ");
        }
             
    }//GEN-LAST:event_exportMergeButtonActionPerformed
    
    private void readFileMerge(File file){
        try {
           
            Workbook workbook = WorkbookFactory.create(file);
            int rowNum;
            
            // Getting the Sheet at index one
            Sheet sheet = workbook.getSheetAt(1);

            // Create a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();
            
            System.out.println(workbook.getNumberOfSheets());
            
            for (Row row: sheet) {
                
                for(Cell cell: row) {
                    
                    String cellValue = dataFormatter.formatCellValue(cell);
                    
                    if(cellValue == "Tech"){
                        rowNum = row.getRowNum();
                    }
                    System.out.print(cellValue + "\t");
                }
                System.out.println();
            }
            
            
                        
        } catch (IOException ex) {
            Logger.getLogger(ExcelFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ExcelFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EncryptedDocumentException ex) {
            Logger.getLogger(ExcelFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean isInteger(String s) {
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
        CellRangeAddress range;
                
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
            
           
            range = new CellRangeAddress(
                    rowNum,rowNum,colNum,excelColumn.size());
           
            
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
            
            for(int col = 0;col<excelColumn.size();col++){
                
                Cell headerCell = header.createCell(colNum++);
                headerCell.setCellValue(excelColumn.get(col));  
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
                    
                    if(tableRow[0].equals("TotalDev") && (curCell == tableRow.length)){
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
        
        CellRangeAddress range = new CellRangeAddress(
                    rowNum,rowNum,colNum,excelColumn.size());
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
            
            for(int col = 0;col<excelColumn.size();col++){
                
                Cell headerCell = header.createCell(colNum++);
                headerCell.setCellValue(excelColumn.get(col));  
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
                    
                    if(tableRow[0].equals("TotalDev") && (curCell == tableRow.length)){
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
        
        techObject tech = new techObject();
        tech.setTechID(techFieldName.getText());
        
        if(!multiMap.isEmpty()){
            int row = getRow(tableModel,techFieldName.getText());
            
            for(Map.Entry<String,Integer> entry:multiMap.entrySet()){
                
                String device = entry.getKey();
                
                int value = entry.getValue();   
                int col = getCol(tableModel,device);

                int oldValue = Integer.parseInt(tableModel.getValueAt(row, col).toString());
                int newValue = oldValue + value;
                
                setTableValues(newValue,row,col);  
                
                /////////////////////////////////////UNDO/////////////////////////////////////////////
                for(int i=0;i<value;i++){
                    tech.addTechDevice(device);
                }
            }
            
            lastActionStack.push(tech);
            
            lastScanDetailArea.setText("Last Scan Details: \n" + "\n" +
                                    "Tech: " +lastActionStack.peek().getTechID()+"\n"
                                    + "Devices: Many \n"
                                    + "Count: " + lastActionStack.peek().getDeviceCount());

            if(lastActionStack.size()>5){
                lastActionStack.remove(0);    
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

        ////////////////////////////////////////////////////////UNDO//////////////////////////////// 
        techObject tech = new techObject();
        tech.setTechID(techFieldName.getText());
        
        for(int i=0;i<6;i++){
            tech.addTechDevice(device);
        }
        lastActionStack.push(tech);
        
        lastScanDetailArea.setText("Last Scan Details: \n" + "\n" +
                                    "Tech: " +lastActionStack.peek().getTechID()+"\n"
                                    + "Device: " + device + "\n"
                                    + "Count: " + lastActionStack.peek().getDeviceCount());
       
        if(lastActionStack.size()>3){
            lastActionStack.remove(0);
        }

    }
    
    private void setTableValues(int val, int row, int col){
        
        tableModel.setValueAt(val, row, col);    
        updateTotalDev(tableModel);
        updateTotalTech(tableModel);
        
        curTable.updateTableSave(tableModel);
        curTable.writeSaveTable();
    }
    
    public static void setTableValues(DefaultTableModel model,int val, int row, int col){
        
        model.setValueAt(val, row, col);    
        updateTotalDev(model);
        updateTotalTech(model);
        curTable.updateTableSave(model);
        curTable.writeSaveTable();
       
    }
    
    private void setMultiTableValues(int val, int row, int col){
        
        mTableModel.setValueAt(val, row, col);    
   
    }
    
    public static void updateTotalDev(DefaultTableModel model){
     
        int col =0;
        int devRow =0;
        int sum =0;
        
        devRow = getRow(model,"TotalDev");
        
        for(String dev:curTable.getAreaDevices()){
            sum = 0;
            col = getCol(model,dev);

            for(int row =0;row<model.getRowCount()-1;row++){
                sum += Integer.parseInt(model.getValueAt(row, col).toString());
            }

            model.setValueAt(sum, devRow, col);

        }
  
    }
    
    private static void updateTotalTech(DefaultTableModel model){
 
        int totCol,sum,value;
        int tttSum = 0;

        totCol = getCol(model,"TechTotal");
        
        for(String tech:curTable.getRosterNum()){
            int row = getRow(model,tech);
            sum = 0;
            
            for(String dev:curTable.getAreaDevices()){
                int col = getCol(model,dev);
                value = Integer.parseInt(model.getValueAt(row, col).toString());
                sum += value;
                
                ////////////////////////////////////////////////////////////////////////DATABASE///////////////////////////////////////////
                if(DatabaseObj.getStatusBoolean()){
                    
                    if(curTable.getEntryID()!=0){
                        
                        try{
                            if(DatabaseObj.executeTechProdEntryEmployeeExistsQ(tech, curTable.getEntryID())){
                                DatabaseObj.executeUpdateTechProdQ(
                                    tech,dev,value,curTable.getEntryID());
                            }else{
                                DatabaseObj.executeTechProdEntriesAppendQ(curTable.getDBEntryInfo(), tech);
                            }          
                        }catch(Exception e){
                            
                            System.out.println(e.toString());
                            errorLogger.writeToLogger(e.toString());
                        }
                    }
                    
                }else{
                    updateDatabaseStatus();
                }
            }

            tttSum += sum;
            
            model.setValueAt(sum,row,totCol);
        }
        
        updateTTT(tttSum,model);
 
    }
    
    private static void updateTTT(int sum,DefaultTableModel model){
        
        int totCol = getCol(model,"TechTotal");
        int totRow = getRow(model,"TotalDev");
        
        model.setValueAt(sum, totRow, totCol);

        ////////////////////////////////////////////////////////////////////////DATABASE///////////////////////////////////////////
        //update database totals
        if(DatabaseObj.getStatusBoolean()){
            
            try{
            
                DatabaseObj.executeUpdateTotalsQuery((int)model.getValueAt(totRow, totCol),curTable.getEntryID());

                dbStatusLabel.setText(" ");
            }catch(Exception e){

                dbStatusLabel.setText(e.toString());
                errorLogger.writeToLogger(e.toString());
            }
        }else{
            updateDatabaseStatus();
        }
  
    }
    private static int getCol(DefaultTableModel model, String deviceName){
        return model.findColumn(deviceName);
    }
    
    private static int getRow(DefaultTableModel model, String value){
  
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
    
    public static void setStyleFontBlack(CellStyle style) {
        XSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
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
    
    public static void cellFillWhite(CellStyle style) {
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
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
    private javax.swing.JMenuItem addExistingTechMenuItem;
    private java.awt.List dTableList;
    private static javax.swing.JLabel dbStatus;
    private static javax.swing.JLabel dbStatusLabel;
    private static javax.swing.JTextField devFieldName;
    private javax.swing.JLabel deviceField;
    private javax.swing.JLabel eQuotaLabel;
    private javax.swing.JMenu editMenu;
    private javax.swing.JCheckBoxMenuItem editModeMenuItem;
    private javax.swing.JButton exportButton;
    private javax.swing.JButton exportMergeButton;
    private javax.swing.JLabel hourLabel;
    public javax.swing.JTable infoTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JTextArea lastScanDetailArea;
    private javax.swing.JTable mTable;
    private javax.swing.JLabel multiLable;
    private javax.swing.JLabel multiScanLabel;
    private javax.swing.JMenuItem optionsMenuItem;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton quotaButton;
    private javax.swing.JLabel quotaLabel;
    private javax.swing.JTextField quotaTextField;
    private javax.swing.JButton snapShotButton;
    private javax.swing.JScrollPane tablePanel;
    private javax.swing.JLabel techField;
    private static javax.swing.JTextField techFieldName;
    private javax.swing.JTable theTable;
    private javax.swing.JMenuItem undoMenuItem;
    // End of variables declaration//GEN-END:variables
}
