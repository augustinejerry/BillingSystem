import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPasswordField;

public class BillingSystem {

	private JFrame frame;
	private JTextField username;
	private JPasswordField password;
	private JLabel failureMessage;
	private boolean userExists;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BillingSystem window = new BillingSystem();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BillingSystem() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		userExists = false;
		
		JLabel lblSignature = new JLabel("Signature");
		lblSignature.setBounds(133, 33, 183, 32);
		lblSignature.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 26));
		frame.getContentPane().add(lblSignature);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(77, 103, 81, 22);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 17));
		frame.getContentPane().add(lblUsername);
		
		username = new JTextField();
		username.setBounds(163, 103, 183, 22);
		frame.getContentPane().add(username);
		username.setColumns(10);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setBounds(140, 178, 137, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String passwordTyped = new String(password.getPassword());
				String usernameTyped = username.getText();
				String passwordSaved = null;
				int employeeIdSaved = 0;
				int invoiceNo = 0;
				System.out.println("username:" + usernameTyped);
				System.out.println("password:" + passwordTyped);
				
				ResultSet rs = null;
				
				DBConnection dbc = new DBConnection();
				rs = dbc.getResultSet("SELECT password, employee_id, (SELECT ISNULL(max(order_id),0) + 1 FROM orders) invoice_id FROM users WHERE username = '" + usernameTyped + "'");
				
				
				
				try {
					while (rs.next()) {
						userExists = true;
						passwordSaved = rs.getString(1);
						employeeIdSaved = Integer.parseInt(rs.getString(2));
						invoiceNo = Integer.parseInt(rs.getString(3));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					if (rs != null) try { rs.close(); } catch(Exception e) {}
				}
				
				if (userExists && passwordSaved.equals(passwordTyped)) {
					//goto billing page
					frame.setVisible(false);
			        new BillingPage(employeeIdSaved, invoiceNo).setVisible(true);
				}
				else {
					//print error message
					failureMessage.setText("Incorrect Username/Password!");
				}
			}
		});
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(81, 138, 77, 21);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
		frame.getContentPane().add(lblPassword);
		
		password = new JPasswordField();
		password.setBounds(163, 138, 183, 22);
		frame.getContentPane().add(password);
		frame.getContentPane().add(btnNewButton);
		
		failureMessage = new JLabel("");
		failureMessage.setBounds(124, 216, 222, 16);
		failureMessage.setForeground(Color.RED);
		frame.getContentPane().add(failureMessage);
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		frame.setVisible(b);
	}
}
