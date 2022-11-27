package program;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Stack with a maximum size. When max size is reached,
 * new elements are still added, but the first (oldest) ones are 
 * removed instead.
 */
public class LineRollBack implements Serializable{
	
	private List<DrawnLine> rollBack;
	
	private int maxSize;
	
	public LineRollBack(int maxSize) {
		rollBack = new ArrayList<>();
		this.maxSize = maxSize;
	}
	
	public int size() {
		return rollBack.size();
	}
	
	public void push(DrawnLine t) {
		if (rollBack.size() < maxSize) {
			rollBack.add(t);
		} else {
			rollBack.remove(0);
			rollBack.add(t);
		}
	}
	
	public DrawnLine pop() {
		if (rollBack.isEmpty())
			return null;
		else
			return rollBack.remove(rollBack.size() - 1);
	}
	
	//read the given element without deleting it
	public DrawnLine read(int i) {
		if (i < 0 || i > size())
			return null;
		return rollBack.get(i);
	}
	
	public int indexOf(DrawnLine l) {
		return rollBack.indexOf(l);
	}
	
}
