import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class AcceptFriendRequestListener implements ActionListener {

		private Connection dbc;
		private String initialN;
		private String searchedN;
		private String selected;
		
		public AcceptFriendRequestListener(String buildQuery, String initialN, String searchedN, Connection dbc) {
			this.initialN = initialN;
			this.searchedN = searchedN;
			this.selected = buildQuery;
			this.dbc = dbc;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("test for calling the accept listener");
			try {
				CallableStatement cs = this.dbc.prepareCall("call acceptFriendRequest(?,?,?,?)");
				cs.setString(1, initialN);
				cs.setString(2, searchedN);
				cs.setString(3, selected);
				cs.registerOutParameter(4, java.sql.Types.INTEGER);
				System.out.println("haha");
				System.out.println(cs);
				cs.execute();
				int ret = cs.getInt(4);
				if (ret == 1) {
					JOptionPane.showMessageDialog((Component) null, "ERROR: user name cannot be empty");
				} else if (ret == 2) {
					JOptionPane.showMessageDialog((Component) null,
							"ERROR: you are already friend with this person.");
				} else {
					JOptionPane.showMessageDialog((Component) null,
							"SUCCESSFUL added this user as your friend! congrats");
				}
			} catch (SQLException var13) {
				var13.printStackTrace();
				JOptionPane.showMessageDialog((Component) null, "Login Failed");
			}
		}
	}


