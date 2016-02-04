import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DataPanel extends JPanel {

	private final Point GRAPH_CENTER = new Point(250, 250);
	private ArrayList<Graph> graphs;
	private HashMap<Graph, int[]> graphToVertexPair;
	private int currentGraphIndex;
	private boolean drawGraph;

	public DataPanel() {
		graphs = new ArrayList<Graph>();
		graphToVertexPair = new HashMap<Graph, int[]>();
		currentGraphIndex = 0;
		drawGraph = false;
	}

	public void create() {
		Utils.log("Creating Data GUI");
		drawGraph = true;
		repaint();
		Utils.log("Data GUI built!");

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
