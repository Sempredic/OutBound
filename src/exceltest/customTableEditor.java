/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Vince
 */
class customTableEditor extends AbstractCellEditor implements TableCellEditor {

  JComponent component = new JTextField();
  
  DefaultTableModel model;
  
  customTableEditor(DefaultTableModel model){
    this.model = model;
  }

  @Override
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
      int rowIndex, int vColIndex) {

    Component c = table.getEditorComponent();

    Object val = value;
    
       if(vColIndex>1&&vColIndex<model.getColumnCount()-1){
            if(rowIndex<table.getRowCount()-1){
                if(isSelected){
        
                    JPanel p = new JPanel(new BorderLayout(5,5));

                    JPanel labels = new JPanel(new GridLayout(0,1,2,2));
                    labels.add(new JLabel("Enter Value", SwingConstants.CENTER));
                    labels.add(new JTextField());
                    p.add(labels, BorderLayout.NORTH);

                    String option = JOptionPane.showInputDialog(p, "Enter Number");

                    if(ExcelFrame.isInteger(option)){
                        if(Integer.parseInt(option)<400&&Integer.parseInt(option)>=0){
                            val = option;
                            ExcelFrame.setTableValues(model, Integer.parseInt(option), rowIndex, vColIndex);
                            ExcelFrame.updateTotalDev(model);
                        }else{
                             JOptionPane.showMessageDialog(table,"Number Exceeds Limit","Try Again", JOptionPane.WARNING_MESSAGE);
                        }
                        
                    }else if(option==null){
                    }else{
                         JOptionPane.showMessageDialog(table,"Not A Number","Try Again", JOptionPane.WARNING_MESSAGE);
                    }

                }
            }
       }
    
   
    return c;
  }

  @Override
  public Object getCellEditorValue() {
    return ((JTextField) component).getText();
  }
}
