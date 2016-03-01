package utils;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.UIManager;

import math.Graph;
import math.SquareMatrix;

/**
 * A utility class to hold convenience methods.
 *    - Log methods to print out message with a tag
 *    - Method to change the look and feel of the application
 *    - Method to draw a graph
 * @author Gunnar Arnesen
 *
 */
public class Utils {

	private static final String DEFAULT_TAG = "[P]";

	private Utils() {}

	/**
	 * Prints the sent message with the default tag.
	 * @param message
	 */
	public static void log(String message) {
		StringBuilder sb = new StringBuilder();
		sb.append(DEFAULT_TAG).append(" ").append(message);
		System.out.println(sb);
	}

	/**
	 * Prints the sent message with the provided tag.
	 * @param tag
	 * @param message
	 */
	public static void log(String tag, String message) {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(tag).append("] ").append(message);
		System.out.println(sb);
	}

	/**
	 * Try to change the look and feel of the application to that of the users system.
	 */
	public static void setLookAndFeel () {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			log("Error", "Look and feel not changed.");
		}
	}
	
	/**
	 * Draws this graph around a center point. The vertices are placed around
	 * with equal distances from each other. Then, the edges are placed between
	 * the vertices.
	 * @param g
	 * @param center
	 */
	public static void drawGraph(Graphics g, Graph graph, Point center) {
		SquareMatrix am = graph.getAdjacencyMatrix();
		int RADIUS = 150;
		int labelRad = RADIUS + 20;
		double div = 2 * Math.PI / am.getSize();
		double angle = 0;
		
		// Calculate and store the x,y coordinates for each vertex. Also draw the labels.
		ArrayList<Point> vertexPoints = new ArrayList<Point>();
		for (int i = 0; i < am.getSize(); i++) {
			int xCoord = (int)(RADIUS * Math.cos(angle) + center.x);
			int yCoord = (int)(RADIUS * Math.sin(angle) + center.y);
			g.drawString("V" + (i + 1), center.x + (int)(labelRad * Math.cos(angle)), center.y + (int)(labelRad * Math.sin(angle)));
			g.fillOval(xCoord - 4, yCoord - 4, 8, 8);
			vertexPoints.add(new Point(xCoord, yCoord));
			angle += div;
		}
		
		// Draw the edges between the vertices
		for (int row = 0; row < am.getSize() - 1; row++) {
			for (int col = row + 1; col < am.getSize(); col++) {
				if (am.get(row, col) == 1) {
					g.drawLine(vertexPoints.get(row).x, vertexPoints.get(row).y, vertexPoints.get(col).x, vertexPoints.get(col).y);
				}
			}
		}
	}

}