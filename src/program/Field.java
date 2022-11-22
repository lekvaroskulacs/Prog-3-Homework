package program;

import gui.*;

/**
 * Also functions as a node in the graph of the drawn line.
 */
abstract public class Field {
	
	protected int x;
	
	protected int y;
	
	protected Table table;
	
	//the graphical panel which corresponds to the field
	protected TablePanel panel; 
	
	public TablePanel getPanel() {
		return panel;
	}
	
	public Table getTable() {
		return table;
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
	
	/**
	 * Checks if the line passes through the node correctly
	 * @return
	 */
	abstract public boolean passThroughCheck();

}
