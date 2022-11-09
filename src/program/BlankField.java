package program;

public class BlankField extends Field {
	
	public BlankField(Table t) {
		table = t;
	}
	
	public boolean passThroughCheck() {
		return true;
	}
	
	public String toString() {
		return "BlankField";
	}
	
}
