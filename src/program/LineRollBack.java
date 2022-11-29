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
	
	/**
	 * Create a roll back storage with the specified maximum size.
	 * @param maxSize the maximum size of this storage (stack).
	 */
	public LineRollBack(int maxSize) {
		rollBack = new ArrayList<>();
		this.maxSize = maxSize;
	}
	
	/**
	 * Returns the current size of the stack.
	 * @return the current size.
	 */
	public int size() {
		return rollBack.size();
	}
	
	/**
	 * Puts an element to the top of the stack, if it won't exceed the maximum size.
	 * @param t the element to be added.
	 */
	public void push(DrawnLine t) {
		if (rollBack.size() < maxSize) {
			rollBack.add(t);
		} else {
			rollBack.remove(0);
			rollBack.add(t);
		}
	}
	
	/**
	 * Removes the top (last added) element of the stack.
	 * @return the removed element, or null if the stack was empty.
	 */
	public DrawnLine pop() {
		if (rollBack.isEmpty())
			return null;
		else
			return rollBack.remove(rollBack.size() - 1);
	}
	
	/**
	 * Reads specified element without deleting it.
	 * @param i the index of the element.
	 * @return the element.
	 */
	public DrawnLine read(int i) {
		if (i < 0 || i > size())
			return null;
		return rollBack.get(i);
	}
	
	/**
	 * Gets the index of the given element.
	 * @param l the element to be examined.
	 * @return the index of the element.
	 */
	public int indexOf(DrawnLine l) {
		return rollBack.indexOf(l);
	}
	
}
