import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.Connection;
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

public class RegisterListener implements ActionListener {

	private JFrame frame;
	private Connection dbc;
	private String password;
	private String username;
	static JTextField tf1;
	static JPasswordField p1;
	private static final Random RANDOM = new SecureRandom();
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();

	public RegisterListener(JTextField tf1, JPasswordField p1, JFrame frame, Connection con) {
		this.frame = frame;
		this.dbc = con;
		this.tf1 = tf1;
		this.p1 = p1;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		CallableStatement cs = null;
		try {
			cs = dbc.prepareCall("call Register(?,?,?)");
			cs.setString(1, tf1.getText());
			cs.setString(2, p1.getText());
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			// cs.registerOutParameter(1, java.sql.Types.INTEGER);
			System.out.println(cs);
			cs.execute();
			ResultSet rs = cs.getResultSet();
			// ResultSet rs = cs.executeQuery();
			if (rs.next()) {
				if (rs.getInt(3) == 1) {
					JOptionPane.showMessageDialog(null, "ERROR: user name cannot be empty");
				} else if (rs.getInt(3) == 2) {
					JOptionPane.showMessageDialog(null, "ERROR: password cannot be empty.");
				} else if (rs.getInt(3) == 3) {
					JOptionPane.showMessageDialog(null, "ERROR: user already existed.");
				} else {
					frame.repaint();
					frame.revalidate();

					JFrame frame1 = new JFrame();
					frame1.setSize(1000, 1000);
					// This is the panel that's going to change when you click
					// the link
					JPanel changingPanel = new MainPagePosts(dbc);

					// Link buttons on the left side of the screen
					Box links = Box.createVerticalBox();
					JButton[] buttonLinks = new JButton[5];
					buttonLinks[0] = new JButton("Profile");
					buttonLinks[0].addActionListener(new addProfileListner(tf1.getText(), frame1, dbc));
					buttonLinks[1] = new JButton("Friends");// TODO: add code in
															// action listener
															// for this
					buttonLinks[2] = new JButton("Reminders");// TODO: add code
																// in action
																// listener for
																// this
					buttonLinks[3] = new JButton("Events");
					buttonLinks[4] = new JButton("Interests");
					JButton closeConnection = new JButton("Close Connection");// THis
																				// should
																				// probably
																				// be
																				// changed
																				// to
																				// something
																				// automatic
					// closeConnection.addActionListener(new
					// ConnectionCloser(dbc));//but it's 3AM so that's beyond my
					// abilites rn
					links.add(closeConnection);
					for (JButton j : buttonLinks) {
						j.addActionListener(new LinksListener(j.getText(), frame1, dbc));
						links.add(j);
					}
					frame1.add(changingPanel, BorderLayout.CENTER);
					frame1.add(links, BorderLayout.WEST);
					frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame1.setVisible(true);
				}
			}
		} catch (

		SQLException e) {
			e.printStackTrace();
		}
		// try{
		// stmt = this.dbc.prepareStatement(query);
		// byte[] salt = this.getNewSalt();
		// password = this.hashPassword(salt, password);
		// cs = this.dbc.prepareCall("{?=call Register("test2", null, "123")}");
		// cs.setString(2, tf1.getText());
		// System.out.println(tf1.getText());
		// cs.setString(4, p1.getText());
		// cs.setBytes(3, null);
		// cs.registerOutParameter(1, java.sql.Types.INTEGER);
		// System.out.println(query);
		// stmt.execute();
		// System.out.println("i am here");
		// if(cs.getInt(1) == 1){
		// JOptionPane.showMessageDialog(null, "Username cannot be null or
		// empty");
		// }
		// if(cs.getInt(1) == 2){
		// JOptionPane.showMessageDialog(null, "ERROR: PasswordSalt cannot be
		// null or empty.");
		// }
		// if(cs.getInt(1) == 3){
		// JOptionPane.showMessageDialog(null, "ERROR:PasswordHash cannot be
		// null or empty.");
		// }
		// if(cs.getInt(1) == 4){
		// JOptionPane.showMessageDialog(null, "ERROR: Username already
		// exists.");
		// }

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
