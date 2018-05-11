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
	
	public MainPagePosts(Connection c, String username,JFrame f){
		this.c = c;
		this.username = username;
		this.frame = f;
		tempP = postsView();
		for(int i = 0; i < tempP.size(); i++){
			this.add(tempP.get(i));
		}
		System.out.println("Username in mainposts constructor: " + this.username);
	}
	
	private String buildParameterizedSqlStatementString(String username) {
		CallableStatement cs = null;
		try {
			cs = this.c.prepareCall("{call ViewPosts(?,?)}");
			cs.setString(1, username);
			cs.registerOutParameter(2,java.sql.Types.INTEGER);
			cs.execute();
//			return cs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "select p.firstN, p.lastN, po.date, po.text, po.ID from person p left join Posts po on po.Poster = p.UserName" +  
		" ORDER BY po.Date DESC LIMIT 10;";
	}
	
	private ArrayList<JPanel> postsView(){
		rs = getPosts();
		JPanel view;
		ArrayList<JPanel> tempP = new ArrayList<JPanel>();
		try {
			while (rs.next() && rs.getString(3) != null) {
				view = new JPanel();
				JButton like = new JButton("Likes");
				JButton viewCommButton = new JButton("View Comments");
				viewCommButton.addActionListener(new CommentActionListener(rs.getInt(5)));
				JLabel likesLabel = new JLabel("" + getInitNumLikes(rs.getInt(5)));
				JLabel isolateLine = new JLabel("------------------------------------------------------------");
				view.add(isolateLine);
				view.add(new JLabel(rs.getString(1) ));
				view.add(new JLabel("        "+ rs.getString(2) + "\n"));
				view.add(new JLabel(rs.getString(3) + "\n"));
				view.add(new JLabel(rs.getString(4) + "\n"));
				like.addActionListener(new LikeListener(c, username, rs.getInt(5),likesLabel));
				view.add(like);
				view.add(likesLabel);
				view.add(viewCommButton);

				view.add(isolateLine);
				tempP.add(view);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return tempP;
		
	}
	public int getInitNumLikes(int postID){
		try {
			CallableStatement stm = c.prepareCall("{call DisplayPostLikes(?,?)}");
			stm.setInt(1, postID);
			stm.registerOutParameter(2, Types.INTEGER);
			stm.executeQuery();
			return stm.getInt(2);
			
		} catch (SQLException exception) {
			JOptionPane.showMessageDialog(this,"There was a problem with liking the post", "Error",JOptionPane.ERROR_MESSAGE);
			System.out.println("error in update label");
			return -12;
		}
	}
	
	public ResultSet getPosts(){
		ResultSet tempRS = null;
		// haven't decided if set username as a field
		String query = buildParameterizedSqlStatementString(username);
		System.out.println(query);
		PreparedStatement stmt = null;
		try {
			stmt = c.prepareStatement(query);
			tempRS = stmt.executeQuery();
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("problem");
		}
		return tempRS;
	}
	private class CommentActionListener implements ActionListener{
		int postID;
		public CommentActionListener(int postNum){
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
						frame.add(new PostCommentsPanel(c,postID,username), BorderLayout.CENTER);
						frame.repaint();
						frame.revalidate();
									
		}
		
	}
	
}
