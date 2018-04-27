import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainPagePosts extends JPanel {
	Connection c;
	ResultSet rs;
	ArrayList<JPanel> tempP;
	
	public MainPagePosts(Connection dbc){
		tempP = postsView();
		for(int i = 0; i < tempP.size(); i++){
			this.add(tempP.get(i));
		}
	}
	
	private String buildParameterizedSqlStatementString(String username) {
		String sqlStatement = "SELECT po.Poster, po.Date, po.Text "
				+ "\nFROM Posts po, person p \n where p.UserName = po.Poster ";
		
		if(username != null){
			sqlStatement = sqlStatement + "and username = ?";
		}
		
		sqlStatement = sqlStatement + "ORDER BY po.Date DESC LIMIT 10;\n";
		
		return sqlStatement;
	}
	
	private ArrayList<JPanel> postsView(){
		rs = getPosts();
		JPanel view;
		ArrayList<JPanel> tempP = new ArrayList<JPanel>();
		try {
			while (rs.next()) {
				view = new JPanel();
				JLabel isolateLine = new JLabel("------------------------------------------------------------");
				view.add(isolateLine);
				view.add(new JLabel(rs.getString(1) ));
				view.add(new JLabel("        "+ rs.getString(2) + "\n"));
				view.add(new JLabel(rs.getString(3) + "\n"));
				view.add(isolateLine);
				tempP.add(view);
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving sodas by restaurants. See printed stack trace.");
			ex.printStackTrace();
		}
		
		return tempP;
		
	}
	
	public ResultSet getPosts(){
		ResultSet tempRS = null;
		// haven't decided if set username as a field
		String query = buildParameterizedSqlStatementString(null);
		System.out.println(query);
		PreparedStatement stmt = null;
		try {
			stmt = c.prepareCall(query);
			tempRS = stmt.executeQuery();
		} catch (SQLException e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "Check a searching type.");
			System.out.println("problem");
		}
		return tempRS;
	}
	
}
