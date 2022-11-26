package program;

import gui.BlackPearlPanel;

public class BlackPearl extends Field {
	
	public BlackPearl(Table t) {
		table = t;
		panel = new BlackPearlPanel(this);
	}
	
	public boolean winConditionCheck(Field prevNode, Field nextNode) {
		boolean ret = true;
		if ((directionOfNeighbor(prevNode) == Direction.north || directionOfNeighbor(prevNode) == Direction.south) && 
			!(directionOfNeighbor(nextNode) == Direction.west || directionOfNeighbor(nextNode) == Direction.east))
			ret = false;
		if ((directionOfNeighbor(prevNode) == Direction.west || directionOfNeighbor(prevNode) == Direction.east) && 
			!(directionOfNeighbor(nextNode) == Direction.north || directionOfNeighbor(nextNode) == Direction.south))
			ret = false;
		return ret;
	}
	
	public String toString() {
		return "BlackPearl";
	}
	
	@Override
	public boolean pearlInCycle() {
		return getTable().getLine().contains(this);
	}
}
