import java.awt.Color;
import java.awt.Component;
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
	static JTextField searchedUName;
	static JTextField initialUName;
	static JLabel uname;
	static JLabel l3;
	JButton enterButton; 
	public ViewOtherProfileListner(JTextField initialUName, JFrame frame1, Connection dbc) {
		this.frame = frame1;
		this.dbc = dbc;
		this.initialUName = initialUName;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
//		JFrame frame1 = new JFrame();
//		frame1.setSize(1000, 1000);
		frame = new JFrame();
		frame.setSize(1000, 1000);
		frame.removeAll();
		
		for(Component c : frame.getContentPane().getComponents()){
            if(c instanceof JPanel){
                frame.remove(c);
            }
		}
		frame.revalidate();
		frame.repaint();
//		
		//This is the panel that's going to change when you click the link
		JPanel changingPanel = new MainPagePosts(dbc,initialUName.getText(),frame);
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
		
		frame.add(enterButton);
		frame.add(uname);
		frame.add(searchedUName);
		enterButton.addActionListener(new ViewProfileListner(initialUName,searchedUName,frame, dbc,false));
//		frame1.add(changingPanel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
