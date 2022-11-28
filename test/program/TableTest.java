package program;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.*;

/**
 * Implies that TableConstructorTest is successful.
 * Implies that DrawnLineTest is successful.
 * Tests a few core functions of Table class, like line drawing, line deletion, undo.
 * 
 */
public class TableTest {
	
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
	}
	
	/**
	 * Tests Table.startLine() function
	 */
	@Test
	public void testStartLine() {
		//start a node
		String message = t.startLine(t.getFieldAt(0, 0));
		Assert.assertTrue(t.getLine().isStartNode(t.getFieldAt(0, 0)));
		Assert.assertEquals(message, null);
		//check if previous is removed
		message = t.startLine(t.getFieldAt(1, 2));
		Assert.assertEquals(message, null);
		Assert.assertFalse(t.getLine().isStartNode(t.getFieldAt(0, 0)));
		Assert.assertTrue(t.getLine().isStartNode(t.getFieldAt(1, 2)));
		//check if it is in the roll back
		LineRollBack lrb = t.getRollBack();
		Assert.assertTrue(lrb.read(lrb.size()-1).isStartNode(t.getFieldAt(0, 0)));
		//start at the same place
		message = t.startLine(t.getFieldAt(1, 2));
		Assert.assertTrue(t.getLine().isStartNode(t.getFieldAt(1, 2)));
		Assert.assertEquals(message, null);
		Assert.assertTrue(lrb.read(lrb.size()-1).isStartNode(t.getFieldAt(1, 2)));
	}
	
	/**
	 * Tests Table.addLinePiece() function
	 */
	@Test
	public void testAddLinePiece() {
		//add elements, meanwhile, check message and the correctness of addition
		t.startLine(t.getFieldAt(0, 0));
		String message = t.addLinePiece(t.getFieldAt(0, 1));
		Assert.assertSame(t.getLine().getElementAt(1), t.getFieldAt(0, 1));
		Assert.assertEquals(message, "added");
		message = t.addLinePiece(t.getFieldAt(0, 2));
		Assert.assertSame(t.getLine().getElementAt(2), t.getFieldAt(0, 2));
		Assert.assertEquals(message, "added");
		message = t.addLinePiece(t.getFieldAt(1, 2));
		Assert.assertSame(t.getLine().getElementAt(3), t.getFieldAt(1, 2));
		Assert.assertEquals(message, "added");
		//try and add to an illegal spot (not a neighbor)
		message = t.addLinePiece(t.getFieldAt(0, 0));
		Assert.assertEquals(message, "illegal");
	}
	
	/**
	 * Tests Table.endLinePiece() function
	 */
	@Test
	public void testEndLine() {
		//add elements, and run it back into itself (not in a cycle)
		//meanwhile, check message and the correctness of addition
		t.startLine(t.getFieldAt(0, 0));
		t.addLinePiece(t.getFieldAt(0, 1));
		t.addLinePiece(t.getFieldAt(0, 2));
		t.addLinePiece(t.getFieldAt(1, 2));
		t.addLinePiece(t.getFieldAt(1, 1));
		String message = t.addLinePiece(t.getFieldAt(0, 1));
		//it has run back into itself, this checks if addLinePiece ends node correctly
		Assert.assertSame(t.getLine().getElementAt(5), null);
		Assert.assertEquals(message, "end");
		
		//start again, and lengthen the line
		t.startLine(t.getFieldAt(1, 1));
		message = t.addLinePiece(t.getFieldAt(1, 0));
		Assert.assertSame(t.getLine().getElementAt(1), t.getFieldAt(1, 0));
		Assert.assertEquals(message, "added");
		//there should be two nodes on this new line
		Assert.assertEquals(t.getLine().numOfNodes(), 2);
		//run it into a cycle, and win
		message = t.addLinePiece(t.getFieldAt(0, 0));
		Assert.assertEquals(message, "Victory \nCongratulations!");
		//this should be a complete line, with 7 nodes
		Assert.assertEquals(t.getLine().numOfNodes(), 7);
	}
	
	@Test
	public void testDeleteLine() {
		//make a line
		t.startLine(t.getFieldAt(0, 0));
		t.addLinePiece(t.getFieldAt(0, 1));
		t.addLinePiece(t.getFieldAt(0, 2));
		//call delete
		t.deleteLine();
		//t.line should be null
		Assert.assertEquals(null, t.getLine());
		//previous line should be in roll back
		Assert.assertSame(t.getRollBack().read(1).getStart(), t.getFieldAt(0, 0));
	}
	
	@Test
	public void testUndo() {
		//make a line
		t.startLine(t.getFieldAt(0, 0));
		t.addLinePiece(t.getFieldAt(0, 1));
		t.addLinePiece(t.getFieldAt(0, 2));
		//delete this line
		t.deleteLine();
		//make another line
		t.startLine(t.getFieldAt(1, 2));
		t.addLinePiece(t.getFieldAt(0, 1));
		t.addLinePiece(t.getFieldAt(0, 2));
		//undoing now will should make the line null
		t.undo();
		Assert.assertEquals(null, t.getLine());
		//undoing one more should get the first line
		t.undo();
		Assert.assertSame(t.getFieldAt(0, 0), t.getLine().getStart());
	}
	
}
