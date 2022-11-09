package program;

public class BlackPearl extends Field {
	
	public BlackPearl(Table t) {
		table = t;
	}
	
	public boolean passThroughCheck() {
		return true;
	}
	
	public String toString() {
		return "BlackPearl";
	}
}
