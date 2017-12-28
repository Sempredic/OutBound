/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltest;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author 311015
 */
public class multiTableRenderer implements TableCellRenderer {
    
    DefaultTableModel model;
    DefaultTableCellRenderer CURRENT_RENDERER;

    multiTableRenderer(DefaultTableModel model, DefaultTableCellRenderer CURRENT_RENDERER){
        this.model = model;
        this.CURRENT_RENDERER = CURRENT_RENDERER;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        Component c = CURRENT_RENDERER.getTableCellRendererComponent(
                                       table, value, isSelected, hasFocus, row, column);
        

        c.setBackground(Color.BLACK);
        c.setForeground(Color.WHITE);

        if(column==1){
            
            if((int)value>0){
                
                c.setBackground(Color.LIGHT_GRAY);
                c.setForeground(Color.BLACK);
            }    
        }
        
        return c;
        
    }
    
}
