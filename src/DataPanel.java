import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This panel presents the interpreted graphs and data on them.
 * Graphs are drawn by placing the vertices around a circle with
 * equal distances then drawing the edges.
 * @author Gunnar Arnesen
 *
 */
@SuppressWarnings("serial")
public class DataPanel extends JPanel {

	private final Point GRAPH_CENTER = new Point(250, 225);
	private ArrayList<Graph> graphs;
	private HashMap<Graph, int[]> graphToVertexPair;
	private int currentGraphIndex;
	private boolean drawGraph;
	private boolean createdOnce;
	
	private JPanel arrowBar;
	final JLabel title;
	final JLabel data;

	/**
	 * Creates a DataPanel object.
	 */
	public DataPanel() {
		graphs = new ArrayList<Graph>();
		graphToVertexPair = new HashMap<Graph, int[]>();
		currentGraphIndex = 0;
		drawGraph = false;
		this.setLayout(new BorderLayout());
		
		arrowBar = new JPanel();
		arrowBar.setLayout(new FlowLayout(FlowLayout.CENTER));
		title = new JLabel();
		data = new JLabel();
		data.setFont(new Font(data.getFont().getName(), Font.PLAIN, 11));
	}

	/**
	 * Builds the UI for the graph presentation panel.
	 */
	public void create() {
		// The panel should only be built once. Adding more graphs can be done without destroying the panel.
		if (createdOnce) {
			return;
		}
		createdOnce = true;
		
		Utils.log("Creating Data GUI");
		
		// Create the left arrow. Add a listener to shift the index left when clicked and update the data.
		final JButton leftArrow = new JButton("<--");
		leftArrow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentGraphIndex = currentGraphIndex == 0 ? graphs.size() - 1 : currentGraphIndex - 1;
				updateData();
				repaint();
			}
		});
		
		// Create the right arrow. Add a listener to shift the index left when clicked.
		final JButton rightArrow = new JButton("-->");
		rightArrow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentGraphIndex = currentGraphIndex == graphs.size() - 1 ? 0 : currentGraphIndex + 1;
				updateData();
				repaint();
			}
		});
		
		// Add the objects to the bar and add the bar to the whole panel.
		arrowBar.add(leftArrow);
		arrowBar.add(title);
		arrowBar.add(rightArrow);
		this.add(arrowBar, BorderLayout.PAGE_START);
		this.add(data, BorderLayout.PAGE_END);
		updateData();
		this.revalidate();
		drawGraph = true;
		repaint();
		
		Utils.log("Data GUI built!");

	}
	
	/**
	 * Resets the data when the right/left arrow is clicked.
	 */
	private void updateData() {
		// Formating for the data to be displayed.
		String FORMAT = "Connected: %s      Has Cycle: %s      Bipartite: %s";
		String FORMAT_PATH = "      Path from %d to %d: %s";
		
		// Grab the data of the graph and set it on the screen.
		title.setText("Graph " + (currentGraphIndex + 1));
		Graph graph = graphs.get(currentGraphIndex);
		String text = String.format(FORMAT, graph.is_connected(), graph.has_cycle(), graph.is_bipartite());
		if (graphToVertexPair.get(graph) != null) {
			int[] vertexPair = graphToVertexPair.get(graph);
			data.setText(text + String.format(FORMAT_PATH, vertexPair[0], vertexPair[1], graph.shortestPath(vertexPair[0], vertexPair[1])));
		} else {
			data.setText(text);
		}
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
	 * 
	 * First the method checks to see if two extra vertices are appended for the shortedPath method.
	 * If there is a vertex pair, remove it from the graph representation and map the pair to the graph
	 * for later.
	 * @param graphRepresentation
	 */
	public boolean input(String graphRepresentation) {
		Utils.log("Parsing graph...");
		try {
			// Get the values by splitting at the comma.
			String [] values = graphRepresentation.split(",");
			int numVertices = Integer.parseInt(values[0]);
			// Check if this representation has two extra vertices appended
			int[] vertexPair = null;
			if (numVertices * numVertices + 3 == values.length) {
				graphRepresentation = String.join(",", Arrays.copyOf(values, values.length-2));
				vertexPair = new int[] {Integer.parseInt(values[values.length - 2]), Integer.parseInt(values[values.length - 1])};
			}
			// Try to create a new graph, add it to the list, and map it.
			Graph graph = new Graph(graphRepresentation);
			graphs.add(graph);
			graphToVertexPair.put(graph, vertexPair);
		} catch (Exception e) {
			Utils.log("Error", "Could not create a graph for (" + graphRepresentation + ")");
			return false;
		}
		Utils.log("Parsing complete!");
		return true;
	}

	/**
	 * Read each line in the input file and send it to the String input method.
	 * @param f
	 */
	public boolean input(File file) {
		Utils.log("Reading file...");
		try (Stream<String> lines = Files.lines(file.toPath())) {
			lines.forEach(this::input);
		} catch (IOException e) {
			Utils.log("Error", "Could not read the input file: " + file.getPath());
			return false;
		}
		Utils.log("File read complete!");
		return true;
	}

}
