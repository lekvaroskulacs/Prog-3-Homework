package program;

import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Table {
	
	private Field fields[][];
	
	public DrawnLine line;
	
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
		rollBack.push(line);
		//if loading user saved table
	}
	
	//returns the Field at the given positions
	public Field getFieldAt(int x, int y) {
		return fields[y][x];
	}
	
	//called on mouse press
	public void startLine(Field startNode) {
		//need to handle some stuff here based on specification
		//if there is no previous line make a new line. 
		//If there is, write to rollback
		if (line != null) 
			rollBack.push(line);
		line = new DrawnLine(startNode);
	}
	
	//called on mouse drag
	public void addLinePiece(Field nextNode) {
		//need to handle stuff here too
		if (line.contains(nextNode)) {
			//stop drawing mode here
			endLine();
		}
		line.addNode(nextNode);
	}
	
	//called on mouse release
	public void endLine() {
		if (line.getStart() == rollBack.getLast().getStart() || line.getStart() == rollBack.getLast().getEnd() ||
			line.getEnd() == rollBack.getLast().getStart() || line.getEnd() == rollBack.getLast().getEnd()) {
			
		}
	}
	
}
