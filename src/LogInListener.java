import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LogInListener implements ActionListener {

	private JFrame frame;
	private Connection dbc;
	private String password;
	private String username;
	private static final Random RANDOM = new SecureRandom();
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();
	static JTextField tf1;
	static JPasswordField p1;
	public LogInListener(JTextField tf1, JPasswordField p1, JFrame frame, Connection con){
		this.frame = frame;
		this.dbc = con;
		this.tf1 = tf1;
		this.p1 = p1;
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Removes the panel that was there before
		for(Component c : frame.getContentPane().getComponents()){
            if(c instanceof JPanel){
                frame.remove(c);
            }
		}
		frame.repaint();
	    frame.revalidate();

	    String query = "select * from person where UserName=?;";
		PreparedStatement stmt;
		try {
			stmt = this.dbc.prepareStatement(query);
			stmt.setString(1, tf1.getText());
			username = tf1.getText();
			System.out.println(tf1.getText());
			System.out.println(stmt);
			ResultSet rs = stmt.executeQuery();
//			System.out.println(1);
			if(rs.next()){
				byte[] salt = rs.getBytes(2);
//				System.out.println(rs.getString(1));
//				System.out.println(rs.getString(3));
//				System.out.println(salt);
//				String storedHash = rs.getString(3);
//				String StoredPassword = this.hashPassword(salt, getStringFromBytes(salt));
				if(!this.p1.getText().equals(rs.getString(3))){
//				if (!this.hashPassword(salt, password).equals(storedHash)){
					JOptionPane.showMessageDialog(null, "Incorrect username or password. please try again");
				}else{
					JFrame frame1 = new JFrame();
					frame1.setSize(1000, 1000);
					//This is the panel that's going to change when you click the link
					JPanel changingPanel = new MainPagePosts(dbc,this.username);
					
					//Link buttons on the left side of the screen
					Box links = Box.createVerticalBox();
					JButton[] buttonLinks = new JButton[6];
					buttonLinks[0] = new JButton("Profile");
					buttonLinks[1] = new JButton("Friends");//TODO: add code in action listener for this
					buttonLinks[2] = new JButton("Reminders");//TODO: add code in action listener for this
					buttonLinks[3] = new JButton("Events");
					buttonLinks[4] = new JButton("Interests");
					buttonLinks[5] = new JButton("create posts");
					JButton closeConnection = new JButton("Close Connection");//THis should probably be changed to something automatic
//					closeConnection.addActionListener(new ConnectionCloser(dbc));//but it's 3AM so that's beyond my abilites rn
					links.add(closeConnection);
					for (JButton j:buttonLinks){
						if(j.getActionCommand().equals("create posts")){
							j.addActionListener(new LinksListener(j.getText(),frame1,username,dbc));
						}else{
							j.addActionListener(new LinksListener(j.getText(),frame1,dbc));

						}
						links.add(j);
					}
					frame1.add(changingPanel, BorderLayout.CENTER);
					frame1.add(links, BorderLayout.WEST);
					frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame1.setVisible(true);
				}
				
			}else{
				JOptionPane.showMessageDialog(null, "Incorrect username or password. please try again");
			}
			
		} catch (SQLException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
			JOptionPane.showMessageDialog(null, "Login Failed");
		}
//		TODO: test if the user & password stored in the database
//        if(true)
//	      {
//	        JFrame frame1 = new JFrame();
//			frame1.setSize(1000, 1000);
//			//This is the panel that's going to change when you click the link
//			JPanel changingPanel = new MainPagePosts();
//			
//			//Link buttons on the left side of the screen
//			Box links = Box.createVerticalBox();
//			JButton[] buttonLinks = new JButton[5];
//			buttonLinks[0] = new JButton("Profile");
//			buttonLinks[1] = new JButton("Friends");//TODO: add code in action listener for this
//			buttonLinks[2] = new JButton("Reminders");//TODO: add code in action listener for this
//			buttonLinks[3] = new JButton("Events");
//			buttonLinks[4] = new JButton("Interests");
//			JButton closeConnection = new JButton("Close Connection");//THis should probably be changed to something automatic
////			closeConnection.addActionListener(new ConnectionCloser(dbc));//but it's 3AM so that's beyond my abilites rn
//			links.add(closeConnection);
//			for (JButton j:buttonLinks){
//				j.addActionListener(new LinksListener(j.getText(),frame1,dbc));
//				links.add(j);
//			}
//			frame1.add(changingPanel, BorderLayout.CENTER);
//			frame1.add(links, BorderLayout.WEST);
//			frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			frame1.setVisible(true);
//		}
//	       
//	       else
//	       {
//	         JOptionPane.showInputDialog("Incorrect login or password",
//	         "Error"); 
//	       }
}
	public byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}
	
	public String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}

	public String hashPassword(byte[] salt, String password) {

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		}
		return getStringFromBytes(hash);
	}

}
