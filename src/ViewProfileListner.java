import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ViewProfileListner implements ActionListener {
	private JFrame frame;
	private Connection dbc;
//	static JTextField tf1;
	static JLabel l2;
	static JLabel l3;
	static JLabel l4;
	static JLabel l5;
	static JLabel l6;
	static JLabel l7;
	static JLabel l8;
	
	static JLabel l9;
	static JLabel l10;
	static JLabel l11;
	static JLabel l12;
	static JLabel l13;
	static JLabel l14;
	static JLabel l15;
	
	static JTextField initalUName;
	static JTextField searchedUName;
	boolean selfOrOther;
	
//	private String uname;
	public ViewProfileListner(JTextField initalUName, JTextField searchedUName, JFrame frame1, Connection dbc, boolean selfOrOther) {
		this.frame = frame;
		this.dbc = dbc;
		this.initalUName = initalUName;
		this.selfOrOther = selfOrOther;
		this.searchedUName = searchedUName;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
//		frame.repaint();
//	    frame.revalidate();

		
		//Link buttons on the left side of the screen
		Box links = Box.createVerticalBox();
		JButton[] buttonLinks = new JButton[5];
		buttonLinks[0] = new JButton("Profile");
		buttonLinks[1] = new JButton("Friends");//TODO: add code in action listener for this
		buttonLinks[2] = new JButton("Reminders");//TODO: add code in action listener for this
		buttonLinks[3] = new JButton("Events");
		buttonLinks[4] = new JButton("Interests");
		JButton closeConnection = new JButton("Close Connection");//THis should probably be changed to something automatic
//		closeConnection.addActionListener(new ConnectionCloser(dbc));//but it's 3AM so that's beyond my abilites rn
		
		  

		try {
			CallableStatement cs = this.dbc.prepareCall("call viewProfile(?,?,?,?,?,?,?,?,?)");
			if(selfOrOther){
				cs.setString(1, initalUName.getText());
			}else{
				cs.setString(1, searchedUName.getText());
			}
			
			cs.registerOutParameter(2, java.sql.Types.DATE);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.registerOutParameter(7, java.sql.Types.VARCHAR);
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			cs.registerOutParameter(9, java.sql.Types.INTEGER);
			System.out.println(cs);
			cs.execute();
			int ret = cs.getInt(9);
			if (ret == 1) {
				JOptionPane.showMessageDialog((Component)null, "ERROR: user name cannot be empty");
			} else if (ret == 2) {
				JOptionPane.showMessageDialog((Component)null, "ERROR: incorrect username.");
			}  else {
			    
				JFrame frame1 = new JFrame();
				frame1.setSize(1000, 1000);
				//This is the panel that's going to change when you click the link
				JPanel changingPanel = new MainPagePosts(dbc);
				
				l2 = new JLabel("First Name: ");
				  l3 = new JLabel("Last Name: ");
				  l4 = new JLabel("Date of Birth: ");
				  l5 = new JLabel("State: ");
				  l6 = new JLabel("College: ");
				  l7 = new JLabel("Profession: ");
				  l8 = new JLabel("Field: ");
				  JButton editProdile = new JButton("edit your profile");
				  JButton addFriend = new JButton("add this user as your friend!!");


				  
				  
//				  l9 = new JLabel()

				  l2.setForeground(Color.blue);
				  l2.setFont(new Font("Serif", Font.BOLD, 20));
				  l3.setForeground(Color.blue);
				  l3.setFont(new Font("Serif", Font.BOLD, 20));
				  l4.setForeground(Color.blue);
				  l4.setFont(new Font("Serif", Font.BOLD, 20));
				  l5.setForeground(Color.blue);
				  l5.setFont(new Font("Serif", Font.BOLD, 20));
				  l6.setForeground(Color.blue);
				  l6.setFont(new Font("Serif", Font.BOLD, 20));
				  l7.setForeground(Color.blue);
				  l7.setFont(new Font("Serif", Font.BOLD, 20));
				  l8.setForeground(Color.blue);
				  l8.setFont(new Font("Serif", Font.BOLD, 20));
				  editProdile.setForeground(Color.CYAN);
				  editProdile.setFont(new Font("Serif", Font.BOLD, 20));
				  addFriend.setForeground(Color.MAGENTA);
				  addFriend.setFont(new Font("Serif", Font.BOLD, 25));

				  l2.setBounds(80, 70, 200, 30);
				  l3.setBounds(80, 110, 200, 30);
				  l4.setBounds(80, 150, 200, 30);
				  l5.setBounds(80, 190, 200, 30);
				  l6.setBounds(80, 230, 200, 30);
				  l7.setBounds(80, 270, 200, 30);
				  l8.setBounds(80, 310, 200, 30);
				  editProdile.setBounds(600, 30,300,40);
				  addFriend.setBounds(150, 380, 300, 40);

				  frame1.add(l2);
				  frame1.add(l3);
				  frame1.add(l4);
				  frame1.add(l5);
				  frame1.add(l6);
				  frame1.add(l7);
				  frame1.add(l8);
				  if(selfOrOther){
					  frame1.add(editProdile);
				  }else{
					  frame1.add(addFriend);
				  }
				  
				if (cs.getString(3)!=null){
					System.out.println(cs.getString(3));
					l10 = new JLabel(cs.getString(3));
				}else{
					l10 = new JLabel("       ");
				}

				if (cs.getString(4)!=null) {
					l11 = new JLabel(cs.getString(4));
				}else{
					l11 = new JLabel("       ");
				}

				if (cs.getString(5)!=null) {
					l12 = new JLabel(cs.getString(5));
				}else{
					l12 = new JLabel("       ");
				}
				if (cs.getString(6)!=null) {
					l13 = new JLabel(cs.getString(6));
				}else{
					l13 = new JLabel("       ");
				}
				if (cs.getString(7)!=null) {
					l14 = new JLabel(cs.getString(7));
				}else{
					l14 = new JLabel("       ");
				}
				if (cs.getString(8)!=null) {
					l15 = new JLabel(cs.getString(8));
				}else{
					l15 = new JLabel("       ");
				}

				if (cs.getDate(2)!=null) {
					l9 = new JLabel(cs.getDate(2).toString());
				}else{
					l9 = new JLabel("       ");
				}


				l12.setForeground(Color.BLACK);
			    l12.setFont(new Font("Arial", Font.BOLD, 16));
			    l13.setForeground(Color.BLACK);
			    l13.setFont(new Font("Arial", Font.BOLD, 16));
			    l14.setForeground(Color.BLACK);
			    l14.setFont(new Font("Arial", Font.BOLD, 16));
			    l15.setForeground(Color.BLACK);
			    l15.setFont(new Font("Arial", Font.BOLD, 16));
			    l9.setForeground(Color.BLACK);
			    l9.setFont(new Font("Arial", Font.BOLD, 16));
			    l10.setForeground(Color.BLACK);
			    l10.setFont(new Font("Arial", Font.BOLD, 16));
			    l11.setForeground(Color.BLACK);
			    l11.setFont(new Font("Arial", Font.BOLD, 16));
			    


				l9.setBounds(300, 70, 200, 30);
				l10.setBounds(300, 110, 200, 30);
				l11.setBounds(300, 150, 200, 30);
				l12.setBounds(300, 190, 200, 30);
				l13.setBounds(300, 230, 200, 30);
				l14.setBounds(300, 270, 200, 30);
				l15.setBounds(300, 310, 200, 30);

				frame1.add(l9);
				frame1.add(l10);
				frame1.add(l11);
				frame1.add(l12);
				frame1.add(l13);
				frame1.add(l14);
				frame1.add(l15);
//				frame1.add(l16);
				if (this.selfOrOther){
					editProdile.addActionListener(new addProfileListner(initalUName.getText(), frame1,dbc));
				}else{
					System.out.println(initalUName.getText());
					System.out.println(searchedUName.getText());
					addFriend.addActionListener(new addFriendListener(initalUName.getText(),searchedUName.getText(),frame1,dbc));
				}
				frame1.add(changingPanel, BorderLayout.CENTER);
//				frame1.add(links, BorderLayout.WEST);
				frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame1.setVisible(true);

			}

		}catch (SQLException var13){
			var13.printStackTrace();
			JOptionPane.showMessageDialog((Component)null, "Login Failed");
		}



//		links.add(closeConnection);
//		for (JButton j:buttonLinks){
//			j.addActionListener(new LinksListener(j.getText(),frame1,dbc));
//			links.add(j);
//		}
		
	}

}
