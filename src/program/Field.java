package program;

import java.io.Serializable;

import gui.*;


/**
 * A Field that makes up the Table the game is played on.
 * Also functions as a node in the line that user might draw.
 * Is an abstract class, that has three possible implementations.
 */
abstract public class Field implements Serializable {
	
	protected int x;
	
	protected int y;
	
	protected Table table;
	
	protected DrawnLine line = null;
	
	protected boolean hasLine = false;
	
	//the graphical panel which corresponds to the field
	protected TablePanel panel; 
	
	public TablePanel getPanel() {
		return panel;
	}
	
	public Table getTable() {
		return table;
	}
	
	public DrawnLine getLine() {
		return line;
	}
	
	public void setLine(DrawnLine l) {
		line = l;
	}
	
	public boolean hasLine() {
		return hasLine;
	}
	
	public void setHasLine(boolean b) {
		hasLine = b;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
 	
	/**
	 * Checks if given Field is a neighbor of this Field on the table.
	 * @param f the field to be examined
	 * @return if f is neighbor of this
	 */
	public boolean isNeighbor(Field f) {
		boolean ret = false;
		//if their x and y coordinates are 1 away at most
		if (Math.abs(f.getX() - x) <= 1 && Math.abs(f.getY() - y) <= 1)
			ret = true;
		//but not if they are the same
		if (f.getX() == x && f.getY() == y)
			ret = false;
		return ret;
	}
	
	/**
	 * Checks if given Field is diagonally one square away from this Field on the Table.
	 * @param f the field to be examined
	 * @return if f is neighbor of this
	 */
	public boolean isDiagonal(Field f) {
		boolean ret = false;
		//if both their coordinates are one apart
		if (Math.abs(f.getX() - x) == 1 && Math.abs(f.getY() - y) == 1)
			ret = true;
		return ret;
	}
	
	/**
	 * Returns the Direction in which the given field is, in relation to this Field
	 * @param f the field to be examined
	 * @return the Direction of f in relation to this Field
	 * @throws IllegalArgumentException
	 */
	public Direction directionOfNeighbor(Field f) throws IllegalArgumentException {
		if (line.numOfNodes() < 3 && 
			!(this == line.getElementAt(line.numOfNodes()-2) || this == line.getElementAt(1))) {
			if (!isNeighbor(f))
				throw new IllegalArgumentException();
		}
		
		if (x < f.getX()) 
			return Direction.east;
		else if (x > f.getX())
			return Direction.west;
		else if (y < f.getY()) 
			return Direction.south;
		else 
			return Direction.north;
	}
	
	/**
	 * Returns whether the line defined by this, node1 and node2 is a ninety degree turn,
	 * meaning that it changes direction.
	 * @param node1 the first node
	 * @param node2 the second node
	 * @return Whether the line defined by this, node1 and node2 is a ninety degree turn
	 */
	public boolean isNinetyDegreeTurn(Field node1, Field node2) {
		boolean result = true;
		if (directionOfNeighbor(node1) == Direction.south && node1.directionOfNeighbor(node2) == Direction.south)
			result = false;
		if (directionOfNeighbor(node1) == Direction.east && node1.directionOfNeighbor(node2) == Direction.east)
			result = false;
		if (directionOfNeighbor(node1) == Direction.north && node1.directionOfNeighbor(node2) == Direction.north)
			result = false;
		if (directionOfNeighbor(node1) == Direction.west && node1.directionOfNeighbor(node2) == Direction.west)
			result = false;
		return result;
	}
	
	/**
	 * Checks if the line defined by prevNode, this and nextNode 
	 * passes through this node correctly, in context of Masyu rules, 
	 * or in other words, if the win condition applies to this Field.
	 * @param prevNode
	 * @param nextNode
	 * @return true if the win condition applies to this field
	 */
	abstract public boolean winConditionCheck(Field prevNode, Field nextNode);
	
	/**
	 * Checks is this Field is in the cycle of the Table's line, if it is needed to be. 
	 * Therefore, only call this, if a cycle is guaranteed.
	 * A BlankField does not need to be part of the cycle, but pearls are required, according to Masyu rules.
	 * @return true if this Field is part of the cycle, or it doesn't need to be part of it.
	 */
	abstract public boolean pearlInCycle();

}
