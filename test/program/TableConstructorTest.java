package program;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.*;

/**
 * Tests the nontrivial Table constructor, and Table.parseTableFromFile function
 *  
 */
public class TableConstructorTest {
	
	private Table t;
	
	/**
	 * Nontrivial Table constructor and file parse test
	 * @throws FileNotFoundException
	 */
	@Test
	public void testParseConstructor() throws FileNotFoundException {
		PrintWriter fw = new PrintWriter("test.txt");
		fw.write( "2\n"
				+ "3\n"
				+ "xw\n"
				+ "bx\n"
				+ "xx\n");
		fw.close();
		
		t = new Table("test.txt");
		
		//test if fields are the correct class
		Assert.assertSame(t.getFieldAt(0, 0).getClass(), BlankField.class);
		Assert.assertSame(t.getFieldAt(1, 0).getClass(), WhitePearl.class);
		Assert.assertSame(t.getFieldAt(0, 1).getClass(), BlackPearl.class);
		Assert.assertSame(t.getFieldAt(1, 1).getClass(), BlankField.class);
		Assert.assertSame(t.getFieldAt(0, 2).getClass(), BlankField.class);
		Assert.assertSame(t.getFieldAt(1, 2).getClass(), BlankField.class);
	}
	
}
