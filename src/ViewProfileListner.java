import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	private String uname;
	public ViewProfileListner(String text, JFrame frame1, Connection dbc) {
		this.frame = frame;
		this.dbc = dbc;
		this.uname = text;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
//		frame.repaint();
//	    frame.revalidate();
	    
		JFrame frame1 = new JFrame();
		frame1.setSize(1000, 1000);
		//This is the panel that's going to change when you click the link
		JPanel changingPanel = new MainPagePosts(dbc);
		
		
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
		
			l2 = new JLabel("First Name: ");
		  l3 = new JLabel("Last Name: ");
		  l4 = new JLabel("Date of Birth: ");
		  l5 = new JLabel("State: ");
		  l6 = new JLabel("College That You Graduated From: ");
		  l7 = new JLabel("Profession: ");
		  l8 = new JLabel("Field: ");
		  
		  
//		  l9 = new JLabel()

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
		  
//		  l12.setForeground(Color.BLACK);
//		  l12.setFont(new Font("Arial", Font.BOLD, 16));
//		  l13.setForeground(Color.BLACK);
//		  l13.setFont(new Font("Arial", Font.BOLD, 16));
//		  l14.setForeground(Color.BLACK);
//		  l14.setFont(new Font("Arial", Font.BOLD, 16));
//		  l15.setForeground(Color.BLACK);
//		  l15.setFont(new Font("Arial", Font.BOLD, 16));
//		  l9.setForeground(Color.BLACK);
//		  l9.setFont(new Font("Arial", Font.BOLD, 16));
//		  l10.setForeground(Color.BLACK);
//		  l10.setFont(new Font("Arial", Font.BOLD, 16));
//		  l11.setForeground(Color.BLACK);
//		  l11.setFont(new Font("Arial", Font.BOLD, 16));
		  		 
		  l2.setBounds(80, 70, 200, 30);
		  l3.setBounds(80, 110, 200, 30);
		  l4.setBounds(80, 150, 200, 30);
		  l5.setBounds(80, 190, 200, 30);
		  l6.setBounds(80, 230, 200, 30);
		  l7.setBounds(80, 270, 200, 30);
		  l8.setBounds(80, 310, 200, 30);

		  frame1.add(l2);
		  frame1.add(l3);
		  frame1.add(l4);
		  frame1.add(l5);
		  frame1.add(l6);
		  frame1.add(l7);
		  frame1.add(l8);
		
		
//		links.add(closeConnection);
//		for (JButton j:buttonLinks){
//			j.addActionListener(new LinksListener(j.getText(),frame1,dbc));
//			links.add(j);
//		}
		frame1.add(changingPanel, BorderLayout.CENTER);
//		frame1.add(links, BorderLayout.WEST);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setVisible(true);
	}

}
