import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class SendFriendRequestListener {

	private Connection dbc;
	private String initialN;
	private String searchedN;
	private String selected;
	
	public SendFriendRequestListener(String selected, String initialN, String searchedN, Connection dbc) {
		this.initialN = initialN;
		this.searchedN = searchedN;
		this.selected = selected;
		this.dbc = dbc;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub.
		try {
			CallableStatement cs = this.dbc.prepareCall("call addFriendRequest(?,?,?)");
			cs.setString(1, initialN);
			cs.setString(2, searchedN);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			System.out.println(cs);
			cs.execute();
			int ret = cs.getInt(4);
			if (ret == 1) {
				JOptionPane.showMessageDialog((Component) null, "ERROR: user name cannot be empty");
			} else if (ret == 2) {
				JOptionPane.showMessageDialog((Component) null, "ERROR: searched user name cannot be empty");
			}else if (ret == 7) {
				JOptionPane.showMessageDialog((Component) null, "ERROR: you cannot send friend request to yourself");
			}else if (ret == 3) {
				JOptionPane.showMessageDialog((Component) null, "ERROR: invalid user name ");
			}else if (ret == 4) {
				JOptionPane.showMessageDialog((Component) null, "ERROR: invalid user name ");
			}else if (ret == 5) {
				JOptionPane.showMessageDialog((Component) null,
						"ERROR: you are already friend with this person.");
			} else if (ret == 6) {
				JOptionPane.showMessageDialog((Component) null,
						"ERROR: you've already send friend request to this user.");
			}else {
				JOptionPane.showMessageDialog((Component) null,
						"SUCCESSFUL send friend request to this user!");
			}
		} catch (SQLException var13) {
			var13.printStackTrace();
			JOptionPane.showMessageDialog((Component) null, "Login Failed");
		}
	}
	

}
