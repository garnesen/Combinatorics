import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class Project1 {
	
	public static void main(String[] args) {
		setLookAndFeel();
		startApplication();
	}
	
	private static void startApplication() {
		JFrame frame = new JFrame("Test screen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(500, 500));
		frame.add(new SelectionPanel());
		frame.setVisible(true);
		frame.pack();
	}
	
	/**
	 * Try to change the look and feel of the application to that of the users system.
	 */
	private static void setLookAndFeel () {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			Utils.log("Error", "Look and feel not changed.");
		}
	}

}
