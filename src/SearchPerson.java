import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SearchPerson extends JPanel {
	Connection con;
	String username;
	
	JButton enterButton;
	
	JLabel displayResults;
	
	JRadioButton[] selectButtons;
	
	JTextField enterName;
	JTextField enterType;
;
	
	
	public SearchPerson(Connection c,String uName){
		con = c;
		username = uName;
		//BoxLayout rButtonBox = new BoxLayout(this,BoxLayout.X_AXIS);
		//BoxLayout textAndEnterBox = new BoxLayout(this,BoxLayout.X_AXIS);
		enterName = new JTextField("Enter the song title");
		enterType = new JTextField("Enter the song artist");
		
		
		
		enterButton = new JButton("Enter");
		displayResults = new JLabel("No interest searched yet");

		
		
		selectButtons = new JRadioButton[4];
		selectButtons[0] = new JRadioButton("Book");
		selectButtons[1] = new JRadioButton("Animal");
		selectButtons[2] = new JRadioButton("Music");
		selectButtons[3] = new JRadioButton("Exercise");
		
		selectButtons[2].setSelected(true);
		
		ButtonGroup selectNew = new ButtonGroup();
		for (JRadioButton b: selectButtons){
			b.addActionListener(new ChooseTypeListener(b.getText()));
			selectNew.add(b);
			this.add(b);
		}
		
		enterButton.addActionListener(new EnterDataListener());
		
		this.add(enterName,BorderLayout.CENTER);
		this.add(enterType);

		this.add(enterButton);
		this.add(displayResults);

		


	}
	/**
	 * 
	 *Checks to see if text has been entered in a box
	 *
	 * @param input
	 * @return true if text has been entered, false otherwise
	 */
	private boolean checkEnteredText(String input){
		if (!input.equals("") && input.length() < 9)
			return true;
		if (input.equals("") || input.substring(0,10).equals("Enter the ")){
			return false;
		}
		return true;
	}
	
	//Returns the panel so that it can be used by the inner classes
	public SearchPerson getThis(){
		return this;
	}
	
	
	class EnterDataListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int result;
			try{
				
			
				if (enterName.getText().length() > 20){
					enterName.setText(enterName.getText().substring(0,19));
				}
				if (enterType.getText().length() > 20){
					enterType.setText(enterType.getText().substring(0,19));
				}
				CallableStatement stm = con.prepareCall("{call SearchPeople(?,?,?,?)}");
				stm.setString(1, enterName.getText());
				stm.setString(2, enterType.getText());
				if (!checkEnteredText(enterName.getText())){
					enterName.setText("Enter the name");
					stm.setInt(3, 1);
				}
				else if (!checkEnteredText(enterType.getText())){
					enterType.setText("Enter the type");
					stm.setInt(3, 0);
				}
				else 
					stm.setInt(3, 2);
				if (selectButtons[0].isSelected())//book is selected
					stm.setInt(4, 1);
				else if (selectButtons[1].isSelected())//animal is selected
					stm.setInt(4, 0);
				else if (selectButtons[2].isSelected()) //music is selected
					stm.setInt(4, 3);
				else //exercise is selected
					stm.setInt(4, 2);
				String nameList = "";
				ResultSet results = stm.executeQuery();
				while(results.next()){	
					nameList = "<html>" + nameList + "<br/>" + results.getString(1) + " " + results.getString(2);
				}
				displayResults.setText(nameList);
			
			}
			catch (SQLException e){
				JOptionPane.showMessageDialog(getThis(),"There was a problem in inserting your interests",
				         "Error",JOptionPane.ERROR_MESSAGE); 
			}
		}
		
		
	}
	class ChooseTypeListener implements ActionListener{
		String type;
		public ChooseTypeListener(String s){
			type = s;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (type.equals("Book")){
				enterName.setText("Enter the book title name");
				enterType.setText("Enter the author name");
				enterName.setVisible(true);
				enterType.setVisible(true);
	
			}
			else if (type.equals("Exercise")){
				enterName.setText("Enter the exercise name");
				enterType.setText("Enter the exercise type");
			
			}
			else if (type.equals("Animal")){
				enterName.setText("Enter the animal name");
				enterType.setText("Enter the animal type");
			
			}
			else if (type.equals("Music")){
				enterName.setText("Enter the song name");
				enterType.setText("Enter the song artist");
	
			}
			
		}
		
	}
}
