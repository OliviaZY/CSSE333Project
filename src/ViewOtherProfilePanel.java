import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ViewOtherProfilePanel extends JPanel{
	Connection c = null;
	String username;
//	JFrame f;
	JTextField searchedUName;
	JTextField initialUName;
	JLabel uname;
	JButton enterButton;
	public ViewOtherProfilePanel(Connection c, String username){
		this.c = c;
		this.username = username;
//		this.f = f;
//		
//		
//		f = new JFrame();
//		f.setSize(1000, 1000);
		
		uname = new JLabel("user name: ");
		uname.setForeground(Color.blue);
		uname.setFont(new Font("Serif", Font.BOLD, 20));
		uname.setBounds(180, 170, 200, 30);
		
		
		searchedUName = new JTextField();
		searchedUName.setBounds(450, 170, 200, 30);
		
		enterButton = new JButton("Enter!");
		enterButton.setForeground(Color.MAGENTA);
		enterButton.setFont(new Font("Serif", Font.BOLD, 30));
		enterButton.setBounds(300,250,200,30);
		
		this.add(enterButton);
		this.add(uname);
		this.add(searchedUName);
		enterButton.addActionListener(new  ViewOtherProfileListner(this.username,c));
//		this.add(changingPanel, BorderLayout.CENTER);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}
