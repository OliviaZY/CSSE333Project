import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class RecommendationListner implements ActionListener {
	private Connection dbc;
	private String initialN;
	private String searchedN;
	private String selected;
	private JFrame frame;
	private JTextField userN;
	public RecommendationListner(JTextField tf1, JFrame frame1, Connection dbc) {
		this.dbc = dbc;
		this.frame = frame;
		this.userN = tf1;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub.

	}

}
