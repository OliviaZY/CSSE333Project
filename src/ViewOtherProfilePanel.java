import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ViewOtherProfilePanel extends JPanel{
	Connection c = null;
	String username;
	private JFrame frame;
//	JFrame f;
	JTextField searchedUName;
	JTextField initialUName;
	String uername;
	JLabel uname;
	JButton enterButton;
	public ViewOtherProfilePanel(Connection c, JFrame frame, String username){
		this.c = c;
		this.username = username;
		this.frame = frame;
		this.uername = username;
//		this.f = f;
//		
//		
//		f = new JFrame();
//		f.setSize(1000, 1000);
		
		uname = new JLabel("user name: ");
		uname.setForeground(Color.blue);
		uname.setFont(new Font("Serif", Font.BOLD, 20));
		uname.setBounds(100, 270, 200, 30);

		searchedUName = new JTextField(8);
		searchedUName.setFont(searchedUName.getFont().deriveFont(50f));
		searchedUName.setForeground(Color.blue);
		searchedUName.setFont(new Font("Serif", Font.BOLD, 20));
		System.out.println("test for enter button for the view other profile");
		searchedUName.setBounds(350, 270, 200, 30);
		System.out.println(searchedUName.getBounds());
		
		enterButton = new JButton("Enter!");
		enterButton.setForeground(Color.MAGENTA);
		enterButton.setFont(new Font("Serif", Font.BOLD, 30));
		enterButton.setBounds(300,450,200,30);
		
		this.add(uname);
		this.add(searchedUName);
		this.add(enterButton);
		
		enterButton.addActionListener(new ViewProfileListner(this.username,this.searchedUName,frame,c,false));
//		this.add(changingPanel, BorderLayout.CENTER);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}
