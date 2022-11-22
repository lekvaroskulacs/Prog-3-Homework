package program;

import gui.WhitePearlPanel;

public class WhitePearl extends Field {
	
	public WhitePearl(Table t) {
		table = t;
		panel = new WhitePearlPanel(this);
	}
	
	public boolean passThroughCheck() {
		return true;
	}
	
	public String toString() {
		return "WhitePearl";
	}

}
