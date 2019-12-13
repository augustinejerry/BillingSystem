import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

import java.awt.event.*;
import java.awt.event.*;
import java.awt.*;

public class TableHideColumn
 implements ListSelectionListener {
  JTable tableView;
  public TableHideColumn() {
   JFrame frame = new JFrame("Table");
   frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {System.exit(0);}});

   // We specify only 2 column names, the last one is hidden
   final String[] names = {"First Name", "Last Name" };
   final Object[][] data = {
     {"Mark", "Andrews", new Integer(1)},
     {"Tom", "Ball", new Integer(2)},
     {"Alan", "Chung", new Integer(3)},
     };

   TableModel dataModel = new AbstractTableModel() {
     public int getColumnCount() { return names.length; }
     public int getRowCount() { return data.length;}
     public Object getValueAt(int row, int col) {
       return data[row][col];
     }
     public String getColumnName(int column) {return names[column];}
     public Class getColumnClass(int col) {
       return getValueAt(0,col).getClass();
     }
     public void setValueAt(Object aValue, int row, int column) {
       data[row][column] = aValue;
       }
     };

   tableView = new JTable(dataModel);

   ListSelectionModel listMod =  tableView.getSelectionModel();
   listMod.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
   listMod.addListSelectionListener(this);

   JScrollPane scrollpane = new JScrollPane(tableView);
   scrollpane.setPreferredSize(new Dimension(300, 300));
   frame.getContentPane().add(scrollpane);
   frame.pack();
   frame.setVisible(true);
   }

 public void valueChanged(ListSelectionEvent e) {
   int maxRows;
   int[] selRows;
   Object value;

   if (!e.getValueIsAdjusting()) {
     selRows = tableView.getSelectedRows();

     if (selRows.length > 0) {
        for (int i= 0; i < 3 ; i++) {
          // get Table data
          TableModel tm = tableView.getModel();
          value = tm.getValueAt(selRows[0],i);
          System.out.print(value + " " );
          }
        System.out.println();
        }
     }
    }

    public static void main(String[] args) {
        new TableHideColumn();
    }
}