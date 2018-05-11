import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PostCommentsPanel extends JPanel {
	Connection con;
	int postID;
	JButton addCom;
	JLabel comments;
	JTextField newCom;
	String username;

	public PostCommentsPanel(Connection c, int postID,String uname) {
		con = c;
		this.postID = postID;
		username = uname;
		addCom = new JButton("Add Comment");
		newCom = new JTextField("Enter new comment");
		addCom.addActionListener(new AddCommentListener());
		
		try {
			comments = new JLabel(this.getComments());
		} catch (SQLException exception) {
			System.out.println("Error getting comments");
		}
		this.add(comments);
		this.add(newCom);
		this.add(addCom);

		
	}

	private String getComments() throws SQLException {
		CallableStatement stm = con.prepareCall("{call DisplayPostComments(?)}");
		stm.setInt(1, postID);
		ResultSet rs = stm.executeQuery();
		String result = "";
		while (rs.next()){
			result = "<html>" + result + "<br/> Username: " + rs.getString(1) + " Comment: " + rs.getString(2);
		}
		return result;
	}
	private class AddCommentListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			CallableStatement stm;
			try {
				stm = con.prepareCall("{call AddComment(?,?,?)}");
				stm.setString(1, username);
				stm.setInt(2,postID);
				stm.setString(3, newCom.getText());
				stm.execute();
				comments.setText(getComments());
			} catch (SQLException exception) {
				System.out.println("Error adding comment");
			}
			
		}
		
	}
	

}
