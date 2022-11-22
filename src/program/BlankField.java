package program;

import gui.EmptyPanel;

public class BlankField extends Field {
	
	public BlankField(Table t) {
		table = t;
		panel = new EmptyPanel(this);
	}
	
	public boolean passThroughCheck() {
		return true;
	}
	
	public String toString() {
		return "BlankField";
	}
	
}
