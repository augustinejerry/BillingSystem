import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class BillDisplayPage extends JFrame {

	private JPanel contentPane;
	private JLabel customerFNameField;
	private JLabel addressLine1Field;
	private JLabel addressLine2Field;
	private JLabel contactNumberField;
	private JLabel emailField;
	private JTable table;
	private boolean addButtonFlag;
	private JLabel customerLNameField;
	private JLabel cityField;
	private JLabel provinceField;
	private JLabel zipField;
	private int orderLineCount;
	private JLabel discountField;
	private JLabel taxField;
	float subtotal = 0;
	float discount = 0;
	float subtotalAfterDiscount = 0;
	float tax = 0;
	float balance = 0;
	float discountRate = 0;
	float taxRate = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BillDisplayPage frame = new BillDisplayPage(1,30);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BillDisplayPage(int employeeId, int invoiceNo) {
		addButtonFlag = false;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 789, 876);
		contentPane = new JPanel();	
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 271, 736, 4);
		contentPane.add(separator);
		
		ResultSet rs = null;
		String headerQuery = null;
		
		headerQuery = "SELECT c.first_name + ' ' + c.last_name fullname,\r\n" + 
				"	c.address_line1 address_line1,\r\n" + 
				"	c.address_ine2 address_line2,\r\n" + 
				"	c.province province,\r\n" + 
				"	c.zip_code zip_code,\r\n" + 
				"	c.contact_number contact_number,\r\n" + 
				"	c.email_id email_id,\r\n" + 
				"	o.payment_method payment_method,\r\n" + 
				"	o.subtotal subtotal,\r\n" + 
				"	o.discount discount,\r\n" + 
				"	o.subtotal - (o.subtotal * (o.discount / 100)) discount_amt,\r\n" + 
				"	o.subtotal - (o.subtotal - (o.subtotal * (o.discount / 100))) subtotal_after_discount,\r\n" + 
				"	o.tax tax,\r\n" + 
				"	(o.subtotal - (o.subtotal - (o.subtotal * (o.discount / 100)))) * (o.tax / 100) tax_amt,\r\n" + 
				"	(o.subtotal - (o.subtotal - (o.subtotal * (o.discount / 100)))) + ((o.subtotal - (o.subtotal - (o.subtotal * (o.discount / 100)))) * (o.tax / 100)) balance\r\n" + 
				"FROM orders o\r\n" + 
				"JOIN customer c\r\n" + 
				"ON o.customer_id = c.customer_id\r\n" + 
				"WHERE o.order_id =" + invoiceNo;
			
		DBConnection dbc = new DBConnection();
		rs = dbc.getResultSet(headerQuery);
		
		try {
			while (rs.next()) {
				JLabel lblSignature = new JLabel("Signature");
				lblSignature.setFont(new Font("Tahoma", Font.BOLD, 20));
				lblSignature.setBounds(293, 29, 147, 25);
				contentPane.add(lblSignature);
				
				JLabel invoiceLabel = new JLabel("Invoice No");
				invoiceLabel.setBounds(438, 95, 64, 25);
				contentPane.add(invoiceLabel);
				
				JLabel lblDate = new JLabel("Date");
				lblDate.setBounds(471, 120, 31, 16);
				contentPane.add(lblDate);
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				LocalDateTime now = LocalDateTime.now();    
				JLabel dateLabel = new JLabel("");
				dateLabel.setText(dtf.format(now));
				dateLabel.setBounds(514, 120, 163, 16);
				contentPane.add(dateLabel);
				
				JLabel lblCustomer = new JLabel("Customer");
				lblCustomer.setBounds(34, 99, 64, 16);
				contentPane.add(lblCustomer);
				
				customerFNameField = new JLabel(rs.getString("fullname"));
				customerFNameField.setBounds(99, 96, 137, 22);
				contentPane.add(customerFNameField);
				
				JLabel lblAddress = new JLabel("Address");
				lblAddress.setBounds(44, 120, 56, 16);
				contentPane.add(lblAddress);
				
				addressLine1Field = new JLabel(rs.getString("address_line1"));
				addressLine1Field.setBounds(99, 120, 290, 22);
				contentPane.add(addressLine1Field);
				
				addressLine2Field = new JLabel(rs.getString("address_line2"));
				addressLine2Field.setBounds(99, 143, 290, 22);
				contentPane.add(addressLine2Field);
				
				JLabel lblNewLabel = new JLabel("Contact No");
				lblNewLabel.setBounds(26, 214, 72, 16);
				contentPane.add(lblNewLabel);
				
				contactNumberField = new JLabel(rs.getString("contact_number"));
				contactNumberField.setBounds(99, 214, 290, 22);
				contentPane.add(contactNumberField);
				
				JLabel lblEmail = new JLabel("e-mail");
				lblEmail.setBounds(53, 242, 45, 16);
				contentPane.add(lblEmail);
				
				emailField = new JLabel(rs.getString("email_id"));
				emailField.setBounds(99, 239, 290, 22);
				contentPane.add(emailField);
				
				
				
				DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
			    rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
				
			    JLabel lblPaymentMethod = new JLabel("Payment Method");
				lblPaymentMethod.setBounds(34, 620, 109, 16);
				contentPane.add(lblPaymentMethod);
				
				JLabel lblSubtotal = new JLabel("Subtotal");
				lblSubtotal.setBounds(579, 620, 56, 16);
				contentPane.add(lblSubtotal);
				
				JLabel subtotalValue = new JLabel();
				subtotalValue.setBounds(647, 620, 90, 16);
				subtotalValue.setHorizontalAlignment(SwingConstants.RIGHT);
				contentPane.add(subtotalValue);
				
				JLabel lblDiscount = new JLabel("Discount");
				lblDiscount.setBounds(534, 649, 56, 16);
				contentPane.add(lblDiscount);
				
				JLabel discountValue = new JLabel();
				discountValue.setBounds(647, 649, 90, 16);
				discountValue.setHorizontalAlignment(SwingConstants.RIGHT);
				contentPane.add(discountValue);
				
				discountField = new JLabel(rs.getString("discount") + "%");
				discountField.setBounds(589, 649, 46, 22);
				discountField.setHorizontalAlignment(SwingConstants.RIGHT);
				contentPane.add(discountField);
				
				JLabel lblSubtotalAfterDiscount = new JLabel("Subtotal After Discount");
				lblSubtotalAfterDiscount.setBounds(498, 678, 137, 16);
				contentPane.add(lblSubtotalAfterDiscount);
				
				JLabel subtotalAfterDiscountValue = new JLabel();
				subtotalAfterDiscountValue.setBounds(647, 678, 90, 16);
				subtotalAfterDiscountValue.setHorizontalAlignment(SwingConstants.RIGHT);
				contentPane.add(subtotalAfterDiscountValue);
				
				JLabel lblTaxRate = new JLabel("Tax Rate");
				lblTaxRate.setBounds(534, 707, 56, 16);
				contentPane.add(lblTaxRate);
				
				JLabel taxValue = new JLabel();
				taxValue.setBounds(647, 707, 90, 16);
				taxValue.setHorizontalAlignment(SwingConstants.RIGHT);
				contentPane.add(taxValue);
				
				JLabel lblBalanceDue = new JLabel("Balance Due");
				lblBalanceDue.setBounds(557, 739, 78, 16);
				contentPane.add(lblBalanceDue);
				
				JLabel balanceDueValue = new JLabel();
				balanceDueValue.setBounds(647, 739, 90, 16);
				balanceDueValue.setHorizontalAlignment(SwingConstants.RIGHT);
				contentPane.add(balanceDueValue);
				
				taxField = new JLabel(rs.getString("tax") + "%");
				taxField.setBounds(589, 704, 42, 22);
				taxField.setHorizontalAlignment(SwingConstants.RIGHT);
				contentPane.add(taxField);
				
				JLabel lblNewLabel_1 = new JLabel(String.valueOf(invoiceNo));
				lblNewLabel_1.setBounds(514, 99, 56, 16);
				contentPane.add(lblNewLabel_1);
				
				
				JLabel lblCity = new JLabel("City");
				lblCity.setBounds(67, 169, 31, 16);
				contentPane.add(lblCity);
				
				cityField = new JLabel();
				cityField.setBounds(99, 166, 290, 22);
				contentPane.add(cityField);
				
				JLabel lblProvince = new JLabel("Province");
				lblProvince.setBounds(42, 193, 56, 16);
				contentPane.add(lblProvince);
				
				provinceField = new JLabel(rs.getString("province"));
				provinceField.setBounds(99, 190, 137, 22);
				contentPane.add(provinceField);
				
				JLabel lblZip = new JLabel("Zip");
				lblZip.setBounds(240, 193, 23, 16);
				contentPane.add(lblZip);
				
				zipField = new JLabel(rs.getString("zip_code"));
				zipField.setBounds(261, 190, 128, 22);
				contentPane.add(zipField);

				
				ArrayList<OrderLine> list = orderLineList(invoiceNo);
						
				orderLineCount = orderLineCounter(invoiceNo);
				System.out.println(orderLineCount);
				Object[][] data = new Object[orderLineCount][6];    
				String column[]={"Barcode", "Description", "Quantity", "Price", "Discount", "Total"};     
					
				for(int i = 0; i < list.size(); i++) {
					data[i][0] = list.get(i).getBarcode();
					data[i][1] = list.get(i).getDescription();
					data[i][2] = list.get(i).getQuantity();
					data[i][3] = list.get(i).getUnitPrice();
					data[i][4] = list.get(i).getDiscount();
					data[i][5] = list.get(i).getTotal();
				 	subtotal += list.get(i).getTotal();
				}
				
				TableModel model = new DefaultTableModel(data,column);  
				
				JTable table = new JTable(model) {
				      private static final long serialVersionUID = 1L;
				      public boolean isCellEditable(int row, int column) {                
				           return column == 1;               
				      };
				};
					    
					    
					    
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				table.getColumnModel().getColumn(0).setPreferredWidth(100);
				table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
				table.getColumnModel().getColumn(1).setPreferredWidth(300);
				table.getColumnModel().getColumn(2).setPreferredWidth(70);
				table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
				table.getColumnModel().getColumn(3).setPreferredWidth(70);
				table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
				table.getColumnModel().getColumn(4).setPreferredWidth(70);
				table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
				table.getColumnModel().getColumn(5).setPreferredWidth(90);
				table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
				    
				JScrollPane sp=new JScrollPane(table);
				sp.setBounds(34, 345, 703, 250);
				contentPane.add(sp);
						
				discountRate = rs.getFloat(10);
				taxRate = rs.getFloat(13);
				discount = (subtotal * (discountRate / 100));
				subtotalAfterDiscount = subtotal - discount;
				tax = (subtotalAfterDiscount * (taxRate / 100));
				balance = subtotalAfterDiscount + tax;
				
				subtotalValue.setText(String.valueOf(subtotal));
				discountValue.setText(String.valueOf(discount));
				taxValue.setText(String.valueOf(tax));
				subtotalAfterDiscountValue.setText(String.valueOf(subtotalAfterDiscount));
				balanceDueValue.setText(String.valueOf(balance));
				
				JButton btnLogout = new JButton("Logout");
				btnLogout.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
				        new BillingSystem().setVisible(true);
					}
				});
				btnLogout.setBounds(651, 13, 97, 25);
				contentPane.add(btnLogout);
				
				JButton btnNextBill = new JButton("Next Bill");
				btnNextBill.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
				        new BillingPage(employeeId, invoiceNo).setVisible(true);
					}
				});
				btnNextBill.setBounds(364, 791, 97, 25);
				contentPane.add(btnNextBill);
				
				JLabel paymentMethodValue = new JLabel(rs.getString(8));
				paymentMethodValue.setBounds(141, 620, 56, 16);
				contentPane.add(paymentMethodValue);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (rs != null) try { rs.close(); } catch(Exception e) {}
		}
		
		
		
		
	}
	
	public int orderLineCounter(int orderId) {
		ResultSet rs = null;
		String countQuery = null;
		int count = 0;
		
		countQuery = "SELECT COUNT(*) FROM order_line\r\n" + 
				"WHERE order_id = " + orderId;
		
		DBConnection dbc = new DBConnection();
		rs = dbc.getResultSet(countQuery);
		
		try {
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (rs != null) try { rs.close(); } catch(Exception e) {}
		}
		return count;
	}
	public ArrayList<OrderLine> orderLineList(int orderId){
		ArrayList<OrderLine> orderLineList = new ArrayList<>();
		
		ResultSet rs = null;
		String listQuery = null;
		
		listQuery = "SELECT ol.quantity\r\n" + 
				"	, i.barcode\r\n" + 
				"	, p.brand_name + ' ' + i.color + ' ' + p.product_name + ' (Size ' + i.size + ')' description \r\n" + 
				"	, i.price\r\n" + 
				"	, i.discount\r\n" + 
				"	, (i.price - (i.price * (i.discount / 100))) * ol.quantity total\r\n" + 
				"FROM order_line ol\r\n" + 
				"JOIN item i\r\n" + 
				"ON ol.item_id = i.item_id\r\n" + 
				"JOIN product p\r\n" + 
				"ON i.product_id = p.product_id\r\n" + 
				"WHERE ol.order_id = " + orderId;
			
		DBConnection dbc = new DBConnection();
		rs = dbc.getResultSet(listQuery);
		
		try {
			while (rs.next()) {
				OrderLine ol = new OrderLine(orderId, rs.getInt("barcode"), rs.getString("description"), rs.getInt("quantity"), rs.getFloat("price"), rs.getInt("discount"), rs.getFloat("total") );
				orderLineList.add(ol);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (rs != null) try { rs.close(); } catch(Exception e) {}
		}
		return orderLineList;
	}
}
