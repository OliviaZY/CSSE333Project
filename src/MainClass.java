import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MainClass {
	static JLabel l1;
	static JLabel l2;
	static JLabel l3;
	static JTextField tf1;
	static JButton btn1;
	static JButton btn2;
	static JPasswordField p1;
	static DatabaseConnecter dbc = new DatabaseConnecter();
	
		 public static void logIn() {	
		  
		  JFrame frame = new JFrame("Login Form");
		  l1 = new JLabel("Login Form");
		  l1.setForeground(Color.blue);
		  l1.setFont(new Font("Serif", Font.BOLD, 20));
		 
		  l2 = new JLabel("Username");
		  l3 = new JLabel("Password");
		  tf1 = new JTextField();
		  p1 = new JPasswordField();
		  btn1 = new JButton("Login");
		  btn2 = new JButton("Register");
		  
		 
		  l1.setBounds(100, 30, 400, 30);
		  l2.setBounds(80, 70, 200, 30);
		  l3.setBounds(80, 110, 200, 30);
		  tf1.setBounds(300, 70, 200, 30);
		  p1.setBounds(300, 110, 200, 30);
		  btn1.setBounds(150, 160, 100, 30);
		  btn2.setBounds(300, 160, 100, 30);
		 
		  frame.add(l1);
		  frame.add(l2);
		  frame.add(tf1);
		  frame.add(l3);
		  frame.add(p1);
		  frame.add(btn1);
		  frame.add(btn2);
		 
		  frame.setSize(800, 800);
		  frame.setLayout(null);
		  frame.setVisible(true);
		  String uname = tf1.getText();
		  String pass = p1.getText();
		  btn1.addActionListener(new LogInListener(tf1, p1, frame,dbc.getConnection()));
		  btn2.addActionListener(new RegisterListener(tf1, p1, frame,dbc.getConnection()));
		  System.out.println(uname);

		 }

	public static void main(String[] args) {
		//Establishes the database connection
		logIn();
	}
	
	
}
		
		
		
	


