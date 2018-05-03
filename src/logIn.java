/**
 * 
 * Creates a log in frame
 *
 * @author Olivia Zhou.
 *         Created Apr 26, 2018.
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
 
public class logIn extends JFrame implements ActionListener{
 JLabel l1, l2, l3;
 JTextField tf1;
 JButton btn1;
 JPasswordField p1;
 logIn() {
  JFrame frame = new JFrame("Login Form");
  l1 = new JLabel("Login Form");
  l1.setForeground(Color.blue);
  l1.setFont(new Font("Serif", Font.BOLD, 20));
 
  l2 = new JLabel("Username");
  l3 = new JLabel("Password");
  tf1 = new JTextField();
  p1 = new JPasswordField();
  btn1 = new JButton("Login");
 
  l1.setBounds(100, 30, 400, 30);
  l2.setBounds(80, 70, 200, 30);
  l3.setBounds(80, 110, 200, 30);
  tf1.setBounds(300, 70, 200, 30);
  p1.setBounds(300, 110, 200, 30);
  btn1.setBounds(150, 160, 100, 30);
 
  frame.add(l1);
  frame.add(l2);
  frame.add(tf1);
  frame.add(l3);
  frame.add(p1);
  frame.add(btn1);
 
  frame.setSize(400, 400);
  frame.setLayout(null);
  frame.setVisible(true);
 }
// public void actionPerformed1(ActionEvent ae)
// {
//   String uname = l1.getText();
//   String pass = p1.getText();
//   if(uname.equals("sudhir@oodlesTech") && pass.equals("abc@123"))
//   {
//      Welcome wel = new Welcome();
//      wel.setVisible(true);
//      JLabel label = new JLabel("Welcome:"+uname);
//      wel.getContentPane().add(label);
//    }
//    else
//    {
//      JOptionPane.showMessageDialog(this,"Incorrect login or password",
//      "Error",JOptionPane.ERROR_MESSAGE); 
//    }
//  }
// }
 public static void main(String[] args) {
  
	 new logIn();
 }
@Override
public void actionPerformed(ActionEvent arg0) {
	String uname = l1.getText();
    String pass = p1.getText();
    if(true)
      {
//         MainClass wel = new MainClass();
//         wel.setVisible(true);
//         JLabel label = new JLabel("Welcome:"+uname);
//         wel.getContentPane().add(label);
//    	return true;
       }
       else
       {
         JOptionPane.showMessageDialog(this,"Incorrect login or password",
         "Error",JOptionPane.ERROR_MESSAGE); 
       }
	
}
}
