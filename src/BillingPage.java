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
import java.awt.Color;

public class BillingPage extends JFrame {

	private JPanel contentPane;
	private JTextField customerFNameField;
	private JTextField addressLine1Field;
	private JTextField addressLine2Field;
	private JTextField contactNumberField;
	private JTextField emailField;
	private JTextField barcodeField;
	private JTextField quantityField;
	private JTable table;
	private boolean addButtonFlag;
	private JTextField customerLNameField;
	private JTextField cityField;
	private JTextField provinceField;
	private JTextField zipField;
	private int orderLineCount;
	private JTextField discountField;
	private JTextField taxField;
	private JLabel errorLabel;
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
					BillingPage frame = new BillingPage(1,1);
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
	public BillingPage(int employeeId, int invoiceNo) {
		addButtonFlag = false;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 789, 876);
		contentPane = new JPanel();	
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
		
		customerFNameField = new JTextField();
		customerFNameField.setBounds(99, 96, 137, 22);
		contentPane.add(customerFNameField);
		customerFNameField.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(44, 120, 56, 16);
		contentPane.add(lblAddress);
		
		addressLine1Field = new JTextField();
		addressLine1Field.setBounds(99, 120, 290, 22);
		contentPane.add(addressLine1Field);
		addressLine1Field.setColumns(10);
		
		addressLine2Field = new JTextField();
		addressLine2Field.setBounds(99, 143, 290, 22);
		contentPane.add(addressLine2Field);
		addressLine2Field.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Contact No");
		lblNewLabel.setBounds(26, 214, 72, 16);
		contentPane.add(lblNewLabel);
		
		contactNumberField = new JTextField();
		contactNumberField.setColumns(10);
		contactNumberField.setBounds(99, 214, 290, 22);
		contentPane.add(contactNumberField);
		
		JLabel lblEmail = new JLabel("e-mail");
		lblEmail.setBounds(53, 242, 45, 16);
		contentPane.add(lblEmail);
		
		emailField = new JTextField();
		emailField.setBounds(99, 239, 290, 22);
		contentPane.add(emailField);
		emailField.setColumns(10);
		
		JLabel lblBarcode = new JLabel("Barcode");
		lblBarcode.setBounds(42, 287, 56, 16);
		contentPane.add(lblBarcode);
		
		barcodeField = new JTextField();
		barcodeField.setBounds(99, 284, 229, 22);
		contentPane.add(barcodeField);
		barcodeField.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 271, 736, 4);
		contentPane.add(separator);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setBounds(367, 287, 56, 16);
		contentPane.add(lblQuantity);
		
		quantityField = new JTextField();
		quantityField.setBounds(424, 284, 109, 22);
		contentPane.add(quantityField);
		quantityField.setColumns(10);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	    rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		
	    JLabel lblPaymentMethod = new JLabel("Payment Method");
		lblPaymentMethod.setBounds(34, 620, 109, 16);
		contentPane.add(lblPaymentMethod);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("Credit");
		comboBox.addItem("Debit");
		comboBox.addItem("Cash");
		comboBox.setSelectedItem("Credit");
		comboBox.setBounds(151, 620, 97, 22);
		contentPane.add(comboBox);
		
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
		
		discountField = new JTextField("0");
		discountField.setBounds(589, 649, 46, 22);
		discountField.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(discountField);
		discountField.setColumns(10);
		
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
		
		taxField = new JTextField();
		taxField.setText("13");
		taxField.setBounds(589, 704, 42, 22);
		taxField.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(taxField);
		taxField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel(String.valueOf(invoiceNo));
		lblNewLabel_1.setBounds(514, 99, 56, 16);
		contentPane.add(lblNewLabel_1);
		
		customerLNameField = new JTextField();
		customerLNameField.setBounds(240, 96, 149, 22);
		contentPane.add(customerLNameField);
		customerLNameField.setColumns(10);
		
		JLabel lblCity = new JLabel("City");
		lblCity.setBounds(67, 169, 31, 16);
		contentPane.add(lblCity);
		
		cityField = new JTextField();
		cityField.setBounds(99, 166, 290, 22);
		contentPane.add(cityField);
		cityField.setColumns(10);
		
		JLabel lblProvince = new JLabel("Province");
		lblProvince.setBounds(42, 193, 56, 16);
		contentPane.add(lblProvince);
		
		provinceField = new JTextField();
		provinceField.setBounds(99, 190, 137, 22);
		contentPane.add(provinceField);
		provinceField.setColumns(10);
		
		JLabel lblZip = new JLabel("Zip");
		lblZip.setBounds(240, 193, 23, 16);
		contentPane.add(lblZip);
		
		zipField = new JTextField();
		zipField.setBounds(261, 190, 128, 22);
		contentPane.add(zipField);
		zipField.setColumns(10);
		
		errorLabel = new JLabel();
		errorLabel.setForeground(Color.RED);
		errorLabel.setBounds(438, 193, 299, 37);
		contentPane.add(errorLabel);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (validation() != null)
					errorLabel.setText(validation());
				else {
				System.out.println("hello");
				String orderInsert = null;
				String orderUpdate = null;
				String orderLineInsert = null;
				String customerInsert = null;
				int customerExists = 0;
				int customerId;
				int result;
				subtotal = 0;
				discount = 0;
				subtotalAfterDiscount = 0;
				tax = 0;
				balance = 0;
				discountRate = 0;
				taxRate = 0;
				
				
				String customerFName = customerFNameField.getText();
				String addressLine1 = addressLine1Field.getText();
				String addressLine2 = addressLine2Field.getText();
				long contactNumber = Long.parseLong(contactNumberField.getText());
				String email = emailField.getText();
				String customerLName = customerLNameField.getText();
				String city = cityField.getText();
				String province = provinceField.getText();
				String zip = zipField.getText();
				
				int quantity = Integer.parseInt(quantityField.getText());
				int barcode = Integer.parseInt(barcodeField.getText());
				
				DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
				LocalDateTime today = LocalDateTime.now();
				
				if (!addButtonFlag) {
					addButtonFlag = true;
					orderInsert = "INSERT INTO orders (order_id, employee_id, order_date)"
								+ "VALUES(" + invoiceNo + ", " + employeeId + ", '" 
								+ dateFormat.format(today) + "')";
					//need to check for the phone number to make sure that the customer is already registered
					ResultSet rs = null;
					
					DBConnection dbc = new DBConnection();
					rs = dbc.getResultSet("SELECT COUNT(*), ISNULL(MAX(customer_id), 0) FROM customer WHERE contact_number = " + contactNumber);
					
					try {
						while (rs.next()) {
							customerExists = Integer.parseInt(rs.getString(1));
							customerId = Integer.parseInt(rs.getString(2));
							if (customerExists == 0) {
								customerInsert = "INSERT INTO customer(\r\n" + 
										"	first_name,\r\n" + 
										"	last_name,\r\n" + 
										"	address_line1,\r\n" + 
										"	address_ine2,\r\n" + 
										"	province,\r\n" + 
										"	zip_code,\r\n" + 
										"	contact_number,\r\n" + 
										"	email_id)\r\n" + 
										"VALUES (\r\n" + 
										"	" + (customerFName.isEmpty() ? "null" : ("'" + customerFName + "'")) + ",\r\n" + 
										"	" + (customerLName.isEmpty() ? "null" : ("'" + customerLName + "'")) + ",\r\n" + 
										"	" + (addressLine1.isEmpty() ? "null" : ("'" + addressLine1 + "'")) + ",\r\n" + 
										"	" + (addressLine2.isEmpty() ? "null" : ("'" + addressLine2 + "'")) + ",\r\n" + 
										"	" + (province.isEmpty() ? "null" : ("'" + province + "'")) + ",\r\n" + 
										"	" + (zip.isEmpty() ? "null" : ("'" + zip + "'")) + ",\r\n" + 
										"	" + (contactNumber == 0 ? "null" : contactNumber) + ",\r\n" + 
										"	" + (email.isEmpty() ? "null" : ("'" + email + "'")) + ")";
								if (customerInsert != null) {
									DBConnection dbc1 = new DBConnection();
									result = dbc1.dml(customerInsert);
									if (result != 1) {
										System.out.println("error in inserting customer values");
									}
								}
								
								orderUpdate = "UPDATE orders SET customer_id = (SELECT customer_id FROM customer WHERE contact_number = " + contactNumber + ") WHERE order_id = " + invoiceNo;
							}
							else {
								orderUpdate = "UPDATE orders SET customer_id = " + customerId + " WHERE order_id = " + invoiceNo;
							}
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finally {
						if (rs != null) try { rs.close(); } catch(Exception e) {}
					}
					
					System.out.println(orderInsert);
					System.out.println(customerInsert);
					if (orderInsert != null) {
						DBConnection dbc1 = new DBConnection();
						result = dbc1.dml(orderInsert);
						if (result != 1) {
							System.out.println("error in inserting order values");
						}
					}
					if (orderUpdate != null) {
						DBConnection dbc1 = new DBConnection();
						result = dbc1.dml(orderUpdate);
						if (result != 1) {
							System.out.println("error in updating customer in orders");
						}
					}
				}
				
				orderLineInsert = "INSERT INTO order_line(\r\n" + 
						"	order_id,\r\n" + 
						"	item_id,\r\n" + 
						"	quantity)\r\n" + 
						"VALUES(\r\n" + 
						"	" + invoiceNo + ",\r\n" + 
						"	(SELECT item_id FROM item WHERE barcode = " + barcode + "),\r\n" + 
						"	" + quantity + ");";
				if (orderLineInsert != null) {
					DBConnection dbc = new DBConnection();
					result = dbc.dml(orderLineInsert);
					if (result != 1) {
						System.out.println("error in inserting order line values");
					}
				}
				
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
				
				discountRate = Float.parseFloat(discountField.getText());
				taxRate = Float.parseFloat(taxField.getText());
				discount = (subtotal * (discountRate / 100));
				subtotalAfterDiscount = subtotal - discount;
				tax = (subtotalAfterDiscount * (taxRate / 100));
				balance = subtotalAfterDiscount + tax;
				
				subtotalValue.setText(String.valueOf(subtotal));
				discountValue.setText(String.valueOf(discount));
				taxValue.setText(String.valueOf(tax));
				subtotalAfterDiscountValue.setText(String.valueOf(subtotalAfterDiscount));
				balanceDueValue.setText(String.valueOf(balance));
			}
			}
		});
		btnNewButton.setBounds(573, 283, 97, 25);
		contentPane.add(btnNewButton);
		
		
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validation() != null)
					errorLabel.setText(validation() + " cannot be null");
				else {
				System.out.println("print bill");
				String paymentMethod = comboBox.getSelectedItem().toString();
				int result = 0;
				discountRate = Float.parseFloat(discountField.getText());
				taxRate = Float.parseFloat(taxField.getText());
				
				String orderUpdate = "UPDATE orders\r\n" + 
						"SET payment_method = '" + paymentMethod + "'\r\n" + 
						"	, subtotal = '" + subtotal + "'\r\n" + 
						"	, discount = '" + discountRate + "'\r\n" + 
						"	, tax = '" + taxRate + "'\r\n" + 
						"	, amount_payable = '" + balance + "'\r\n" + 
						"WHERE order_id = '" + invoiceNo + "';";
				if (orderUpdate != null) {
					DBConnection dbc = new DBConnection();
					result = dbc.dml(orderUpdate);
					if (result != 1) {
						System.out.println("error in updating order table values");
					}
				}
				
				setVisible(false);
		        new BillDisplayPage(employeeId, invoiceNo).setVisible(true);
			
				}
			}
		});
		btnPrint.setBounds(367, 777, 97, 25);
		contentPane.add(btnPrint);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
		        new BillingSystem().setVisible(true);
			}
		});
		btnLogout.setBounds(651, 13, 97, 25);
		contentPane.add(btnLogout);
		
	}
	
	public String validation() {
		if (contactNumberField.getText().equals("")){  
		    contactNumberField.requestFocusInWindow();
		    return "Please enter a contact number.";
		}
		else if (contactNumberField.getText().contains("[a-zA-Z]+") == false && contactNumberField.getText().length() != 10){
			contactNumberField.requestFocusInWindow();
		    return "Please enter a valid contact number.";
		}
		else if (barcodeField.getText().equals("")) {
			barcodeField.requestFocusInWindow();
		    return "Please scan a product.";
		}
		else if (quantityField.getText().equals("")) {
			quantityField.requestFocusInWindow();
		    return "Please enter the quantity for the scanned product.";
		}
		else if (barcodeField.getText() != null) {
			ResultSet rs = null;
			
			DBConnection dbc = new DBConnection();
			rs = dbc.getResultSet("SELECT COUNT(*) FROM item WHERE barcode = " + barcodeField.getText());

			try {
				while (rs.next()) {
					if (rs.getInt(1) == 0) {
						barcodeField.requestFocusInWindow();
					    return "Product not found.";
					}	
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if (rs != null) try { rs.close(); } catch(Exception e) {}
			}
		}
		errorLabel.setText("");
		return null;
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
