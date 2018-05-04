import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.Connection;
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

		CallableStatement cs = null;

		try {
			cs = this.dbc.prepareCall("call Login(?,?,?)");
			cs.setString(1, tf1.getText());
			cs.setString(2, p1.getText());
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			System.out.println(cs);
			cs.execute();
			int ret = cs.getInt(3);
			if (ret == 1) {
				JOptionPane.showMessageDialog((Component)null, "ERROR: user name cannot be empty");
			} else if (ret == 2) {
				JOptionPane.showMessageDialog((Component)null, "ERROR: password cannot be empty.");
			} else if (ret == 3) {
				JOptionPane.showMessageDialog((Component)null, "ERROR: incorrect user name.");
			} else if(ret == 4){
				JOptionPane.showMessageDialog((Component)null, "ERROR: incorrect password.");
			}else{
					JFrame frame1 = new JFrame();
					frame1.setSize(1000, 1000);
					//This is the panel that's going to change when you click the link
					JPanel changingPanel = new MainPagePosts(dbc);

					//Link buttons on the left side of the screen
					Box links = Box.createVerticalBox();
					JButton[] buttonLinks = new JButton[6];
					buttonLinks[0] = new JButton("Profile");
					buttonLinks[0].addActionListener(new ViewProfileListner(tf1,frame1,dbc,true));
					buttonLinks[1] = new JButton("Friends");//TODO: add code in action listener for this
					buttonLinks[2] = new JButton("Reminders");//TODO: add code in action listener for this
					buttonLinks[3] = new JButton("Events");
					buttonLinks[4] = new JButton("Interests");
					buttonLinks[5] = new JButton("view a user's info");
					buttonLinks[5].addActionListener(new ViewOtherProfileListner(frame1,dbc));
					JButton closeConnection = new JButton("Close Connection");//THis should probably be changed to something automatic
//					closeConnection.addActionListener(new ConnectionCloser(dbc));//but it's 3AM so that's beyond my abilites rn
					links.add(closeConnection);
					for (JButton j:buttonLinks){
						j.addActionListener(new LinksListener(j.getText(),frame1,dbc));
						links.add(j);
					}
					frame1.add(changingPanel, BorderLayout.CENTER);
					frame1.add(links, BorderLayout.WEST);
					frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame1.setVisible(true);
				}
				
			}catch (SQLException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
			JOptionPane.showMessageDialog(null, "Login Failed");
		}

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
