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
 * @author garretje. Created Apr 26, 2018.
 */
public class EventsPanel extends JPanel {
	Connection c = null;
	JTextField searchbox;
	// search Button group
	JButton enterButton;
	JRadioButton eNameCheck;
	JRadioButton eDateCheck;
	JRadioButton eAddressCheck;
	// Create Event
	JButton createEvent;
	// prev/next page buttons
	JButton prev;
	JButton next;
	int pageNum;

	String currEvent;

	ArrayList<JTextField> temp;
	// Result Set
	ResultSet rs;
	
	// ArrayList of event contents
	ArrayList<JPanel> e;
	
	
	/**
	 * This is a event searching panel
	 */
	public EventsPanel(Connection c) {
		this.c = c;
		searchbox = new JTextField("Check a button and enter an event!");
		enterButton = new JButton("Enter");
		eNameCheck = new JRadioButton("Search by event names");
		eDateCheck = new JRadioButton("Search by dates");
		eAddressCheck = new JRadioButton("Search by address");
		createEvent = new JButton("Create an event!");

		prev = new JButton("<- previous");
		next = new JButton("next ->");

		// add search events button group
		ButtonGroup eventGroup = new ButtonGroup();
		eventGroup.add(eNameCheck);
		eventGroup.add(eDateCheck);
		eventGroup.add(eAddressCheck);

		enterButton.addActionListener(new ButtonListener(p));
		createEvent.addActionListener(new ButtonListener());
		prev.addActionListener(new ButtonListener());
		next.addActionListener(new ButtonListener());

		this.add(searchbox, BorderLayout.WEST);
		this.add(enterButton);
		this.add(eNameCheck);
		this.add(eDateCheck);
		this.add(eAddressCheck);
		this.add(createEvent);
		
		// Create event view
		e = this.eventView();
		for(int i = 0; i < e.size(); i++){
			this.add(e.get(i));
		}
		
		
		
		this.add(prev, BorderLayout.AFTER_LAST_LINE);
		this.add(next);
		
	}
	
	private ArrayList<JPanel> eventView(){
		rs = getEvents();
		JPanel view;
		ArrayList<JPanel> tempP = new ArrayList<JPanel>();
		try {
			while (rs.next()) {
				System.out.println(rs.getString(1) + " , " + rs.getString(2) + " , " + rs.getString(3));

				view = new JPanel();
				JLabel isolateLine = new JLabel("------------------------------------------------------------");
				view.add(isolateLine);
				view.add(new JLabel("Event Name: "+ rs.getString(1)));
				view.add(new JLabel("Date: "+ rs.getString(2)));
				view.add(new JLabel("Address: "+ rs.getString(3)));
				view.add(isolateLine);
				tempP.add(view);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return tempP;
		
	}
	/**
	 * retrieve data from mysql database under some conditions
	 * 
	 * @return result set of events
	 */
	
	public ResultSet getEvents(){
		ResultSet tempRS = null;
		String query = buildParameterizedSqlStatementString();
		PreparedStatement stmt = null;
		try {
			System.out.println(query + this.currEvent);
			stmt = c.prepareCall(query);
			if (currEvent != null)
				stmt.setString(1, "%" + currEvent + "%");
			tempRS = stmt.executeQuery();

		} catch (SQLException e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "Check a searching type.");
			System.out.println("problem");
		}
		return tempRS;
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

		// Buttons
		JButton confirm = new JButton("Confirm");
		confirm.addActionListener(new ButtonListener());
		
		// add J components into a Panel
		createEventPanel.add(eName);
		createEventPanel.add(nameField, BorderLayout.CENTER);
		createEventPanel.add(eDate);
		createEventPanel.add(dateField, BorderLayout.CENTER);
		createEventPanel.add(address);
		createEventPanel.add(addressField, BorderLayout.CENTER);
		createEventPanel.add(confirm, BorderLayout.SOUTH);

		ArrayList<JTextField> fields = new ArrayList<>();
		fields.add(nameField);
		fields.add(dateField);
		fields.add(addressField);

		createEventFrame.add(createEventPanel, BorderLayout.CENTER);
		createEventFrame.setVisible(true);
		return fields;
	}

	/**
	 * Create an event
	 *  
	 * @param EventName: Name of the event
	 * @param Date: Holding date of the event
	 * @param Address: Holding address of the event
	 * @return if the event is successfully created
	 */

	public boolean createEvent(String EventName, String Date, String Address) {
		CallableStatement cs = null;
		try {
			cs = this.c.prepareCall("{call CreateEvent(?,?,?)}");
			// cs.registerOutParameter(1, java.sql.Types.INTEGER);
			cs.setString(1, EventName);
			cs.setString(2, Date);
			cs.setString(3, Address);
			cs.execute();
			// if (cs.getInt(1) == 0) {
			// System.out.println("yay!");
			// }
			// if (cs.getInt(1) == 1) {
			// JOptionPane.showMessageDialog(null, "ERROR: Event name cannot be
			// null or empty");
			// }
			// if (cs.getInt(1) == 2) {
			// JOptionPane.showMessageDialog(null, "ERROR: Holding date cannot
			// be null or empty");
			// }
			// if (cs.getInt(1) == 3) {
			// JOptionPane.showMessageDialog(null, "ERROR: Address cannot be
			// null or empty");
			// }

			// if the same userName have same eventName at the same date, then
			// raise error
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Build a query
	 * 
	 * @return sqlStatement: Final query
	 */
	private String buildParameterizedSqlStatementString() {
		String sqlStatement = "SELECT EventName, Date, Address \nFROM Events\n";

		if (eNameCheck.isSelected()) {
			sqlStatement = sqlStatement + " WHERE EventName Like ? \n";
		}
		if (eDateCheck.isSelected()) {
			sqlStatement = sqlStatement + " WHERE date = ? \n";
		}
		if (eAddressCheck.isSelected()) {
			sqlStatement = sqlStatement + " WHERE address LIKE ? \n";
		}
		sqlStatement = sqlStatement + "ORDER BY ID DESC LIMIT 5;\n";

		return sqlStatement;
	}
	
	
	
	class ButtonListener implements ActionListener {
		
		JPanel p;
		
		public ButtonListener(){
			
		}
		
		public ButtonListener(JPanel p){
			p = p;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equals("Enter")) {
				currEvent = searchbox.getText();
				e = eventView();
				
			} else if (arg0.getActionCommand().equals("Create an event!")) {
				temp = addEventPanel();
			} else if (arg0.getActionCommand().equals("Confirm")) {
				ArrayList<String> eventAspects = new ArrayList<>();

				for (int i = 0; i < 3; i++) {
					eventAspects.add(temp.get(i).getText());
				}
				createEvent(eventAspects.get(0), eventAspects.get(1), eventAspects.get(2));
				temp = null;

			} else if (arg0.getActionCommand().equals("<- previous")) {
				
			} else if (arg0.getActionCommand().equals("next ->")) {

			}
		}

	}
}
