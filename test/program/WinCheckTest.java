package program;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.*;

/**
 * Tests the win-checking algorithms and functions
 *
 */
public class WinCheckTest {
	
	private Table t;
	
	@Before
	public void constructTable() throws FileNotFoundException {
		PrintWriter fw = new PrintWriter("test.txt");
		fw.write( "2\n"
				+ "3\n"
				+ "bx\n"
				+ "xw\n"
				+ "xx\n");
		fw.close();
		
		t = new Table("test.txt");
		
		//this is a correct cycle
		t.startLine(t.getFieldAt(0, 0));
		t.addLinePiece(t.getFieldAt(0, 1));
		t.addLinePiece(t.getFieldAt(0, 2));
		t.addLinePiece(t.getFieldAt(1, 2));
		t.addLinePiece(t.getFieldAt(1, 1));
		t.addLinePiece(t.getFieldAt(1, 0));
		t.addLinePiece(t.getFieldAt(0, 0));
	}
	
	@Test
	public void testWhitePearlWinCheck() {
		DrawnLine line = t.getLine();
		Assert.assertTrue(t.getFieldAt(1, 1).winConditionCheck(line.getElementAt(3), line.getElementAt(5)));
	}
	
	@Test
	public void testBlackPearlWinCheck() {
		DrawnLine line = t.getLine();
		Assert.assertTrue(t.getFieldAt(0, 0).winConditionCheck(line.getElementAt(5), line.getElementAt(1)));
	}
	
	/**
	 * Tests Table.checkWin()
 	 */
	@Test
	public void testCheckWin() {
		Assert.assertTrue(t.checkWin());
	}
}
