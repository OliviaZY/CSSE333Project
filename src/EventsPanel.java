import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * 
 * Creates the panel displaying events
 *
 * @author garretje. Created Apr 26, 2018. Edited by Yuhong
 */
public class EventsPanel extends JPanel {
	Connection c = null;
	String username = null;
	JTextField searchbox;
	// search Button group
	JButton enterButton;
	JRadioButton eNameCheck;
	JRadioButton eDateCheck;
	JRadioButton eAddressCheck;
	JRadioButton iAnimalCheck;
	JRadioButton iBookCheck;
	JRadioButton iExerciseCheck;
	JRadioButton iMusicCheck;
	// Create interest related button group
	JRadioButton aniCheck;
	JRadioButton bookCheck;
	JRadioButton exerCheck;
	JRadioButton musicCheck;
	JRadioButton noInterest;
	// Create Event
	JButton createEvent;

	// prev/next page buttons
	int pageNum;

	String currEvent;

	ArrayList<JTextField> temp;
	// Result Set
	private ResultSet rs;

	// ArrayList of event contents
	ArrayList<JPanel> e;

	/**
	 * This is a event searching panel
	 */
	public EventsPanel(Connection c, String username) {
		this.c = c;
		this.username = username;
		searchbox = new JTextField("Check a button and enter an event!");
		enterButton = new JButton("Enter");
		eNameCheck = new JRadioButton("Search by event names");
		eDateCheck = new JRadioButton("Search by dates");
		eAddressCheck = new JRadioButton("Search by address");

		iAnimalCheck = new JRadioButton("Search by Animals");
		iBookCheck = new JRadioButton("Search by Books");
		iExerciseCheck = new JRadioButton("Search by Exercises");
		iMusicCheck = new JRadioButton("Search by Music");

		aniCheck = new JRadioButton("The interest is relate to Animals");
		bookCheck = new JRadioButton("The interest is relate to Books");
		exerCheck = new JRadioButton("The interest is relate to Exersices");
		musicCheck = new JRadioButton("The interest is relate to Musics");
		noInterest = new JRadioButton("This event doesn't create any interest");
		createEvent = new JButton("Create an event!");



		// add search events button group
		ButtonGroup eventGroup = new ButtonGroup();
		eventGroup.add(eNameCheck);
		eventGroup.add(eDateCheck);
		eventGroup.add(eAddressCheck);
		eventGroup.add(iAnimalCheck);
		eventGroup.add(iBookCheck);
		eventGroup.add(iExerciseCheck);
		eventGroup.add(iMusicCheck);

		ButtonGroup interestGroup = new ButtonGroup();
		interestGroup.add(aniCheck);
		interestGroup.add(bookCheck);
		interestGroup.add(exerCheck);
		interestGroup.add(musicCheck);
		interestGroup.add(noInterest);

		enterButton.addActionListener(new ButtonListener(this));
		createEvent.addActionListener(new ButtonListener(this));
//		prev.addActionListener(new ButtonListener(this));
//		next.addActionListener(new ButtonListener(this));

		this.add(searchbox, BorderLayout.WEST);
		this.add(enterButton);
		this.add(eNameCheck);
		this.add(eDateCheck);
		this.add(eAddressCheck);
		this.add(iAnimalCheck);
		this.add(iBookCheck);
		this.add(iExerciseCheck);
		this.add(iMusicCheck);
		this.add(createEvent);
//		this.add(prev, BorderLayout.AFTER_LAST_LINE);
//		this.add(next);
		
		// Create event view
		rs = buildGeneralEventResultSet();
		e = this.eventView();
		for (int i = 0; i < e.size(); i++) {
			this.add(e.get(i));
		}

		

	}

	private ArrayList<JPanel> eventView() {
		JPanel view;
		ArrayList<JPanel> tempP = new ArrayList<JPanel>();
		if (rs == null) {
			view = new JPanel();
			JLabel noResult = new JLabel("No result...");
			noResult.setForeground(Color.orange);
			noResult.setFont(new Font("Serif", Font.BOLD, 50));
			noResult.setBounds(300, 300, 200, 30);
			view.add(noResult);
			tempP.add(view);
		}
		try {
			while (rs != null && rs.next()) {
				System.out.println(
						rs.getString(1) + " , " + rs.getString(2) + " , " + rs.getString(3) + " , " + rs.getString(4));
				view = new JPanel();
				JLabel isolateLine = new JLabel("------------------------------------------------------------");
				view.add(new JLabel("Event Name: " + rs.getString(1)));
				view.add(new JLabel("Date: " + rs.getString(2)));
				view.add(new JLabel("Address: " + rs.getString(3)));
				// if this event is owned by user
				JButton deleteEvent = new JButton("delete this event");
				JButton followEvent = new JButton("sign up!");
				if (rs.getString(4).equals(this.username)) {
					deleteEvent.addActionListener(new ButtonListener(username, rs.getInt(5), this));
					view.add(deleteEvent);
				} else {
					followEvent.addActionListener(new ButtonListener(username, rs.getInt(5), this));
					view.add(followEvent);
				}
				view.add(isolateLine);
				tempP.add(view);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return tempP;

	}

	/**
	 * Pop out a panel where the events are created
	 * 
	 * @return an ArrayList of JTextFiel which contains users' inputs
	 */

	public ArrayList<JTextField> addEventPanel() {
		// Event name *: text box
		JFrame createEventFrame = new JFrame("Create Event!");
		JPanel createEventPanel = new JPanel();
		createEventFrame.setSize(500, 500);
		// Event name *: text box
		JLabel eName = new JLabel("Event Name: ");
		JTextField nameField = new JTextField("Please Enter name of the event");
		eName.setForeground(Color.BLACK);
		eName.setFont(new Font("Serif", Font.BOLD, 20));
		nameField.setBounds(500, 100, 100, 20);

		// Event date *: text box
		JLabel eDate = new JLabel("Date: ");
		JTextField dateField = new JTextField("YYYY-MM-DD");
		eDate.setForeground(Color.BLACK);
		eDate.setFont(new Font("Serif", Font.BOLD, 20));
		dateField.setBounds(500, 150, 100, 20);

		// Event Address *: text box
		JLabel address = new JLabel("Address: ");
		JTextField addressField = new JTextField("Ex: Chicago, IL, US");
		address.setForeground(Color.BLACK);
		address.setFont(new Font("Serif", Font.BOLD, 20));
		addressField.setBounds(500, 200, 100, 20);

		// Event creates interest *: text box
		JLabel interest = new JLabel("Interest: ");
		JTextField interestField = new JTextField("Name of the interest");
		System.out.println(interestField.getText().length());
		interest.setForeground(Color.BLACK);
		interest.setFont(new Font("Serif", Font.BOLD, 20));
		interestField.setBounds(500, 250, 100, 20);

		// Buttons
		// JButton addPic = new JButton("I want to add a picture");
		// addPic.addActionListener(new ButtonListener(this));
		JButton confirm = new JButton("Confirm");
		confirm.addActionListener(new ButtonListener(this,createEventFrame));

		// add J components into a Panel
		createEventPanel.add(eName);
		createEventPanel.add(nameField, BorderLayout.CENTER);
		createEventPanel.add(eDate);
		createEventPanel.add(dateField, BorderLayout.CENTER);
		createEventPanel.add(address);
		createEventPanel.add(addressField, BorderLayout.CENTER);

		createEventPanel.add(aniCheck);
		createEventPanel.add(bookCheck);
		createEventPanel.add(exerCheck);
		createEventPanel.add(musicCheck);
		createEventPanel.add(noInterest);

		createEventPanel.add(interest);
		createEventPanel.add(interestField);
		createEventPanel.add(confirm, BorderLayout.SOUTH);

		ArrayList<JTextField> fields = new ArrayList<>();
		fields.add(nameField);
		fields.add(dateField);
		fields.add(addressField);
		fields.add(interestField);

		createEventFrame.add(createEventPanel, BorderLayout.CENTER);
		createEventFrame.setVisible(true);
		return fields;
	}

	/**
	 * Create an event
	 * 
	 * @param EventName:
	 *            Name of the event
	 * @param Date:
	 *            Holding date of the event
	 * @param Address:
	 *            Holding address of the event
	 * @return if the event is successfully created
	 */

	public boolean createEvent(String EventName, String Date, String Address, String interest) {
		CallableStatement cs = null;
		// check if the date is valid 
		if(Date.length() != 10){
			JOptionPane.showMessageDialog(null, "Please type in a valid date!");
			return false;
		}else{
			for(int i = 0; i < Date.length(); i++){
				if(Date.charAt(i) < '0' || Date.charAt(i) > '9'){
					if(i == 4 || i == 7){
						if(Date.charAt(i) == '-') continue;
					}
					JOptionPane.showMessageDialog(null, "Please type in a valid date!");
					return false;
				}
			}
		}
		try {
			
			cs = this.c.prepareCall("{call CreateEvent(?,?,?,?,?,?,?)}");
			cs.setString(1, EventName);
			cs.setString(2, Date);
			cs.setString(3, Address);
			cs.setString(4, username);

			if (aniCheck.isSelected())
				cs.setString(5, "Animal");
			else if (bookCheck.isSelected())
				cs.setString(5, "Book");
			else if (exerCheck.isSelected())
				cs.setString(5, "Exercise");
			else if (musicCheck.isSelected())
				cs.setString(5, "Music");
			else 
				cs.setString(5, null);
			
			cs.setString(6, interest);
			cs.registerOutParameter(7, java.sql.Types.INTEGER);
			System.out.println(cs);
			cs.execute();
			if (cs.getInt(7) == 0) {
				JOptionPane.showMessageDialog(null, "successfully created!");
				
			}
			if (cs.getInt(7) == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Event name cannot be null or empty");
			}
			if (cs.getInt(7) == 2) {
				JOptionPane.showMessageDialog(null, "ERROR: Holding date cannot be null or empty");
			}
			if (cs.getInt(7) == 3) {
				JOptionPane.showMessageDialog(null, "ERROR: Address cannot be null or empty");
			}
			if (cs.getInt(7) == 4) {
				JOptionPane.showMessageDialog(null,
						"ERROR: System can't recognize the user. Please report this error massage to developers");
			}
//			if (cs.getInt(7) == 5) {
//				JOptionPane.showMessageDialog(null, "ERROR: Check interest Type!");
//			}
			if (cs.getInt(7) == 6) {
				JOptionPane.showMessageDialog(null, "ERROR: Invalid date!");
			}
			if (cs.getInt(7) == 7) {
				JOptionPane.showMessageDialog(null,
						"ERROR: A user can't create two event which are hold at the same time");
			}
			if (cs.getInt(7) == 8) {
				JOptionPane.showMessageDialog(null,
						"ERROR: Can't recognize the interest. Please type in proper interest name or create a new interest!");
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	private ResultSet buildGeneralEventResultSet() {
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			cs = this.c.prepareCall("{call ViewEvents(?,?,?,?,?)}");
			cs.registerOutParameter(1, java.sql.Types.VARCHAR);
			cs.registerOutParameter(2, java.sql.Types.DATE);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			cs.registerOutParameter(5, java.sql.Types.INTEGER);
			rs = cs.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	private ResultSet buildSearchResultSet() {
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			cs = this.c.prepareCall("{call SearchEvents(?,?,?,?,?,?,?,?,?,?)}");

			if (eNameCheck.isSelected()) {
				cs.setString(1, this.searchbox.getText());
				cs.setString(2, null);
				cs.setString(3, null);
				cs.setString(8, null);
				cs.setString(9, null);

			} else if (eDateCheck.isSelected()) {
				cs.setString(1, null);
				cs.setString(2, this.searchbox.getText());
				cs.setString(3, null);
				cs.setString(8, null);
				cs.setString(9, null);

			} else if (eAddressCheck.isSelected()) {
				cs.setString(1, null);
				cs.setString(2, null);
				cs.setString(3, this.searchbox.getText());
				cs.setString(8, null);
				cs.setString(9, null);

			} else if (iAnimalCheck.isSelected()) {
				cs.setString(1, null);
				cs.setString(2, null);
				cs.setString(3, null);
				cs.setString(8, this.searchbox.getText());
				cs.setString(9, "Animal");
			} else if (iBookCheck.isSelected()) {
				cs.setString(1, null);
				cs.setString(2, null);
				cs.setString(3, null);
				cs.setString(8, this.searchbox.getText());
				cs.setString(9, "Book");
			} else if (iExerciseCheck.isSelected()) {
				cs.setString(1, null);
				cs.setString(2, null);
				cs.setString(3, null);
				cs.setString(8, this.searchbox.getText());
				cs.setString(9, "Exercise");
			} else if (iMusicCheck.isSelected()) {
				cs.setString(1, null);
				cs.setString(2, null);
				cs.setString(3, null);
				cs.setString(8, this.searchbox.getText());
				cs.setString(9, "Music");
			} else {
				JOptionPane.showMessageDialog(null, "Please check searching option!");
				return null;
			}
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			cs.registerOutParameter(5, java.sql.Types.DATE);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.registerOutParameter(7, java.sql.Types.VARCHAR);
			cs.registerOutParameter(10, java.sql.Types.INTEGER);
			rs = cs.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	private boolean signUpEvent(int id){
		CallableStatement cs = null;
		try {
			cs = this.c.prepareCall("{call DeletePosts(?,?,?)}");
			cs.setInt(1,id);
			cs.setString(2, username);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.execute();
			if(cs.getInt(3) == 1){
				JOptionPane.showMessageDialog(null, "System can not recognize the post! Report to devolopers!");
			} else if(cs.getInt(3) == 1){
				JOptionPane.showMessageDialog(null, "No post can not be deleted!");
			} else{
				JOptionPane.showMessageDialog(null, "successed!");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean deleteFromDB(int id) {
		CallableStatement cs = null;
		try {
			cs = this.c.prepareCall("{call DeleteEvents(?,?,?)}");
			cs.setInt(1, id);
			cs.setString(2, username);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.execute();
			if(cs.getInt(3) == 1){
				JOptionPane.showMessageDialog(null,"System can not recognize Event! Report to devolopers!");
			} else if(cs.getInt(3) == 2){
				JOptionPane.showMessageDialog(null,"No event can not be deleted!");
			} else if(cs.getInt(3) == 0){
				JOptionPane.showMessageDialog(null,"successed!");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	// Button listener

	public class ButtonListener implements ActionListener {
		EventsPanel pan;
		String username;
		int id;
		JFrame f;
		
		public ButtonListener(EventsPanel p, JFrame f){
			pan = p;
			this.f = f;
		}

		public ButtonListener(EventsPanel p) {
			pan = p;
		}

		public ButtonListener(String username, int id, EventsPanel p) {
			this.pan = p;
			this.username = username;
			this.id = id;
		}

		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equals("Enter")) {
				while (!e.isEmpty())
					pan.remove(e.remove(0));
				rs = buildSearchResultSet();
				e = eventView();
				for (int i = 0; i < e.size(); i++) {
					pan.add(e.get(i));
				}
			} else if (arg0.getActionCommand().equals("Create an event!")) {
				temp = addEventPanel();
			} else if (arg0.getActionCommand().equals("Confirm")) {
				ArrayList<String> eventAspects = new ArrayList<>();

				for (int i = 0; i < 4; i++) {
					eventAspects.add(temp.get(i).getText());
					System.out.println("--" + temp.get(i).getText());
				}
				System.out.println("interest: " + eventAspects.get(3).equals("Name of the interest"));
				if(eventAspects.get(3).length() == 0 || eventAspects.get(3).equals("Name of the interest"))
					
					createEvent(eventAspects.get(0), eventAspects.get(1), eventAspects.get(2), null);
				else{
					createEvent(eventAspects.get(0), eventAspects.get(1), eventAspects.get(2), eventAspects.get(3));
				}
				f.setVisible(false);
			} else if (arg0.getActionCommand().equals("delete this event")) {
				System.out.println("delete!");
				// call delete event sproc
				deleteFromDB(id);
				// refresh the panel
				while (!e.isEmpty())
					pan.remove(e.remove(0));
				rs = buildGeneralEventResultSet();
				e = eventView();
				for (int i = 0; i < e.size(); i++) {
					pan.add(e.get(i));
				}
			} else if (arg0.getActionCommand().equals("sign up!")) {
				// go sign up
				signUpEvent(id);
			} 
			pan.repaint();
			pan.revalidate();
		}

	}
}