import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class LoginPage implements ActionListener {

	JFrame frame = new JFrame("Login");
	JLabel lblUsername = new JLabel("Username");
	JLabel lblPassword = new JLabel("Password");
	JTextField txtUsername = new JTextField();
	JPasswordField txtPassword = new JPasswordField();
	JButton btnLogin = new JButton("Login");
	JButton btnReset = new JButton("Reset");
	private HashMap<String, String[]> userCredentials = new HashMap<String, String[]>();
	
	public LoginPage(HashMap<String, String[]> userCredentials) {
		this.userCredentials = userCredentials;
		lblUsername.setBounds(30, 15, 100, 30);
		lblPassword.setBounds(30, 50, 100, 30);
		txtUsername.setBounds(110, 15, 200,  30);
		txtPassword.setBounds(110, 50, 200, 30);
		btnLogin.setBounds(130, 90, 80, 25);
		btnReset.setBounds(200, 90, 80, 25);		
		btnLogin.setFocusable(false);
		btnLogin.addActionListener(this);
		btnReset.addActionListener(this);
		
		// UI components to the JFrame
		frame.add(lblUsername);
		frame.add(lblPassword);
		frame.add(txtUsername);
		frame.add(txtPassword);
		frame.add(btnLogin);
		frame.add(btnReset);
		
		// Set Frame properties
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(340, 180);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// If reset button is clicked remove the entered text
		if(e.getSource() == btnReset) {
			reset();
		}
		
		// Check if user has clicked the login button
		if(e.getSource() == btnLogin) {
			String username = txtUsername.getText();
			String password = String.valueOf(txtPassword.getPassword());
			
			// Check if user name and password text fields and show a message
			if(username.equals("")) {
				JOptionPane.showMessageDialog(null, "Please enter username");
			} else if(password.equals("")) {
				JOptionPane.showMessageDialog(null, "Please enter password");
			} else {
				if(userCredentials.containsKey(username)) {
					// Get a list log info value
					String[] loginInfo = userCredentials.get(username);
					
					// Check if passwords are the same
					if(loginInfo[0].equals(password)) {
						int userId = Integer.parseInt(loginInfo[2]);
						String adminValue = loginInfo[1];
						
						frame.dispose();
						
						// Check is user is an admin or not
						if(adminValue.equals("1")) {
							new AdminMenuPage();
						} else {
							new UserMenuPage(userId);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Incorrect password entered");
						reset();
					}

				} else {
					JOptionPane.showMessageDialog(null, "Incorrect username entered");
					reset();
				}	

		  }
	   }
	}

	/**
	 * This method will clear/remove text from text fields
	 */
	private void reset() {
		txtUsername.setText("");
		txtPassword.setText("");
		txtUsername.requestFocus();
	}
}

