import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JFrame;

public class CancelProfileListener implements ActionListener {
	private Connection dbc;
	JFrame frame;
	public CancelProfileListener(String uname, JFrame frame, Connection dbc) {
		this.dbc = dbc;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame.repaint();
	    frame.revalidate();
	    frame.setVisible(false);
	}

}
