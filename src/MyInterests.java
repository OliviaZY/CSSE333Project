import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class MyInterests extends JPanel {
	Connection con;
	String username;
	
	JButton enterButton;
	JButton deleteButton;
	
	JLabel displayResults;
	
	JRadioButton[] selectButtons;
	
	JTextField enterName;
	JTextField enterType;
	JTextField enterMusYear;
	JTextField enterMusTheme;
	
	
	public MyInterests(Connection c,String uName){
		con = c;
		username = uName;
		//BoxLayout rButtonBox = new BoxLayout(this,BoxLayout.X_AXIS);
		//BoxLayout textAndEnterBox = new BoxLayout(this,BoxLayout.X_AXIS);
		enterName = new JTextField("Enter the song title");
		enterType = new JTextField("Enter the song artist");
		enterMusYear = new JTextField("Enter the year published");
		enterMusTheme = new JTextField("Enter the song theme");
		
		
		enterButton = new JButton("Enter");
		deleteButton = new JButton("Delete");
		displayResults = new JLabel("");
		updateLabel();

		
		
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
		deleteButton.addActionListener(new DeleteActionListener());
		
		this.add(enterName,BorderLayout.CENTER);
		this.add(enterType);
		this.add(enterMusTheme);
		this.add(enterMusYear);
		this.add(enterButton);
		this.add(deleteButton);
		this.add(displayResults);

		


	}
	private void updateLabel(){
		String result = "";
		try {
			CallableStatement stm = con.prepareCall("{Call ListPersonInterests(?)}");
			stm.setString(1,username);
			ResultSet results = stm.executeQuery();

			while(results.next()){	
				result = "<html>" + result + "<br/> Name: " + results.getString(results.findColumn("Name"));
			}
			
			
		} catch (SQLException exception) {
			JOptionPane.showMessageDialog(this,"There was a problem in getting your interests",
			         "Error",JOptionPane.ERROR_MESSAGE);
			System.out.println("ERROR ERROR ERROR in update label");
		}

		if (!result.equals("")){
			displayResults.setText(result);
		}
		
	}
	//Returns the panel so that it can be used by the inner classes
	public MyInterests getThis(){
		return this;
	}
	private void checkInputLength(){
		if (enterName.getText().length() > 20){
			enterName.setText(enterName.getText().substring(0,19));
		}
		if (enterType.getText().length() > 20){
			enterType.setText(enterType.getText().substring(0,19));
		}

	}
	
	private void checkInputType(){
		
		if (enterName.getText().equals("") || (enterName.getText().length() > 10 && enterName.getText().substring(0,11).equals("Enter the ") 
				)){
			JOptionPane.showMessageDialog(getThis(),"Please fill out all fields",
			         "Error",JOptionPane.ERROR_MESSAGE); 
		}
		if (enterType.getText().equals("") || (enterType.getText().length() > 10 && enterType.getText().substring(0,11).equals("Enter the ") 
				)){			JOptionPane.showMessageDialog(getThis(),"Please fill out all fields",
			         "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	private void checkMusicInputType(){
		if (enterMusYear.getText().equals("") || (enterMusYear.getText().length() > 10 && enterMusYear.getText().substring(0,11).equals("Enter the ") 
				)){
			JOptionPane.showMessageDialog(getThis(),"Please fill out all fields",
			         "Error",JOptionPane.ERROR_MESSAGE); 
		}
		if (enterMusTheme.getText().equals("") || (enterMusTheme.getText().length() > 10 && enterMusTheme.getText().substring(0,11).equals("Enter the ") 
				)){
			JOptionPane.showMessageDialog(getThis(),"Please fill out all fields",
			         "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	class DeleteActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int result;
			try{
				checkInputLength();
				checkInputType();
			if (selectButtons[0].isSelected()){//book is selected
				
				CallableStatement stm = con.prepareCall("{call DeletePersonalBook(?,?,?,?)}");
				stm.setString(1, enterName.getText());
				stm.setString(2, enterType.getText());
				stm.setString(3, username);
				stm.registerOutParameter(4, Types.INTEGER);
				stm.executeQuery();
				result = stm.getInt(4);
			}
			else if (selectButtons[1].isSelected()){//Animal is selected
				CallableStatement stm = con.prepareCall("{call DeletePersonalAnimal(?,?,?,?)}");
				stm.setString(1, enterName.getText());
				stm.setString(2, enterType.getText());
				stm.setString(3, username);
				stm.registerOutParameter(4, Types.INTEGER);
				stm.executeQuery();
				result = stm.getInt(4);
			}
			else if (selectButtons[2].isSelected()){//Music is selected
//				if (Integer.getInteger(enterMusYear.getText()) < 1600 || Integer.getInteger(enterMusYear.getText() ) > 2018){
//				JOptionPane.showMessageDialog(getThis(),"Invalid Year Input",
//				         "Error",JOptionPane.ERROR_MESSAGE);
//			}
				checkMusicInputType();
			if (enterMusTheme.getText().length() > 20){
				enterMusTheme.setText(enterMusTheme.getText().substring(0,19));
			}
				CallableStatement stm = con.prepareCall("{call DeletePersonalMusic(?,?,?,?,?,?)}");
				stm.setString(1, enterName.getText());
				stm.setString(2, enterType.getText());
				stm.setString(3, enterMusYear.getText());
				stm.setString(4, enterMusTheme.getText());
				stm.setString(5, username);
				stm.registerOutParameter(6, Types.INTEGER);
				stm.executeQuery();
				result = stm.getInt(6);
			}
			else if (selectButtons[3].isSelected()){//Exercise is selected
				CallableStatement stm = con.prepareCall("{call DeletePersonalExercise(?,?,?,?)}");
				stm.setString(1, enterName.getText());
				stm.setString(2, enterType.getText());
				stm.setString(3, username);
				stm.registerOutParameter(4, Types.INTEGER);
				stm.executeQuery();
				result = stm.getInt(4);
			}
			updateLabel();
			}
			catch (SQLException e){
				JOptionPane.showMessageDialog(getThis(),"There was a problem in deleting your interests",
				         "Error",JOptionPane.ERROR_MESSAGE); 
			}			
		}
		
	}
	class EnterDataListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int result;
			try{
				checkInputLength();
				checkInputType();
				checkInputLength();
			if (selectButtons[0].isSelected()){//book is selected
				if (enterName.getText().length() > 20){
					enterName.setText(enterName.getText().substring(0,19));
				}
				if (enterType.getText().length() > 20){
					enterType.setText(enterType.getText().substring(0,19));
				}
				CallableStatement stm = con.prepareCall("{call InsertPersonalBook(?,?,?,?)}");
				stm.setString(1, enterName.getText());
				stm.setString(2, enterType.getText());
				stm.setString(3, username);
				stm.registerOutParameter(4, Types.INTEGER);
				stm.executeQuery();
				result = stm.getInt(4);
			}
			else if (selectButtons[1].isSelected()){//Animal is selected
				CallableStatement stm = con.prepareCall("{call InsertPersonalAnimal(?,?,?,?)}");
				stm.setString(1, enterName.getText());
				stm.setString(2, enterType.getText());
				stm.setString(3, username);
				stm.registerOutParameter(4, Types.INTEGER);
				stm.executeQuery();
				result = stm.getInt(4);
			}
			else if (selectButtons[2].isSelected()){//Music is selected
				
//				if (Integer.getInteger(enterMusYear.getText()) < 1600 || Integer.getInteger(enterMusYear.getText() ) > 2018){
//				JOptionPane.showMessageDialog(getThis(),"Invalid Year Input",
//				         "Error",JOptionPane.ERROR_MESSAGE);
//			}
				checkMusicInputType();
			if (enterMusTheme.getText().length() > 20){
				enterMusTheme.setText(enterMusTheme.getText().substring(0,19));
			}
				CallableStatement stm = con.prepareCall("{call InsertPersonalMusic(?,?,?,?,?,?)}");
				stm.setString(1, enterName.getText());
				stm.setString(2, enterType.getText());
				stm.setString(3, enterMusYear.getText());
				stm.setString(4, enterMusTheme.getText());
				stm.setString(5, username);
				stm.registerOutParameter(6, Types.INTEGER);
				stm.executeQuery();
				result = stm.getInt(6);
			}
			else if (selectButtons[3].isSelected()){//Exercise is selected
				CallableStatement stm = con.prepareCall("{call InsertPersonalExercise(?,?,?,?)}");
				stm.setString(1, enterName.getText());
				stm.setString(2, enterType.getText());
				stm.setString(3, username);
				stm.registerOutParameter(4, Types.INTEGER);
				stm.executeQuery();
				result = stm.getInt(4);
			}
			updateLabel();
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
				enterMusYear.setVisible(false);
				enterMusTheme.setVisible(false);
			}
			else if (type.equals("Exercise")){
				enterName.setText("Enter the exercise name");
				enterType.setText("Enter the exercise type");
				enterMusYear.setVisible(false);
				enterMusTheme.setVisible(false);
			}
			else if (type.equals("Animal")){
				enterName.setText("Enter the animal name");
				enterType.setText("Enter the animal type");
				enterMusYear.setVisible(false);
				enterMusTheme.setVisible(false);
			}
			else if (type.equals("Music")){
				enterName.setText("Enter the song name");
				enterType.setText("Enter the song artist");
				enterMusYear.setVisible(true);
				enterMusTheme.setVisible(true);
				enterMusYear.setText("Enter the year the song was published");
				enterMusTheme.setText("Enter the song's theme");
			}
			
		}
		
	}
}
