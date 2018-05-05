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
	static JTextField FirstNJ;
	static JTextField LastNJ;
	static JTextField DBirthJ;
	static JTextField StateJ;
	static JTextField CollegeJ;
	static JTextField ProfessionJ;
	static JTextField FieldJ;

	static String uname;
	java.util.Date date1;

	private Connection dbc;
	JFrame frame;

	public SaveProfileListener(JTextField tf1, JTextField tf2, JTextField tf3, JTextField tf4, JTextField tf5,
			JTextField tf6, JTextField tf7, String uname, JFrame frame, Connection dbc) {
		this.uname = uname;
		this.FirstNJ = tf1;
		this.LastNJ = tf2;
		this.DBirthJ = tf3;
		this.StateJ = tf4;
		this.CollegeJ = tf5;
		this.ProfessionJ = tf6;
		this.FieldJ = tf7;
		this.dbc = dbc;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame.repaint();
		frame.revalidate();
		frame.setVisible(false);
		String sDate1 = this.DBirthJ.getText();
		System.out.println(this.DBirthJ.getText());
		System.out.println("this is a test for sDate1");
		if (sDate1 != "" || sDate1 != null || sDate1 != "yyyy-mm-dd") {
			try {
				date1 = new SimpleDateFormat("yyyy-mm-dd").parse(sDate1);
			} catch (ParseException exception) {
				// TODO Auto-generated catch-block stub.
				exception.printStackTrace();
			}
		}else{
			this.date1 = null;
		}
		// java.util.Date date = SimpleDateFormat.parse(pattern);
		try {
			CallableStatement cs = this.dbc.prepareCall("call SaveProfile(?,?,?,?,?,?,?,?,?)");
			cs.setString(1, uname);
			if (sDate1 == "" ||sDate1 == null || sDate1.equals("yyyy-mm-dd")){
				cs.setDate(2, null);
			}else{
				cs.setDate(2, new Date(date1.getTime()));
			}
			
			cs.setString(3, StateJ.getText());
			cs.setString(4, ProfessionJ.getText());
			cs.setString(5, CollegeJ.getText());
			cs.setString(6, FieldJ.getText());
			cs.setString(7, FirstNJ.getText());
			cs.setString(8, LastNJ.getText());
			cs.registerOutParameter(9, java.sql.Types.INTEGER);
			System.out.println(cs);
			System.out.println("haha, iam here");
			cs.execute();
			int ret = cs.getInt(9);

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Expection!!!! add bg info failed");
		}

	}
}
