package program;

import java.util.Scanner;
import java.io.*;

public class Table {
	
	private Field fields[][];
	
	private DrawnLine line;
	
	private LineRollBack rollBack;
	
	//tested, works fine.
	//parses a file, and initializes fields[][] based on it
	//required file format can be seen in the "gamesave.dat" file
	private void parseTableFromFile(String filename) throws IOException {
		File f = new File(filename);
		FileInputStream fi = new FileInputStream(f);
		Scanner sc = new Scanner(fi);
		
		int x = sc.nextInt();
		int y = sc.nextInt();
		
		fields = new Field[y][x];
		
		for (int i = 0; i < y; ++i) {
			String line = sc.next();
			for (int j = 0; j < x; ++j) {
				char c = line.charAt(j);
				if (c == 'x')
					fields[i][j] = new BlankField(this);
				else if (c == 'b')
					fields[i][j] = new BlackPearl(this);
				else if (c == 'w')
					fields[i][j] = new WhitePearl(this);
				fields[i][j].setX(j);
				fields[i][j].setY(i);
			}
		}
		
		sc.close();
	}
	
	public Table() {
		fields = null;
		line = null;
		rollBack = null;
	}
	
	//constructor
	public Table(String filename) {	
		rollBack = new LineRollBack(5);
		//if loading non-saved table
		try {
			parseTableFromFile(filename);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		line = null;
		//if loading user saved table
	}
	
	public LineRollBack getRollBack() {
		return rollBack;
	}
	
	public DrawnLine getLine() {
		return line;
	}
	
	public int getWidth() {
		return fields[0].length;
	}
	
	public int getHeight() {
		return fields.length;
	}
	
	//returns the Field at the given positions
	public Field getFieldAt(int x, int y) {
		return fields[y][x];
	}
	
	//called on mouse press
	public void startLine(Field startNode) {
		//if there is a previous line and it's a cycle, or the new one doesn't start on 
		//the previous one's end point, drawing is blocked
		if (line != null && line.isStartNode(line.getEnd()) && line.numOfNodes() != 1) {
			System.out.println("Drawing is blocked. Remove cycle.");
		} else if (line != null && line.contains(startNode) && !line.isStartNode(startNode) && !line.isEndNode(startNode)) {
			System.out.println("Drawing is blocked. Can't start in the middle of another line.");
		} else {
			rollBack.push(line);
			//only want to keep the 2 latest lines 
			if (rollBack.read(rollBack.size()-2)!= null) {
				Field[] nodes = rollBack.read(rollBack.size()-2).getElements(0, rollBack.read(rollBack.size()-2).numOfNodes()-1);
				for (Field n : nodes)  {
					if (!line.contains(n)) {
						n.setHasLine(false);
						n.setLine(null);
					}
				}
			}
			line = new DrawnLine(startNode);
			startNode.setHasLine(true);
			startNode.setLine(line);
			
		}
	}
	
	//called on mouse drag, returns false if line was ended while the function ran
	public boolean addLinePiece(Field nextNode) {
		//only add node if the new one is a neighbor of the last (check legality of argument)
		if (line != null && nextNode.isNeighbor(line.getEnd())) {
			//this is only possible if the user moves the cursor fast enough, that it
			//registers at a diagonal field as the next node. just end the line here
			if (nextNode.isDiagonal(line.getEnd())) {
				endLine();
				return false;
			}
			//in this case, we have a cycle, and we need to finish it by adding the last node
			if (line.isStartNode(nextNode) && line.numOfNodes() > 2) {
				line.addNode(nextNode);
				nextNode.setHasLine(true);
				nextNode.setLine(line);
				endLine();
				return false;
			//we need to end the line if it ends up back in itself (not at its start node)
			} else if (line.contains(nextNode)) {
				endLine();
				return false;
			//in this case, we might have a cycle, if we combine the two lines
			} else if (rollBack.read(rollBack.size()-1) != null && rollBack.read(rollBack.size()-1).contains(nextNode)) {
				//in this case we can connect the two lines, as the new one ends in one of the old one's end points
				if (rollBack.read(rollBack.size()-1).isEndNode(nextNode) || rollBack.read(rollBack.size()-1).isStartNode(nextNode)) {
					line.addNode(nextNode);
					nextNode.setHasLine(true);
					nextNode.setLine(line);
				}
				//then end the line, as it has run into the previous one
				endLine(); 
				return false;
			//in any other case, add a node (except if the line is finished)
			} else {
				if (!line.getFinished()) {
					line.addNode(nextNode);
					nextNode.setHasLine(true);
					nextNode.setLine(line);
				}
				return true;
			}
		} else {
			//the argument was illegal (but no line was ended)
			return true;
		}
		
	}
	
	//called on mouse release
	public void endLine() {
		//finish line
		line.setFinished(true);
		//check for a cycle
		if (line.getStart() == line.getEnd() && line.numOfNodes() > 2) {
			System.out.println("It's a cycle!!");
			if (rollBack.read(rollBack.size()-1)!= null) {
				Field[] nodes = rollBack.read(rollBack.size()-1).getElements(0, rollBack.read(rollBack.size()-1).numOfNodes()-1);
				for (Field n : nodes)  {
					if (!line.contains(n)) {
						n.setHasLine(false);
						n.setLine(null);
					}
				}
			}
			//check if win
			boolean win = checkWin();
			if (win) System.out.println("Victory!");
		} else {
			//if there's no cycle, but there are two lines, try to connect them
			if (rollBack.read(rollBack.size()-1) != null) {
				//check if the two lines need to be connected (if their endpoints are the same)
				if (line.getStart() == rollBack.read(rollBack.size()-1).getStart() || line.getStart() == rollBack.read(rollBack.size()-1).getEnd() ||
						line.getEnd() == rollBack.read(rollBack.size()-1).getStart() || line.getEnd() == rollBack.read(rollBack.size()-1).getEnd()) {
					line.connectWith(rollBack.read(rollBack.size()-1));
					//after connecting, check again, if now there is a cycle
					if (line.getStart() == line.getEnd() && line.numOfNodes() > 2) {
						System.out.println("It's a cycle!!");
						//check if win
						boolean win = checkWin();
						if (win) System.out.println("Victory!");
					}
				//the lines end points are not the same
				} else {
					//remove previous line
					if (rollBack.read(rollBack.size()-1)!= null) {
						Field[] nodes = rollBack.read(rollBack.size()-1).getElements(0, rollBack.read(rollBack.size()-1).numOfNodes()-1);
						for (Field n : nodes)  {
							if (!line.contains(n)) {
								n.setHasLine(false);
								n.setLine(null);
							}
						}
					}
				}
			}
		} 
	}
	
	//only call if a cycle is guaranteed
	public boolean checkWin() {
		boolean win = true;
		Field[] nodes = line.getElements(0, line.numOfNodes()-1);
		for (int j = 0; j < getHeight(); ++j)
			for (int i = 0; i < getWidth(); ++i) {
				if (!getFieldAt(i,j).pearlInCycle())
					return false;
			}
		for (Field n : nodes) {
			Field prevNode =  line.getElementAt(line.getNodeIndex(n)-1);
			Field nextNode = line.getElementAt(line.getNodeIndex(n)+1);
			//because we're guaranteed a cycle
			if (prevNode == null) prevNode = line.getEnd();
			if (nextNode == null) nextNode = line.getStart();
			win = n.winConditionCheck(prevNode, nextNode);
			if (win == false)
				break;
		}
		return win;
	}
	
	public void undo() {
		//invalidate current line
		if (line != null) {
			Field[] nodes = line.getElements(0, line.numOfNodes()-1);
			for (int i = 0; i < line.numOfNodes(); ++i) {
				nodes[i].setHasLine(false);
				nodes[i].setLine(null);
			}
		}
		line = rollBack.pop();
		lineRevalidate();
	}
	
	public void deleteLine() {
		rollBack.push(line);
		line = null;
		lineRevalidate();
	}
	
	public void lineRevalidate() {
		//if line is not null, validate the current line
		if (line != null) {
			Field[] nodes = line.getElements(0, line.numOfNodes()-1);
			for (int i = 0; i < line.numOfNodes(); ++i) {
				nodes[i].setHasLine(true);
				nodes[i].setLine(line);
			}
		}
		//then invalidate the previous line, if not null, and it isn't part of the current line
		DrawnLine prevLine = rollBack.read(rollBack.size()-1);
		if (prevLine != null) {
			Field[] prevLineNodes = prevLine.getElements(0, prevLine.numOfNodes()-1);
			for (int i = 0; i < prevLine.numOfNodes(); ++i) {
				if (line != null &&!line.contains(prevLineNodes[i]) || line == null) {	
					prevLineNodes[i].setHasLine(false);
					prevLineNodes[i].setLine(null);
				}
			}
		}
	}
}
