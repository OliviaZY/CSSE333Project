import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * 
 * Creates the panel that displays the user's profile
 *
 * @author garretje. Created Apr 26, 2018.
 */
public class ProfilePanel extends JPanel {
	Connection c = null;
	String currInterest;
	JTextField searchbox;
	JButton enterButton;
	JRadioButton aniCheck;
	JRadioButton musCheck;
	JRadioButton exCheck;
	JRadioButton bookCheck;
	
	public ProfilePanel(Connection c){
		this.c = c;
		searchbox = new JTextField("Enter an interest here!");
		enterButton = new JButton("Enter");
		aniCheck = new JRadioButton("Search Animals");
		musCheck = new JRadioButton("Search Music");
		exCheck = new JRadioButton("Search Exercises");
		bookCheck = new JRadioButton("Search Books");

		
		ButtonGroup interGroup = new ButtonGroup();
		interGroup.add(aniCheck);
		interGroup.add(musCheck);
		interGroup.add(exCheck);
		interGroup.add(bookCheck);

		

		
		enterButton.addActionListener(new EnterListener());

		
		this.add(searchbox,BorderLayout.WEST);
		this.add(enterButton);
		this.add(aniCheck);
		this.add(bookCheck);
		this.add(exCheck);
		this.add(musCheck);
		
	}
	private String buildQuery() {
		if (aniCheck.isSelected())
			return "SELECT aName, FROM Animal, WHERE aType = ?;";
		return null;
		
	}
	
	class EnterListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			currInterest = searchbox.getText();
			String query = buildQuery();
			System.out.println(query + currInterest);
			PreparedStatement stmt = null;
			try{
				stmt = c.prepareCall(query);
				System.out.println(1);
				stmt.setString(1, currInterest);
				System.out.println(2);
				stmt.executeQuery();
				System.out.println(3);
				//rs.next();
				//System.out.println(4);
				//System.out.println(rs.getString(rs.findColumn("aName")));
			}
			catch(SQLException e){
				System.out.println("problem");
			}
			
		}
		
	}
}
