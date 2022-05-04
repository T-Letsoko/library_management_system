
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class AddUserPage implements ActionListener {
	JFrame frame = new JFrame("Enter User Details");
	JButton btnAdd = new JButton("Add User");
	JLabel lblUsername = new JLabel("Username");
	JLabel lblPassword = new JLabel("Password");
	JTextField txtUsername = new JTextField();
	JPasswordField txtPassword = new JPasswordField();
	JRadioButton rdoAdmin = new JRadioButton("Admin");
	JRadioButton rdoUser = new JRadioButton("User");
	
	public AddUserPage() {
		lblUsername.setBounds(30, 15, 100, 30);
		lblPassword.setBounds(30, 50, 100, 30);
		txtUsername.setBounds(110, 15, 200, 30);
		txtPassword.setBounds(110, 50, 200, 30);
		rdoAdmin.setBounds(55, 80, 80, 30);
		rdoUser.setBounds(130, 80, 80, 30);
		//rdoAdmin.setSelected(true);

		ButtonGroup btnGroup = new ButtonGroup();
		
		btnGroup.add(rdoAdmin);btnGroup.add(rdoUser);
		
		btnAdd.setBounds(130, 130, 120, 25);
		btnAdd.addActionListener(this);
		
		// add to frame
		frame.add(lblUsername);
		frame.add(lblPassword);
		frame.add(txtUsername);
		frame.add(txtPassword);
		frame.add(rdoAdmin);
		frame.add(rdoUser);
		frame.add(btnAdd);
		frame.setSize(350, 200);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// validate if text fields are empty
		if(e.getSource() == btnAdd) {
			String userName = txtUsername.getText();
			String password = String.valueOf(txtPassword.getPassword());
			boolean admin = false;
			
			if(rdoAdmin.isSelected()) {
				admin = true;
			}
			
			if(userName.equals("")) {
				JOptionPane.showMessageDialog(null, "Please enter usename");
			} else if(password.equals("")) {
				JOptionPane.showMessageDialog(null, "Please enter password");
			} else {
				try {
					Connection connection = ConnectionDb.getConnection();
					String query = "INSERT INTO Users(Username, Password, Admin) VALUES('" + userName + "', '" + 
					password + "', " + admin + ")";
					
					Statement stmt = connection.createStatement();
					int rowAffected = stmt.executeUpdate(query);
					
					if(rowAffected > 0) {
						JOptionPane.showMessageDialog(null, "User added!");
						txtUsername.setText("");
						txtPassword.setText("");
					}
					frame.dispose();
					stmt.close();
					connection.close();
				} catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}


