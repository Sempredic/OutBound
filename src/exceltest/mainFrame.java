/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import table.Table;

/**
 *
 * @author Vince
 */
public class mainFrame extends javax.swing.JFrame {

    /**
     * Creates new form mainFrame
     */
    protected LinkedHashMap<String,String> rosterList;
    protected LinkedHashMap<String,String> existingRosterList;
    boolean done;
    JTextField techNumber;
    KeyboardFocusManager focusManager;
    JTextField techName;
    boolean is_iDevice;
    manageFrame areaFrame;
  
    
    public mainFrame() {
        initComponents();
        areaFrame = new manageFrame();
        rosterList = new LinkedHashMap<String,String>();
        existingRosterList = new LinkedHashMap<String,String>();
        done = false;
        techNumber = new JTextField("");
        techName = new JTextField("");
        is_iDevice = true;
        initExistingTechList();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        doneButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        createTechButton = new javax.swing.JButton();
        delTechButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        remTechButton = new javax.swing.JButton();
        addTechButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        finalRosterList = new java.awt.List();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        selectAllButton = new javax.swing.JButton();
        deSelectButton = new javax.swing.JButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        existingList = new java.awt.List();
        nameList = new java.awt.List();
        jPanel7 = new javax.swing.JPanel();
        existingList2 = new java.awt.List();
        nameList2 = new java.awt.List();
        rosterSelectAllButton = new javax.swing.JButton();
        rosterDeselectAllButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mAreaOption = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));
        setResizable(false);
        setSize(new java.awt.Dimension(700, 515));

        doneButton.setText("Done");
        doneButton.setPreferredSize(new java.awt.Dimension(57, 15));
        doneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneButtonActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(225, 225, 225));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        createTechButton.setText("Create New Tech");
        createTechButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createTechButtonActionPerformed(evt);
            }
        });

        delTechButton.setText("Delete Tech");
        delTechButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delTechButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(createTechButton, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delTechButton, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(createTechButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(delTechButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        remTechButton.setText("<---Remove Tech ");
        remTechButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remTechButtonActionPerformed(evt);
            }
        });

        addTechButton.setText("Add Tech --->");
        addTechButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTechButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addTechButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(remTechButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addTechButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(remTechButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(225, 225, 225));

        finalRosterList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        finalRosterList.setMultipleMode(true);

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Roster List");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(finalRosterList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(finalRosterList, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Existing Techs");

        selectAllButton.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        selectAllButton.setText("Select All");
        selectAllButton.setBorderPainted(false);
        selectAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllButtonActionPerformed(evt);
            }
        });

        deSelectButton.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        deSelectButton.setText("DeSelect All");
        deSelectButton.setBorderPainted(false);
        deSelectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deSelectButtonActionPerformed(evt);
            }
        });

        existingList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        existingList.setMultipleMode(true);
        existingList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                existingListMousePressed(evt);
            }
        });

        nameList.setEnabled(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(existingList, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(nameList, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(existingList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nameList, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane3.addTab("1st", jPanel6);

        existingList2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        existingList2.setMultipleMode(true);
        existingList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                existingList2MousePressed(evt);
            }
        });

        nameList2.setEnabled(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(existingList2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(nameList2, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(existingList2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nameList2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane3.addTab("2nd", jPanel7);

        rosterSelectAllButton.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        rosterSelectAllButton.setText("Select All");
        rosterSelectAllButton.setBorderPainted(false);
        rosterSelectAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rosterSelectAllButtonActionPerformed(evt);
            }
        });

        rosterDeselectAllButton.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        rosterDeselectAllButton.setText("DeSelect All");
        rosterDeselectAllButton.setBorderPainted(false);
        rosterDeselectAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rosterDeselectAllButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTabbedPane3)
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(selectAllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(deSelectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(rosterSelectAllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rosterDeselectAllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectAllButton)
                    .addComponent(deSelectButton)
                    .addComponent(rosterSelectAllButton)
                    .addComponent(rosterDeselectAllButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Outbound Tool");
        jLabel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));

        jMenu1.setText("Options");
        jMenu1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jMenu1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        mAreaOption.setText("Manage Areas");
        mAreaOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mAreaOptionActionPerformed(evt);
            }
        });
        jMenu1.add(mAreaOption);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(doneButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(19, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addComponent(doneButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addTechButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTechButtonActionPerformed

        if(existingList.isShowing()){
            for(String tech:existingList.getSelectedItems()){
                if(!rosterList.containsKey(tech)){
                    finalRosterList.add(tech);
                    rosterList.put(tech, existingRosterList.get(tech));
                }
            }
        }else{
           for(String tech:existingList2.getSelectedItems()){
                if(!rosterList.containsKey(tech)){
                    finalRosterList.add(tech);
                    rosterList.put(tech, existingRosterList.get(tech));
                }
            } 
        }
        
    }//GEN-LAST:event_addTechButtonActionPerformed


    private void createTechButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createTechButtonActionPerformed
        // TODO add your handling code here:
     
        JPanel p = new JPanel(new BorderLayout(5,5));

        JPanel labels = new JPanel(new GridLayout(0,1,2,2));
        labels.add(new JLabel("Tech Number", SwingConstants.RIGHT));
        labels.add(new JLabel("Tech Name", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0,1,2,2));
        techNumber.requestFocusInWindow();
        controls.add(techNumber);
        controls.add(techName);
        p.add(controls, BorderLayout.CENTER);

        int option = JOptionPane.showConfirmDialog(this, p, "Create Tech", JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            
            if(techNumber.getText().length()==4){
                if(techName.getText().length()<=10){
                    if(!existingRosterList.containsKey(techNumber.getText())){
                        if(!existingRosterList.containsValue(techName.getText())){
                            
                            if(existingList.isShowing()){
                                existingList.add(techNumber.getText());
                                nameList.add(techName.getText());
                            }else{
                                existingList2.add(techNumber.getText());
                                nameList2.add(techName.getText());
                            }

                            if(techName.getText().length()==0){
                                existingRosterList.put(techNumber.getText(),"**");
                            }else{
                                existingRosterList.put(techNumber.getText(),techName.getText());
                            }

                            techNumber.setText("");
                            techName.setText("");
                        }else{
                            JOptionPane.showMessageDialog(this,"Tech Name Already Exists","Try Again", JOptionPane.WARNING_MESSAGE);
                            techNumber.setText("");
                            techName.setText("");
                        }
                        
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
    }//GEN-LAST:event_createTechButtonActionPerformed

    private void doneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneButtonActionPerformed
        // TODO add your handling code here:
        Object[] options = {"iDevice","Android"};
        String dpt = (String)JOptionPane.showInputDialog(
                            this,
                            new JLabel("Select Area", SwingConstants.CENTER),
                            "Department Type",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            options,
                            null);
       if(dpt == "Android"){
           is_iDevice = false;


       }else if(dpt == null){
           return;
       }

        writeToFileSave();
        writeToFileSave2();
        setRoster(rosterList);
        prepExcelFrame();
        dispose();
    }//GEN-LAST:event_doneButtonActionPerformed

    private void writeToFileSave(){
        String tName = new String();
        
        try{
            PrintWriter writer = new PrintWriter("roster.txt", "UTF-8");
            
            for(String tNumber:existingList.getItems()){
                if(existingRosterList.containsKey(tNumber)){
                    tName = existingRosterList.get(tNumber);
                }
                writer.println(tNumber + " " + tName);
            }
            
            writer.close();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
   
    }
    
    private void writeToFileSave2(){

        String tName2 = new String();
        
        try{
            PrintWriter writer2 = new PrintWriter("roster2.txt", "UTF-8");
            
            for(String tNumber:existingList2.getItems()){
                if(existingRosterList.containsKey(tNumber)){
                    tName2 = existingRosterList.get(tNumber);
                }
                writer2.println(tNumber + " " + tName2);
            }

            writer2.close();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
   
    }
    
    private void prepExcelFrame(){
        Table newTable = new Table(rosterList,is_iDevice);
        ExcelFrame newExcelFrame = new ExcelFrame(newTable,is_iDevice);
        runExcel(newExcelFrame);
    }
    private void setRoster(LinkedHashMap<String,String> rosterList){
        this.rosterList = rosterList;
    }
    
    public LinkedHashMap<String,String> getRosterList(){
        return rosterList;
    }
    
     public boolean getSceneStatus(){
        return done;
    }
     
    public void setSceneStatus(){
        this.done = false;
    }
    private void remTechButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remTechButtonActionPerformed
        // TODO add your handling code here:
        for(String tech:finalRosterList.getSelectedItems()){
            finalRosterList.remove(tech);
            rosterList.remove(tech);
        }
    }//GEN-LAST:event_remTechButtonActionPerformed

    private void delTechButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delTechButtonActionPerformed

        ArrayList<String> list = new ArrayList<String>();
        
        if(existingList.isShowing()){
            for(int i:existingList.getSelectedIndexes()){
                list.add(nameList.getItem(i));

            }

            if(existingList.getSelectedItems().length != 0){
                for(String tech:existingList.getSelectedItems()){

                    existingList.remove(tech);
                    existingRosterList.remove(tech);

                }
            }

            for(String name:list){
                nameList.remove(name);
            }
        }else{
            for(int i:existingList2.getSelectedIndexes()){
                list.add(nameList2.getItem(i));

            }

            if(existingList2.getSelectedItems().length != 0){
                for(String tech:existingList2.getSelectedItems()){

                    existingList2.remove(tech);
                    existingRosterList.remove(tech);

                }
            }

            for(String name:list){
                nameList2.remove(name);
            }
        }
        
        
        
        
    }//GEN-LAST:event_delTechButtonActionPerformed

    private void selectAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllButtonActionPerformed
        // TODO add your handling code here:
        
        if(existingList.isShowing()){
            for(int i = 0;i<existingList.getItemCount();i++){
                existingList.select(i);
            }
        }else{
            for(int i = 0;i<existingList2.getItemCount();i++){
                existingList2.select(i);
            }
        }
        
    }//GEN-LAST:event_selectAllButtonActionPerformed

    private void deSelectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deSelectButtonActionPerformed
        // TODO add your handling code here:
        if(existingList.isShowing()){
            for(int i = 0;i<existingList.getItemCount();i++){
                existingList.deselect(i);
            }
        }else{
            for(int i = 0;i<existingList2.getItemCount();i++){
                existingList2.deselect(i);
            }
        }
        
    }//GEN-LAST:event_deSelectButtonActionPerformed

    private void rosterSelectAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rosterSelectAllButtonActionPerformed
        // TODO add your handling code here:
        for(int i = 0;i<finalRosterList.getItemCount();i++){
            finalRosterList.select(i);
        }
    }//GEN-LAST:event_rosterSelectAllButtonActionPerformed

    private void rosterDeselectAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rosterDeselectAllButtonActionPerformed
        // TODO add your handling code here:
        for(int i = 0;i<finalRosterList.getItemCount();i++){
            finalRosterList.deselect(i);
        }
    }//GEN-LAST:event_rosterDeselectAllButtonActionPerformed

    private void existingListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_existingListMousePressed
        // TODO add your handling code here

    }//GEN-LAST:event_existingListMousePressed

    private void existingList2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_existingList2MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_existingList2MousePressed

    private void mAreaOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mAreaOptionActionPerformed
        // TODO add your handling code here:
        areaFrame.setVisible(true);
        
    }//GEN-LAST:event_mAreaOptionActionPerformed
    private void initExistingTechList(){
        
        try{
            File tmpDir = new File("roster.txt");
            File tmpDir2 = new File("roster2.txt");
            boolean exists = tmpDir.exists();
            boolean exists2 = tmpDir2.exists();
            
            
            if(exists == false){
                writeToFileSave();
            }
            
            if(exists2 == false){
                writeToFileSave2();
            }
            
            for(String name:Files.readAllLines(Paths.get("roster.txt"))){
                String[] li = {" "," "};
                li = name.split(" ");
                
                if(!existingRosterList.containsKey(li[0])){
                   existingRosterList.put(li[0],li[1]);
                   existingList.add(li[0]);
                   nameList.add(li[1]);
                }
            }
            
            for(String names:Files.readAllLines(Paths.get("roster2.txt"))){
                String[] li = {" "," "};
                li = names.split(" ");
                
                if(!existingRosterList.containsKey(li[0])){
                   existingRosterList.put(li[0],li[1]);
                   existingList2.add(li[0]);
                   nameList2.add(li[1]);
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * @param args the command line arguments
     */
    public void run(){
           java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                             
                new mainFrame().setVisible(true);
                
            }
        }); 

    }
    
    private void runExcel(ExcelFrame frame){
        
           java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {             
                frame.setVisible(true);
            }
        }); 

    }
    
    public static void main(String args[]){ 
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
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
           
     
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addTechButton;
    private javax.swing.JButton createTechButton;
    private javax.swing.JButton deSelectButton;
    private javax.swing.JButton delTechButton;
    private javax.swing.JButton doneButton;
    protected java.awt.List existingList;
    protected java.awt.List existingList2;
    private java.awt.List finalRosterList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JMenuItem mAreaOption;
    private java.awt.List nameList;
    private java.awt.List nameList2;
    private javax.swing.JButton remTechButton;
    private javax.swing.JButton rosterDeselectAllButton;
    private javax.swing.JButton rosterSelectAllButton;
    private javax.swing.JButton selectAllButton;
    // End of variables declaration//GEN-END:variables
}
