/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import java.awt.Component;
import java.util.Arrays;
import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Vince
 * 
 */

public class spinner{
    
    public spinner(JTable table){
        table.getColumn("Fails").setCellRenderer(new SpinnerRenderer());
        table.getColumn("Fails").setCellEditor(new SpinnerEditor());
    }
    
    class SpinnerRenderer extends JSpinner implements TableCellRenderer {
        public SpinnerRenderer() {
            setOpaque(true);
            
        }
   
        public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
            //setModel((SpinnerModel) value);
     
            return this;
        }
    }
   
    class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {
       final JSpinner spinner = new JSpinner();

       public SpinnerEditor() {

          spinner.setModel(new SpinnerNumberModel(0,0,20,1));
          
          spinner.addChangeListener(new ChangeListener() {
             public void stateChanged(ChangeEvent e) {
                //System.out.println("Value : " + ((JSpinner)e.getSource()).getValue());
             }
          });
       }

       public Component getTableCellEditorComponent(JTable table, Object value,
                        boolean isSelected, int row, int column) {
          //spinner.setModel( new SpinnerNumberModel(1,0,20,1));
          spinner.setValue(value);

          return spinner;
       }

       public Object getCellEditorValue() {
          //Object sm = String.valueOf(spinner.getModel().getValue()); 
          //SpinnerModel sm = spinner.getModel();
          //return sm;
          return (Integer)spinner.getValue();
       }
       
    }

}

