import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ViewOtherProfileListner implements ActionListener {
	private JFrame frame;
	private Connection dbc;
	static JTextField tf1;
	static JLabel uname;
	static JLabel l3;
	JButton enterButton; 
	public ViewOtherProfileListner(JFrame frame1, Connection dbc) {
		this.frame = frame1;
		this.dbc = dbc;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame = new JFrame();
		frame.setSize(1000, 1000);
		//This is the panel that's going to change when you click the link
		JPanel changingPanel = new MainPagePosts(dbc);
		uname = new JLabel("user name: ");
		uname.setForeground(Color.blue);
		uname.setFont(new Font("Serif", Font.BOLD, 20));
		uname.setBounds(180, 170, 200, 30);
		
		
		tf1 = new JTextField();
		tf1.setBounds(450, 170, 200, 30);
		
		

		
		enterButton = new JButton("Enter!");
		enterButton.setForeground(Color.MAGENTA);
		enterButton.setFont(new Font("Serif", Font.BOLD, 30));
		enterButton.setBounds(300,250,200,30);
		
		frame.add(enterButton);
		frame.add(uname);
		frame.add(tf1);
		enterButton.addActionListener(new ViewProfileListner(tf1,frame, dbc,false));
		frame.add(changingPanel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
