import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class ViewFriendPost {
	Connection c;
	JFrame frame;
	String personName;
	public ViewFriendPost(Connection dbc,String username,String personName) {
		c = dbc;
		 frame = new JFrame();
		frame.setSize(1000, 1000);
		JLabel j = new JLabel("No posts :(");
		CallableStatement cs;
		this.personName = personName;
		try {
			JPanel view = new JPanel();
			cs = dbc.prepareCall("call FriendsPosts(?)");
			cs.setString(1, username);
			ResultSet rs = cs.executeQuery();
			while ( rs != null && rs.next()) {
			JButton like = new JButton("Likes");
			JButton viewCommButton = new JButton("View Comments");
			viewCommButton.addActionListener(new CommentActionListener(rs.getInt(5)));
			JLabel likesLabel = new JLabel("" + getInitNumLikes(rs.getInt(5)));
			JLabel isolateLine = new JLabel("------------------------------------------------------------");
			view.add(isolateLine);
			view.add(new JLabel(rs.getString(1)));
			view.add(new JLabel("        " + rs.getString(2) + "\n"));
			view.add(new JLabel(rs.getString(3) + "\n"));
			view.add(new JLabel(rs.getString(4) + "\n"));
			like.addActionListener(new LikeListener(c, personName, rs.getInt(5), likesLabel));
			view.add(like);
			view.add(likesLabel);
			view.add(viewCommButton);
			
			view.add(isolateLine);
			j.setOpaque(true);;
			frame.add(view);
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		
		
		if (!j.isOpaque())
			frame.add(j);
		frame.setVisible(true);
	}
	
	public int getInitNumLikes(int postID) {
		try {
			CallableStatement stm = c.prepareCall("{call DisplayPostLikes(?,?)}");
			stm.setInt(1, postID);
			stm.registerOutParameter(2, Types.INTEGER);
			stm.executeQuery();
			return stm.getInt(2);

		} catch (SQLException exception) {
			JOptionPane.showMessageDialog(frame, "There was a problem with liking the post", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println("error in update label");
			return -12;
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
			frame.add(new PostCommentsPanel(c, postID, personName), BorderLayout.CENTER);
			frame.repaint();
			frame.revalidate();

		}

	}

}
