import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public final class JTableHeaderHideTest extends JPanel {
   private final String[] columnNames = {"String", "Integer", "Boolean"};
   private final Object[][] data = {{"Tutorials Point", 100, true}, {"Tutorix", 200, false}, {"Tutorials Point", 300, true}, {"Tutorix", 400, false}};
   private final TableModel model = new DefaultTableModel(data, columnNames);
//   {
//      @Override 
//      public Class getColumnClass(int column) {
//         return getValueAt(0, column).getClass();
//      }
//   };
   private final JTable table = new JTable(model);
   private final JScrollPane scrollPane = new JScrollPane(table);
   public JTableHeaderHideTest() {
      super(new BorderLayout());
      add(scrollPane);
//      JCheckBox check = new JCheckBox("JTableHeader visible: ", true);
//      check.addActionListener(ae -> {
//         JCheckBox cb = (JCheckBox) ae.getSource();
//         scrollPane.getColumnHeader().setVisible(cb.isSelected());
//         scrollPane.revalidate();
//      });
//      add(check, BorderLayout.NORTH);
   }
   public static void main(String[] args) {
      JFrame frame = new JFrame("JTableHeaderHide Test");
      frame.setSize(375, 250);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(new JTableHeaderHideTest());
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }
}