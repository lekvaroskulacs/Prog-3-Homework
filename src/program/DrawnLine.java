package program;

import java.util.ArrayList;
import java.util.List;

public class DrawnLine {

	private List<Field> line;
	
	public DrawnLine() {
		line = new ArrayList<Field>();
	}
	
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
	
	public boolean contains(Field node) {
		return line.contains(node);
	}
	
	public boolean isStartNode(Field node) {
		return node == line.get(0);
	}
	
	public boolean isEndNode(Field node) {
		return node == line.get(line.size()-1);
	}
	
	public Field getStart() {
		return line.get(0);
	}
	
	public Field getEnd() {
		return line.get(line.size() - 1);
	}
	
	public Field[] getElements(int from, int to) {
		Field[] ret = new Field[to-from+1];
		for (; from <= to; ++from) {
			ret[from] = line.get(from);
		}
		return ret;
	}
	
	public void connectWith(DrawnLine line) {
		
	}
		
}
