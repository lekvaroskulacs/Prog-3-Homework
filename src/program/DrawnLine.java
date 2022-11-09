package program;

import java.util.ArrayList;
import java.util.List;

public class DrawnLine {

	private List<Field> line;
	
	public DrawnLine(Field startNode) {
		line = new ArrayList<Field>();
		line.add(startNode);
	}
	
	public int numOfNodes() {
		return line.size();
	}
	
	public int numOfEdges() {
		return line.size()-1;
	}
	
	public void addNode(Field nextNode) {
		line.add(nextNode);
	}
		
}
