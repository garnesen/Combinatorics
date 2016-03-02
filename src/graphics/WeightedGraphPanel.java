package graphics;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import math.PrimsAlgorithm;
import utils.Utils;

@SuppressWarnings("serial")
public class WeightedGraphPanel extends JPanel {

	private final Point GRAPH_CENTER = new Point(GraphProjects.GRAPH_SIZE / 2, GraphProjects.GRAPH_SIZE / 2);

	private final String TAG = "Prim Graphics";
	private ArrayList<PrimsAlgorithm> graphs;
	private int currentGraphIndex;
	private boolean drawGraph;
	private boolean createdOnce;

	private JPanel arrowBar;
	private JPanel stepBar;

	private final JLabel title;

	public WeightedGraphPanel() {
		graphs = new ArrayList<PrimsAlgorithm>();
		currentGraphIndex = 0;
		drawGraph = false;

		this.setLayout(new BorderLayout());
		arrowBar = new JPanel();
		stepBar = new JPanel();
		arrowBar.setLayout(new FlowLayout(FlowLayout.CENTER));
		stepBar.setLayout(new FlowLayout(FlowLayout.CENTER));
		title = new JLabel();
	}

	public void create() {
		// The panel should only be built once. Adding more graphs can be done without destroying the panel.
		if (createdOnce) {
			return;
		}
		createdOnce = true;

		Utils.log(TAG, "Prim Data GUI");

		// Create the left arrow. Add a listener to shift the index left when clicked and update the data.
		final JButton leftArrow = new JButton("<--");
		leftArrow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentGraphIndex = currentGraphIndex == 0 ? graphs.size() - 1 : currentGraphIndex - 1;
				repaint();
			}
		});

		// Create the right arrow. Add a listener to shift the index right when clicked and update the data.
		final JButton rightArrow = new JButton("-->");
		rightArrow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentGraphIndex = currentGraphIndex == graphs.size() - 1 ? 0 : currentGraphIndex + 1;
				repaint();
			}
		});

		// Create the backwards step button
		final JButton backArrow = new JButton(" Step Back ");
		backArrow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				graphs.get(currentGraphIndex).stepBackward();
			}
		});

		// Create the forwards step button
		final JButton forwardArrow = new JButton("Step Forward");
		forwardArrow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				graphs.get(currentGraphIndex).stepForward();
			}
		});

		// Add the objects to the bar and add the bar to the whole panel.
		arrowBar.add(leftArrow);
		arrowBar.add(title);
		arrowBar.add(rightArrow);
		stepBar.add(backArrow);
		stepBar.add(forwardArrow);
		this.add(arrowBar, BorderLayout.PAGE_START);
		this.add(stepBar, BorderLayout.PAGE_END);
		this.revalidate();
		drawGraph = true;
		repaint();

		Utils.log(TAG, "Prim GUI built!");
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (drawGraph) {
			Utils.drawGraph(g, graphs.get(currentGraphIndex), GRAPH_CENTER);
		}
	}

	/**
	 * Add the graph sent to the list of graphs.
	 * @param graphRepresentation
	 * @return true if parsed, false otherwise
	 */
	public boolean input(String graphRepresentation) {
		Utils.log(TAG, "Parsing graph...");
		try {
			PrimsAlgorithm graph = new PrimsAlgorithm(graphRepresentation);
			graphs.add(graph);
		} catch (Exception e) {
			Utils.log("Error", "Could not create a graph for (" + graphRepresentation + ")");
			return false;
		}
		Utils.log(TAG, "Parsing complete!");
		return true;
	}

	/**
	 * Read each line in the input file and send it to the String input method.
	 * @param file
	 * @return true if parsed, false otherwise
	 */
	public boolean input(File file) {
		Utils.log(TAG, "Reading file...");
		try (Stream<String> lines = Files.lines(file.toPath())) {
			lines.forEach(this::input);
		} catch (IOException e) {
			Utils.log("Error", "Could not read the input file: " + file.getPath());
			return false;
		}
		Utils.log(TAG, "File read complete!");
		return true;
	}

	/**
	 * Gets the InputNotifier for this panel.
	 * @return the notifier
	 */
	public InputNotifier getNotifier() {
		return new InputNotifier() {

			@Override
			public void onEnter(String data) {
				File inputFile = new File(data);
				if (inputFile.exists()) {
					if (!input(inputFile)) {
						Utils.log(TAG, "Halting graph creation.");
						return;
					}
				} else {
					if (!input(data)) {
						Utils.log(TAG, "Halting graph creation.");
						return;
					}
				}
				create();
			}
		};
	}

}
