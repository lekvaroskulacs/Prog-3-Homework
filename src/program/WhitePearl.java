package program;

import gui.WhitePearlPanel;

public class WhitePearl extends Field {
	
	public WhitePearl(Table t) {
		table = t;
		panel = new WhitePearlPanel(this);
	}
	
	public boolean winConditionCheck(Field prevNode, Field nextNode) {
		boolean ret = true;
		if (directionOfNeighbor(prevNode) == Direction.south && directionOfNeighbor(nextNode) != Direction.north)
			ret = false;
		if (directionOfNeighbor(prevNode) == Direction.west && directionOfNeighbor(nextNode) != Direction.east)
			ret = false;
		if (directionOfNeighbor(prevNode) == Direction.north && directionOfNeighbor(nextNode) != Direction.south)
			ret = false;
		if (directionOfNeighbor(prevNode) == Direction.east && directionOfNeighbor(nextNode) != Direction.west)
			ret = false;
		return ret;
	}
	
	public String toString() {
		return "WhitePearl";
	}

	@Override
	public boolean pearlInCycle() {
		return getTable().getLine().contains(this);
	}

}
