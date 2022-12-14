package program;

import gui.EmptyPanel;

public class BlankField extends Field {
	
	public BlankField(Table t) {
		table = t;
		panel = new EmptyPanel(this);
	}
	
	/**
	 * In the case of a BlankField, always returns true.
	 */
	public boolean winConditionCheck(Field prevNode, Field nextNode) {
		return true;
	}
	
	public String toString() {
		return "BlankField";
	}

	@Override
	public boolean pearlInCycle() {
		return true;
	}
	
}
