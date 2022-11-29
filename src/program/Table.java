package program;

import java.util.Scanner;
import java.io.*;

/**
 * The Table on which the game is played. Responsible for managing
 * the line that the user draws, and checking for the correct solution.
 *
 */
public class Table implements Serializable{
	
	private Field fields[][];
	
	private DrawnLine line;
	
	private LineRollBack rollBack;
	
	/**
	 * Parses and initializes a Table from the specified file.
	 * The file has a special format, which can be seen in the "levelX.dat"
	 * files. The format is not specified any further.
	 * @param filename the name of the file to parse from.
	 * @throws IOException
	 */
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
	
	/**
	 * Constructs an empty Table
	 */
	public Table() {
		fields = null;
		line = null;
		rollBack = new LineRollBack(5);
	}
	
	/**
	 * Construct a Table from specified file.
	 * @param filename the name of the file in which the table data is saved.
	 */
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
	
	/**
	 * Returns the width of the Table, measured in number of Fields.
	 * @return the amount of Fields that the table holds horizontally.
	 */
	public int getWidth() {
		return fields[0].length;
	}
	
	/**
	 * Returns the height of the Table, measured in number of Fields.
	 * @return the amount of Fields that the table holds vertically.
	 */
	public int getHeight() {
		return fields.length;
	}
	
	public Field[][] getFields() {
		return fields;
	}
	
	public void setFields(Field[][] f) {
		fields = f;
	}
	
	/**
	 * Returns the field at the specified position.
	 * @param x the x coordinate of the field.
	 * @param y the y coordinate of the field.
	 * @return
	 */
	public Field getFieldAt(int x, int y) {
		return fields[y][x];
	}
	
	/**
	 * Starts a line, and pushes the current one to the rollBack.
	 * Depending on the startNode, it might cancel this behavior, while returning a
	 * message specifying the reason. This might be because: the user tried to drawn
	 * in the middle of an already existing line, or the user tried to draw while a 
	 * cycle was in place.
	 * @param startNode the starting node of the line.
	 * @return the error message, or null if there is no error.
	 */
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
	
	/**
	 * Adds another node to the line. Returns with null, if
	 * the argument is illegal. This happens if the argument Field is not
	 * a neighbor of the end of the current line. The line might also get ended
	 * in some cases, which might also cause a victory to happen. The result of the
	 * method is specified in the return string, which can have multiple values.
	 * @param nextNode the node to be added.
	 * @return a short message explaining the result of the method.
	 */
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
	
	/**
	 * Finished the current line, and checks for a correct solution, if the now
	 * finished line is a cycle.
	 * @return a congratulatory message in case of a victory, otherwise null.
	 */
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
	
	/**
	 * Checks if the win conditions apply to the current cycle.
	 * Only call, if the current line is guaranteed to be a cycle.
	 * @return true if the win conditions apply.
	 */
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
	
	/**
	 * Undoes the previous step, meaning that the line returns
	 * to the state it was before the latest user interaction.
	 */
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
	
	/**
	 * Replaces the current line with null (empties the table).
	 */
	public void deleteLine() {
		rollBack.push(line);
		line = null;
		lineRevalidate();
	}
	
	/**
	 * Validates the current state of the line. Needs to be called if
	 * the line is modified outside of the xxxLine() methods of Table, for example
	 * in undo() and deleteLine().
	 */
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
	
	/**
	 * Saves the current state of the specified table into the save file
	 * specified in savedLevel. For example, if savedLevel is 2, the table
	 * will be saved in the "gamesave2.dat" file.
	 * @param t the Table to be serialized.
	 * @param savedLevel the level which the table corresponds to.
	 */
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
	
	/**
	 * Deserializes a previously saved table from the file specified by level.
	 * @param t the Table to deserialize the data to.
	 * @param level the level which the data corresponds to.
	 * @throws FileNotFoundException if the file doesn't exist (yet).
	 */
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
