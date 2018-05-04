import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class addFriendListener implements ActionListener {
	private JFrame frame;
	private Connection dbc;

	static String initialUName;
	static String searchedUName;
	public addFriendListener(String initialUName, String searchedUName, JFrame frame1, Connection dbc) {
		this.initialUName = initialUName;
		this.searchedUName = searchedUName;
		this.frame = frame1;
		this.dbc = dbc;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		for(Component c : frame.getContentPane().getComponents()){
            if(c instanceof JPanel){
                frame.remove(c);
            }
		}
//		frame.repaint();
//	    frame.revalidate();

		CallableStatement cs = null;

		try {
			cs = this.dbc.prepareCall("call addFriendRequest(?,?,?)");
			cs.setString(1, initialUName);
			cs.setString(2, searchedUName);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			System.out.println(cs);
			cs.execute();
			int ret = cs.getInt(3);
			if (ret == 1) {
				JOptionPane.showMessageDialog((Component)null, "ERROR: initial user name cannot be empty");
			} else if (ret == 2) {
				JOptionPane.showMessageDialog((Component)null, "ERROR: searched user name cannot be empty");
			} else if (ret == 3) {
				JOptionPane.showMessageDialog((Component)null, "ERROR: incorrect initial user name.");
			} else if(ret == 4){
				JOptionPane.showMessageDialog((Component)null, "ERROR: incorrect searched user name.");
			}else if(ret == 5){
				JOptionPane.showMessageDialog((Component)null, "ERROR: you are already friend with this searched user.");
			}else if(ret == 6){
				JOptionPane.showMessageDialog((Component)null, "ERROR: you have already sent a friend request with this searched user.");
			}else{
				JOptionPane.showMessageDialog((Component)null, "SUCCESSFUL, your request has been send to the searched user.");
				}
		}catch (SQLException exception) {
			exception.printStackTrace();
			JOptionPane.showMessageDialog(null, "Add action Failed");
		}
	

	}

}
