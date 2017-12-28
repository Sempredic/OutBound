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
 * @author Vince
 */
public class newTableRenderer implements TableCellRenderer{

    DefaultTableModel model;
    DefaultTableCellRenderer CURRENT_RENDERER;

    newTableRenderer(DefaultTableModel model,DefaultTableCellRenderer CURRENT_RENDERER){
        this.model = model;
        this.CURRENT_RENDERER = CURRENT_RENDERER;
    }

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
        
        Component c = CURRENT_RENDERER.getTableCellRendererComponent(jtable,
                o, bln, bln1, i, i1);
        
        if (i % 2 == 0){
            c.setBackground(Color.GRAY);
            c.setForeground(Color.BLACK); 
        }else{
            c.setBackground(Color.LIGHT_GRAY);
            c.setForeground(Color.DARK_GRAY);
        }
        
        if(i1==model.getColumnCount()-1){
            c.setBackground(Color.DARK_GRAY);
            c.setForeground(Color.WHITE);
        }else if(i==jtable.getRowCount()-1){
            c.setBackground(Color.DARK_GRAY);
            c.setForeground(Color.WHITE);
        }
        
        if(i1>1&&i1<model.getColumnCount()-1){
            if(i<jtable.getRowCount()-1){
                Color prev = c.getBackground();

                if(!o.equals("0")){
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }else{
                    c.setBackground(prev);
                }
            }
        }
        
        if(i==jtable.getRowCount()-1&&i1==jtable.getColumnCount()-1){
   
            c.setBackground(Color.BLACK);
            c.setForeground(Color.WHITE);
        }

        return c;

    }
    
}
