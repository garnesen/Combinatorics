package math;

import java.util.ArrayList;
import java.util.List;

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
		verticesVisited = new ArrayList<Integer>();
	}
	
	/**
	 * Creates a WeightedGraph from the string representation of it.
	 * @param amRepresentation
	 */
	public PrimsAlgorithm(String amRepresentation) {
		super(amRepresentation);
		verticesVisited = new ArrayList<Integer>();
	}
	
	private List<Integer> verticesVisited;
	
	/**
	 * Steps forward once in Prim's algorithm. This is defined by either getting
	 * a random vertex if this is the first step or adding a vertex adjacent to
	 * one of the current visited vertices that has the least-expensive edge.
	 * @return true if a step is performed, false otherwise
	 */
	public boolean stepForward() {
		//  All the vertices have visited.
		if (verticesVisited.size() == this.getNumberVertices()) {
			return false;
		}
		
		// The algorithm is taking the first step, pick a random vertex.
		if (verticesVisited.size() == 0) {
			int vertex = (int) (Math.random() * this.getNumberVertices());
			verticesVisited.add(vertex);
			return true;
		}
		
		// Pick a least-expensive edge with one vertex in the array and one not.
		int minEdge = Integer.MAX_VALUE;
		int newVertex = -1;
		SquareMatrix am = getAdjacencyMatrix();
		for (int vertex : verticesVisited) {
			for (int adjVertex : getAdjacentVertices(vertex)) {
				if (minEdge > am.get(vertex, adjVertex)) {
					minEdge = am.get(vertex, adjVertex);
					newVertex = adjVertex;
				}
			}
		}
		verticesVisited.add(newVertex);
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
			return false;
		}
		
		// Remove the last step made.
		verticesVisited.remove(verticesVisited.size() - 1);
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
	
}
