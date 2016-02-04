import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * The main class for Project1.
 * @author Gunnar Arnesen
 *
 */
public class Project1 {

	public static void main(String[] args) {
		Utils.setLookAndFeel();
		startApplication();
	}

	/**
	 * Start up the application.
	 */
	private static void startApplication() {
		JFrame frame = new JFrame("Test screen");
		frame.setLayout(new BorderLayout());
		DataPanel data = new DataPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(500, 500));
		frame.add(new SelectionPanel(data), BorderLayout.PAGE_START);
		frame.add(data, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.pack();
	}

}
