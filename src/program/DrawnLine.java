package program;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawnLine {

	private List<Field> line;
	
	private boolean finished;
	
	public DrawnLine() {
		line = new ArrayList<Field>();
		finished = false;
	}
	
	public DrawnLine(Field startNode) {
		line = new ArrayList<Field>();
		line.add(startNode);
		finished = false;
	}
	
	public int numOfNodes() {
		return line.size();
	}
	
	public boolean getFinished() {
		return finished;
	}
	
	public void setFinished(boolean fin) {
		finished = fin;
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
	
	//returns null if out of bounds
	public Field getElementAt(int i) {
		if (i >= line.size() || i < 0)
			return null;
		else
			return line.get(i);
	}
	
	public int getNodeIndex(Field f) {
		return line.indexOf(f);
	}
	
	//gets elements from the first parameter to the second, including both
	//to get the entire line, call from 0 to numOfNodes-1
	public Field[] getElements(int from, int to) {
		Field[] ret = new Field[to-from+1];
		int poz = 0;
		for (; from <= to; ++from) {
			ret[poz++] = line.get(from);
		}
		return ret;
	}
	
	public void connectWith(DrawnLine otherLine) {
		
		if (this.getStart() == otherLine.getStart()) {
			//don't get the first node of otherLine, because its the last node of this.line
			Field[] otherLineNodes = otherLine.getElements(1, otherLine.numOfNodes() - 1);
			Collections.reverse(this.line);
			for (Field f : otherLineNodes) {
				this.line.add(f);
			}
			
		} else if (this.getStart() == otherLine.getEnd()) {
			//don't get the last node of otherLine, for similar reasons
			Field[] otherLineNodes = otherLine.getElements(0, otherLine.numOfNodes() - 2);
			Collections.reverse(this.line);
			for (int i = otherLineNodes.length - 1; i >= 0; --i) {
				this.line.add(otherLineNodes[i]);
			}
			
		} else if (this.getEnd() == otherLine.getStart()) {
			
			Field[] otherLineNodes = otherLine.getElements(1, otherLine.numOfNodes() - 1);
			for (Field f : otherLineNodes) {
				this.line.add(f);
			}
		
		} else if (this.getEnd() == otherLine.getEnd()) {
			
			Field[] otherLineNodes = otherLine.getElements(0, otherLine.numOfNodes() - 2);
			for (int i = otherLineNodes.length - 1; i >= 0; --i) {
				this.line.add(otherLineNodes[i]);
			}
		}
		
	}
		
}
