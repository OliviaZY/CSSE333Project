import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ListOfRecommendationUserListener {

	private Connection dbc;
	private String initialN;
	private String searchedN;
	private String selected;

	private JTextField userN;
	
	JRadioButton college;
	JRadioButton location;
	JRadioButton workField;
	JRadioButton favBook;
	JRadioButton favMusic;
	JRadioButton favAni;
	JRadioButton favExer;


	public ListOfRecommendationUserListener(String selected, JTextField userN, Connection dbc) {
		this.userN = userN;
		this.selected = selected;
		this.dbc = dbc;
	}

	public void actionPerformed(ActionEvent e) {
		JFrame frame1 = new JFrame();
		frame1.setSize(1000, 1000);
		
		try {
			CallableStatement cs = this.dbc.prepareCall("call FriendRecommendation(?,?,?,?)");
			cs.setString(1, userN.getText());
			cs.setString(2, selected);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.registerOutParameter(4, java.sql.Types.INTEGER);
			
			cs.execute();
			int ret = cs.getInt(3);
			int returnedInt = cs.getInt(4);
			JButton[] buttonLinksAcc = new JButton[ret];
			JButton[] buttonLinksDec = new JButton[ret];
			System.out.println(cs);
			ResultSet rs = cs.executeQuery();
			System.out.println("returned value is " + returnedInt);
			
			for (int i =0 ; i<returnedInt;i++){
				buttonLinksAcc[i]=new JButton("view his/her profile");
				buttonLinksAcc[i].setForeground(Color.blue);
				buttonLinksAcc[i].setFont(new Font("Serif", Font.BOLD, 20));
				buttonLinksAcc[i].setBounds(500, 400+30*i, 250, 30);
				frame1.add(buttonLinksAcc[i]);
				
				
//				buttonLinksDec[i]=new JButton("decline");
//				buttonLinksDec[i].setForeground(Color.blue);
//				buttonLinksDec[i].setFont(new Font("Serif", Font.BOLD, 20));
//				buttonLinksDec[i].setBounds(850, 400+30*i, 100, 30);
//				frame1.add(buttonLinksDec[i]);
				
			}
			int i = 0;
			while (rs.next()) {
				
				System.out.println(i);
				JTextField l1 = new JTextField(rs.getString(1));
//				buttonLinksDec[i].addActionListener(new declineFriendRequestListner(username.getText(),l1.getText(), frame1,dbc));
				buttonLinksAcc[i].addActionListener(new ViewProfileListner(userN.getText(),l1,frame1,dbc,false));

//				System.out.println("this is a test for select the first row in the result set");
				l1.setBounds(100, 300*i, 200, 30);
				l1.setForeground(Color.blue);
				l1.setFont(new Font("Serif", Font.BOLD, 40));
				frame1.add(l1);
				i++;
				
				
			}
			frame1.setVisible(true);

		} catch (SQLException var13) {
			var13.printStackTrace();
			JOptionPane.showMessageDialog((Component) null, "Login Failed");
		}
	}
//		System.out.println("test for calling the returing list of recommendation user listener");
//		try {
//			CallableStatement cs = this.dbc.prepareCall("call FriendRecommendation(?,?,?)");
////			cs.setString(1, initialN);
////			cs.setString(2, searchedN);
//			cs.setString(3, selected);
//			cs.registerOutParameter(4, java.sql.Types.INTEGER);
//			System.out.println("haha");
//			System.out.println(cs);
//			cs.execute();
//			int ret = cs.getInt(4);
//			if (ret == 1) {
//				JOptionPane.showMessageDialog((Component) null, "ERROR: user name cannot be empty");
//			} else if (ret == 2) {
//				JOptionPane.showMessageDialog((Component) null, "ERROR: you are already friend with this person.");
//			} else {
//				JOptionPane.showMessageDialog((Component) null, "SUCCESSFUL added this user as your friend! congrats");
//			}
//		} catch (SQLException var13) {
//			var13.printStackTrace();
//			JOptionPane.showMessageDialog((Component) null, "Login Failed");
//		}
//	}

}
