import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class AddInterests extends JPanel {
	Connection con;
	
	JButton enterButton;
	
	JRadioButton[] selectButtons;
	
	JTextField enterName;
	JTextField enterType;
	JTextField enterMusYear;
	JTextField enterMusTheme;
	
	
	public AddInterests(Connection c){
		con = c;
		//BoxLayout rButtonBox = new BoxLayout(this,BoxLayout.X_AXIS);
		//BoxLayout textAndEnterBox = new BoxLayout(this,BoxLayout.X_AXIS);
		enterName = new JTextField("Enter the song title");
		enterType = new JTextField("Enter the song artist");
		enterMusYear = new JTextField("Enter the year published");
		enterMusTheme = new JTextField("Enter the song theme");
		
		enterButton = new JButton("Enter");

		
		
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
		this.add(enterMusTheme);
		this.add(enterMusYear);
		this.add(enterButton);

		


	}
	class EnterDataListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int result;
			try{

			if (selectButtons[0].isSelected()){//book is selected
				CallableStatement stm = con.prepareCall("{call InsertBook(?,?,?)}");
				stm.setString(1, enterName.getText());
				stm.setString(2, enterType.getText());
				stm.registerOutParameter(3, Types.INTEGER);
				stm.executeQuery();
				result = stm.getInt(3);
			}
			else if (selectButtons[1].isSelected()){//Animal is selected
				CallableStatement stm = con.prepareCall("{call InsertAnimal(?,?,?)}");
				stm.setString(1, enterName.getText());
				stm.setString(2, enterType.getText());
				stm.registerOutParameter(3, Types.INTEGER);
				stm.executeQuery();
				result = stm.getInt(3);
			}
			else if (selectButtons[2].isSelected()){//Music is selected
				CallableStatement stm = con.prepareCall("{call InsertMusic(?,?,?,?,?)}");
				stm.setString(1, enterName.getText());
				stm.setString(2, enterType.getText());
				stm.setString(3, enterMusYear.getText());
				stm.setString(4, enterMusTheme.getText());
				stm.registerOutParameter(5, Types.INTEGER);
				stm.executeQuery();
				result = stm.getInt(5);
			}
			else if (selectButtons[3].isSelected()){//Exercise is selected
				CallableStatement stm = con.prepareCall("{call InsertExercise(?,?,?)}");
				stm.setString(1, enterName.getText());
				stm.setString(2, enterType.getText());
				stm.registerOutParameter(3, Types.INTEGER);
				stm.executeQuery();
				result = stm.getInt(3);
			}
			}
			catch(SQLException e){
				
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
			System.out.println(type);
			if (type.equals("Book")){
				System.out.println(2 + type);
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
