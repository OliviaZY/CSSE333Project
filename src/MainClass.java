import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainClass {

	public static void main(String[] args) {
		//Establishes the database connection
		DatabaseConnecter dbc = new DatabaseConnecter();
		
		//TODO: Clean this up/move it out of main? It's here now because I (Joanna) wanted to throw something together fast
		JFrame frame1 = new JFrame();
		frame1.setSize(1000, 1000);
		//This is the panel that's going to change when you click the link
		JPanel changingPanel = new MainPagePosts();
		//Link buttons on the left side of the screen
		Box links = Box.createVerticalBox();
		JButton[] buttonLinks = new JButton[5];
		buttonLinks[0] = new JButton("Profile");
		buttonLinks[1] = new JButton("Friends");
		buttonLinks[2] = new JButton("Reminders");
		buttonLinks[3] = new JButton("Events");
		buttonLinks[4] = new JButton("Intrests");
		for (JButton j:buttonLinks){
			links.add(j);
		}
				
		frame1.add(changingPanel, BorderLayout.CENTER);
		frame1.add(links, BorderLayout.WEST);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setVisible(true);
	}

}
