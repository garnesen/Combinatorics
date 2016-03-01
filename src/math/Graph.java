package math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A graph represented by a SquareMatrix, more specifically an adjacency matrix. The
 * class provides methods to find if the graph is connected, has cycles, is bipartite,
 * and shortest paths.
 * @author Gunnar Arnesen
 *
 */
public class Graph {

	/** am --> Adjacency Matrix **/
	private SquareMatrix am;
	private int numVertices;

	/** Store results to method calls here so they do not need to be called multiple times**/
	private HashMap<String, Boolean> results;
	private HashMap<String, String> shortestPathResults;


	/**
	 * Create a Graph from a SquareMatrix (adjacency matrix).
	 * @param am
	 */
	public Graph(SquareMatrix am) {
		this.am = am;
		numVertices = am.getSize();
		results = new HashMap<String, Boolean>();
		shortestPathResults = new HashMap<String, String>();
	}

	/**
	 * Creates a Graph from the string representation of it.
	 * @param amRepresentation
	 */
	public Graph(String amRepresentation) {
		if ((am = parseGraph(amRepresentation)) == null) {
			throw new IllegalArgumentException("Graph representation is invalid: " + amRepresentation);
		}
		results = new HashMap<String, Boolean>();
		shortestPathResults = new HashMap<String, String>();
	}

	/**
	 * Get the SquareMatrix that represents the adjacency matrix of this graph.
	 * @return the SquareMatrix
	 */
	public SquareMatrix getAdjacencyMatrix() {
		return am;
	}

	/**
	 * This method checks if the graph is connected.
	 * 
	 * To do so, start with an empty list of vertices. Then call the recursive function
	 * travelToAdjacentVertices. If the size of the list equals the size of the matrix,
	 * then all vertices were traveled to.
	 * @return true if connected, false otherwise
	 */
	private List<Integer> verticesSeen;
	public boolean is_connected() {
		// Check if this method has already been called once
		if (results.containsKey("is_connected")) {
			return results.get("is_connected");
		}

		verticesSeen = new ArrayList<Integer>();
		travelToAdjacentVertices(0);

		// Store the result
		results.put("is_connected", verticesSeen.size() == am.getSize());
		return results.get("is_connected");
	}

	/**
	 * This recursive function collects all vertices that can be traveled to from the
	 * sent vertex. To do this, first add the vertex to the list. Then, take the given 
	 * vertex and see if there is a 1 in it's row in the adjacency matrix. This means 
	 * there is an edge from the sent vertex to the new vertex. Next, check to make sure 
	 * this vertex has not already been found by looking in the list. If it has not been 
	 * found, travel to the adjacent vertices of the new vertex.
	 * @param vertex - The starting vertex
	 */
	private void travelToAdjacentVertices(int vertex) {
		verticesSeen.add(vertex);
		for (int adjVertex : getAdjacentVertices(vertex)) {
			if (!verticesSeen.contains(adjVertex)) {
				travelToAdjacentVertices(adjVertex);
			}
		}
	}

	/**
	 * This method checks if the graph has cycles.
	 * 
	 * First start with a cloned graph and a list with all the vertices. Then, while there are
	 * still vertices in the list and not 'done' remove vertices from the graph and list that 
	 * have 0 or 1 adjacent vertices. These vertices will not create a cycle, so remove them.
	 * If the list becomes empty, there are no cycles. If there is a point where no more 
	 * vertices can be removed then it is 'done'; this means a cycle is in the graph.
	 * @return true if the graph contain a cycle, false otherwise
	 */
	public boolean has_cycle() {
		// Check if this method has already been called once
		if (results.containsKey("has_cycle")) {
			return results.get("has_cycle");
		}

		// Make a copy of the current graph so we can edit it
		Graph temp = new Graph(am.clone());

		// Fill a list with the vertices
		List<Integer> vertices = IntStream.range(0, am.getSize()).boxed().collect(Collectors.toList());

		// Keep removing nodes with 1 or 0 adjacent vertices until the list is empty or there are none left
		boolean done = false;
		while (vertices.size() > 0 && !done) {
			done = true;
			for (int i = 0; i < vertices.size(); i++) {
				int vertex = vertices.get(i);
				int adjVertices = temp.getAdjacentVertices(vertex).size();
				if (adjVertices == 1) {
					vertices.remove(i);
					temp.disconnectVertex(vertex);
					done = false;
					break;
				} else if (adjVertices == 0) {
					vertices.remove(i);
				}
			}
		}

		// If all vertices were removed, there are no cycles.
		if (vertices.size() == 0) {
			results.put("has_cycle", false);
		} else {
			results.put("has_cycle", true);
		}

		return results.get("has_cycle");
	}

	/**
	 * Check if the graph is bipartite.
	 * 
	 * First clone the adjacency matrix so it can be modified. Then, calculate (adjacency matrix)^n
	 * where 3 < n <= size and n is odd. For each of these graphs, check if the matrix has any values
	 * that are greater than 0 in the diagonal. If this is true, there is an odd cycle meaning the graph
	 * is not bipartite. If none of the computer matrices have values greater than 0 in the diagonal,
	 * the graph is bipartite.
	 * @return true if the graph is bipartite, false otherwise
	 */
	public boolean is_bipartite() {
		// Check if this method has already been called once
		if (results.containsKey("is_bipartite")) {
			return results.get("is_bipartite");
		}
		
		// Computer (matrix)^n where 3<n<=size and check the diagonals
		SquareMatrix power = am.clone();
		for (int i = 3; i <= am.getSize(); i+=2) {
			power = power.multiply(power).multiply(power);
			if (power.getDiagonal().stream().anyMatch(a -> a > 0)) {
				results.put("is_bipartite", false);
				return false;
			}
		}
		
		// Store the result
		results.put("is_bipartite", true);
		return true;
	}

	/**
	 * Calculates a shortest path from the start to end vertex.
	 * 
	 * This algorithm uses a BFS approach to find the shortest path from start to end vertex. Most of
	 * the code uses the Java 8 stream API. The code works by getting the adjacent vertices multiple
	 * times until the end node is found. Then the path from start to end is built and printed.
	 * @param start
	 * @param end
	 * @return a string representing a shortest path from start to end vertex
	 */
	public String shortestPath(int start, int end) {
		// Check if this method has already been called once
		String key = start + "_" + end;
		if (shortestPathResults.containsKey(key)) {
			return shortestPathResults.get(key);
		}
		
		// Fix the indices
		int fixedStart = start - 1;
		int fixedEnd = end - 1;

		// Create lists to hold the vertices traversed and current nodes
		List<Integer> visitedVertices = new ArrayList<>(Arrays.asList(fixedStart));
		List<PathNode> nodes = Collections.singletonList(new PathNode(fixedStart, null));
		
		// BFS until the end vertex is found
		while (!visitedVertices.contains(fixedEnd)) {
			nodes = nodes.stream()
					.flatMap(
							node -> getAdjacentVertices(node.val).stream()
							.filter(val -> !visitedVertices.contains(val))
							.map(val -> new PathNode(val, node))
					).collect(Collectors.toList());
			nodes.stream().map(node -> node.val).forEach(visitedVertices::add);
		}
		
		PathNode endNode = nodes.stream().filter(node -> node.val == fixedEnd).findFirst().get();

		// Build the output string
		StringBuilder result = new StringBuilder();
		while (endNode != null) {
			result.insert(0, ",").insert(1, endNode.val + 1);
			endNode = endNode.prev;
		}
		result.replace(0,1,"");
		
		// Store the result
		shortestPathResults.put(key, result.toString());
		return result.toString();
	}
	
	/**
	 * A Node that contains a pointer to the Node before it in a path.
	 */
	private class PathNode {
		int val;
		PathNode prev;
		public PathNode(int val, PathNode prev) {
			this.val = val;
			this.prev = prev;
		}
	}
	
	/**
	 * Removes all edges that are coming off the given vertex.
	 * @param vertex
	 * @return the number of edges removed
	 */
	public int disconnectVertex(int vertex) {
		int edgesRemoved = 0;
		for (int col = 0; col < am.getSize(); col++) {
			if (am.get(vertex, col) == 1) {
				am.set(vertex, col, 0);
				am.set(col, vertex, 0);
				edgesRemoved++;
			}
		}
		return edgesRemoved;
	}

	/**
	 * Gets a list of vertices adjacent to the given vertex.
	 * @param vertex
	 * @return
	 */
	public List<Integer> getAdjacentVertices(int vertex) {
		List<Integer> vertices = new ArrayList<Integer>();
		for (int col = 0; col < am.getSize(); col++) {
			if (am.get(vertex, col) == 1) {
				vertices.add(col);
			}
		}
		return vertices;
	}

	/**
	 * Parses an adjacency matrix from it's string representation.
	 * @param rep
	 * @return the SquareMatrix if parsed or null if invalid
	 */
	private SquareMatrix parseGraph(String rep) {
		String[] values = rep.split(",");
		numVertices = Integer.parseInt(values[0]);

		// Check to make sure this graph is actually valid!
		if (values.length != numVertices * numVertices + 1) {
			return null;
		}

		// Convert the graph from a string to a matrix.
		int[][] graphMatrix = new int[numVertices][numVertices];
		for (int row = 0; row < numVertices; row++) {
			for (int col = 0; col < numVertices; col++) {
				graphMatrix[row][col] = Integer.parseInt(values[row * numVertices + col + 1]);
			}
		}
		return new SquareMatrix(graphMatrix);
	}

}