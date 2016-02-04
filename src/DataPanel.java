import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DataPanel extends JPanel {

	private ArrayList<Graph> graphs;

	public DataPanel() {
		graphs = new ArrayList<Graph>();
	}

	/**
	 * Add the graph sent to the list of graphs. If it has an invalid representation,
	 * print an error and do not add it to the list.
	 * @param graphRepresentation
	 */
	public void input(String graphRepresentation) {
		try {
			Graph graph = new Graph(graphRepresentation);
			graphs.add(graph);
		} catch (Exception e) {
			Utils.log("Error", "Could not create a graph for (" + graphRepresentation + "): " + e.getMessage());
		}
	}

	/**
	 * Read each line in the input file and send it to the String input method.
	 * @param f
	 */
	public void input(File file) {
		try (Stream<String> lines = Files.lines(file.toPath())) {
			lines.forEach(this::input);
		} catch (IOException e) {
			Utils.log("Error", "Could not read the input file: " + file.getPath());
		}
	}

}
