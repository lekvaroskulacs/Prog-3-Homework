package program;

public class WhitePearl extends Field {
	
	public WhitePearl(Table t) {
		table = t;
	}
	
	public boolean passThroughCheck() {
		return true;
	}
	
	public String toString() {
		return "WhitePearl";
	}

}
