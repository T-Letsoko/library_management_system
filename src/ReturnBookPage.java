import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ReturnBookPage implements ActionListener{
	JFrame frame = new JFrame("Enter Details");
	JLabel lblIssueId = new JLabel("Issue ID(IID)");
	JLabel lblReturnDt = new JLabel("Return Date(DD-MM-YYYY)");
	JTextField txtIssueId = new JTextField();
	JTextField txtReturnDt = new JTextField();
	JButton btnReturn = new JButton("Return");
	
	public ReturnBookPage() {
		lblIssueId.setBounds(30, 15, 100, 30);
		lblReturnDt.setBounds(30, 50, 150, 30);
		
		txtIssueId.setBounds(110, 15, 200, 30);
		txtReturnDt.setBounds(180, 50, 130, 30);
		
		btnReturn.setBounds(130, 170, 80, 25);
		btnReturn.addActionListener(this);
		
		frame.add(lblIssueId);
		frame.add(lblReturnDt);
		frame.add(txtIssueId);
		frame.add(txtReturnDt);
		frame.add(btnReturn);
		frame.setSize(350, 250);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnReturn) {
			returnBook();
		}
	}
	
	/**
	 * This method will fetch issue info and update issue table with return date
	 * and also calculate fine is book is returned late.
	 */
	public void returnBook() {
		int issueId = Integer.parseInt(txtIssueId.getText());
		String strReturnDate = txtReturnDt.getText();
		String strIssuedDate = null;
		int period = 0;
		try {
			Connection connection = ConnectionDb.getConnection();
			String query = "SELECT * FROM ISSUE WHERE IID = " + issueId;
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			
			// Get date objects to calculate days/period
			while(rs.next()) {
				strIssuedDate = rs.getString("IssueDate");
				period = rs.getInt("Period");
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate returnDate = LocalDate.parse(strReturnDate, formatter);
			LocalDate issueDate = LocalDate.parse(strIssuedDate, formatter);
			
			// Get difference between return and issue date and store value in difference variable
			int difference = (int)ChronoUnit.DAYS.between(issueDate, returnDate);
			
			// update query
			String updateQuery = "UPDATE ISSUE SET ReturnDate = '" + returnDate + "' WHERE IID = " + issueId;
			
			int rowAffected = statement.executeUpdate(updateQuery);
			
			if(difference > period) {
				int fine = (difference - period) * 10;
				String updateQuery2 = "UPDATE ISSUE SET FINE = " + fine + " WHERE IID = " + issueId;
				rowAffected = statement.executeUpdate(updateQuery2);
				JOptionPane.showMessageDialog(null, "Fine: R" + fine);
			}
			
			if(rowAffected > 0)
				JOptionPane.showMessageDialog(null, "Book Returned!");
			
			statement.close();
			rs.close();
			connection.close();
		} catch(SQLException ex) {
			ex.printStackTrace();
		} 
	}
	
	
	
	
	
	
}
