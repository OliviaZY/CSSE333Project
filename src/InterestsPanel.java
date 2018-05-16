import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * 
 * Creates the panel that allows users to view and search interests
 *
 * @author garretje.
 *         Created Apr 26, 2018.
 */
public class InterestsPanel extends JPanel {
	Connection c = null;
	JFrame frame;
	String username;
	
	String statement;
	
	JTextField aniSearch;
	JTextField musSearch;
	JTextField bookSearch;
	JTextField exSearch;
	

	JButton newEnterButton;
	
	JRadioButton aniName;
	JRadioButton aniType;
	
	JRadioButton musTitle;
	JRadioButton musArtist;
	JRadioButton musTheme;
	JRadioButton musYear;
	
	JRadioButton exName;
	JRadioButton exType;
	
	JRadioButton bookName;
	JRadioButton bookAuth;
	
	
	
	JLabel searchResults;
	JButton searchPeople;
	
	
	public InterestsPanel(Connection c,JFrame f,String name){
		frame = f;
		this.c = c;
		username = name;
		//Searchboxes
		aniSearch = new JTextField("Enter an animal to search");
		bookSearch = new JTextField("Enter a book to search");
		musSearch = new JTextField("Enter music to search");
		exSearch = new JTextField("Enter an exercise to search");
		
		//EnterButtons
		JButton[] enterButtons = new JButton[4];
		enterButtons[0] = new JButton("SearchAnimals");
		enterButtons[1] = new JButton("SearchBooks");
		enterButtons[2] = new JButton("SearchMusic");
		enterButtons[3] = new JButton("SearchExercises");
		
		//Enter New Interest Button
		newEnterButton = new JButton("Enter a New Interest?");
		
		//Search for people by interest button
		searchPeople = new JButton("Search for friends by interest?");

		//RadioButtons that determine what you're searching
		aniName = new JRadioButton("Search Animals by Name");
		aniType = new JRadioButton("Search Animals by Type"); 
		musTitle = new JRadioButton("Search Songs by Title");
		musArtist = new JRadioButton("Search Songs by Artist");
		musYear = new JRadioButton("Search Songs by Year");
		musTheme = new JRadioButton("Search Songs by Theme");
		exName = new JRadioButton("Search Exercises by Name");
		exType = new JRadioButton("Search Exercises by Type");
		bookAuth = new JRadioButton("Search Books by Author");
		bookName = new JRadioButton("Search Books by Title");

		//The label that displays the results
		searchResults = new JLabel();
		
		//Putting RadioButtons into groups, so you can only search by one column at a time per table		
		ButtonGroup aniGroup = new ButtonGroup();
		aniGroup.add(aniName);
		aniGroup.add(aniType);
		aniName.setSelected(true);
		
		ButtonGroup exGroup = new ButtonGroup();
		exGroup.add(exName);
		exGroup.add(exType);
		exName.setSelected(true);
		
		ButtonGroup bookGroup = new ButtonGroup();
		bookGroup.add(bookName);
		bookGroup.add(bookAuth);
		bookName.setSelected(true);
		
		ButtonGroup musGroup = new ButtonGroup();
		musGroup.add(musTitle);
		musGroup.add(musArtist);
		musGroup.add(musTheme);
		musGroup.add(musYear);
		musTitle.setSelected(true);
		
		
		
		//Adding action listeners to all the enter buttons
		for (JButton b:enterButtons){
			b.addActionListener(new EnterListener(b.getText()));
		}
		
		//Adding action listener to the enter new button
		newEnterButton.addActionListener(new NewEnterListener());
		
		//Adding action listener to the searchPerson button
		searchPeople.addActionListener(new SearchPersonListener());
		

		//adding everything to the panel
		this.add(aniSearch);
		this.add(enterButtons[0]);
		this.add(aniName);
		this.add(aniType);
		
		this.add(bookSearch);
		this.add(enterButtons[1]);
		this.add(bookName);
		this.add(bookAuth);
		
		this.add(exSearch);
		this.add(enterButtons[3]);
		this.add(exName);
		this.add(exType);
		
		this.add(musSearch);
		this.add(enterButtons[2]);
		this.add(musTitle);
		this.add(musArtist);
		this.add(musYear);
		this.add(musTheme);
		
		this.add(newEnterButton);
		
		this.add(searchPeople);
		
		this.add(searchResults,BorderLayout.PAGE_END);
		
		
		
	}
	/**
	 * 
	 * Builds the query depending on which enter button was clicked and which RadioButtons are checked
	 *
	 * @param the label of the button that was pressed
	 * @return query string
	 */
	private String buildQuery(String str) {
		if (str.equals("SearchAnimals")){
			statement = "{Call GetAnimals(?,?)}";
			if (aniName.isSelected())
				return "aName";
			else //if aniType is selected
				return "aType";
		}
		
		if (str.equals("SearchBooks")){
			statement = "{Call GetBooks(?,?)}";
			if (bookName.isSelected())
				return "title";
			else //if author is selected
				return "author";
		}
		if (str.equals("SearchExercises")){
			statement = "{Call GetExercise(?,?)}";
			if (exName.isSelected())
				return "eName";
			else //if eType is selected
				return "eType";
		}
		if (str.equals("SearchMusic")){
			statement = "{Call GetMusic(?,?,?)}";
			if (musTitle.isSelected())
				return "SongTitle";
			else if (musArtist.isSelected())
				return "Artist";
			else if (musYear.isSelected())
				return "YearPub";
			else if (musTheme.isSelected())
				return "Theme";
		}
		System.out.println("Something went wrong in buildquery() in InterestsPanel");
		return null;
	}
	/**
	 * 
	 * Runs a query using input from the textbox, which enter button was pressed, and which RadioButton was selected
	 * Allows the user to search interests
	 *
	 * @author garretje.
	 *         Created Apr 27, 2018.
	 */
	class EnterListener implements ActionListener{
		String type;
		String currInterest;
		int colIndex1;
		int colIndex2;
		int colIndex3;
		int colIndex4;
		public EnterListener(String buttonText){
			type = buttonText;
		}
		/**
		 * 
		 * 
		 *
		 * @param rs
		 * @throws SQLException
		 */
		private void setColIndexes(ResultSet rs) throws SQLException{
			if (type.equals("SearchAnimals")){
				colIndex1 = rs.findColumn("aName");
				colIndex2 = rs.findColumn("aType");
			}
			else if (type.equals("SearchBooks")){
				colIndex1 = rs.findColumn("title");
				colIndex2 = rs.findColumn("author");
			}
			else if (type.equals("SearchExercises")){
				colIndex1 = rs.findColumn("eName");
				colIndex2 = rs.findColumn("eType");
			}
			else if (type.equals("SearchMusic")){
				colIndex1 = rs.findColumn("SongTitle");
				colIndex2 = rs.findColumn("Artist");
				colIndex3 = rs.findColumn("YearPub");
				colIndex4 = rs.findColumn("Theme");
			}
		}
		private void setType(){
			if (type.equals("SearchAnimals")){
				currInterest = aniSearch.getText();
			}
			else if (type.equals("SearchBooks")){
				currInterest = bookSearch.getText();
			}
			else if (type.equals("SearchExercises")){
				currInterest = exSearch.getText();
			}
			else if (type.equals("SearchMusic"))
				currInterest = musSearch.getText();
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setType();
			String col = buildQuery(type);
			//System.out.println(query + currInterest);
			CallableStatement stmt = null;
			try{
				stmt = c.prepareCall(statement);
				stmt.setString(1,col);
				stmt.setString(2, this.currInterest);
				if (type.equalsIgnoreCase("SearchMusic")){
					if (col.equals("YearPub"))
						stmt.setInt(3, Integer.parseInt(this.currInterest));
					else
						stmt.setInt(3, 0);

				}
					
				ResultSet rs = stmt.executeQuery();
				this.setColIndexes(rs);
				String result = "";
			
				while(rs.next()){
					if (type.equals("SearchMusic"))
						result = "<html>" + result + "<br/> Title: " + rs.getString(colIndex1) + " Artist: " + rs.getString(colIndex2) + 
						" Year: " + rs.getString(colIndex3) + " Theme: " + rs.getString(colIndex4);
					else 	
						result = "<html>" + result + "<br/> Name: " + rs.getString(colIndex1) + " Type: " + rs.getString(colIndex2);
				}
				searchResults.setText(result);
				
			}
			catch(SQLException e){
				System.out.println("problem");
			}
			
		}
		
	}
	class NewEnterListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// Removes the panel that was there before
			for (Component c : frame.getContentPane().getComponents()) {
				if (c instanceof JPanel) {
					frame.remove(c);
				}
			}
			frame.add(new MyInterests(c,username), BorderLayout.CENTER);
			frame.repaint();
			frame.revalidate();
			
		}
		
	}
		class SearchPersonListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// Removes the panel that was there before
			for (Component c : frame.getContentPane().getComponents()) {
				if (c instanceof JPanel) {
					frame.remove(c);
				}
			}
			frame.add(new SearchPerson(c,username), BorderLayout.CENTER);
			frame.repaint();
			frame.revalidate();
			
		}
		
	}
	
}
