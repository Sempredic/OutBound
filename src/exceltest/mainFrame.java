/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
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
  
    
    public mainFrame() {
        initComponents();
        rosterList = new LinkedHashMap<String,String>();
        existingRosterList = new LinkedHashMap<String,String>();
        done = false;
        techNumber = new JTextField("");
        techName = new JTextField("");
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
        existingList = new java.awt.List();
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
        rosterSelectAllButton = new javax.swing.JButton();
        rosterDeselectAllButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        selectAllButton = new javax.swing.JButton();
        deSelectButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();

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

        existingList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        existingList.setMultipleMode(true);
        existingList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                existingListMousePressed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(finalRosterList, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(rosterSelectAllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rosterDeselectAllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 20, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(finalRosterList, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rosterSelectAllButton)
                    .addComponent(rosterDeselectAllButton))
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(20, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(selectAllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deSelectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(existingList, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(existingList, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectAllButton)
                    .addComponent(deSelectButton))
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

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(doneButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addComponent(doneButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addTechButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTechButtonActionPerformed

        for(String tech:existingList.getSelectedItems()){
            if(!rosterList.containsKey(tech)){
                finalRosterList.add(tech);
                rosterList.put(tech, existingRosterList.get(tech));
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
                        existingList.add(techNumber.getText());
                        if(techName.getText().length()==0){
                            existingRosterList.put(techNumber.getText(),"**");
                        }else{
                            existingRosterList.put(techNumber.getText(),techName.getText());
                        }
                        
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
    }//GEN-LAST:event_createTechButtonActionPerformed

    private void doneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneButtonActionPerformed
        // TODO add your handling code here:
        writeToFileSave();
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
    
    private void prepExcelFrame(){
        Table newTable = new Table(rosterList);
        ExcelFrame newExcelFrame = new ExcelFrame(newTable);
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

    private void existingListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_existingListMousePressed
        // TODO add your handling code here
  

    }//GEN-LAST:event_existingListMousePressed

    private void delTechButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delTechButtonActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if(existingList.getSelectedItems().length != 0){
            for(String tech:existingList.getSelectedItems()){
                existingList.remove(tech);
                existingRosterList.remove(tech);
            }
        }
        
    }//GEN-LAST:event_delTechButtonActionPerformed

    private void selectAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllButtonActionPerformed
        // TODO add your handling code here:
        for(int i = 0;i<existingList.getItemCount();i++){
            existingList.select(i);
        }
    }//GEN-LAST:event_selectAllButtonActionPerformed

    private void deSelectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deSelectButtonActionPerformed
        // TODO add your handling code here:
        for(int i = 0;i<existingList.getItemCount();i++){
            existingList.deselect(i);
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
    private void initExistingTechList(){
        
        try{
            File tmpDir = new File("roster.txt");
            boolean exists = tmpDir.exists();
            
            if(exists == false){
                writeToFileSave();
            }
            
            for(String name:Files.readAllLines(Paths.get("roster.txt"))){
                String[] li = {" "," "};
                li = name.split(" ");
                
                if(!existingRosterList.containsKey(li[0])){
                   existingRosterList.put(li[0],li[1]);
                   existingList.add(li[0]); 
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
    private java.awt.List finalRosterList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton remTechButton;
    private javax.swing.JButton rosterDeselectAllButton;
    private javax.swing.JButton rosterSelectAllButton;
    private javax.swing.JButton selectAllButton;
    // End of variables declaration//GEN-END:variables
}
