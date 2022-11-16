package program;

import java.util.ArrayList;
import java.util.List;

/**
 * Stack with a maximum size. When max size is reached,
 * new elements are still added, but the first (oldest) ones are 
 * removed instead.
 */
public class LineRollBack {
	
	private List<DrawnLine> rollBack;
	
	private int maxSize;
	
	public LineRollBack(int maxSize) {
		rollBack = new ArrayList<>();
		this.maxSize = maxSize;
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
	
	//read the last added element 
	public DrawnLine getLast() {
		return rollBack.get(rollBack.size() - 1);
	}
	
}
