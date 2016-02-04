import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class Project1 {

	public static void main(String[] args) {
		Utils.setLookAndFeel();
		startApplication();
	}

	private static void startApplication() {
		JFrame frame = new JFrame("Test screen");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		DataPanel data = new DataPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(500, 500));
		frame.add(new SelectionPanel(data));
		frame.add(data);
		frame.setVisible(true);
		frame.pack();
	}

}
