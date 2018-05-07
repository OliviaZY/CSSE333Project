import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainPagePosts extends JPanel {
	Connection c = null;
	ResultSet rs = null;
	ArrayList<JPanel> tempP;
	public String username = null;
	public MainPagePosts(Connection c, String username){
		this.c = c;
		tempP = postsView();
		for(int i = 0; i < tempP.size(); i++){
			this.add(tempP.get(i));
		}
		this.username = username;
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
		return "select p.firstN, p.lastN, po.date, po.text from person p left join Posts po on po.Poster = p.UserName" +  
		" ORDER BY po.Date DESC LIMIT 10;";
	}
	
	private ArrayList<JPanel> postsView(){
		rs = getPosts();
		JPanel view;
		ArrayList<JPanel> tempP = new ArrayList<JPanel>();
		try {
			while (rs.next() && rs.getString(3) != null) {
				view = new JPanel();
				JLabel isolateLine = new JLabel("------------------------------------------------------------");
				view.add(isolateLine);
				view.add(new JLabel(rs.getString(1) ));
				view.add(new JLabel("        "+ rs.getString(2) + "\n"));
				view.add(new JLabel(rs.getString(3) + "\n"));
				view.add(new JLabel(rs.getString(4) + "\n"));
				view.add(isolateLine);
				tempP.add(view);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return tempP;
		
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
	
}
