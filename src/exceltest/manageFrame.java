/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author 311015
 */
public class manageFrame extends javax.swing.JFrame {

    /**
     * Creates new form manageFrame
     */
    
    LinkedHashMap<String,cellArea> areaMap;
    ArrayList<String> existingAreaArray;
    ArrayList<String> deviceTypes;
    ArrayList<String> assDevListArray;

    String current;
 
    
    public manageFrame() {
        
        areaMap = new LinkedHashMap<String,cellArea>();
        deviceTypes = new ArrayList<String>();
        existingAreaArray = new ArrayList<String>();
        current = new String();
        assDevListArray = new ArrayList<String>();
      
        
        initComponents();
        initExistingAreasList();
        updateExistingAreaList();
        
    }
    
    public LinkedHashMap<String,cellArea> getAreaMap(){
        return areaMap;
    }
    
    public ArrayList<String> getAreaMapNames(){
        
        for(Map.Entry<String,cellArea> area:areaMap.entrySet()){
            if(!existingAreaArray.contains(area.getKey())){
                existingAreaArray.add(area.getKey());
            }
        }
        return existingAreaArray;
    }
    
    public cellArea getAreaByName(String area){
        
        return areaMap.get(area);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        areaManagerLabel = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        editApplyButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        assDevNameField = new javax.swing.JTextField();
        assignedDevicesLabel = new javax.swing.JLabel();
        assDevDelButton = new javax.swing.JButton();
        assDevAddButton = new javax.swing.JButton();
        areaNameLabel = new javax.swing.JLabel();
        assignedDevList = new java.awt.List();
        jPanel4 = new javax.swing.JPanel();
        createAreaButton = new javax.swing.JButton();
        existingAreaList = new java.awt.List();
        existingListLabel = new javax.swing.JLabel();
        deleteAreaButton = new javax.swing.JButton();
        editAreaButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Area Manager");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(51, 51, 255));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        areaManagerLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        areaManagerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        areaManagerLabel.setText("Area Manager");
        areaManagerLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTabbedPane1.setBackground(new java.awt.Color(0, 0, 0));

        editApplyButton.setText("Apply");
        editApplyButton.setEnabled(false);
        editApplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editApplyButtonActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        assDevNameField.setEditable(false);

        assignedDevicesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        assignedDevicesLabel.setText("Assigned Devices");
        assignedDevicesLabel.setEnabled(false);

        assDevDelButton.setText("Del");
        assDevDelButton.setEnabled(false);
        assDevDelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assDevDelButtonActionPerformed(evt);
            }
        });

        assDevAddButton.setText("Add");
        assDevAddButton.setEnabled(false);
        assDevAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assDevAddButtonActionPerformed(evt);
            }
        });

        areaNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        areaNameLabel.setText("Area Name");
        areaNameLabel.setEnabled(false);

        assignedDevList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        assignedDevList.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(assDevNameField)
                    .addComponent(assignedDevList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(assignedDevicesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(areaNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(assDevAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(assDevDelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(areaNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(assDevNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(assignedDevicesLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(assignedDevList, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(assDevAddButton)
                    .addComponent(assDevDelButton))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        assDevNameField.setEnabled(false);

        jPanel4.setBackground(new java.awt.Color(153, 153, 153));

        createAreaButton.setText("Create New Area");
        createAreaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAreaButtonActionPerformed(evt);
            }
        });

        existingAreaList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        existingAreaList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                existingAreaListMouseClicked(evt);
            }
        });
        existingAreaList.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                existingAreaListItemStateChanged(evt);
            }
        });
        existingAreaList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                existingAreaListFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                existingAreaListFocusLost(evt);
            }
        });

        existingListLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        existingListLabel.setForeground(new java.awt.Color(255, 255, 255));
        existingListLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        existingListLabel.setText("Existing Areas");

        deleteAreaButton.setText("Remove Area(s)");
        deleteAreaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteAreaButtonActionPerformed(evt);
            }
        });

        editAreaButton.setText("Edit Area");
        editAreaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editAreaButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(existingAreaList, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(deleteAreaButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(createAreaButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editAreaButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(existingListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(existingListLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(existingAreaList, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(createAreaButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editAreaButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteAreaButton)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(editApplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editApplyButton))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Edit Areas", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(areaManagerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(areaManagerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void deleteAreaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteAreaButtonActionPerformed
        // TODO add your handling code here:
        
        int option = JOptionPane.showConfirmDialog(this,"Are You Sure?", "Deleting Area",
                JOptionPane.YES_NO_CANCEL_OPTION);
        
        if(option==JOptionPane.YES_OPTION){
            if(areaMap.containsKey(existingAreaList.getSelectedItem())){
                areaMap.remove(existingAreaList.getSelectedItem());
                existingAreaArray.remove(existingAreaList.getSelectedItem());
            }

            assDevNameField.setText(" ");
            assignedDevList.removeAll(); 

            updateExistingAreaList();
            writeAreasToFile();
        }
        
    }//GEN-LAST:event_deleteAreaButtonActionPerformed

    private void createAreaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAreaButtonActionPerformed
        // TODO add your handling code here:

        Object[] options = DatabaseObj.getAreaList().toArray();
        
        String area = (String)JOptionPane.showInputDialog(
                            this,
                            new JLabel("Select Existing Area", SwingConstants.CENTER),
                            "Add Area",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            options,
                            null);

        if(area != null){
            if(!areaMap.containsKey(area)){
                cellArea newArea = new cellArea(area);

                areaMap.put(area, newArea);

                existingAreaArray.add(area);                

                updateExistingAreaList(); 
            }else{
                JOptionPane.showMessageDialog(this,"Area Already Exists","Try Again", JOptionPane.WARNING_MESSAGE);
            }
        }     
    }//GEN-LAST:event_createAreaButtonActionPerformed

    private void updateExistingAreaList(){
       
        existingAreaList.removeAll();
        
        for(String value:existingAreaArray){
            
            existingAreaList.add(value);        
        }
            
    }
    private void existingAreaListFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_existingAreaListFocusGained
        // TODO add your handling code here:
       
    }//GEN-LAST:event_existingAreaListFocusGained

    private void existingAreaListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_existingAreaListMouseClicked
        // TODO add your handling code here:      
      
              
    }//GEN-LAST:event_existingAreaListMouseClicked

    private void editAreaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editAreaButtonActionPerformed
        // TODO add your handling code here:
        assDevListArray.clear();

        if(!assignedDevList.isEnabled()){
            if(existingAreaList.getSelectedItem()!=null){
            
                editApplyButton.setEnabled(true);
                assDevNameField.setEnabled(true);
                assignedDevList.setEnabled(true);
                assDevAddButton.setEnabled(true);
                assDevDelButton.setEnabled(true);
                areaNameLabel.setEnabled(true);
                assignedDevicesLabel.setEnabled(true);

                createAreaButton.setEnabled(false);
                deleteAreaButton.setEnabled(false);
                existingAreaList.setEnabled(false);
                existingListLabel.setEnabled(false);

                editAreaButton.setText("Cancel Edit");

            }else{
                JOptionPane.showMessageDialog(this,"Select an Existing Area","Try Again", JOptionPane.WARNING_MESSAGE);
            }
        }else{
            updateAssignedDevList(areaMap.get(existingAreaList.getSelectedItem()));
            
            editApplyButton.setEnabled(false);
            assDevNameField.setEnabled(false);
            assignedDevList.setEnabled(false);
            assDevAddButton.setEnabled(false);
            assDevDelButton.setEnabled(false);

            areaNameLabel.setEnabled(false);
            assignedDevicesLabel.setEnabled(false);
            createAreaButton.setEnabled(true);
            deleteAreaButton.setEnabled(true);
            existingAreaList.setEnabled(true);
            existingListLabel.setEnabled(true);
            
            editAreaButton.setText("Edit Area");
        }
        
        
    }//GEN-LAST:event_editAreaButtonActionPerformed

    private void editApplyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editApplyButtonActionPerformed
        // TODO add your handling code here:
        commitAssDevList();
        
        editApplyButton.setEnabled(false);
        assDevNameField.setEnabled(false);
        assignedDevList.setEnabled(false);
        assDevAddButton.setEnabled(false);
        assDevDelButton.setEnabled(false);
        
        areaNameLabel.setEnabled(false);
        assignedDevicesLabel.setEnabled(false);
        createAreaButton.setEnabled(true);
        deleteAreaButton.setEnabled(true);
        existingAreaList.setEnabled(true);
        existingListLabel.setEnabled(true);
       
        editAreaButton.setText("Edit Area");
        
        writeAreasToFile();
   
    }//GEN-LAST:event_editApplyButtonActionPerformed

    private void assDevAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assDevAddButtonActionPerformed
        // TODO add your handling code here:
      
        Object[] options = DatabaseObj.getDevicesList().toArray();
        
        String deviceName = (String)JOptionPane.showInputDialog(
                            this,
                            new JLabel("Select Existing Device", SwingConstants.CENTER),
                            "Add Device",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            options,
                            null);
     
        if(deviceName != null){
         
            if(areaMap.containsKey(existingAreaList.getSelectedItem())){  
                if(!areaMap.get(existingAreaList.getSelectedItem()).getDeviceTypes().contains(deviceName)){
                    if(!assDevListArray.contains(deviceName)){
                        assDevListArray.add(deviceName);
                    }else{
                        JOptionPane.showMessageDialog(this,"Device Already Exists","Try Again", JOptionPane.WARNING_MESSAGE);
                    }
                    
                }else{
                    JOptionPane.showMessageDialog(this,"Device Already Exists","Try Again", JOptionPane.WARNING_MESSAGE);
                }

                updateAssignedDevList(areaMap.get(existingAreaList.getSelectedItem()));
                updateAssignedDevList();
            }    
        }
    }//GEN-LAST:event_assDevAddButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        int option =0;
        
        if(!assignedDevList.isEnabled()){
          evt.getWindow().dispose();
        }else{
            option = JOptionPane.showConfirmDialog(this,"Apply Area Changes?", "Exiting",
                JOptionPane.YES_NO_CANCEL_OPTION);
            if(option==JOptionPane.YES_OPTION){
                
                commitAssDevList();
        
                editApplyButton.setEnabled(false);
                assDevNameField.setEnabled(false);
                assignedDevList.setEnabled(false);
                assDevAddButton.setEnabled(false);
                assDevDelButton.setEnabled(false);

                areaNameLabel.setEnabled(false);
                assignedDevicesLabel.setEnabled(false);
                createAreaButton.setEnabled(true);
                deleteAreaButton.setEnabled(true);
                existingAreaList.setEnabled(true);
                existingListLabel.setEnabled(true);
                
                editAreaButton.setText("Edit Area");

                writeAreasToFile();
                
                evt.getWindow().dispose();
            }else if(option==JOptionPane.NO_OPTION){
                
                updateAssignedDevList(areaMap.get(existingAreaList.getSelectedItem()));
                
                editApplyButton.setEnabled(false);
                assDevNameField.setEnabled(false);
                assignedDevList.setEnabled(false);
                assDevAddButton.setEnabled(false);
                assDevDelButton.setEnabled(false);

                areaNameLabel.setEnabled(false);
                assignedDevicesLabel.setEnabled(false);
                createAreaButton.setEnabled(true);
                deleteAreaButton.setEnabled(true);
                existingAreaList.setEnabled(true);
                existingListLabel.setEnabled(true);
                
                editAreaButton.setText("Edit Area");
                
                evt.getWindow().dispose();
            }
        }
    }//GEN-LAST:event_formWindowClosing

    private void existingAreaListItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_existingAreaListItemStateChanged
        // TODO add your handling code here:
        if(existingAreaList.getSelectedItem() != null){
             
            //update current area name
            assDevNameField.setText(areaMap.get(existingAreaList.getSelectedItem()).getAreaName());

            //update current area devices list                
            updateAssignedDevList(areaMap.get(existingAreaList.getSelectedItem()));

        }else{
            assDevNameField.setText(" ");
            assignedDevList.removeAll();
        }
    }//GEN-LAST:event_existingAreaListItemStateChanged

    private void existingAreaListFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_existingAreaListFocusLost
        // TODO add your handling code here:

        
    }//GEN-LAST:event_existingAreaListFocusLost

    private void assDevDelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assDevDelButtonActionPerformed
        // TODO add your handling code here:
        String deviceName = assignedDevList.getSelectedItem();
        String areaName = existingAreaList.getSelectedItem();
        
        if(deviceName != null){
            if(areaMap.get(areaName).getDeviceTypes().contains(deviceName)){
                if(JOptionPane.showConfirmDialog(this,"Delete Device From " + areaName + " ?", "Exiting",
                    JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
                    
                    areaMap.get(areaName).removeDeviceType(deviceName);
                }  
            }else if(assDevListArray.contains(deviceName)){
                if(JOptionPane.showConfirmDialog(this,"Delete Device From " + areaName + " ?", "Exiting",
                    JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
                    
                    assDevListArray.remove(deviceName);
                } 
            }

            updateAssignedDevList(areaMap.get(areaName));
            updateAssignedDevList();
              
        }
    }//GEN-LAST:event_assDevDelButtonActionPerformed

    void updateAssignedDevList(cellArea area){
        
        assignedDevList.removeAll();
        
        for(String device:area.getDeviceTypes()){
            assignedDevList.add(device);
        }
              
    }
    
    void updateAssignedDevList(){

        for(String device:assDevListArray){
            assignedDevList.add(device);
        }
              
    }
    
    void commitAssDevList(){
        
        for(String device:assDevListArray){
            areaMap.get(existingAreaList.getSelectedItem()).addDeviceType(device);
        }
        
        assDevListArray.clear();
    
    }

    private void initExistingAreasList(){
        
        int areaAmount=0;
        int counter = 0;
        String areaName = " ";
        String devices = " ";
        ArrayList<String> list = new ArrayList<String>();
        
        try{
            
            File tmpDir = new File("cellAreas.txt");

            boolean exists = tmpDir.exists();  
            
            if(exists == false){
                writeAreasToFile();
            }
            
            list = (ArrayList)Files.readAllLines(Paths.get("cellAreas.txt"));
            
            if(isInteger(list.get(0))){
                areaAmount = Integer.parseInt(list.remove(0));
            }

            for(int i=0;i<areaAmount;i++){
                
                cellArea newArea = new cellArea();
                int lines = Integer.parseInt(list.get(counter));
                
                counter++;
                
                for(int j=0;j<lines;j++){
                    if(j==0){
                        newArea.updateAreaName(list.get(counter));
                    }else{
                        newArea.addDeviceType(list.get(counter));
                    }
                         
                    counter++;
                }   
                areaMap.put(newArea.getAreaName(), newArea);
                existingAreaArray.add(newArea.getAreaName());
            }
            
        }catch(Exception e){
            System.out.println("error");
        }
    }
    
    private boolean isInteger(String string){
        try{
            Integer.parseInt(string);
        }catch(Exception e){
            return false;
        }
        
        return true;
    }

    private void writeAreasToFile(){
        
        try{
            
            PrintWriter writer = new PrintWriter("cellAreas.txt", "UTF-8");
            
            writer.println(areaMap.size());

            for(Map.Entry<String,cellArea> areas:areaMap.entrySet()){
                
                //get number of lines to write
                writer.println(areas.getValue().getDeviceTypes().size()+1);
                writer.println(areas.getKey());
                
                for(String dev:areas.getValue().getDeviceTypes()){
                    writer.println(dev);
                } 
            }
            
            writer.close();
            
        }catch(Exception e){
            System.out.println("error");
        }
        
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
            java.util.logging.Logger.getLogger(manageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(manageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(manageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(manageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel areaManagerLabel;
    private javax.swing.JLabel areaNameLabel;
    private javax.swing.JButton assDevAddButton;
    private javax.swing.JButton assDevDelButton;
    private javax.swing.JTextField assDevNameField;
    private java.awt.List assignedDevList;
    private javax.swing.JLabel assignedDevicesLabel;
    private javax.swing.JButton createAreaButton;
    private javax.swing.JButton deleteAreaButton;
    private javax.swing.JButton editApplyButton;
    private javax.swing.JButton editAreaButton;
    private java.awt.List existingAreaList;
    private javax.swing.JLabel existingListLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
