import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class LinksListener implements ActionListener {
	private String type;
	private JFrame frame;
	private Connection c;

	public LinksListener(String link, JFrame frame, Connection con) {
		type = link;
		this.frame = frame;
		c = con;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Removes the panel that was there before
		for (Component c : frame.getContentPane().getComponents()) {
			if (c instanceof JPanel) {
				frame.remove(c);
			}
		}
		// Opens a new panel, depending on the button that was clicked
		if (type.equals("Interests"))
			frame.add(new InterestsPanel(c), BorderLayout.CENTER);
		else if (type.equals("Events"))
			frame.add(new EventsPanel(c));
		else if (type.equals("Profile"))
//			frame.add(new ProfilePanel(c), BorderLayout.CENTER);

		frame.repaint();
		frame.revalidate();

	}
}