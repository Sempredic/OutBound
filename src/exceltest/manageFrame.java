/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author 311015
 */
public class manageFrame extends javax.swing.JFrame {

    /**
     * Creates new form manageFrame
     */
    
    LinkedHashMap<String,cellArea> areaMap;
    ArrayList<String> deviceTypes;
    ArrayList<String> assignedDevArray;
    String current;
 
    
    public manageFrame() {
        
        areaMap = new LinkedHashMap<String,cellArea>();
        deviceTypes = new ArrayList<String>();
        assignedDevArray = new ArrayList<String>();
        current = new String();
        
        initComponents();
        initExistingDevicesList();
        
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
        areaNameField2 = new javax.swing.JTextField();
        assignedDevicesLabel = new javax.swing.JLabel();
        removeButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        areaNameLabel = new javax.swing.JLabel();
        assignedDevList = new java.awt.List();
        jPanel4 = new javax.swing.JPanel();
        createAreaButton = new javax.swing.JButton();
        existingAreaList = new java.awt.List();
        existingListLabel = new javax.swing.JLabel();
        deleteButton2 = new javax.swing.JButton();
        editAreaButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        deviceNameField = new javax.swing.JTextField();
        addEditButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        editDevicesList = new java.awt.List();
        applyButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        areaManagerLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        areaManagerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        areaManagerLabel.setText("Area Manager");
        areaManagerLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        editApplyButton.setText("Apply");
        editApplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editApplyButtonActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        assignedDevicesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        assignedDevicesLabel.setText("Assigned Devices");
        assignedDevicesLabel.setEnabled(false);

        removeButton.setText("Del");
        removeButton.setEnabled(false);

        addButton.setText("Add");
        addButton.setEnabled(false);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        areaNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        areaNameLabel.setText("Area Name");
        areaNameLabel.setEnabled(false);

        assignedDevList.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(areaNameField2)
                    .addComponent(assignedDevList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(assignedDevicesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(areaNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(removeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(areaNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(areaNameField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(assignedDevicesLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(assignedDevList, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(removeButton))
                .addContainerGap())
        );

        areaNameField2.setEnabled(false);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        createAreaButton.setText("Create New Area");
        createAreaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAreaButtonActionPerformed(evt);
            }
        });

        existingAreaList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                existingAreaListMouseClicked(evt);
            }
        });
        existingAreaList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                existingAreaListFocusGained(evt);
            }
        });

        existingListLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        existingListLabel.setText("Existing Areas");

        deleteButton2.setText("Remove Area(s)");
        deleteButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButton2ActionPerformed(evt);
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
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(existingListLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(existingAreaList, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(deleteButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(createAreaButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editAreaButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(existingListLabel)
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(existingAreaList, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(createAreaButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editAreaButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton2)))
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
                .addGap(20, 20, 20))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(editApplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editApplyButton)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Edit Areas", jPanel2);

        addEditButton.setText("Add-->");
        addEditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEditButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("<--Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Device Name:");

        applyButton.setText("Apply");
        applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 162, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(deleteButton)
                            .addComponent(addEditButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(applyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(71, 71, 71))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(deviceNameField)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(editDevicesList, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editDevicesList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deviceNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(addEditButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 206, Short.MAX_VALUE)
                        .addComponent(applyButton)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Edit Devices", jPanel3);

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

    private void applyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyButtonActionPerformed
        // TODO add your handling code here:
        writeToFile();
    }//GEN-LAST:event_applyButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
        for(String name:editDevicesList.getSelectedItems()){
            editDevicesList.remove(name);
        }

    }//GEN-LAST:event_deleteButtonActionPerformed

    private void addEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEditButtonActionPerformed
        // TODO add your handling code here:
        if(!deviceTypes.contains(deviceNameField.getText())){
            editDevicesList.add(deviceNameField.getText());
            deviceTypes.add(deviceNameField.getText());
        }
        

    }//GEN-LAST:event_addEditButtonActionPerformed

    private void deleteButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButton2ActionPerformed
        // TODO add your handling code here:
        if(areaMap.containsKey(existingAreaList.getSelectedItem())){
            areaMap.remove(existingAreaList.getSelectedItem());
            existingAreaList.remove(existingAreaList.getSelectedItem());
        }
        
    }//GEN-LAST:event_deleteButton2ActionPerformed

    private void createAreaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAreaButtonActionPerformed
        // TODO add your handling code here:
        JPanel p = new JPanel(new BorderLayout(5,5));

        JPanel labels = new JPanel(new GridLayout(0,1,2,2));

        String areaName = JOptionPane.showInputDialog(p, "Enter Area Name");
        
        if(!areaMap.containsKey(areaName)){
            
            cellArea newArea = new cellArea(areaName);
  
            areaMap.put(areaName, newArea);
            
            existingAreaList.add(areaName);
        }else{
            JOptionPane.showMessageDialog(this,"Area Already Exists","Try Again", JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_createAreaButtonActionPerformed

    private void existingAreaListFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_existingAreaListFocusGained
        // TODO add your handling code here:
       
    }//GEN-LAST:event_existingAreaListFocusGained

    private void existingAreaListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_existingAreaListMouseClicked
        // TODO add your handling code here:
  

        
        
        if(existingAreaList.getSelectedItem() != null){
            
            if(current == existingAreaList.getSelectedItem()){
               
                areaNameField2.setText(areaMap.get(existingAreaList.getSelectedItem()).getAreaName());
            
                updateAssignedDevList(areaMap.get(existingAreaList.getSelectedItem()));
            }else{
                assignedDevList.removeAll();
                
            }

            
  
        }else{
            areaNameField2.setText(" ");
            assignedDevList.removeAll();
        }
        
        current = existingAreaList.getSelectedItem();
        
        
    }//GEN-LAST:event_existingAreaListMouseClicked

    private void editAreaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editAreaButtonActionPerformed
        // TODO add your handling code here:
        areaNameField2.setEnabled(true);
        assignedDevList.setEnabled(true);
        addButton.setEnabled(true);
        removeButton.setEnabled(true);
        areaNameLabel.setEnabled(true);
        assignedDevicesLabel.setEnabled(true);
        
        createAreaButton.setEnabled(false);
        deleteButton2.setEnabled(false);
        existingAreaList.setEnabled(false);
        existingListLabel.setEnabled(false);
    }//GEN-LAST:event_editAreaButtonActionPerformed

    private void editApplyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editApplyButtonActionPerformed
        // TODO add your handling code here:
        areaNameField2.setEnabled(false);
        assignedDevList.setEnabled(false);
        addButton.setEnabled(false);
        removeButton.setEnabled(false);
        
        areaNameLabel.setEnabled(false);
        assignedDevicesLabel.setEnabled(false);
        createAreaButton.setEnabled(true);
        deleteButton2.setEnabled(true);
        existingAreaList.setEnabled(true);
        existingListLabel.setEnabled(true);
   
    }//GEN-LAST:event_editApplyButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        JPanel p = new JPanel(new BorderLayout(5,5));

        String deviceName = JOptionPane.showInputDialog(p, "Enter Device Name");
        
        if(areaMap.containsKey(existingAreaList.getSelectedItem())){
            if(!areaMap.get(existingAreaList.getSelectedItem()).getDeviceTypes().contains(deviceName)){
                areaMap.get(existingAreaList.getSelectedItem()).addDeviceType(deviceName);
            }
                  
            
            updateAssignedDevList(areaMap.get(existingAreaList.getSelectedItem()));
        }
        
    }//GEN-LAST:event_addButtonActionPerformed

    void updateAssignedDevList(cellArea area){
        for(String device:area.getDeviceTypes()){
            if(!assignedDevArray.contains(device)){
                assignedDevArray.add(device);
            }
        }
        
        assignedDevList.removeAll();
        
        for(String dev:assignedDevArray){
            assignedDevList.add(dev);
        }
              
    }
    private void initExistingDevicesList(){
        
        try{
            
            File tmpDir = new File("devices.txt");

            boolean exists = tmpDir.exists();  
            
            if(exists == false){
                writeToFile();
            }

            for(String name:Files.readAllLines(Paths.get("devices.txt"))){
 
                if(!deviceTypes.contains(name)){
                   deviceTypes.add(name);
                   editDevicesList.add(name);
                }
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void writeToFile(){
        
        try{
            
            PrintWriter writer = new PrintWriter("devices.txt", "UTF-8");
            
            for(String tDevice:editDevicesList.getItems()){
                
                writer.println(tDevice);
            }
            
            writer.close();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
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
    private javax.swing.JButton addButton;
    private javax.swing.JButton addEditButton;
    private javax.swing.JButton applyButton;
    private javax.swing.JLabel areaManagerLabel;
    private javax.swing.JTextField areaNameField2;
    private javax.swing.JLabel areaNameLabel;
    private java.awt.List assignedDevList;
    private javax.swing.JLabel assignedDevicesLabel;
    private javax.swing.JButton createAreaButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton deleteButton2;
    private javax.swing.JTextField deviceNameField;
    private javax.swing.JButton editApplyButton;
    private javax.swing.JButton editAreaButton;
    private java.awt.List editDevicesList;
    private java.awt.List existingAreaList;
    private javax.swing.JLabel existingListLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton removeButton;
    // End of variables declaration//GEN-END:variables
}
