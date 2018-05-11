import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class LikeListener implements ActionListener {
	Connection c;
	String name;
	int id;
	JLabel numLikes;
	public LikeListener(Connection con, String userName, int PostID,JLabel l){
		c = con;
		name = userName;
		id = PostID;
		numLikes = l;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		try {
			CallableStatement stm = c.prepareCall("{call LikePosts(?,?,?)}");
			stm.setInt(1, id);
			stm.setString(2, name);
			stm.registerOutParameter(3, Types.INTEGER);
			stm.execute();
			if (stm.getInt(3) == -1){
				CallableStatement stm2 = c.prepareCall("{call DislikePosts(?,?,?)}");
				stm2.setInt(1, id);
				stm2.setString(2, name);
				stm2.registerOutParameter(3, Types.INTEGER);
				stm2.execute();
				if (stm2.getInt(3) >= 0)
					numLikes.setText("" + stm2.getInt(3));
			}
			else if (stm.getInt(3) > 0)
				numLikes.setText("" + stm.getInt(3));
			
		} catch (SQLException exception) {
			JOptionPane.showMessageDialog(numLikes,"There was a problem with liking the post", "Error",JOptionPane.ERROR_MESSAGE);
			System.out.println("error in update label");
		}
	}

}
