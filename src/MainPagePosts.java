import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainPagePosts extends JPanel {
	Connection c = null;
	ResultSet rs = null;
	JFrame frame;
	ArrayList<JPanel> tempP;
	private String username;

	public MainPagePosts(Connection c, String username, JFrame f) {
		this.c = c;
		this.username = username;
		this.frame = f;
		tempP = postsView();
		for (int i = 0; i < tempP.size(); i++) {
			this.add(tempP.get(i));
		}
		System.out.println("Username in mainposts constructor: " + this.username);
	}

	private void buildResultSet() {
		CallableStatement cs = null;
		try {
			cs = this.c.prepareCall("{call ViewPosts(?,?,?,?,?,?,?,?)}");
			cs.setString(1, username);
			cs.registerOutParameter(2, java.sql.Types.INTEGER); // return val
			cs.registerOutParameter(3, java.sql.Types.VARCHAR); // firstN
			cs.registerOutParameter(4, java.sql.Types.VARCHAR); // lastN
			cs.registerOutParameter(5, java.sql.Types.DATE); // date
			cs.registerOutParameter(6, java.sql.Types.VARCHAR); // text
			cs.registerOutParameter(7, java.sql.Types.INTEGER); // post ID
			cs.registerOutParameter(8, java.sql.Types.VARCHAR); // post's owner
			rs = cs.executeQuery();
			// return cs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private ArrayList<JPanel> postsView() {
		buildResultSet();
		JPanel view;
		ArrayList<JPanel> tempP = new ArrayList<JPanel>();
		try {
			while ( rs != null && rs.next()) {
				view = new JPanel();
				JButton like = new JButton("Likes");
				JButton viewCommButton = new JButton("View Comments");
				JButton deletePosts = new JButton("delete this Post");
				viewCommButton.addActionListener(new CommentActionListener(rs.getInt(5)));
				JLabel likesLabel = new JLabel("" + getInitNumLikes(rs.getInt(5)));
				JLabel isolateLine = new JLabel("------------------------------------------------------------");
				view.add(isolateLine);
				view.add(new JLabel(rs.getString(1)));
				view.add(new JLabel("        " + rs.getString(2) + "\n"));
				view.add(new JLabel(rs.getString(3) + "\n"));
				view.add(new JLabel(rs.getString(4) + "\n"));
				like.addActionListener(new LikeListener(c, username, rs.getInt(5), likesLabel));
				view.add(like);
				view.add(likesLabel);
				view.add(viewCommButton);
				if(username.equals(rs.getString(6))){
					deletePosts.addActionListener(new DeleteActionListener(username,rs.getInt(5),this));
					view.add(deletePosts);
				}
				view.add(isolateLine);
				tempP.add(view);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return tempP;

	}

	public int getInitNumLikes(int postID) {
		try {
			CallableStatement stm = c.prepareCall("{call DisplayPostLikes(?,?)}");
			stm.setInt(1, postID);
			stm.registerOutParameter(2, Types.INTEGER);
			stm.executeQuery();
			return stm.getInt(2);

		} catch (SQLException exception) {
			JOptionPane.showMessageDialog(this, "There was a problem with liking the post", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println("error in update label");
			return -12;
		}
	}
	
	private boolean deletePosts(int id){
		CallableStatement cs = null;
		try {
			cs = this.c.prepareCall("{call DeletePosts(?,?,?)}");
			cs.setInt(1,id);
			cs.setString(2, username);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.execute();
			if(cs.getInt(3) == 1){
				JOptionPane.showMessageDialog(null, "System can not recognize the post! Report to devolopers!");
			} else if(cs.getInt(3) == 1){
				JOptionPane.showMessageDialog(null,"No post can not be deleted!");
			} else{
				JOptionPane.showMessageDialog(null,"successed!");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private class DeleteActionListener implements ActionListener{
		String username;
		int id;
		JPanel pan;
		public DeleteActionListener(String username, int id, JPanel p){
			this.username = username;
			this.id = id;
			this.pan = p;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			deletePosts(id);
			
			while (!tempP.isEmpty())
				pan.remove(tempP.remove(0));
			tempP = postsView();
			for (int i = 0; i < tempP.size(); i++) {
				pan.add(tempP.get(i));
			}
			frame.repaint();
			frame.revalidate();
		}
		
	}

	private class CommentActionListener implements ActionListener {
		int postID;

		public CommentActionListener(int postNum) {
			postID = postNum;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// Removes the panel that was there before
			for (Component c : frame.getContentPane().getComponents()) {
				if (c instanceof JPanel) {
					frame.remove(c);
				}
			}
			frame.add(new PostCommentsPanel(c, postID, username), BorderLayout.CENTER);
			frame.repaint();
			frame.revalidate();

		}

	}

}
