import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class addProfileListner implements ActionListener {
	static JLabel l1;
	static JLabel l2;
	static JLabel l3;
	static JLabel l4;
	static JLabel l5;
	static JLabel l6;
	static JLabel l7;
	static JLabel l8;
	
	static JTextField tf1;
	static JTextField tf2;
	static JTextField tf3;
	static JTextField tf4;
	static JTextField tf5;
	static JTextField tf6;
	static JTextField tf7;
	static JTextField tf8;
	
	static String uname;
	
	static JButton btn1;
	static JButton btn2;
	private Connection dbc;
	JFrame frame;
	public addProfileListner(String uname, JFrame frame1, Connection dbc) {
		this.dbc = dbc;
		this.frame = frame1;
		this.uname = uname;
	}
	public void actionPerformed(ActionEvent arg0) {
		
		frame.repaint();
	    frame.revalidate();
	    
	    
		JFrame frame = new JFrame("Let's set up the profile to meet more people :)");
		  l1 = new JLabel("Let's set up the profile to meet more people :)");
		  l1.setForeground(Color.MAGENTA);
		  l1.setFont(new Font("Serif", Font.BOLD, 25));
		 
		  l2 = new JLabel("First Name");
		  l3 = new JLabel("Last Name");
		  l4 = new JLabel("Date of Birth");
		  l5 = new JLabel("State");
		  l6 = new JLabel("College That You Graduated From");
		  l7 = new JLabel("Profession");
		  l8 = new JLabel("Field");
		  
		  tf1 = new JTextField();
		  tf2 = new JTextField();
		  tf3 = new JTextField("yyyy-mm-dd");
		  tf4 = new JTextField();
		  tf5 = new JTextField();
		  tf6 = new JTextField();
		  tf7 = new JTextField();
		  tf8 = new JTextField();
		  btn1 = new JButton("Save!!!");
		  btn2 = new JButton("Cancel, I will edit it later.");
		  
		 
		  l1.setBounds(200, 30, 400, 30);
		  l2.setBounds(80, 70, 200, 30);
		  l3.setBounds(80, 110, 200, 30);
		  l4.setBounds(80, 150, 200, 30);
		  l5.setBounds(80, 190, 200, 30);
		  l6.setBounds(80, 230, 200, 30);
		  l7.setBounds(80, 270, 200, 30);
		  l8.setBounds(80, 310, 200, 30);
		  
		  tf1.setBounds(300, 70, 200, 30);
		  tf2.setBounds(300, 110, 200, 30);
		  tf3.setBounds(300, 150, 200, 30);
		  tf4.setBounds(300, 190, 200, 30);
		  tf5.setBounds(300, 230, 200, 30);
		  tf6.setBounds(300, 270, 200, 30);
		  tf7.setBounds(300, 310, 200, 30);
//		  tf8.setBounds(300, 110, 200, 30);
		  
		  btn1.setBounds(150, 360, 100, 30);
		  btn2.setBounds(300, 360, 300, 30);
		 
		  frame.add(l1);
		  frame.add(l2);
		  frame.add(l3);
		  frame.add(l4);
		  frame.add(l5);
		  frame.add(l6);
		  frame.add(l7);
		  frame.add(l8);
		  
		  frame.add(tf1);
		  frame.add(tf2);		  
		  frame.add(tf3);
		  frame.add(tf4);
		  frame.add(tf5);
		  frame.add(tf6);
		  frame.add(tf7);
		  
		  frame.add(btn1);
		  frame.add(btn2);
		 
		  frame.setSize(800, 800);
		  frame.setLayout(null);
		  frame.setVisible(true);
		  btn1.addActionListener(new SaveProfileListener(tf1, tf2, tf3, tf4, tf5, tf6, tf7,uname, frame,dbc));
	}

}
