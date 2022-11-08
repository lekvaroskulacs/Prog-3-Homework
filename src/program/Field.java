package program;

/**
 * Also functions as a node in the graph of the drawn line.
 */
abstract public class Field {
	
	protected Table table;
	
	
	/**
	 * Checks if the line passes through the node correctly
	 * @return
	 */
	abstract public boolean passThroughCheck();
}
