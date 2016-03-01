package graphics;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import utils.Utils;

/**
 * The main class for the Graph Projects.
 * @author Gunnar Arnesen
 *
 */
public class GraphProjects {
	
	private static final String GRAPH_INFO = "Graph Information";
	private static final String GRAPH_WEIGHTED = "Weighted Graphs";
	
	public static final int GRAPH_SIZE = 600;
	
	private static JFrame frame;
	private static StartPanel start;

	/**
	 * Start up the application.
	 */
	private static void startApplication() {
		Utils.log("Starting the application...");
		
		// Create the frame
		frame = new JFrame("Graph Projects");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(GRAPH_SIZE, GRAPH_SIZE));
		
		// Create the start panel and add it to the frame
		start = new StartPanel(listener(), GRAPH_INFO, GRAPH_WEIGHTED);
		frame.add(start);
		
		// Specific order to center the frame on the screen
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * Creates the listener that each program button will use.
	 * @return the listener
	 */
	public static ActionListener listener() {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Utils.log("Button selected: " + e.getActionCommand());
				frame.remove(start);
				if (e.getActionCommand().equals(GRAPH_INFO)) {
					frame.setLayout(new BorderLayout());
					GraphInfoPanel data = new GraphInfoPanel();
					frame.add(new SelectionPanel(data.getNotifier()), BorderLayout.PAGE_START);
					frame.add(data, BorderLayout.CENTER);
					frame.pack();
				} else if (e.getActionCommand().equals(GRAPH_WEIGHTED)) {
					// open panel
				}
			}
		};
		return listener;
	}
	
	/**
	 * Start up the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Utils.setLookAndFeel();
		startApplication();
	}

}
