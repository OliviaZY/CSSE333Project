import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class addProfileListner implements ActionListener {
	static JLabel l1;
	static JLabel FirstN;
	static JLabel LastN;
	static JLabel DBirth;
	static JLabel State;
	static JLabel College;
	static JLabel Profession;
	static JLabel Field;

	static JTextField FirstNJ;
	static JTextField LastNJ;
	static JTextField DBirthJ;
	static JTextField StateJ;
	static JTextField CollegeJ;
	static JTextField ProfessionJ;
	static JTextField FieldJ;
	static JTextField tf8;

	static String uname;

	static JButton btn1;
	static JButton btn2;
	private Connection dbc;
	JFrame frame;

	public addProfileListner(String uname, JFrame frame1, Connection dbc) {
		this.dbc = dbc;
		this.frame = frame1;
		this.uname = uname;
	}

	public void actionPerformed(ActionEvent arg0) {

		frame.repaint();
		frame.revalidate();

		JFrame frame = new JFrame("Let's set up the profile to meet more people :)");
		l1 = new JLabel("Let's set up the profile to meet more people :)");
		l1.setForeground(Color.MAGENTA);
		l1.setFont(new Font("Serif", Font.BOLD, 25));

		FirstN = new JLabel("First Name");
		LastN = new JLabel("Last Name");
		DBirth = new JLabel("Date of Birth");
		State = new JLabel("State");
		College = new JLabel("College");
		Profession = new JLabel("Profession");
		Field = new JLabel("Field");

		try {
			CallableStatement cs = this.dbc.prepareCall("call viewProfile(?,?,?,?,?,?,?,?,?)");
			cs.setString(1, uname);
			cs.registerOutParameter(4, java.sql.Types.DATE);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.registerOutParameter(7, java.sql.Types.VARCHAR);
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			cs.registerOutParameter(9, java.sql.Types.INTEGER);
			System.out.println(cs);
			cs.execute();
			int ret = cs.getInt(9);
			if (ret == 1) {
				JOptionPane.showMessageDialog((Component) null, "ERROR: user name cannot be empty");
			} else if (ret == 2) {
				JOptionPane.showMessageDialog((Component) null, "ERROR: incorrect username.");
			} else {
				if(cs.getDate(4)!=null){
					DBirthJ = new JTextField(cs.getDate(4).toString()); // first
				}else{
					DBirthJ = new JTextField(); // first
				}
				if(cs.getString(5)!= null){
					StateJ = new JTextField(cs.getString(5)); // first
				}else{
					StateJ = new JTextField(); // first
				}
				if(cs.getString(7)!= null){
					ProfessionJ = new JTextField(cs.getString(7)); // first
				}else{
					ProfessionJ = new JTextField(); // first
				}
				if(cs.getString(6)!= null){
					CollegeJ = new JTextField(cs.getString(6)); // first
				}else{
					CollegeJ = new JTextField(); // first
				}
				if(cs.getString(8)!= null){
					FieldJ = new JTextField(cs.getString(8)); // first
				}else{
					FieldJ = new JTextField(); // first
				}
				if(cs.getString(7)!= null){
					FirstNJ = new JTextField(cs.getString(8)); // first
				}else{
					FirstNJ = new JTextField(); // first
				}
				if(cs.getString(3)!= null){
					LastNJ = new JTextField(cs.getString(3)); // first
				}else{
					LastNJ = new JTextField(); // first
				}
				if(cs.getString(2)!= null){
					FirstNJ = new JTextField(cs.getString(2)); // first
				}else{
					FirstNJ = new JTextField(); // first
				}
				

//				tf2 = new JTextField(); // last
//				tf3 = new JTextField("yyyy-mm-dd"); // birth
//				tf4 = new JTextField(); // state
//				State = new JTextField(); // college
//				Profession = new JTextField(); // profession
//				Field = new JTextField(); // field
//				tf8 = new JTextField();
				btn1 = new JButton("Save!!!");
				btn2 = new JButton("Cancel, I will edit it later.");

				l1.setBounds(200, 30, 400, 30);
				FirstN.setBounds(80, 70, 200, 30);
				LastN.setBounds(80, 110, 200, 30);
				DBirth.setBounds(80, 150, 200, 30);
				State.setBounds(80, 190, 200, 30);
				College.setBounds(80, 230, 200, 30);
				Profession.setBounds(80, 270, 200, 30);
				Field.setBounds(80, 310, 200, 30);

				FirstNJ.setBounds(300, 70, 200, 30);
				LastNJ.setBounds(300, 110, 200, 30);
				DBirthJ.setBounds(300, 150, 200, 30);
				StateJ.setBounds(300, 190, 200, 30);
				CollegeJ.setBounds(300, 230, 200, 30);
				ProfessionJ.setBounds(300, 270, 200, 30);
				FieldJ.setBounds(300, 310, 200, 30);
				// tf8.setBounds(300, 110, 200, 30);

				btn1.setBounds(150, 360, 100, 30);
				btn2.setBounds(300, 360, 300, 30);

				frame.add(l1);
				frame.add(FirstN);
				frame.add(LastN);
				frame.add(DBirth);
				frame.add(State);
				frame.add(College);
				frame.add(Profession);
				frame.add(Field);

				frame.add(FirstNJ);
				frame.add(LastNJ);
				frame.add(DBirthJ);
				frame.add(StateJ);
				frame.add(CollegeJ);
				frame.add(ProfessionJ);
				frame.add(FieldJ);

				frame.add(btn1);
				frame.add(btn2);

				frame.setSize(800, 800);
				frame.setLayout(null);
				frame.setVisible(true);
				btn1.addActionListener(new SaveProfileListener(FirstNJ, LastNJ, DBirthJ, StateJ, CollegeJ, ProfessionJ, FieldJ, uname, frame, dbc));
				btn2.addActionListener(new SaveProfileListener(FirstNJ, LastNJ, DBirthJ, StateJ, CollegeJ, ProfessionJ, FieldJ, uname, frame, dbc));
			}
		} catch (SQLException var13) {
			var13.printStackTrace();
			JOptionPane.showMessageDialog((Component) null, "add profile Failed");
		}

	}
}
