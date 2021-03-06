/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import static exceltest.DatabaseObj.getStatusBoolean;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Vince
 */
public class techLabeler extends javax.swing.JFrame {

    String year;
    String techNumber;
    String newLabel;
    ArrayList<String> labelsList;

    /**
     * Creates new form techLabeler
     */
    public techLabeler() {
        year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        techNumber = new String();
        newLabel = new String();
        labelsList = new ArrayList<String>();

        initComponents();
        readFileSave();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelGeneratorTab = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        techNumberField = new javax.swing.JTextField();
        techYearLabel = new javax.swing.JLabel();
        techNumberLabel = new javax.swing.JLabel();
        techYearField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        labelList = new java.awt.List();
        labelNameField = new javax.swing.JTextField();
        labelNameLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        labelAddButton = new javax.swing.JButton();
        labelDelButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        labelGeneratorLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        generateButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        selectorMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Label Generator");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        techNumberField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        techYearLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        techYearLabel.setText("Year");

        techNumberLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        techNumberLabel.setText("Tech Number");

        techYearField.setText(year);
        techYearField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(techNumberLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(techNumberField)
                    .addComponent(techYearLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(techYearField))
                .addGap(90, 90, 90))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(techNumberLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(techNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(techYearLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(techYearField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(129, Short.MAX_VALUE))
        );

        labelGeneratorTab.addTab("Tech", jPanel1);

        labelList.setMultipleMode(true);

        labelNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNameLabel.setText("Label Name");

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        labelAddButton.setText("Add -->");
        labelAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labelAddButtonActionPerformed(evt);
            }
        });

        labelDelButton.setText("<--Remove");
        labelDelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labelDelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelAddButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelDelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(labelAddButton)
                .addGap(18, 18, 18)
                .addComponent(labelDelButton)
                .addGap(24, 24, 24))
        );

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Stored Labels");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(8);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(2);
        jTextArea1.setText("You Can Select Up to 3 Labels\nThen \"Generate\"");
        jTextArea1.setBorder(null);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNameField)
                            .addComponent(labelNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelList, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(labelNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        labelGeneratorTab.addTab("Device", jPanel2);

        labelGeneratorLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelGeneratorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGeneratorLabel.setText("Label Generator");

        generateButton.setText("Generate");
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(generateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(generateButton)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jMenu1.setText("File");

        selectorMenuItem.setText("Back To Selector");
        selectorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectorMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(selectorMenuItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelGeneratorTab)
                    .addComponent(labelGeneratorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelGeneratorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelGeneratorTab, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void readFileSave(){
        try{
            
            File file = new File("Data\\labelerLabels.txt");
        
            if(!file.exists()){
                file.createNewFile();
            }
 
            for(String label:Files.readAllLines(Paths.get("Data\\labelerLabels.txt"))){
                if(!labelsList.contains(label)){
                   labelsList.add(label);
                   labelList.add(label);
                }
            }

        }catch(Exception e){
            System.out.println(e.toString());
            errorLogger.writeToLogger(e.toString());
        }
    }
    
    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        // TODO add your handling code here: 
        ArrayList<String> deviceLabel = new ArrayList<String>();
        
        try{
            if(labelGeneratorTab.getSelectedIndex()==0){
                if(techNumberField.getText().length()>0){
                    
                    if(techNumberField.getText().length()<10){
                        if(techYearField.getText().length()<5){
                            labelerObj techLabel = new labelerObj(techNumberField.getText().trim(),techYearField.getText().substring(2).trim());
                            techLabel.generateTech();
                        }
                    }else{
                        JOptionPane.showMessageDialog(this,new JLabel("Input Too Long",JLabel.CENTER),"Try Again", JOptionPane.PLAIN_MESSAGE);
                        techNumberField.setText("");
                    }
                }else{
                    JOptionPane.showMessageDialog(this,new JLabel("No Input",JLabel.CENTER),"Try Again", JOptionPane.PLAIN_MESSAGE);
                }  
            }else{
                if(labelList.getSelectedItems().length>0){
                    labelerObj devLabel = new labelerObj();
                    
                    if(labelList.getSelectedItems().length <4){
                        for(String lbl:labelList.getSelectedItems()){
                            deviceLabel.add(lbl);
                        }

                        devLabel.generateDevice(deviceLabel);
                    }else{
                        JOptionPane.showMessageDialog(this,new JLabel("Max Of 3 Labels Can Be Selected",JLabel.CENTER),"Try Again", JOptionPane.PLAIN_MESSAGE);
                    }
                    
                }
            }
        }catch(Exception e){
            System.out.println(e.toString());
            errorLogger.writeToLogger(e.toString());
            JOptionPane.showMessageDialog(this,new JLabel("Error Generating Label",JLabel.CENTER),"Try Again", JOptionPane.PLAIN_MESSAGE);
        }
        
    }//GEN-LAST:event_generateButtonActionPerformed

    private void labelAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labelAddButtonActionPerformed
        // TODO add your handling code here:
        if(labelNameField.getText().length()>0){
            if(!labelsList.contains(labelNameField.getText())){
                labelList.add(labelNameField.getText());
                labelsList.add(labelNameField.getText());
                labelNameField.setText("");
            }else{
                JOptionPane.showMessageDialog(this,new JLabel("Label Already Exists",JLabel.CENTER),"Try Again", JOptionPane.PLAIN_MESSAGE);
                labelNameField.setText("");
            }
            
        }
    }//GEN-LAST:event_labelAddButtonActionPerformed

    private void labelDelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labelDelButtonActionPerformed
        // TODO add your handling code here:
        for(String label:labelList.getSelectedItems()){
            if(labelsList.contains(label)){
                labelsList.remove(label);
                labelList.remove(label);
            }
        }
              
    }//GEN-LAST:event_labelDelButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        try{
            PrintWriter writer = new PrintWriter("Data\\labelerLabels.txt", "UTF-8");
            
            for(String label:labelsList){
                writer.println(label);
            }

            writer.close();
            
        }catch(Exception e){
            System.out.println(e.toString());
            errorLogger.writeToLogger(e.toString());
        }
        
    }//GEN-LAST:event_formWindowClosing

    private void selectorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectorMenuItemActionPerformed
        // TODO add your handling code here:
        initFrame frame = new initFrame();
        frame.run();
        dispose();
    }//GEN-LAST:event_selectorMenuItemActionPerformed

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
            java.util.logging.Logger.getLogger(techLabeler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(techLabeler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(techLabeler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(techLabeler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */

    }
    
    public void run(){
                java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new techLabeler().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton generateButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton labelAddButton;
    private javax.swing.JButton labelDelButton;
    private javax.swing.JLabel labelGeneratorLabel;
    private javax.swing.JTabbedPane labelGeneratorTab;
    private java.awt.List labelList;
    private javax.swing.JTextField labelNameField;
    private javax.swing.JLabel labelNameLabel;
    private javax.swing.JMenuItem selectorMenuItem;
    private javax.swing.JTextField techNumberField;
    private javax.swing.JLabel techNumberLabel;
    private javax.swing.JTextField techYearField;
    private javax.swing.JLabel techYearLabel;
    // End of variables declaration//GEN-END:variables
}
