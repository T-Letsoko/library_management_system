
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
public class AdminMenuPage extends JFrame implements ActionListener {
	
	JFrame frame = new JFrame("Admin Functions");
	JButton btnViewBooks = new JButton("View Books");
	JButton btnViewUsers = new JButton("View Users");
	JButton btnViewIssuedBooks = new JButton("View Issued Books");
	JButton btnAddUser = new JButton("Add User");
	JButton btnAddBook = new JButton("Add Book");
	JButton btnIssueBook = new JButton("Issue Book");
	JButton btnReturnBook = new JButton("Return Book");
	JButton btnLogout = new JButton("Logout");
	
	public AdminMenuPage() {
		btnViewBooks.setBounds(20, 20, 120, 25);
		btnViewBooks.addActionListener(this);
		
		btnViewUsers.setBounds(150, 20, 120, 25);
		btnViewUsers.addActionListener(this);
		
		btnViewIssuedBooks.setBounds(280, 20, 160, 25);
		btnViewIssuedBooks.addActionListener(this);
		
		btnAddUser.setBounds(20, 60, 120, 25);
		btnAddUser.addActionListener(this);
		
		btnAddBook.setBounds(150, 60, 120, 25);
		btnAddBook.addActionListener(this);
		
		btnIssueBook.setBounds(450, 20, 120, 25);
		btnIssueBook.addActionListener(this);
		
		btnReturnBook.setBounds(280, 60, 160, 25);
		btnReturnBook.addActionListener(this);
		
		btnLogout.setBounds(450, 60, 120, 25);
		btnLogout.addActionListener(this);
				
		frame.add(btnViewBooks);
		frame.add(btnViewUsers);
		frame.add(btnViewIssuedBooks);
		frame.add(btnAddUser);
		frame.add(btnAddBook);
		frame.add(btnIssueBook);
		frame.add(btnReturnBook);
		frame.add(btnLogout);
		
		frame.setSize(600, 200);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnViewBooks) {
			viewBooks();
		}
		
		if(e.getSource() == btnViewUsers) {
			viewUsers();
		}
		
		if(e.getSource() == btnViewIssuedBooks) {
			viewIssuedBooks();
		}
		
		if(e.getSource() == btnIssueBook) {
			issueBook();
		}
		
		if(e.getSource() == btnAddUser) {
			new AddUserPage();
		}
		
		if(e.getSource() == btnAddBook) {
			new AddBookPage();
		}
		
		if(e.getSource() == btnReturnBook) {
			new ReturnBookPage();
		}
		
		if(e.getSource() == btnLogout) {
			frame.dispose();
			Credentials credentials = new Credentials();
			new LoginPage(credentials.getCredentials());
		}
	}

	/**
	 * This method will get and show all the books available
	 */
	private void viewBooks() {
		JFrame frame = new JFrame("Books Available");
		JTable table = new JTable();
		
		try {
			Connection connection = ConnectionDb.getConnection();
			String query = "SELECT * FROM BOOKS";
			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery(query);
			
			table.setModel(DbUtils.resultSetToTableModel(results));
			
			JScrollPane scrollPane = new JScrollPane(table);
			
			frame.add(scrollPane);
			
			frame.setSize(800, 400);
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			
			statement.close();
			results.close();
			connection.close();
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * This method will get and show all registered users
	 */
	private void viewUsers() {
		JFrame frame = new JFrame("Users List");
		JTable table = new JTable();
		
		try {
			Connection connection = ConnectionDb.getConnection();
			String query = "SELECT * FROM USERS";
			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery(query);
			
			table.setModel(DbUtils.resultSetToTableModel(results));
			
			JScrollPane scrollPane = new JScrollPane(table);
			
			frame.add(scrollPane);
			
			frame.setSize(800, 400);
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			
			statement.close();
			results.close();
			connection.close();
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * This function show books issued to a user or member
	 */
	private void viewIssuedBooks() {
		JFrame frame = new JFrame("Issued Books");
		JTable table = new JTable();
		
		try {
			Connection connection = ConnectionDb.getConnection();
			
			// SQL Join query
			String query = "SELECT ISSUE.IID, ISSUE.UID, ISSUE.BID, ISSUE.ISSUEDATE, ISSUE.RETURNDATE, "
					+ "ISSUE.PERIOD, ISSUE.FINE, BOOKS.BOOKNAME, BOOKS.GENRE, BOOKS.PRICE "
					+ "FROM ISSUE "
					+ "INNER JOIN BOOKS "
					+ "ON ISSUE.BID = BOOKS.BID";
			
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			
			// Add data to the table
			table.setModel(DbUtils.resultSetToTableModel(result));
			
			// Add table to scroll pane
			JScrollPane scrollPane = new JScrollPane(table);
			
			frame.add(scrollPane);
			
			// Set frame properties
			frame.setSize(900, 400);
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			//frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			statement.close();
			result.close();
			connection.close();
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * This method will create window and allow a user to issue out a book
	 */
	public void issueBook() {
		JFrame frame = new JFrame("Enter Issue Details");
		JLabel lblBookId = new JLabel("Book ID(BID)");
		JLabel lblUserId = new JLabel("User ID(UID)");
		JLabel lblPeriod = new JLabel("Period(days)");
		JLabel lblIssuedDate = new JLabel("Issued Date(DD-MM-YYYY)");
		JTextField txtBookId = new JTextField();
		JTextField txtUserId = new JTextField();
		JTextField txtPeriod = new JTextField();
		JTextField txtIssuedDate = new JTextField();
		JButton btnSubmit = new JButton("Submit");
		
		lblBookId.setBounds(30, 15, 100, 30);
		lblUserId.setBounds(30, 53, 100, 30);
		lblPeriod.setBounds(30, 90, 100, 30);
		lblIssuedDate.setBounds(30, 127, 150, 30);
		
		txtBookId.setBounds(110, 15, 200, 30);
		txtUserId.setBounds(110, 53, 200, 30);
		txtPeriod.setBounds(110, 90, 200, 30);
		txtIssuedDate.setBounds(180, 130, 130, 30);
		
		btnSubmit.setBounds(130, 170, 80, 25);
		
		btnSubmit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				int bookId = Integer.parseInt(txtBookId.getText());
				int userId = Integer.parseInt(txtUserId.getText());
				int period = Integer.parseInt(txtPeriod.getText());
				String issueDate = txtIssuedDate.getText();
				
				try {
					Connection connection = ConnectionDb.getConnection();
					
					String query = "INSERT INTO ISSUE(BID, UID, PERIOD, ISSUEDATE) VALUES("
							+ bookId + ", " + userId + ", " + period + ", '" + issueDate + "')";
					
					Statement statement = connection.createStatement();
					int rowAffected = statement.executeUpdate(query);
					
					if(rowAffected > 0) {
						JOptionPane.showMessageDialog(null, "Book Issued!");
					}
					
					statement.close();
					connection.close();
				} catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		
		frame.add(lblBookId);
		frame.add(lblUserId);
		frame.add(lblPeriod);
		frame.add(lblIssuedDate);
		frame.add(txtBookId);
		frame.add(txtUserId);
		frame.add(txtPeriod);
		frame.add(txtIssuedDate);
		frame.add(btnSubmit);
		
		frame.setSize(350, 250);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}


}
