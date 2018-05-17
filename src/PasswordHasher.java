import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;
/**
 * 
 * Hashes and deHashes Password
 *
 * @author garretje and heavily inspired by the Lab7 code by Dr. Stouder
 *         Created May 16, 2018.
 */
	public class PasswordHasher {
		private static final Random RANDOM = new SecureRandom();
		private static final Base64.Encoder enc = Base64.getEncoder();
		private static final Base64.Decoder dec = Base64.getDecoder();
		private Connection conn = null;

		public PasswordHasher(Connection c) {
			conn = c;
		}

		
		
		public boolean login(String username, String password) {
			if (username == null || password == null || username.equals("") || password.equals(""))
				return false;
			CallableStatement statement = null;
			try {
				statement = conn.prepareCall("{Call Login2(?)}");
				statement.setString(1, username);
				ResultSet rs = statement.executeQuery();
				int saltIndex = rs.findColumn("PassSalt");
				int hashIndex = rs.findColumn("PassHash");
				rs.next();
				byte[] salt = rs.getBytes(saltIndex);
				String hash = rs.getString(hashIndex);
				if (salt == null && hash.equals(password)){
					return true;
				}
				if (hashPassword(salt,password).equals(hash)){
					return true; 
				}
				return false;
			} catch (SQLException exception) {
				 return false;
			}
			
		}

		public boolean register(String username, String password) {
			byte[] salt = getNewSalt();
			String hashed = hashPassword(salt, password);
			try {
				if (username.equals(""))
					username = null;
				if (password.equals(""))
					throw new SQLException();
				CallableStatement cs = conn.prepareCall("{Call Register2(?,?,?,?)}");
				System.out.println(2);
				cs.setString(1,username);
				System.out.println(3);
				cs.setBytes(3, salt);
				System.out.println(4);
				cs.setString(2, hashed);
				System.out.println(5);
				cs.registerOutParameter(4, Types.INTEGER);
				System.out.println(6);
				cs.execute();
				System.out.println(7);
				if (cs.getInt(4) == 1)
					return true;
				else if (cs.getInt(4) == 0)
					JOptionPane.showMessageDialog(null, "User not registered,please try again");
				else if (cs.getInt(4) == 2)
					JOptionPane.showMessageDialog(null, "Please enter both a username and a password");
				else if(cs.getInt(4) == 3)
					JOptionPane.showMessageDialog(null, "Try a new username, this one's taken");
				return false;
			} catch (SQLException exception) {
				 JOptionPane.showMessageDialog(null, "User not registered");
				 return false;
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
