package program;

import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Table {
	
	private Field fields[][];
	
	private List<Field> vonal;
	
	//requires testing
	private void parseTableFromFile(String filename) throws IOException {
		File f = new File(filename);
		FileInputStream fi = new FileInputStream(f);
		Scanner sc = new Scanner(fi);
		
		int x = sc.nextInt();
		int y = sc.nextInt();
		
		fields = new Field[x][y];
		
		for (int i = 0; i < y; ++i) {
			for (int j = 0; j < x; ++j) {
				int c = sc.nextInt();
				if (c == 'x')
					fields[j][i] = new BlankField(this);
				else if (c == 'b')
					fields[j][i] = new BlackPearl(this);
				else if (c == 'w')
					fields[j][i] = new WhitePearl(this);
			}
		}
		
		sc.close();
	}
	
	public Table(String filename) {	
		try {
			parseTableFromFile(filename);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
}
