public class Graph {

	// am = Adjacency Matrix
	private SquareMatrix am;
	private int numVertices;

	public Graph(SquareMatrix am) {
		this.am = am;
		numVertices = am.getSize();
	}

	public Graph(String amRepresentation) {
		if ((am = parseGraph(amRepresentation)) == null) {
			throw new IllegalArgumentException("Graph representation is invalid: " + amRepresentation);
		}
	}
	
	public SquareMatrix getAdjacencyMatrix() {
		return am;
	}

	public boolean is_connected() {
		return false;
	}

	public boolean has_cycle() {
		return false;
	}

	public boolean is_bipartite() {
		return false;
	}

	public String shortestPath(int start, int end) {
		return null;
	}
	
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