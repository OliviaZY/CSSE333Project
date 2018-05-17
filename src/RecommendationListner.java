import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class RecommendationListner implements ActionListener {
	private Connection dbc;
	private String selected;
	private JFrame frame;
	private JTextField userN;
	
	JRadioButton college;
	JRadioButton location;
	JRadioButton workField;
//	JRadioButton favBook;
//	JRadioButton favMusic;
//	JRadioButton favAni;
//	JRadioButton favExer;
	
	JButton enter;
	public RecommendationListner(JTextField tf1, JFrame frame1, Connection dbc) {
		this.dbc = dbc;
		this.frame = frame1;
		this.userN = tf1;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame.repaint();
		frame.revalidate();
		JFrame frame1 = new JFrame();
		frame1.setSize(1000, 1000);
		
		Box verticalBox = Box.createVerticalBox();
		
		ButtonGroup typeGroup = new ButtonGroup();
		college = new JRadioButton("search user with same college");
		location = new JRadioButton("search user with same location");
		workField = new JRadioButton("search user with same workField");
//		favBook = new JRadioButton("search user with same favorite Book");
//		favMusic = new JRadioButton("search user with same favorite music");
//		favAni = new JRadioButton("search user with same favorite Animal");
//		favExer = new JRadioButton("search user with same favorite Exercise");
//		boss = new JRadioButton("boss");
//		mentor = new JRadioButton("mentor");
		
		enter = new JButton("enter!");
		
		verticalBox.add(college);
		verticalBox.add(location);
		verticalBox.add(workField);
//		verticalBox.add(favBook);
//		verticalBox.add(favMusic);
//		verticalBox.add(favAni);
//		verticalBox.add(favExer);

		
		typeGroup.add(college);
		typeGroup.add(location);
		typeGroup.add(workField);
//		typeGroup.add(favBook);
//		typeGroup.add(favMusic);
//		typeGroup.add(favAni);
//		typeGroup.add(favExer);

		enter.setBounds(300,350,100,30);
		
		frame1.add(enter);
		frame1.add(verticalBox);
		
		enter.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	
		    	selected = buildQuery();
				System.out.println("test for selected recommendation "+selected);
				new ListOfRecommendationUserListener(selected,userN, dbc).actionPerformed(e);
				
		    }});

			frame1.validate();
			frame1.setVisible(true);
	}
	
	private String buildQuery() {
			if (college.isSelected())
				return "college";
			else if(location.isSelected())
				return "location";
			else if(workField.isSelected())
				return "workField";
//			else if(favBook.isSelected())
//				return "favBook";
//			else if(favMusic.isSelected())
//				return "favMusic";
//			else if(favAni.isSelected())
//				return "favAni";
//			else if(favExer.isSelected())
//				return "favExer";
			else
				return null;
		}

	}


