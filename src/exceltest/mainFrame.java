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

        addTechButton = new javax.swing.JButton();
        createTechButton = new javax.swing.JButton();
        existingList = new java.awt.List();
        finalRosterList = new java.awt.List();
        doneButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        remTechButton = new javax.swing.JButton();
        delTechButton = new javax.swing.JButton();
        selectAllButton = new javax.swing.JButton();
        deSelectButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuBar1 = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(700, 515));

        addTechButton.setText("Add Tech --->");
        addTechButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTechButtonActionPerformed(evt);
            }
        });

        createTechButton.setText("Create New Tech");
        createTechButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createTechButtonActionPerformed(evt);
            }
        });

        existingList.setMultipleMode(true);
        existingList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                existingListMousePressed(evt);
            }
        });

        finalRosterList.setMultipleMode(true);

        doneButton.setText("Done");
        doneButton.setPreferredSize(new java.awt.Dimension(57, 15));
        doneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Existing Techs");

        jLabel2.setText("Roster List");

        remTechButton.setText("<---Remove Tech ");
        remTechButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remTechButtonActionPerformed(evt);
            }
        });

        delTechButton.setText("Delete Tech");
        delTechButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delTechButtonActionPerformed(evt);
            }
        });

        selectAllButton.setText("Select All");
        selectAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllButtonActionPerformed(evt);
            }
        });

        deSelectButton.setText("DeSelect All");
        deSelectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deSelectButtonActionPerformed(evt);
            }
        });
        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(existingList, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(selectAllButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deSelectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(addTechButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(createTechButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(delTechButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(finalRosterList, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(doneButton, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(remTechButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addComponent(addTechButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(remTechButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(createTechButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(delTechButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(finalRosterList, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(doneButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(deSelectButton)
                            .addComponent(selectAllButton))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(existingList, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                        .addGap(124, 124, 124))))
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton remTechButton;
    private javax.swing.JButton selectAllButton;
    // End of variables declaration//GEN-END:variables
}
