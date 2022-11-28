package program;

import java.util.Scanner;
import java.io.*;

public class Table implements Serializable{
	
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
		rollBack = new LineRollBack(5);
	}
	
	//construct table by parsing file
	public Table(String filename) {	
		rollBack = new LineRollBack(5);
		//if loading non-saved table
		try {
			parseTableFromFile(filename);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		line = null;
	}
	
	public LineRollBack getRollBack() {
		return rollBack;
	}
	
	public void setRollBack(LineRollBack r) {
		rollBack = r;
	}
	
	public DrawnLine getLine() {
		return line;
	}
	
	public void setLine(DrawnLine l) {
		line = l;
	}
	
	public int getWidth() {
		return fields[0].length;
	}
	
	public int getHeight() {
		return fields.length;
	}
	
	public Field[][] getFields() {
		return fields;
	}
	
	public void setFields(Field[][] f) {
		fields = f;
	}
	
	//returns the Field at the given positions
	public Field getFieldAt(int x, int y) {
		return fields[y][x];
	}
	
	//called on mouse press
	public String startLine(Field startNode) {
		//if there is a previous line and it's a cycle, or the new one doesn't start on 
		//the previous one's end point, drawing is blocked
		if (line != null && line.isStartNode(line.getEnd()) && line.numOfNodes() != 1) {
			return "Drawing is blocked \nRemove loop to draw.";
		} else if (line != null && line.contains(startNode) && !line.isStartNode(startNode) && !line.isEndNode(startNode)) {
			return "Drawing is blocked \nCan't start in the middle of another line.";
		} else {
			rollBack.push(line);
			//only want to keep the 2 latest lines 
			if (rollBack.read(rollBack.size()-2)!= null) {
				Field[] nodes = rollBack.read(rollBack.size()-2).getElements(0, rollBack.read(rollBack.size()-2).numOfNodes()-1);
				for (Field n : nodes)  {
					if (line != null && !line.contains(n)) {
						n.setHasLine(false);
						n.setLine(null);
					}
				}
			}
			line = new DrawnLine(startNode);
			startNode.setHasLine(true);
			startNode.setLine(line);
			return null;
		}
	}
	
	//called on mouse drag, returns with "end" if line was ended while the function ran
	public String addLinePiece(Field nextNode) {
		//only add node if the new one is a neighbor of the last (check legality of argument)
		if (line != null && nextNode.isNeighbor(line.getEnd())) {
			//this is only possible if the user moves the cursor fast enough, that it
			//registers at a diagonal field as the next node. just end the line here
			if (nextNode.isDiagonal(line.getEnd())) {
				String message = endLine();
				return message == null ? message : "end";
			}
			//in this case, we have a cycle, and we need to finish it by adding the last node
			if (line.isStartNode(nextNode) && line.numOfNodes() > 2) {
				line.addNode(nextNode);
				nextNode.setHasLine(true);
				nextNode.setLine(line);
				String message = endLine();
				return message != null ? message : "end";
			//we need to end the line if it ends up back in itself (not at its start node)
			} else if (line.contains(nextNode)) {
				String message = endLine();
				return message != null ? message : "end";
			//in this case, we might have a cycle, if we combine the two lines
			} else if (rollBack.read(rollBack.size()-1) != null && rollBack.read(rollBack.size()-1).contains(nextNode)) {
				//in this case we can connect the two lines, as the new one ends in one of the old one's end points
				if (rollBack.read(rollBack.size()-1).isEndNode(nextNode) || rollBack.read(rollBack.size()-1).isStartNode(nextNode)) {
					line.addNode(nextNode);
					nextNode.setHasLine(true);
					nextNode.setLine(line);
				}
				//then end the line, as it has run into the previous one
				String message = endLine();
				return message != null ? message : "end";
			//in any other case, add a node (except if the line is finished)
			} else {
				if (!line.getFinished()) {
					line.addNode(nextNode);
					nextNode.setHasLine(true);
					nextNode.setLine(line);
					return "added";
				}
				return "end";
			}
		} else {
			//the argument was illegal (but no line was ended)
			return "illegal";
		}
	}
	
	//called on mouse release
	public String endLine() {
		//i don't know why, but sometimes it gets called with an empty line
		if (line == null)
			return null;
		//finish line
		line.setFinished(true);
		//check for a cycle
		if (line.getStart() == line.getEnd() && line.numOfNodes() > 2) {
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
			if (win) {
				return "Victory \nCongratulations!";
			}
		} else {
			//if there's no cycle, but there are two lines, try to connect them
			if (rollBack.read(rollBack.size()-1) != null) {
				//check if the two lines need to be connected (if their endpoints are the same)
				if (line.getStart() == rollBack.read(rollBack.size()-1).getStart() || line.getStart() == rollBack.read(rollBack.size()-1).getEnd() ||
						line.getEnd() == rollBack.read(rollBack.size()-1).getStart() || line.getEnd() == rollBack.read(rollBack.size()-1).getEnd()) {
					line.connectWith(rollBack.read(rollBack.size()-1));
					//after connecting, check again, if now there is a cycle
					if (line.getStart() == line.getEnd() && line.numOfNodes() > 2) {
						//check if win
						boolean win = checkWin();
						if (win) {
							return "Victory \nCongratulations!";
						}
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
		return null;
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
			if (prevNode == null) prevNode = line.getElementAt(line.numOfNodes()-2);
			if (nextNode == null) nextNode = line.getElementAt(1);
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
	
	static public void serializeTable(Table t, int savedLevel) {
		try {
			FileOutputStream f = new FileOutputStream("savedata"+ File.separator + "gamesave" + savedLevel + ".dat");
			ObjectOutputStream out = new ObjectOutputStream(f);
			out.writeObject(t);
			out.close();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	static public void deserializeTable(Table t, int level) throws FileNotFoundException {
		try {
			FileInputStream f = new FileInputStream("savedata" + File.separator + "gamesave" + level + ".dat");
			ObjectInputStream in = new ObjectInputStream(f);
			t = (Table)in.readObject();
			in.close();
		} catch(IOException ioe) {
			if (ioe.getClass() == FileNotFoundException.class)
				throw (FileNotFoundException)ioe;
			ioe.printStackTrace();
		} catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}
}
