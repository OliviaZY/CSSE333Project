import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class declineFriendRequestListner implements ActionListener {
	private JFrame frame;
	private Connection dbc;
	private String initialN;
	private String searchedN;

	public declineFriendRequestListner(String searchedN, String initialSearchN, JFrame frame1, Connection dbc) {
		this.frame = frame1;
		this.dbc = dbc;
		this.initialN = initialSearchN;
		this.searchedN = searchedN;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		frame.repaint();
		frame.revalidate();
		try {
			CallableStatement cs = this.dbc.prepareCall("call declineFriendRequest(?,?,?)");
			cs.setString(1, this.initialN);
			cs.setString(2, this.searchedN);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			System.out.println(cs);
			cs.execute();
			int ret = cs.getInt(3);
			if (ret == 1) {
				JOptionPane.showMessageDialog((Component) null, "ERROR: user name cannot be empty");
			} else {
				JOptionPane.showMessageDialog((Component) null, "SUCCESS!");
			}
		} catch (SQLException var13) {
			var13.printStackTrace();
			JOptionPane.showMessageDialog((Component) null, "Login Failed");
		}

	}
}
