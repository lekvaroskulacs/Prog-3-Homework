package program;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the line that is drawn by the user. In essence, it is a sequence of
 * Fields (also referred to as nodes). Mostly the Table class is responsible for calling
 * the methods of this class in the correct context.
 *
 */
public class DrawnLine implements Serializable{

	private List<Field> line;
	
	private boolean finished;
	
	/**
	 * Creates an empty line.
	 */
	public DrawnLine() {
		line = new ArrayList<Field>();
		finished = false;
	}
	
	/**
	 * Creates a line, with its starting node set.
	 * @param startNode the starting node of the line.
	 */
	public DrawnLine(Field startNode) {
		line = new ArrayList<Field>();
		line.add(startNode);
		finished = false;
	}
	
	/**
	 * Get the length of this line.
	 * @return the number of nodes in this line.
	 */
	public int numOfNodes() {
		return line.size();
	}
	
	public boolean getFinished() {
		return finished;
	}
	
	public void setFinished(boolean fin) {
		finished = fin;
	}
	
	/**
	 * Adds a node to the end of this line.
	 * @param nextNode the node to be added.
	 */
	public void addNode(Field nextNode) {
		line.add(nextNode);
	}
	
	/**
	 * Checks if this line contains the given node.
	 * @param node the node to be checked.
	 * @return if this line contains the node.
	 */
	public boolean contains(Field node) {
		return line.contains(node);
	}
	
	/**
	 * Checks if the given node is the starting node of this line.
	 * @param node the node to be checked.
	 * @return if the node is the starting node.
	 */
	public boolean isStartNode(Field node) {
		return node == line.get(0);
	}
	
	/**
	 * Checks if the given node is the end node of this line.
	 * @param node the node to be checked.
	 * @return if the node is the end node.
	 */
	public boolean isEndNode(Field node) {
		return node == line.get(line.size()-1);
	}
	
	public Field getStart() {
		return line.get(0);
	}
	
	public Field getEnd() {
		return line.get(line.size() - 1);
	}
	
	/**
	 * Returns the element at the given index.
	 * @param i the index of the required element.
	 * @return the element, or null if the given index is out of bounds.
	 */
	public Field getElementAt(int i) {
		if (i >= line.size() || i < 0)
			return null;
		else
			return line.get(i);
	}
	
	/**
	 * Returns the index of the given Field in this line.
	 * @param f the Field to be examined.
	 * @return the index of f or -1 if it isn't in the line.
	 */
	public int getNodeIndex(Field f) {
		return line.indexOf(f);
	}
	
	/**
	 * Gets elements in the specified bounds from this line.
	 * Call from 0 to numOfNodes()-1 to get the entire line.
	 * @param from beginning index, inclusive.
	 * @param to end index, inclusive.
	 * @return the array of Fields which makes up this line.
	 */
	public Field[] getElements(int from, int to) {
		Field[] ret = new Field[to-from+1];
		int poz = 0;
		for (; from <= to; ++from) {
			ret[poz++] = line.get(from);
		}
		return ret;
	}
	
	/**
	 * Connects this line with another, one of their ending points are the same.
	 * If they aren't the same, nothing happens. The line may or may not be reversed
	 * while this function is running, meaning the start of this line may not be the same
	 * after calling this function.
	 * @param otherLine the line to connect this line with.
	 */
	public void connectWith(DrawnLine otherLine) {
		
		if (this.getStart() == otherLine.getStart()) {
			//don't get the first node of otherLine, because its the last node of this.line
			Field[] otherLineNodes = otherLine.getElements(1, otherLine.numOfNodes() - 1);
			Collections.reverse(this.line);
			for (Field f : otherLineNodes) {
				this.line.add(f);
			}
			
		} else if (this.getStart() == otherLine.getEnd()) {
			//don't get the last node of otherLine, for similar reasons
			Field[] otherLineNodes = otherLine.getElements(0, otherLine.numOfNodes() - 2);
			Collections.reverse(this.line);
			for (int i = otherLineNodes.length - 1; i >= 0; --i) {
				this.line.add(otherLineNodes[i]);
			}
			
		} else if (this.getEnd() == otherLine.getStart()) {
			
			Field[] otherLineNodes = otherLine.getElements(1, otherLine.numOfNodes() - 1);
			for (Field f : otherLineNodes) {
				this.line.add(f);
			}
		
		} else if (this.getEnd() == otherLine.getEnd()) {
			
			Field[] otherLineNodes = otherLine.getElements(0, otherLine.numOfNodes() - 2);
			for (int i = otherLineNodes.length - 1; i >= 0; --i) {
				this.line.add(otherLineNodes[i]);
			}
		}
		
	}
		
}
