package program;

import gui.BlackPearlPanel;

public class BlackPearl extends Field {
	
	public BlackPearl(Table t) {
		table = t;
		panel = new BlackPearlPanel(this);
	}
	
	public boolean passThroughCheck() {
		return true;
	}
	
	public String toString() {
		return "BlackPearl";
	}
}
