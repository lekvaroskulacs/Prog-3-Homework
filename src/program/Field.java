package program;

import java.io.Serializable;

import gui.*;


/**
 * Also functions as a node in the graph of the drawn line.
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
	
	public boolean isDiagonal(Field f) {
		boolean ret = false;
		//if both their coordinates are one apart
		if (Math.abs(f.getX() - x) == 1 && Math.abs(f.getY() - y) == 1)
			ret = true;
		return ret;
	}
	
	public Direction directionOfNeighbor(Field f) throws IllegalArgumentException {
		if (!isNeighbor(f))
			throw new IllegalArgumentException();
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
	 * Checks if the line passes through the node correctly
	 * @return
	 */
	abstract public boolean winConditionCheck(Field prevNode, Field nextNode);
	
	//checks if the given field is a pearl, and if it is, is it in the cycle
	abstract public boolean pearlInCycle();

}
