package program;

import org.junit.*;

public class DrawnLineTest {
	
	private DrawnLine line;
	private Table t;
	
	@Before
	public void testSetup() {
		t = new Table();
		line = new DrawnLine();
		
		line.addNode(new WhitePearl(t));
		line.addNode(new BlankField(t));
		line.addNode(new BlankField(t));
	}
	
	/**
	 * tests DrawnLine.connectedWith
	 */
	@Test
	public void testConnectWith() {
		//make both lines ends the same node
		Field sameNode = new BlankField(t);
		line.addNode(sameNode);
		DrawnLine otherLine = new DrawnLine();
		otherLine.addNode(new BlackPearl(t));
		otherLine.addNode(sameNode);
		
		line.connectWith(otherLine);
		//in theory, this should result in a 5 long line
		Assert.assertEquals(5, line.numOfNodes());
		//if they are connected, line's end should be the otherLine's start
		Assert.assertSame(line.getEnd(), otherLine.getStart());
	}

}
