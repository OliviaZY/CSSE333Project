import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class acceptFriendRequestListner implements ActionListener {
	private JFrame frame;
	private Connection dbc;
	private String initialN;
	private String searchedN;
	private JLabel type;

	public acceptFriendRequestListner(String searchedN, String initialSearchN, JFrame frame1, Connection dbc) {
		this.frame = frame1;
		this.dbc = dbc;
		this.initialN = initialSearchN;
		this.searchedN = searchedN;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame.repaint();
		frame.revalidate();
		JFrame frame1 = new JFrame();
		frame1.setSize(1000, 1000);
		String[] friendType = { "parent", "coworker", "collegeMate", "relation", "fromSamePlace" };
		JComboBox<String> friendList = new JComboBox<>(friendType);
		JComboBox petList = new JComboBox(friendType);
		// petList.setSelectedIndex(4);
		petList.setBounds(300, 300, 100, 30);
		// add to the parent container (e.g. a JFrame):
		frame1.add(petList);

		// get the selected item:
		String selectedType = String.valueOf(petList.getSelectedItem());
		System.out.println("You seleted the type: " + selectedType);
		try {
			CallableStatement cs = this.dbc.prepareCall("call acceptFriendRequest(?,?,?,?)");
			cs.setString(1, initialN);
			cs.setString(2, searchedN);
			cs.setString(3, selectedType);
			cs.registerOutParameter(4, java.sql.Types.INTEGER);
			cs.execute();
			int ret = cs.getInt(4);
			if (ret == 1) {
				JOptionPane.showMessageDialog((Component) null, "ERROR: user name cannot be empty");
			} else if (ret == 2) {
				JOptionPane.showMessageDialog((Component) null, "ERROR: you are already friend with this person.");
			} else {
				JOptionPane.showMessageDialog((Component) null, "SUCCESSFUL added this user as your friend! congrats");
			}
			frame1.validate();
			frame1.setVisible(true);
		} catch (SQLException var13) {
			var13.printStackTrace();
			JOptionPane.showMessageDialog((Component) null, "Login Failed");
		}

	}
}
