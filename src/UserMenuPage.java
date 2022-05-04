import javax.swing.*;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserMenuPage implements ActionListener{
	JFrame frame = new JFrame("User Functions");
	JButton btnViewBooks = new JButton("View Books");
	JButton btnMyBooks = new JButton("My Books");
	JButton btnLogout = new JButton("Logout");
	private int userId;
	
	public UserMenuPage(int userId) {
		this.userId = userId;
		btnViewBooks.setBounds(20, 20, 120, 25);
		btnViewBooks.addActionListener(this);
		
		btnMyBooks.setBounds(150, 20, 120, 25);
		btnMyBooks.addActionListener(this);
		
		btnLogout.setBounds(280, 20, 120, 25);
		btnLogout.addActionListener(this);
		
		frame.add(btnViewBooks);
		frame.add(btnMyBooks);
		frame.add(btnLogout);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420, 100);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnViewBooks) {
			showBooksAvailable();
		} 
		
		if(e.getSource() == btnMyBooks) {
			showMyBooks();
		}
		
		if(e.getSource() == btnLogout) {
			Credentials credentials = new Credentials();
			frame.dispose();
			new LoginPage(credentials.getCredentials());
		}
	}
	
	/*
	 * This function will show available books in the database
	 */
	private void showBooksAvailable() {
		
		try {
			JFrame frame = new JFrame("Available Books");
			JTable table = new JTable();
			Connection connection = ConnectionDb.getConnection();
			Statement stmt = connection.createStatement();
			String sqlSelect = "select * from books";
			ResultSet rs = stmt.executeQuery(sqlSelect); 
			
			table.setModel(DbUtils.resultSetToTableModel(rs));
			JScrollPane scrollPane = new JScrollPane(table);
			frame.add(scrollPane);
			frame.setSize(800, 400);
			//frame.setLayout(null);
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			
			rs.close();
			stmt.close();
			connection.close();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}	

	}
	
	/**
	 * This function show books issued to a user or member
	 */
	private void showMyBooks() {
		JFrame frame = new JFrame("My Books");
		JTable table = new JTable();
		
		try {
			Connection connection = ConnectionDb.getConnection();
			
			// SQL Join query
			String query = "SELECT ISSUE.IID, ISSUE.UID, ISSUE.BID, ISSUE.ISSUEDATE, ISSUE.RETURNDATE, "
					+ "ISSUE.PERIOD, ISSUE.FINE, BOOKS.BOOKNAME, BOOKS.GENRE, BOOKS.PRICE "
					+ "FROM ISSUE "
					+ "INNER JOIN BOOKS "
					+ "ON ISSUE.BID = BOOKS.BID "
					+ "WHERE UID = " + userId;
			
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			
			// Add data to the table
			table.setModel(DbUtils.resultSetToTableModel(result));
			
			// Add table to scroll pane
			JScrollPane scrollPane = new JScrollPane(table);
			
			frame.add(scrollPane);
			
			// Set frame properties
			frame.setSize(700, 300);
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
}
