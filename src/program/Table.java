package program;

import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Table {
	
	private Field fields[][];
	
	public DrawnLine line;
	
	public LineRollBack rollBack;
	
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
			}
		}
		
		sc.close();
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
	
	//returns the Field at the given positions
	public Field getFieldAt(int x, int y) {
		return fields[y][x];
	}
	
	//called on mouse press
	public void startLine(Field startNode) {
		//if there is a previous line and it's a cycle, or the new one doesn't start on 
		//the previous one's end point, drawing is blocked
		if (line != null && line.isStartNode(line.getEnd())) {
			System.out.println("Drawing is blocked. Remove cycle.");
		} else if (line != null && line.contains(startNode) && !line.isStartNode(startNode) && !line.isEndNode(startNode)) {
			System.out.println("Drawing is blocked. Can't start in the middle of another line.");
		} else {
			rollBack.push(line);
			line = new DrawnLine(startNode);
		}
	}
	
	//called on mouse drag
	public void addLinePiece(Field nextNode) {
		//in this case, we have a cycle, and we need to finish it by adding the last node
		if (line.isStartNode(nextNode) && line.numOfNodes() > 2) {
			//stop drawing mode here
			line.addNode(nextNode);
			endLine();
		//we need to end the line if it ends up back in itself (not at its start node)
		} else if (line.contains(nextNode)) {
			endLine();
		//in this case, we might have a cycle, if we combine the two lines
		} else if (rollBack.getLast() != null && rollBack.getLast().contains(nextNode)) {
			//stop drawing mode here
			
			//in this case we can connect the two lines, as the new one ends in one of the old one's end points
			if (rollBack.getLast().isEndNode(nextNode) || rollBack.getLast().isStartNode(nextNode))
				line.addNode(nextNode);
			//then end the line, as it has run into the previous one
			endLine(); 
			
		//in any other case, add a node
		} else {
			line.addNode(nextNode);
		}
		
	}
	
	//called on mouse release
	public void endLine() {
		//check for a cycle
		if (line.getStart() == line.getEnd() && line.numOfNodes() > 2) {
			System.out.println("It's a cycle!!");
			//check if win
		} else {
			//if there's no cycle, but there are two lines, try to connect them
			if (rollBack.getLast() != null) {
				//check if the two lines need to be connected (if their endpoints are the same)
				if (line.getStart() == rollBack.getLast().getStart() || line.getStart() == rollBack.getLast().getEnd() ||
						line.getEnd() == rollBack.getLast().getStart() || line.getEnd() == rollBack.getLast().getEnd()) {
					line.connectWith(rollBack.getLast());
					//after connecting, check again, if now there is a cycle
					if (line.getStart() == line.getEnd() && line.numOfNodes() > 2) {
						System.out.println("It's a cycle!!");
						//check if win
					}
				}
			}
		} 
	}
}
