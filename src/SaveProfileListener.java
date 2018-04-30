import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SaveProfileListener implements ActionListener {	
	static JTextField tf1;
	static JTextField tf2;
	static JTextField tf3;
	static JTextField tf4;
	static JTextField tf5;
	static JTextField tf6;
	static JTextField tf7;
	
	static String uname;
	
	private Connection dbc;
	JFrame frame;
	public SaveProfileListener(JTextField tf1, JTextField tf2, JTextField tf3, JTextField tf4, JTextField tf5,
			JTextField tf6, JTextField tf7, String uname, JFrame frame, Connection dbc) {
		this.uname = uname;
		this.tf1 = tf1;
		this.tf2 = tf2;
		this.tf3 = tf3;
		this.tf4 = tf4;
		this.tf5 = tf5;
		this.tf6 = tf6;
		this.tf7 = tf7;
		this.dbc = dbc;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String query = "update person set firstN = '" + this.tf1.getText()+"', lastN = '"+this.tf2.getText()
		+"', dateOfBirth = '" + this.tf3.getText()+"', state = '" 
				+ this.tf4.getText()+"', college = '" + this.tf5.getText()+"',profession = '"
		+ this.tf6.getText()+"',field = '" + this.tf7.getText()+"' where userName = '"+this.uname+ "';";
		PreparedStatement stmt;
		try{
			stmt = this.dbc.prepareStatement(query);
			System.out.println(query);
			stmt.execute();
//			
			frame.repaint();
		    frame.revalidate();
		    frame.setVisible(false);
		}catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Expection!!!! add bg info failed");
		}

	}
}
