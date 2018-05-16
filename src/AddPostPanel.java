import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AddPostPanel extends JPanel{
	Connection c = null;
	JButton commit;
	JTextField posts;
	ArrayList<JTextField> ar = new ArrayList<JTextField>();
	String username;
	public AddPostPanel(Connection c, String username) {
		this.c = c;
		this.username = username;
		JFrame createPostFrame = new JFrame("Create Posts");
		JPanel createPostPanel = new JPanel();
		createPostFrame.setSize(500, 500);
		posts = new JTextField("Enter your posts here~ limit 200 characters!");
		posts.setBounds(1000, 50, 400, 300);

		commit = new JButton("commit!");
		commit.addActionListener(new CommitListener(username));
		createPostPanel.add(posts);
		ar.add(posts);
		createPostPanel.add(commit);
		createPostFrame.add(createPostPanel);
		createPostFrame.setVisible(true);
	}
	
	class CommitListener implements ActionListener{
		String username;
		public CommitListener(String username) {
			this.username = username;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("commit!")){
				createPosts(ar.get(0).getText(), username, ar.get(1).getText()); 
			}
			
		}
		
	}

	public boolean createPosts(String text, String poster, String picture) {
		CallableStatement cs = null;
		try {
			cs = this.c.prepareCall("{call CreatePosts(?,?,?,?)}");
			cs.setString(1, text);
			cs.setString(2, poster);
			cs.setString(3, picture);
			cs.registerOutParameter(4, java.sql.Types.INTEGER);
			cs.execute();
			if (cs.getInt(4) == 0) {
				JOptionPane.showMessageDialog(null, "successfully created!");
			}
			if (cs.getInt(4) == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Text cannot be null or empty");
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	
}
