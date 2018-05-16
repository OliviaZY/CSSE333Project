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
			CallableStatement cs = this.dbc.prepareCall("call listOfFriendInit(?,?)");
			cs.setString(1, username.getText());
			cs.registerOutParameter(2, java.sql.Types.INTEGER);

			cs.execute();
			int ret = cs.getInt(2);
			// JButton[] buttonLinksAcc = new JButton[ret];
			// JButton[] buttonLinksDec = new JButton[ret];
			if (ret == 1) {
				JOptionPane.showMessageDialog((Component) null, "ERROR: user name cannot be empty");
			} else if (ret == 2) {
				JOptionPane.showMessageDialog((Component) null, "Dude, get a friend!");
			} else {
				System.out.println(cs);
				ResultSet rs = cs.executeQuery();
				System.out.println(rs);

				// for (int i =0 ; i<ret;i++){
				// buttonLinksAcc[i]=new JButton("accpet as a friend");
				// buttonLinksAcc[i].setForeground(Color.blue);
				// buttonLinksAcc[i].setFont(new Font("Serif", Font.BOLD, 20));
				// buttonLinksAcc[i].setBounds(600, 400+30*i, 100, 30);
				// frame1.add(buttonLinksAcc[i]);
				//
				//
				// buttonLinksDec[i]=new JButton("decline");
				// buttonLinksDec[i].setForeground(Color.blue);
				// buttonLinksDec[i].setFont(new Font("Serif", Font.BOLD, 20));
				// buttonLinksDec[i].setBounds(750, 400+30*i, 100, 30);
				// frame1.add(buttonLinksDec[i]);
				//
				// }
				int i = 0;
				// while (rs.next()){
				// i++;
				// }
				// System.out.println("this is a test for counting the total row
				// of
				// the result set");
				// System.out.println(i);
				while (rs.next()) {

					System.out.println(i);
					JLabel l1 = new JLabel(rs.getString(1));
					// buttonLinksDec[i].addActionListener(new
					// declineFriendRequestListner(username.getText(),l1.getText(),
					// frame1,dbc));
					// buttonLinksAcc[i].addActionListener(new
					// acceptFriendRequestListner(username.getText(),l1.getText(),
					// frame1,dbc));

					// JButton accept = new JButton("accept as a friend!");
					// rs.next();
					System.out.println("this is a test for select the first row in the result set");
					l1.setBounds(100, 300 * i, 200, 30);
					// accept.setPreferredSize(new Dimension(100,50));
					l1.setForeground(Color.blue);
					l1.setFont(new Font("Serif", Font.BOLD, 40));
					// accept.setForeground(Color.blue);
					// accept.setFont(new Font("Serif", Font.BOLD, 40));
					// l1.setBounds(80, 70, 200, 30);
					// frame1.add(accept);
					frame1.add(l1);
					i++;

				}
				frame1.setVisible(true);
				// for (int i = 1; i<rs.getRow();i++){
				// JLabel l1 = new JLabel(rs.getString(i));
				// System.out.println(l1.getText());
				// System.out.println("this is a test for select the first row
				// in
				// the result set");
				// l1.setBounds(100*i, 100*i, 200, 30);
				// frame1.add(l1);
				// frame1.setVisible(true);
				// }
			}
		} catch (SQLException var13) {
			var13.printStackTrace();
			JOptionPane.showMessageDialog((Component) null, "view friend failed");
		}
	}

}
