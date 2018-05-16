import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ViewFriendListListner implements ActionListener {

	private JFrame frame;
	private Connection dbc;
	private JTextField username;
	static JLabel l3;

	public ViewFriendListListner(JTextField tf1, JFrame frame1, Connection dbc) {
		this.frame = frame;
		this.dbc = dbc;
		this.username = tf1;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFrame frame1 = new JFrame();
		frame1.setSize(1000, 1000);

		try {
			// check if the user name is contained in the usernameinit attribute
			CallableStatement cs = this.dbc.prepareCall("call ListOfFriendInit(?,?)");
			cs.setString(1, username.getText());
			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			cs.execute();
			int ret = cs.getInt(2);
			ResultSet rs = cs.executeQuery();
			
			// check if the user name is contained in the
			// usernameseached attribute
			CallableStatement cs2 = this.dbc.prepareCall("call listOfFriendSearched(?,?)");
			cs2.setString(1, username.getText());
			cs2.registerOutParameter(2, java.sql.Types.INTEGER);
			cs2.execute();
			int ret2 = cs2.getInt(2);
			ResultSet rs2 = cs2.executeQuery();

			if (ret == 1) {
				JOptionPane.showMessageDialog((Component) null, "ERROR: user name cannot be empty");
			} else if (ret == 2 && ret2 == 2) {
				JOptionPane.showMessageDialog((Component) null, "Dude, get a friend!");
			} else {
				int i = 0;
				while (rs.next()) {
					System.out.println(i);
					JLabel l1 = new JLabel(rs.getString(1));
					System.out.println("this is a test for select the first row in the result set");
					l1.setBounds(100, 200 * i, 200, 30);
					l1.setForeground(Color.blue);
					l1.setFont(new Font("Serif", Font.BOLD, 40));
					frame1.add(l1);
					i++;
				}
				if (ret2 == 3) {
					while (rs2.next()) {
						System.out.println(i);
						JLabel l1 = new JLabel(rs2.getString(1));
						System.out.println("this is a test for select the first row in the result set");
						l1.setBounds(100, 200 * i, 200, 30);
						l1.setForeground(Color.blue);
						l1.setFont(new Font("Serif", Font.BOLD, 40));
						frame1.add(l1);
						i++;
					}
				}


			}
			frame1.setVisible(true);

		} catch (SQLException var13) {
			var13.printStackTrace();
			JOptionPane.showMessageDialog((Component) null, "view friend failed");
		}
	}

}
