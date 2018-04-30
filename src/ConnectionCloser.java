import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * 
 * To be deleted later, once we find a better way to close hte connetion
 *
 * @author garretje.
 *         Created Apr 26, 2018.
 */
public class ConnectionCloser implements ActionListener{
	DatabaseConnecter datacon;
	public ConnectionCloser(DatabaseConnecter d){
		datacon = d;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		datacon.closeConnection();
		
	}

}
