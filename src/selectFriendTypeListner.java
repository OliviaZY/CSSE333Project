import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class selectFriendTypeListner implements ActionListener {
	private JFrame frame;
	private Connection dbc;
	private String initialN;
	private String searchedN;
	private JLabel type;
	
	JRadioButton parent;
	JRadioButton CollegeMate;
	JRadioButton workMate;
	JRadioButton sameLocation;
	JRadioButton sameIns;
	JRadioButton boyfrined;
	JRadioButton girlfriend;
	JRadioButton boss;
	JRadioButton mentor;
	
	JButton enter;
	
	private String selected;
	
	

	public selectFriendTypeListner(String searchedN, String initialSearchN, JFrame frame1, Connection dbc) {
		this.frame = frame1;
		this.dbc = dbc;
		this.initialN = initialSearchN;
		this.searchedN = searchedN;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame.repaint();
		frame.revalidate();
		JFrame frame1 = new JFrame();
		frame1.setSize(1000, 1000);
		
		Box verticalBox = Box.createVerticalBox();

		ButtonGroup typeGroup = new ButtonGroup();
		parent = new JRadioButton("parent");
		CollegeMate = new JRadioButton("CollegeMate");
		workMate = new JRadioButton("workMate");
		sameLocation = new JRadioButton("sameLocation");
		sameIns = new JRadioButton("sameIns");
		boyfrined = new JRadioButton("boyfrined");
		girlfriend = new JRadioButton("girlfriend");
		boss = new JRadioButton("boss");
		mentor = new JRadioButton("mentor");
		
		enter = new JButton("enter!");
		
		verticalBox.add(parent);
		verticalBox.add(CollegeMate);
		verticalBox.add(workMate);
		verticalBox.add(sameLocation);
		verticalBox.add(sameIns);
		verticalBox.add(boyfrined);
		verticalBox.add(girlfriend);
		verticalBox.add(boss);
		verticalBox.add(mentor);
		
		typeGroup.add(parent);
		typeGroup.add(CollegeMate);
		typeGroup.add(workMate);
		typeGroup.add(sameLocation);
		typeGroup.add(sameIns);
		typeGroup.add(boyfrined);
		typeGroup.add(girlfriend);
		typeGroup.add(boss);
		typeGroup.add(mentor);

//		bookName.setSelected(true);
		enter.setBounds(300,350,100,30);
		
//		frame1.add(typeGroup);
		frame1.add(enter);
		frame1.add(verticalBox);
		
//		CallableStatement cs;
//		try {
//			cs = this.dbc.prepareCall("call acceptFriendRequest(?,?,?,?)");
//			cs.setString(1, initialN);
//			cs.setString(2, searchedN);
//
//			cs.registerOutParameter(4, java.sql.Types.INTEGER);
//		} catch (SQLException exception) {
//			// TODO Auto-generated catch-block stub.
//			exception.printStackTrace();
//		}
		
		enter.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	
		    	selected = buildQuery();
				System.out.println("test for selected trype"+selected);
				new AcceptFriendRequestListener(selected,initialN, searchedN, dbc).actionPerformed(e);
				
		    }});
	

		
		
//		String[] friendType = { "parent", "coworker", "collegeMate", "relation", "fromSamePlace" };
//		JComboBox<String> friendList = new JComboBox<>(friendType);
//		JComboBox petList = new JComboBox(friendType);
//		// petList.setSelectedIndex(4);
//		petList.setBounds(300, 300, 100, 30);
		// add to the parent container (e.g. a JFrame):
//		frame1.add(petList);

		// get the selected item:
//		String selectedType = String.valueOf(petList.getSelectedItem());
//		System.out.println("You seleted the type: " + selectedType);
		
		

			frame1.validate();
			frame1.setVisible(true);
//		} catch (SQLException var13) {
//			var13.printStackTrace();
//			JOptionPane.showMessageDialog((Component) null, "Login Failed");
//		}

	}
	
	private String buildQuery() {
			if (parent.isSelected())
				return "parent";
			else if(CollegeMate.isSelected())
				return "CollegeMate";
			else if(workMate.isSelected())
				return "workMate";
			else if(sameLocation.isSelected())
				return "sameLocation";
			else if(sameIns.isSelected())
				return "sameIns";
			else if(boyfrined.isSelected())
				return "boyfrined";
			else if(girlfriend.isSelected())
				return "girlfriend";
			else if(boss.isSelected())
				return "boss";
			else if(mentor.isSelected())
				return "mentor";
			else
				return null;
		}
}
