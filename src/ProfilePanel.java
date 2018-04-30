//import java.awt.BorderLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//import javax.swing.ButtonGroup;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//
///**
// * 
// * Creates the panel that displays the user's profile
//public class ProfilePanel extends JPanel {
//	private JFrame frame;
//	private Connection dbc;
////	static JTextField tf1;
//	static JLabel l2;
//	static JLabel l3;
//	static JLabel l4;
//	static JLabel l5;
//	static JLabel l6;
//	static JLabel l7;
//	static JLabel l8;
//	private String uname;
//	
//	public ProfilePanel(Connection c){
//		
//
//		
//		ButtonGroup interGroup = new ButtonGroup();
//		interGroup.add(aniCheck);
//		interGroup.add(musCheck);
//		interGroup.add(exCheck);
//		interGroup.add(bookCheck);
//
//		
//
//		
//		enterButton.addActionListener(new EnterListener());
//
//		
//		this.add(searchbox,BorderLayout.WEST);
//		this.add(enterButton);
//		this.add(aniCheck);
//		this.add(bookCheck);
//		this.add(exCheck);
//		this.add(musCheck);
//		
//	}
//	private String buildQuery() {
//		if (aniCheck.isSelected())
//			return "SELECT aName, FROM Animal, WHERE aType = ?;";
//		return null;
//		
//	}
//	
//	class EnterListener implements ActionListener{
//		
//		@Override
//		public void actionPerformed(ActionEvent arg0) {
//			currInterest = searchbox.getText();
//			String query = buildQuery();
//			System.out.println(query + currInterest);
//			PreparedStatement stmt = null;
//			try{
//				stmt = c.prepareCall(query);
//				System.out.println(1);
//				stmt.setString(1, currInterest);
//				System.out.println(2);
//				stmt.executeQuery();
//				System.out.println(3);
//				//rs.next();
//				//System.out.println(4);
//				//System.out.println(rs.getString(rs.findColumn("aName")));
//			}
//			catch(SQLException e){
//				System.out.println("problem");
//			}
//			
//		}
//		
//	}
//}
