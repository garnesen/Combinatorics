package math;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import utils.Utils;

/**
 * A class that represents a weighted graph. It extends the functionality
 * of the Graph; the only difference is the values in the adjacency matrix 
 * as convey the weight of a connection between nodes. 
 * @author Gunnar Arnesen
 *
 */
public class PrimsAlgorithm extends Graph {

	/**
	 * Create a WeightedGraph from a SquareMatrix (adjacency matrix).
	 * @param am
	 */
	public PrimsAlgorithm(SquareMatrix am) {
		super(am);
		edgesVisited = new ArrayList<Point>();
		verticesVisited = new ArrayList<Integer>();
		currentWeight = 0;
	}

	/**
	 * Creates a WeightedGraph from the string representation of it.
	 * @param amRepresentation
	 */
	public PrimsAlgorithm(String amRepresentation) {
		super(amRepresentation);
		edgesVisited = new ArrayList<Point>();
		verticesVisited = new ArrayList<Integer>();
		currentWeight = 0;
	}

	private List<Point> edgesVisited; 
	private List<Integer> verticesVisited;
	private int currentWeight;
	
	/**
	 * Steps forward once in Prim's algorithm. This is defined by either getting
	 * a random vertex if this is the first step or adding a vertex adjacent to
	 * one of the current visited vertices that has the least-expensive edge.
	 * @return true if a step is performed, false otherwise
	 */
	public boolean stepForward() {
		//  All the vertices have visited.
		if (verticesVisited.size() == this.getNumberVertices()) {
			Utils.log("Prim", "Total weight of minimal spanning tree: " + currentWeight);
			return false;
		}

		// The algorithm is taking the first step, pick a random vertex.
		if (verticesVisited.size() == 0) {
			int vertex = (int) (Math.random() * this.getNumberVertices());
			verticesVisited.add(vertex);
			Utils.log("Prim", "Added random vertex " + (vertex + 1));
			return true;
		}

		// Pick a least-expensive edge with one vertex in the array and one not.
		int minEdge = Integer.MAX_VALUE;
		int curVertex = -1;
		int newVertex = -1;
		SquareMatrix am = getAdjacencyMatrix();
		for (int vertex : verticesVisited) {
			List<Integer> uniqueAdjacentVertices = getAdjacentVertices(vertex).stream().filter(v -> !verticesVisited.contains(v)).collect(Collectors.toList());
			for (int adjVertex : uniqueAdjacentVertices) {
				if (minEdge > am.get(vertex, adjVertex)) {
					minEdge = am.get(vertex, adjVertex);
					curVertex = vertex;
					newVertex = adjVertex;
				}
			}
		}
		edgesVisited.add(new Point(curVertex, newVertex));
		verticesVisited.add(newVertex);
		currentWeight += minEdge;

		// Log the data
		StringBuilder sb = new StringBuilder("Using edge ").append(curVertex + 1).append("-").append(newVertex + 1).append(", with weight ").append(minEdge);
		Utils.log("Prim", sb.toString());

		return true;
	}

	/**
	 * Steps backwards in Prim's algorithm. This is done by removing the vertex
	 * last visited.
	 * @return true if step backwards is performed, false otherwise
	 */
	public boolean stepBackward() {
		// There are no vertices so we cannot step backwards in the algorithm.
		if (verticesVisited.size() == 0) {
			Utils.log("Prim", "Cannot step back more");
			return false;
		}

		// Remove the last step made.
		int vertex = verticesVisited.remove(verticesVisited.size() - 1);
		if (edgesVisited.size() > 0) {
			Point edge = edgesVisited.remove(edgesVisited.size() - 1);
			currentWeight -= getAdjacencyMatrix().get(edge.x, edge.y);
			Utils.log("Prim", "Removed vertex " + (vertex+1) + " and edge " + (edge.x+1) + "-" + (edge.y+1));
		} else {
			Utils.log("Prim", "Removed vertex " + (vertex+1));
		}

		return true;
	}

	/**
	 * Resets the algorithm by clearing the list.
	 */
	public void reset() {
		verticesVisited.clear();
	}

	/**
	 * Gets a list of vertices that have been visited.
	 * @return the list of visited vertices
	 */
	public List<Integer> getVerticesVisited() {
		return verticesVisited;
	}

	/**
	 * Get a list of edges that have been visited.
	 * @return the list of visited edges
	 */
	public List<Point> getEdgesVisited() {
		return edgesVisited;
	}

	/**
	 * Check if a given edge has been visited.
	 * @param edge
	 * @return
	 */
	public boolean seenEdge(Point edge) {
		return edgesVisited.stream().anyMatch(e -> (e.x == edge.x && e.y == edge.y) || (e.x == edge.y && e.y == edge.x));
	}

}
