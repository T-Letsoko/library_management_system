import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;
public class AddBookPage implements ActionListener {
	JFrame frame = new JFrame("Enter Book Details");
	JLabel lblBookName = new JLabel("Book Name");
	JLabel lblPrice = new JLabel("Price");
	JLabel lblGenre = new JLabel("Genre");
	JTextField txtBookName = new JTextField();
	JTextField txtPrice = new JTextField();
	JTextField txtGenre = new JTextField();
	JButton btnSubmit = new JButton("Submit");
	
	AddBookPage(){
		lblBookName.setBounds(30, 15, 100, 30);
		txtBookName.setBounds(110, 15, 200, 30);
		
		lblPrice.setBounds(30, 53, 100, 30);
		txtPrice.setBounds(110, 53, 200, 30);
		
		lblGenre.setBounds(30, 90, 100, 30);
		txtGenre.setBounds(110, 90, 200, 30);
		
		btnSubmit.setBounds(130, 130, 80, 25);
		btnSubmit.addActionListener(this);
		
		frame.add(lblBookName);
		frame.add(txtBookName);
		frame.add(lblPrice);
		frame.add(txtPrice);
		frame.add(lblGenre);
		frame.add(txtGenre);
		frame.add(btnSubmit);
		frame.setSize(350, 200);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnSubmit) {
			addBook();
		}
	}
	
	private void addBook() {
		String bookName = txtBookName.getText();
		int price = Integer.parseInt(txtPrice.getText());
		String genre = txtGenre.getText();
		
		try {
			Connection connection = ConnectionDb.getConnection();
			String query = "INSERT INTO BOOKS(BOOKNAME, PRICE, GENRE) VALUES('" + bookName + 
					"', " + price + ", '" + genre + "')";
			
			Statement statement = connection.createStatement();
			
			int rowsAffected = statement.executeUpdate(query);
			
			if(rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Book added!");
				reset();
			}
			
			statement.close();
			connection.close();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	private void reset() {
		txtBookName.setText("");
		txtPrice.setText("");
		txtGenre.setText("");
	}
}
