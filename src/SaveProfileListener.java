import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SaveProfileListener implements ActionListener {
	static JTextField tf1;
	static JTextField tf2;
	JTextField tf3;
	static JTextField tf4;
	static JTextField tf5;
	static JTextField tf6;
	static JTextField tf7;

	static String uname;
	java.util.Date date1;

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
		frame.repaint();
		frame.revalidate();
		frame.setVisible(false);
		String sDate1 = tf3.getText();
		if (sDate1 != " " || sDate1 != null) {
			try {
				date1 = new SimpleDateFormat("yyyy-mm-dd").parse(sDate1);
			} catch (ParseException exception) {
				// TODO Auto-generated catch-block stub.
				exception.printStackTrace();
			}
		}else{
			date1 = null;
		}
		// java.util.Date date = SimpleDateFormat.parse(pattern);
		try {
			CallableStatement cs = this.dbc.prepareCall("call SaveProfile(?,?,?,?,?,?,?,?,?)");
			cs.setString(1, uname);
			cs.setDate(2, new Date(date1.getTime()));
			cs.setString(3, tf4.getText());
			cs.setString(4, tf6.getText());
			cs.setString(5, tf5.getText());
			cs.setString(6, tf7.getText());
			cs.setString(7, tf1.getText());
			cs.setString(8, tf2.getText());
			cs.registerOutParameter(9, java.sql.Types.INTEGER);
			System.out.println(cs);
			cs.execute();
			int ret = cs.getInt(9);

			//

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Expection!!!! add bg info failed");
		}

	}
}
