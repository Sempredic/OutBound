/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import java.awt.Color;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
    DefaultTableModel labelerTableModel;
    HashMap<String,Integer> multiMap;
    static int multiCounter;
    String[] multiColumn;
    Object[][] multiDataTable;
    String[] infoColumn;
    String[] labelerColumn;
    Object[][] infoDataTable;
    Object[][] labelerTable;
    static XSSFWorkbook workbook;
    static CellStyle style;
    static CellStyle headStyle;
    static CellStyle titleStyle;
    static XSSFSheet sheet;
    static XSSFSheet sheet2;
    static XSSFSheet LabelerSheet;
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
    CellStyle lockedStyle;
    boolean lablerEnabled;
    JTable lTable;
    ArrayList lTableRoster;
    JScrollPane labelerScrollPane;
    
    
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
        lablerEnabled = false;
        lTable = new JTable();
        lTableRoster = new ArrayList();
        
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
        
        buildTableSaves();
        
        abstractButton = (AbstractButton) multiScanButton;
        multiSelected = abstractButton.getModel().isSelected();
        
        if(multiSelected){
            multiLable.setText("ON");         
        }else{
            multiLable.setText("OFF");
        }
        
        titleLabel.setText(curTable.getTableName());
        
        initLabelers();
        
    }
    
    private void initLabelers(){
        
        if(DatabaseObj.getStatusBoolean() && curTable.getEntryID() != 0){
                try{
                    ArrayList<ArrayList> labelerData = DatabaseObj.executeGetLabelerRecordsQ(curTable.getEntryID());
                    
                    if(!labelerData.isEmpty()){
                        
                        for(ArrayList list:labelerData){
                            Object nObject = list.get(1).toString() + (String)list.get(2).toString();
                            list.set(1, nObject);
                            list.remove(2);
                        }
                        
                        labelerColumn = new String[] {"Tech#","Name","Units"};
                        labelerTable = new Object[labelerData.size()][labelerData.get(0).size()];

                        for(int row = 0;row < labelerData.size();row++){
                            for(int col = 0;col < labelerData.get(0).size();col++){
                                labelerTable[row][col] = labelerData.get(row).get(col);
                                if(col == 0){
                                    lTableRoster.add(labelerData.get(row).get(col));
                                }  
                            }
                        }

                        lTable.setEnabled(false);

                        lTable.setRowHeight(20);
                        lTable.setModel(new DefaultTableModel(labelerTable,labelerColumn));

                        boolean labels = false;
                        
                        for(int tab = 0;tab <jTabbedPane1.getTabCount();tab++){
                            if(jTabbedPane1.getTitleAt(tab)=="Labeler"){
                                labels = true;
                            }
                        }
                        
                        if(!labels){
                           jTabbedPane1.addTab("Labeler",new JScrollPane(lTable)); 
                        }
                        

                        //System.out.println(labelerData);
                    }else{
                        System.out.println("No Labeler Data Found");
                    }
                    
                }catch(Exception e){
                    System.out.println(e.toString());
                }
            
            }
    }
    
    private void buildTableSaves(){
        
        if(curTable.getEntryID()!=0){
            File fileFolder = new File("Production\\"+curTable.getAreaName());
            String fileLocation = null;
            
            if(fileFolder.exists()){
            
                for(File file:fileFolder.listFiles()){
                    String[] fileName = file.getName().split("_");

                    if(fileName[0].contains(String.valueOf(curTable.getEntryID()))){
                        fileLocation = file.getAbsolutePath();
                    }     
                }

                if(fileLocation !=null){
                    File file = new File(fileLocation);
                    readFileMerge(file);
                }else{
                    System.out.println("Doesn't Exist");
                }
            }

            
        }
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
        
        String employeeNumber = " ";
        String employeeName = " ";
        String[] employeeArray = new String[2];
        existingTechList.clear();
        
         try{
             File tmpDir = new File("Data\\employeesList.txt");
             boolean exists = tmpDir.exists();
             
             if(exists){
                for(String name:Files.readAllLines(Paths.get("Data\\employeesList.txt"))){

                    if(name.contains("BD")){
                        employeeArray = name.split(" ");

                        if(employeeArray.length == 2){
                            employeeNumber = employeeArray[0];
                            employeeName = employeeArray[1]; 
                        }else{
                            employeeNumber = employeeArray[0] + " BD";
                            employeeName = employeeArray[2];   
                        }

                    }else{
                        employeeArray = name.split(" ");
                        employeeNumber = employeeArray[0];
                        employeeName = employeeArray[1]; 
                    }
                    
                    System.out.println(existingRosterList);
                    
                    if(!existingRosterList.containsKey(employeeName)){
                       existingRosterList.put(employeeNumber,employeeName);
                       existingTechList.add(employeeNumber);
                    }
                }
            }
        }catch(Exception e){
            System.out.println("ExcelFrame InitExistingTechList");
            System.out.println(e.toString());
            dbStatusLabel.setText("");
            dbStatusLabel.setText(e.toString());
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
        multiScanButton = new javax.swing.JToggleButton();
        techField = new javax.swing.JLabel();
        multiLable = new javax.swing.JLabel();
        techFieldName = new javax.swing.JTextField();
        devFieldName = new javax.swing.JTextField();
        deviceField = new javax.swing.JLabel();
        caseLabel = new javax.swing.JLabel();
        caseTextField = new javax.swing.JTextField();
        labelerTextField = new javax.swing.JTextField();
        labelerLabel = new javax.swing.JLabel();
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
        titleLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        editMenu = new javax.swing.JMenu();
        undoMenuItem = new javax.swing.JMenuItem();
        editModeMenuItem = new javax.swing.JCheckBoxMenuItem();
        addDeviceMenuItem = new javax.swing.JMenuItem();
        addLabelerMenuItem = new javax.swing.JMenuItem();
        labelerMenuItem = new javax.swing.JMenu();
        addExistingTechMenuItem = new javax.swing.JMenuItem();
        labelerCheckMenuItem = new javax.swing.JCheckBoxMenuItem();
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
    multiScanLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
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

    multiScanButton.setSelected(true);
    multiScanButton.setText("Multi Scan");
    multiScanButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    multiScanButton.setEnabled(false);
    multiScanButton.addChangeListener(new javax.swing.event.ChangeListener() {
        public void stateChanged(javax.swing.event.ChangeEvent evt) {
            multiScanButtonStateChanged(evt);
        }
    });
    multiScanButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            multiScanButtonActionPerformed(evt);
        }
    });
    multiScanButton.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            multiScanButtonKeyPressed(evt);
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

    caseLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    caseLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    caseLabel.setText("Case");

    caseTextField.setEnabled(false);
    caseTextField.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            caseTextFieldKeyPressed(evt);
        }
    });

    labelerTextField.setEnabled(false);
    labelerTextField.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            labelerTextFieldKeyPressed(evt);
        }
    });

    labelerLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    labelerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    labelerLabel.setText("Labeler");

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(multiScanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(multiLable, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(caseTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                        .addComponent(techFieldName, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(techField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(devFieldName, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(deviceField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                        .addComponent(caseLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelerTextField)
                        .addComponent(labelerLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(techField)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(techFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(caseLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(caseTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelerLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(labelerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(deviceField)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(devFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(29, 29, 29)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(multiScanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(multiLable, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addComponent(dTableList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
    titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    titleLabel.setText(" ");

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

    addDeviceMenuItem.setText("Add Device");
    addDeviceMenuItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            addDeviceMenuItemActionPerformed(evt);
        }
    });
    editMenu.add(addDeviceMenuItem);

    addLabelerMenuItem.setText("Add Labeler");
    addLabelerMenuItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            addLabelerMenuItemActionPerformed(evt);
        }
    });
    editMenu.add(addLabelerMenuItem);

    jMenuBar1.add(editMenu);

    labelerMenuItem.setText("Tools");
    labelerMenuItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            labelerMenuItemActionPerformed(evt);
        }
    });

    addExistingTechMenuItem.setText("Add Existing Tech");
    addExistingTechMenuItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            addExistingTechMenuItemActionPerformed(evt);
        }
    });
    labelerMenuItem.add(addExistingTechMenuItem);

    labelerCheckMenuItem.setSelected(true);
    labelerCheckMenuItem.setText("Toggle Labeler");
    labelerMenuItem.add(labelerCheckMenuItem);

    optionsMenuItem.setText("Options...");
    optionsMenuItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            optionsMenuItemActionPerformed(evt);
        }
    });
    labelerMenuItem.add(optionsMenuItem);

    jMenuBar1.add(labelerMenuItem);

    setJMenuBar(jMenuBar1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
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
                .addComponent(jScrollPane2)
                .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(multiScanLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGap(18, 18, 18)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(11, 11, 11))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addComponent(titleLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jTabbedPane1)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(multiScanLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                //devFieldName.setEnabled(true);
                techFieldName.setEditable(false);
                caseTextField.setEnabled(true);
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
        String labelerID = labelerTextField.getText();
        
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
                caseTextField.setText("");
                labelerTextField.setText("");
                devFieldName.setEnabled(false);
            }else if(devFieldName.getText().toUpperCase().equals("CLEAR")){
                if(!multiMap.isEmpty()){
                    multiMap.clear();
                }
                manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusPreviousComponent();
                techFieldName.setEditable(true);
                techFieldName.setText("");
                caseTextField.setText("");
                devFieldName.setEnabled(false);
                devFieldName.setText("");
                labelerTextField.setText("");
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
            
            if((devFieldName.getText().equals("") &&!multiMap.isEmpty())||(devFieldName.getText().equals("validate") &&!multiMap.isEmpty())){
                //toMulti();
                //////////////////////////////////////////DATABASE////////////////
                try{
                    if(DatabaseObj.getStatusBoolean()&&curTable.getEntryID()!=0){
                        
                        File dbFile = new File(DatabaseObj.getDatabaseLocation());
                        
                        if(dbFile.exists()){
                            
                            System.out.println(DatabaseObj.executeCaseEntryAppendQ(
                                curTable.getDBEntryInfo(),techFieldName.getText(),caseTextField.getText(),multiMap,labelerID));
                            DatabaseObj.executeLabelerProdEntriesAppendQ();
                            dbStatusLabel.setText("");  
                            //DatabaseObj.getConnectionObj().rollback();
                        }else{
                            System.out.println("devFieldNamePressed");
                        }
                        
                    }
                    
                }catch(Exception e){

                    System.out.println(e.toString());
                    dbStatusLabel.setText("");
                    dbStatusLabel.setText(e.toString());
                    errorLogger.writeToLogger(e.toString());
                    
                }
                
                commitMTable();
                techFieldName.setEditable(true);
                manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusPreviousComponent();
                techFieldName.setText("");
                caseTextField.setText("");
                devFieldName.setText("");
                labelerTextField.setText("");
                devFieldName.setEnabled(false);
                if(oFrame.getCheckBoxStatus()){
                    if(multiSelected){
                        multiSelected = false;
                        multiScanButton.setSelected(false);
                        multiLable.setText("OFF");
                    }
                }
                
                JOptionPane.showMessageDialog(this,
                        new JLabel("Case Added Successfully!",JLabel.CENTER),
                        "Success",
                        JOptionPane.PLAIN_MESSAGE);
            }else if(devFieldName.getText().toUpperCase().equals("CLEAR")){
                
                for(int i=0;i<multiDataTable.length;i++){
                    setMultiTableValues(0,i,1);
                }
        
                multiMap.clear();
                manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusPreviousComponent();
                techFieldName.setEditable(true);
                techFieldName.setText("");
                caseTextField.setText("");
                devFieldName.setEnabled(false);
                devFieldName.setText("");    
                labelerTextField.setText("");
            }else if(DatabaseObj.getSKUStatusBoolean()){
                if(tableModel.findColumn(devFieldName.getText())!=-1){
                    toMulti();
                }else{                   
                        ///////////////////////////////////DATABASE////////////////////////////////
                    try{
                        String skuValue = " ";
                        
                        if(!devFieldName.getText().contains(" ")){
                            skuValue = DatabaseObj.getDeviceNameFromSKUQ(devFieldName.getText());
                        }
                        

                        if(tableModel.findColumn(skuValue)!=-1){
                            devFieldName.setText(skuValue);
                            toMulti();
                            dbStatusLabel.setText("");
                        }else if(curTable.getAreaDevices().contains(devFieldName.getText().trim())){
                            if(tableModel.findColumn(devFieldName.getText().trim())!=-1){
                                devFieldName.setText(devFieldName.getText().trim());
                                toMulti();
                                dbStatusLabel.setText("");
                            }
                        }else if(curTable.getAreaDevices().contains(devFieldName.getText().split(" ")[0].trim())){
                            String[] stAr = devFieldName.getText().split(" ");

                            if(tableModel.findColumn(stAr[0].trim())!=-1){
                                if(isInteger(stAr[1].trim())){
                                    
                                    devFieldName.setText(stAr[0].trim());
                                    toMulti(Integer.parseInt(stAr[1].trim()));
                                    
                                    dbStatusLabel.setText("");

                                }
                            }else{
                                JOptionPane.showMessageDialog(this,
                                new JLabel("Device Type Not Found",JLabel.CENTER),
                                "Error",
                                JOptionPane.PLAIN_MESSAGE);
                                devFieldName.setText("");
                                dbStatusLabel.setText("");
                            }  
                            
                        }else{
                            JOptionPane.showMessageDialog(this,
                            new JLabel("Device Type Not Found",JLabel.CENTER),
                            "Error",
                            JOptionPane.PLAIN_MESSAGE);
                            devFieldName.setText("");
                            dbStatusLabel.setText("");

                        }
                    }catch(Exception e){
                        System.out.println(e.toString());
                        dbStatusLabel.setText("");
                        dbStatusLabel.setText(e.toString());
                        errorLogger.writeToLogger(e.toString());
                    }
                }
            }else if(!DatabaseObj.getSKUStatusBoolean()){
                    
                if(tableModel.findColumn(devFieldName.getText())!=-1){
                    toMulti();
                }else{
                    JOptionPane.showMessageDialog(this,
                        new JLabel("Device Type Not Found",JLabel.CENTER),
                        "Error",
                        JOptionPane.PLAIN_MESSAGE);
                    devFieldName.setText("");
                }
  
            }else{
                //custom title, no icon
                System.out.println(multiMap.isEmpty());
                JOptionPane.showMessageDialog(this,
                    new JLabel("Device Type Not Found",JLabel.CENTER),
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
                //curTable.writeSaveTable();
      
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
    
    private void multiScanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_multiScanButtonActionPerformed
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
    }//GEN-LAST:event_multiScanButtonActionPerformed

    private void multiScanButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_multiScanButtonStateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_multiScanButtonStateChanged

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
            makeLabelerTable(getLabelerModel());
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
                location.append("Production\\"+curTable.getAreaName()+"\\"+curTable.getEntryID()+"_"+"OutBoundProd_");   
            }else{
                location.append("Production\\Offline\\"+curTable.getEntryID()+"_"+"OutBoundProd_");
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
        int lc=0;
        int lr=0;
        int newV=0;
        int newLV=0;
        
        if(lastActionStack.size()>0){
            
            if(lastActionStack.peek().getDeviceCount()>0){
                
                techObject curTech = lastActionStack.pop();
                
                if(lastActionStack.size()>0){
                    lastScanDetailArea.setText("Last Scan Details: \n" + "\n" +
                                    "Tech: " +lastActionStack.peek().getTechID()+"\n"
                                    + "Case: " +lastActionStack.peek().getCaseID()+"\n"
                                    + "Count: " + lastActionStack.peek().getDeviceCount() + "\n"
                                    + "Labeled By: " + lastActionStack.peek().getLabelerID());
                }else{
                    lastScanDetailArea.setText(" ");
                }
                
                if(DatabaseObj.getStatusBoolean()&&curTable.getEntryID()!=0){
                    //////////////////////////////////DATABASE/////////////////////////////
                    try{
                        String caseID =DatabaseObj.executeCaseEntryExistsQ(
                            curTable.getDBEntryInfo(),curTech.getCaseID());
                        ArrayList deletedList = DatabaseObj.executeDeleteCaseEntryQ(caseID);
                        int techID = (int)deletedList.get(0);
                        LinkedHashMap<String,Integer> devList = (LinkedHashMap)deletedList.get(1);

                        for(Map.Entry<String,Integer>entry:devList.entrySet()){
                            if(entry.getValue()>0){
                                int col = getCol(tableModel, entry.getKey());
                                int row = getRow(tableModel, DatabaseObj.getTechIDQ(techID));

                                int newVal = (Integer) tableModel.getValueAt(row, col) - entry.getValue();
                                setTableValues(newVal, row, col);
                                dbStatusLabel.setText("");
                            }
                        }
                    }catch(Exception e){
                        System.out.println(e.toString());
                        dbStatusLabel.setText("");
                        dbStatusLabel.setText(e.toString());
                        errorLogger.writeToLogger(e.toString());
                    }
                    
                }else{
                    for(String dev:curTech.getDevices()){
                        c = getCol(tableModel, dev);
                        r = getRow(tableModel, curTech.getTechID());
                        //////LABELER//////////////////////////////////////////
                        lc = getCol(getLabelerModel(), "Units");
                        lr = getRow(getLabelerModel(), curTech.getLabelerID());

                        if((Integer)tableModel.getValueAt(r, c)>0){
                            newV = (Integer) tableModel.getValueAt(r, c) - 1;
                            setTableValues(newV, r, c);
                        } 
                        ///////////LABELER/////////////////////////////////////
                        if((Integer)getLabelerModel().getValueAt(lr, lc)>0){
                            newLV = (Integer) getLabelerModel().getValueAt(lr, lc) - 1;
                            setLabelerTableValues(newLV, lr, lc);
                        }
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
        
        initExistingTechs();
        
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

    private void multiScanButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_multiScanButtonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_multiScanButtonKeyPressed

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

    private void addDeviceMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDeviceMenuItemActionPerformed
        // TODO add your handling code here:
      
        
    }//GEN-LAST:event_addDeviceMenuItemActionPerformed

    private void caseTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_caseTextFieldKeyPressed
        // TODO add your handling code here:
        int keyCode = evt.getKeyCode();
        String caseID = new String();
        boolean error=false;
        ArrayList deletedList = new ArrayList();
        
        if(keyCode == KeyEvent.VK_ENTER){
            
            caseTextField.setText(caseTextField.getText().trim());
            
            try{
                if(DatabaseObj.getStatusBoolean()){
                    caseID =DatabaseObj.executeCaseEntryExistsQ(
                        curTable.getDBEntryInfo(),caseTextField.getText());
                }else{
                    caseID = null;
                }
                
            }catch(Exception e){
                System.out.println(e.toString());
                dbStatusLabel.setText("");
                dbStatusLabel.setText(e.toString());
                errorLogger.writeToLogger(e.toString());
            }
            
            if(caseID==null){
                
                if(labelerCheckMenuItem.getState()){
                    labelerTextField.setEnabled(true);
                    caseTextField.setEnabled(false);

                    manager.focusNextComponent(labelerTextField);
                    manager.focusNextComponent();
                }else{
                    devFieldName.setEnabled(true);
                    caseTextField.setEnabled(false);

                    manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                    manager.focusNextComponent();
                }
                
            }else{
                
                ArrayList<ArrayList> caseInfo = new ArrayList<ArrayList>();
                StringBuilder info = new StringBuilder();
                
                try{
                    caseInfo = DatabaseObj.executeGetCasesQ("CaseID","=",caseTextField.getText());
                    if(!caseInfo.isEmpty()){
                        caseInfo.get(0).remove(0);
                        caseInfo.get(0).remove(2);
                        caseInfo.get(0).remove(2);
                    }
                    
                    info.append("\n");
                    info.append("   Case Details:\n");
                    
                    for(int i = 0;i<caseInfo.get(0).size();i++){
                        switch(i){
                            case 0:
                                info.append("           Date: "+caseInfo.get(0).get(0)+"\n");
                                break;
                            case 1:
                                info.append("           Time: "+caseInfo.get(0).get(1)+"\n");
                                break;
                            case 2:
                                info.append("           Units: "+caseInfo.get(0).get(2)+"\n");
                                break;
                            case 3:
                                info.append("           User: "+caseInfo.get(0).get(3)+"\n");
                                break;
                            default:
                                break;
                        }
                    }

                }catch(Exception e){
                    System.out.println(e.toString());
                    errorLogger.writeToLogger(e.toString());
                }
                
                int option = JOptionPane.showConfirmDialog(
                        this,"Case Entry Already Exists, Overwrite? \n"
                                + info,"Case Warning",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
                
                if(option==0){
                    ///////////////////////////////Database//////////////////////////////
                    try{

                        lastActionStack.clear();
                        lastScanDetailArea.setText("");
                        deletedList=DatabaseObj.executeDeleteCaseEntryQ(caseID);
                        int techID = (int)deletedList.get(0);
                        LinkedHashMap<String,Integer> devList = (LinkedHashMap)deletedList.get(1);
                              
                        for(Map.Entry<String,Integer>entry:devList.entrySet()){
                            if(entry.getValue()>0){
                                int c = getCol(tableModel, entry.getKey());
                                int r = getRow(tableModel, DatabaseObj.getTechIDQ(techID));

                                int newV = (Integer) tableModel.getValueAt(r, c) - entry.getValue();
                                setTableValues(newV, r, c);
                            }
                        }
                        
                        dbStatusLabel.setText("");
                                            
                    }catch(Exception e){
                        
                        System.out.println(e.toString());
                        dbStatusLabel.setText("");
                        dbStatusLabel.setText(e.toString());
                        errorLogger.writeToLogger(e.toString());
                        error = true;
                    }
 
                    if(error){
                        techFieldName.setEditable(true);
                        techFieldName.setText("");
                        caseTextField.setEnabled(false);
                        caseTextField.setText("");

                        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                        manager.focusPreviousComponent(caseTextField);
                    }else{
                        
                        

                        //manager.focusNextComponent(techFieldName);
                        
                        
                        
                        if(labelerCheckMenuItem.getState()){
                            labelerTextField.setEnabled(true);
                            caseTextField.setEnabled(false);

                            manager.focusNextComponent(labelerTextField);
                            manager.focusNextComponent();
                            labelerTextField.requestFocus();
                        }else{
                            devFieldName.setEnabled(true);
                            caseTextField.setEnabled(false);
                            manager.focusNextComponent();
                        }
                        
      
                    }
                    
                }
                else if(option==1){
                    techFieldName.setEditable(true);
                    techFieldName.setText("");
                    caseTextField.setEnabled(false);
                    caseTextField.setText("");

                    manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                    manager.focusPreviousComponent(caseTextField);
                }
            }
        }
    }//GEN-LAST:event_caseTextFieldKeyPressed

    private void labelerTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_labelerTextFieldKeyPressed
        // TODO add your handling code here:
        int keyCode = evt.getKeyCode();
    
        if(keyCode == KeyEvent.VK_ENTER){
            
            if(lTableRoster.contains(labelerTextField.getText().trim())){
                labelerTextField.setEnabled(false);
                devFieldName.setEnabled(true);

                manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }else if(labelerTextField.getText().toUpperCase().trim().equals("CLEAR")){

                manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusPreviousComponent();
                techFieldName.setEditable(true);
                techFieldName.setText("");
                caseTextField.setText("");
                devFieldName.setEnabled(false);
                devFieldName.setText("");
                labelerTextField.setEnabled(false);
                labelerTextField.setText("");
                
            }else{
                JOptionPane.showMessageDialog(this,"Labeler Doesn't Exist","Try Again", JOptionPane.WARNING_MESSAGE);
                labelerTextField.setText("");
            }
            
            
        }
    }//GEN-LAST:event_labelerTextFieldKeyPressed

    private void labelerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labelerMenuItemActionPerformed
        // TODO add your handling code here:
        labelerTextField.setText("");
    }//GEN-LAST:event_labelerMenuItemActionPerformed

    private void addLabelerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLabelerMenuItemActionPerformed
        // TODO add your handling code here:
        boolean found = false;
        
        initExistingTechs();
        
        for(int index=0;index<jTabbedPane1.getTabCount();index++){
           if(jTabbedPane1.getTitleAt(index)=="Labeler"){
               found = true;
           } 
        }
        
        if(!found){
            System.out.println("Labeler Table Not Found");
            initLabelerTable();
            lTable.setModel(new DefaultTableModel(labelerTable,labelerColumn));

            jTabbedPane1.addTab("Labeler",new JScrollPane(lTable));

        }
        
        String[] newInfoRow = new String[lTable.getModel().getColumnCount()];
        
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
            if(!lTableRoster.contains(tech)){

                for(int i=0;i<newInfoRow.length;i++){
                    newInfoRow[i] = "0";
                }

                newInfoRow[0] = tech;

                if(existingRosterList.containsKey(tech)){
                  
                    lTableRoster.add(tech);

                    newInfoRow[1] = existingRosterList.get(tech);
                }else{
                    
                    JOptionPane.showMessageDialog(this,"Unknown Error","Try Again", JOptionPane.WARNING_MESSAGE);
                }
                
                getLabelerModel().insertRow(0, newInfoRow);
    
            }else{
                
                JOptionPane.showMessageDialog(this,"Tech Already Exists","Try Again", JOptionPane.WARNING_MESSAGE);
            }
        }

    }//GEN-LAST:event_addLabelerMenuItemActionPerformed
    
    private void initLabelerTable(){
        
        //int rosterSize = curTable.getRosterNum().size();

        labelerColumn = new String [] {
                "Tech#","Name","Units"};
        labelerTable = new Object[0][3];
        
        lTable.setEnabled(false);

        lTable.setRowHeight(20);
        
        

    }
    
    private void readFileMerge(File file){
        try {
           
            
            Workbook workbook = WorkbookFactory.create(file);
            // Getting the Sheet at index one
            Sheet sheet = workbook.getSheetAt(0);
            Sheet labelerSheet = null;
            ArrayList<ArrayList> arrayDataTable = new ArrayList<ArrayList>();
            
            if(DatabaseObj.getStatusBoolean() && curTable.getEntryID() != 0){
                try{
                    ArrayList<ArrayList> labelerData = DatabaseObj.executeGetLabelerRecordsQ(curTable.getEntryID());
                    
                    if(!labelerData.isEmpty()){
                        
                        for(ArrayList list:labelerData){
                            Object nObject = list.get(1).toString() + (String)list.get(2).toString();
                            list.set(1, nObject);
                            list.remove(2);
                        }
                        
                        labelerColumn = new String[] {"Tech#","Name","Units"};
                        labelerTable = new Object[labelerData.size()][labelerData.get(0).size()];

                        for(int row = 0;row < labelerData.size();row++){
                            for(int col = 0;col < labelerData.get(0).size();col++){
                                labelerTable[row][col] = labelerData.get(row).get(col);
                                if(col == 0){
                                    lTableRoster.add(labelerData.get(row).get(col));
                                }  
                            }
                        }

                        lTable.setEnabled(false);

                        lTable.setRowHeight(20);
                        lTable.setModel(new DefaultTableModel(labelerTable,labelerColumn));

                        jTabbedPane1.addTab("Labeler",new JScrollPane(lTable));

                        //System.out.println(labelerData);
                    }else{
                        System.out.println("No Labeler Data Found");
                    }
                    
                }catch(Exception e){
                    System.out.println(e.toString());
                }
            
            }else{
//              for(int sheets = 0;sheets<workbook.getNumberOfSheets();sheets++){
//
//                if(workbook.getSheetName(sheets).equals("Labeler Prod")){
//                    labelerSheet = workbook.getSheetAt(sheets);
//                    DataFormatter dataFormatter = new DataFormatter();
//                    ArrayList<ArrayList> labelerData = new ArrayList<ArrayList>();
//
//                    for (Row row: labelerSheet) {
//                        ArrayList<String> arrayRow = new ArrayList<String>();
//                        for(Cell cell: row) {
//
//                            String cellValue = dataFormatter.formatCellValue(cell);
//                            arrayRow.add(cellValue);
//
//                        }
//                        labelerData.add(arrayRow);
//                    }
//
//                    ///FILL labelerTable HERE
//                    labelerData.remove(0);
//
//                    labelerColumn = new String [labelerData.get(0).size()];
//                    labelerTable = new Object[labelerData.size()-1][labelerData.get(0).size()];
//
//                    for(int lc = 0;lc < labelerData.get(0).size();lc++){
//                        labelerColumn[lc] = (String)labelerData.get(0).get(lc);
//                    }
//
//                    labelerData.remove(0);
//
//                    for(int row = 0;row < labelerData.size();row++){
//                        for(int col = 0;col < labelerData.get(0).size();col++){
//                            labelerTable[row][col] = labelerData.get(row).get(col);
//                            if(col == 0){
//                                lTableRoster.add(labelerData.get(row).get(col));
//                            }  
//                        }
//                    }
//
//                    lTable.setEnabled(false);
//
//                    lTable.setRowHeight(20);
//                    lTable.setModel(new DefaultTableModel(labelerTable,labelerColumn));
//
//                    jTabbedPane1.addTab("Labeler",new JScrollPane(lTable));
//                }
//            }
            }

            ArrayList<ArrayList> dataTables = new ArrayList<ArrayList>();

            // Create a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();

            for (Row row: sheet) {
                ArrayList<String> arrayRow = new ArrayList<String>();
                for(Cell cell: row) {
                    
                    String cellValue = dataFormatter.formatCellValue(cell);
                    arrayRow.add(cellValue);
                   
                }
                dataTables.add(arrayRow);
            }

            int rowCounter = 0;
            ArrayList<String>tableID = new ArrayList<String>();
            ArrayList<ArrayList> tempTab = new ArrayList<ArrayList>();
           
            ArrayList<ArrayList> newArrayList = new ArrayList<ArrayList>();
            
            for(int i=0;i<dataTables.size();i++){
                
                if(dataTables.get(i).get(0).toString().startsWith("HOUR")){
                    
                    rowCounter =0;
                    
                    dTableList.add(dataTables.get(i).get(0).toString().substring(5));
                    tableID.add(dataTables.get(i).get(0).toString().substring(5));
                    dataTables.remove(i);
                    if(i != 0){
                        tempTab.add(newArrayList);
                        newArrayList = new ArrayList<ArrayList>();
                    
                         
                    }
                    
                    
                }
                
                if(dataTables.get(i).get(0).toString().startsWith("Tech#")){
                    dataTables.remove(i);
                }
                
                newArrayList.add(dataTables.get(i));
                
                if(i == dataTables.size()-1){
                    tempTab.add(newArrayList);
                }
                
                rowCounter++;
                
            }
            
            //int columns = dataTables.get(0).size();
            //int entries = (dataTables.size()/rowCounter);
            int entries = tempTab.size();
            int columns = 0;
            int counter = 0;
            int rows = 0;
            int inc = 0;
            
            for(ArrayList list:tempTab){
                
                columns = list.get(0).toString().split(",").length;
                rows = list.size();
                String[][] tempTable = new String[rows][columns];
                
                for(int row=0;row<rows;row++){
                    for(int col=0;col<columns;col++){
                        
                        tempTable[row][col] = (String)dataTables.get(counter+row).get(col);
                    }  
                }
                
                curTable.addToDataTableList(tempTable, tableID.get(inc));
                counter+=rows;
                inc++;
            }

             /*
            for(int i=0;i<entries;i++){
               
                String[][] tempTable = new String[rowCounter][columns];
                for(int row=0;row<rowCounter;row++){
                    for(int col=0;col<columns;col++){
                        
                        tempTable[row][col] = (String)dataTables.get(counter+row).get(col);
                    }  
                }
                curTable.addToDataTableList(tempTable, tableID.get(i));
                counter+=rowCounter;
              
                
            }
              */
                        
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
    
    private DefaultTableModel getLabelerModel(){
        return (DefaultTableModel)lTable.getModel();
    }
    
    public void makeTables(String[] selectedList){
        
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Prod Tables");
        style = workbook.createCellStyle();
        headStyle = workbook.createCellStyle();
        titleStyle = workbook.createCellStyle();
        lockedStyle = workbook.createCellStyle();
        lockedStyle.setLocked(true);
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
    
    public void makeLabelerTable(DefaultTableModel curModel){
        
        if(getLabelerModel().getRowCount() >0){
            LabelerSheet = workbook.createSheet("Labeler Prod");
        
            int colNum = 1;
            int rowNum = 1;
            int curCell = 0;

            Object[][] labelTable = new Object[getLabelerModel().getRowCount()+1][getLabelerModel().getColumnCount()];

            System.out.println();


            for(int col = 0; col <getLabelerModel().getColumnCount();col++){
                labelTable[0][col] = labelerColumn[col];

            }


            for(int row = 0; row <getLabelerModel().getRowCount();row++){
                for(int col = 0; col <getLabelerModel().getColumnCount();col++){
                    labelTable[row+1][col] =getLabelerModel().getValueAt(row, col);

                }
            }

    //        for(int rows = 0; rows <labelTable.length;rows++){
    //            for(int cols = 0; cols <labelTable[0].length;cols++){
    //                System.out.print(labelTable[rows][cols] +  " ");  
    //            }
    //            System.out.println();
    //        }

            CellRangeAddress range = new CellRangeAddress(
                        rowNum,rowNum,colNum,getLabelerModel().getColumnCount());

            LabelerSheet.addMergedRegion(range);

            //Creates the cell
            Row title = LabelerSheet.createRow(rowNum);
            Cell titleCell = title.createCell(colNum);
            titleCell.setCellValue("LABELER PRODUCTION");
            setStyleFontWhite(titleStyle);
            cellFillBlack(titleStyle);

            titleCell.setCellStyle(titleStyle);

            CellUtil.setAlignment(titleCell,HorizontalAlignment.CENTER);

            rowNum++;

            for(Object[] tableRow:labelTable){

                Row row = LabelerSheet.createRow(rowNum++);

                for(Object tCell:tableRow){
                    curCell++;
                    Cell cell = row.createCell(colNum++);
                    cell.setCellValue(tCell.toString());


                    cellBorderBlack(style);
                    cellFillLGrey(style);
                    setStyleFontBold(style);
                    cell.setCellStyle(style); 


                }
                colNum=1;   
                curCell=0;
            }
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
    
    private void toMulti(int num){

        String device = devFieldName.getText();
        devFieldName.setText("");
        
        if(!multiMap.containsKey(device)){
            multiMap.put(device, num);
        }else{
            int currentVal = multiMap.get(device);
            multiMap.replace(device, currentVal+num);
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
                
                ////////////////////////////////////LABELER///////////////////////////////////////////
                
                if(!labelerTextField.getText().trim().equals("")){
                    
                    int lRow = getRow(getLabelerModel(),labelerTextField.getText().trim());
                    int lValue = entry.getValue();   
                    int lCol = getCol(getLabelerModel(),"Units");
                    int oldLValue = Integer.parseInt(getLabelerModel().getValueAt(lRow, lCol).toString());
                    int newLValue = oldLValue + lValue;

                    setLabelerTableValues(newLValue,lRow,lCol); 
                    tech.setLabelerID(labelerTextField.getText().trim());
                }
                
                
                /////////////////////////////////////UNDO/////////////////////////////////////////////
                for(int i=0;i<value;i++){
                    tech.addTechDevice(device);
                }
            }
            
            tech.setCaseID(caseTextField.getText());
            
            lastActionStack.push(tech);
            
            lastScanDetailArea.setText("Last Scan Details: \n" + "\n" +
                                    "Tech: " +lastActionStack.peek().getTechID()+"\n"
                                    + "Case: "+lastActionStack.peek().getCaseID()+ "\n"
                                    + "Devices: Many \n"
                                    + "Count: " + lastActionStack.peek().getDeviceCount() + "\n"
                                    + "Labeled By: " + lastActionStack.peek().getLabelerID());

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
        tech.setCaseID(caseTextField.getText());
        
        for(int i=0;i<6;i++){
            tech.addTechDevice(device);
        }
        lastActionStack.push(tech);
        
        lastScanDetailArea.setText("Last Scan Details: \n" + "\n" +
                                    "Tech: " +lastActionStack.peek().getTechID()+"\n"
                                    + "Case: " + lastActionStack.peek().getCaseID() + "\n"
                                    + "Device: " + device + "\n"
                                    + "Count: " + lastActionStack.peek().getDeviceCount()  + "\n"
                                    + "Labeled By: " + lastActionStack.peek().getLabelerID());
       
        if(lastActionStack.size()>3){
            lastActionStack.remove(0);
        }

    }
    
    private void setTableValues(int val, int row, int col){
        
        tableModel.setValueAt(val, row, col);    
        updateTotalDev(tableModel);
        updateTotalTech(tableModel);
        
        curTable.updateTableSave(tableModel,theTable);
        curTable.writeSaveTable();
    }
    
    public static void setTableValues(DefaultTableModel model,int val, int row, int col){
        
        model.setValueAt(val, row, col);    
        updateTotalDev(model);
        updateTotalTech(model);
        curTable.updateTableSave(model,theTable);
        curTable.writeSaveTable();
       
    }
    
    private void setMultiTableValues(int val, int row, int col){
        
        mTableModel.setValueAt(val, row, col);    
   
    }
    
    private void setLabelerTableValues(int val, int row, int col){
        getLabelerModel().setValueAt(val, row, col);
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
                            
                            dbStatusLabel.setText("");
                        }catch(Exception e){
                            
                            System.out.println(e.toString());
                            dbStatusLabel.setText("");
                            dbStatusLabel.setText(e.toString());
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

                dbStatusLabel.setText("");
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
    private javax.swing.JMenuItem addDeviceMenuItem;
    private javax.swing.JMenuItem addExistingTechMenuItem;
    private javax.swing.JMenuItem addLabelerMenuItem;
    private javax.swing.JLabel caseLabel;
    private javax.swing.JTextField caseTextField;
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
    private javax.swing.JCheckBoxMenuItem labelerCheckMenuItem;
    private javax.swing.JLabel labelerLabel;
    private javax.swing.JMenu labelerMenuItem;
    private javax.swing.JTextField labelerTextField;
    private javax.swing.JTextArea lastScanDetailArea;
    private javax.swing.JTable mTable;
    private javax.swing.JLabel multiLable;
    private javax.swing.JToggleButton multiScanButton;
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
    private static javax.swing.JTable theTable;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JMenuItem undoMenuItem;
    // End of variables declaration//GEN-END:variables
}
