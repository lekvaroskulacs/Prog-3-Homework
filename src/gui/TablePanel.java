package gui;



import javax.swing.JPanel;

import program.Field;
import program.LineRollBack;
import program.Table;

import java.awt.*;
import java.io.Serializable;

import program.Direction;
import program.DrawnLine;

/**
 * The graphical representation of the Field class. Every Field has
 * a TablePanel associated with it.
 * Responsible for drawing everything that happens on the Field, which the object is
 * associated with.
 *
 */
abstract public class TablePanel extends JPanel implements Serializable{
	
	protected Field f;
	
	public Field getField() {
		return f;
	}
	
	/**
	 * Draws a line from the middle of this panel to the direction of the specified Field.
	 * @param g the Graphics object to protect.
	 * @param dir the Field, in which direction to draw in.
	 */
	private void lineDrawHelper(Graphics g, Field dir) {
		if (dir == null)
			return;
		int panelWidth = getWidth()-1;
		int panelHeight = getHeight()-1;
		if (f.directionOfNeighbor(dir) == Direction.east)
			g.drawLine(panelWidth/2, panelHeight/2, panelWidth, panelHeight/2);
		if (f.directionOfNeighbor(dir) == Direction.west)
			g.drawLine(panelWidth/2, panelHeight/2, -panelWidth, panelHeight/2);
		if (f.directionOfNeighbor(dir) == Direction.north)
			g.drawLine(panelWidth/2, panelHeight/2, panelWidth/2, -panelHeight);
		if (f.directionOfNeighbor(dir) == Direction.south)
			g.drawLine(panelWidth/2, panelHeight/2, panelWidth/2, panelHeight);
	}
	
	/**
	 * Draws the line, or pieces of lines on this panel in relation to the state
	 * of the Field, which this panel is associated with.
	 * @param g the Graphics object to protect.
	 */
	protected void paintLine(Graphics g) {
		g.setColor(Color.GRAY);
		if (f.hasLine()) {
			int panelWidth = getWidth()-1;
			int panelHeight = getHeight()-1;
			
			if (f.getLine() == null)
				return;
			
			Field previousNode = f.getLine().getElementAt(f.getLine().getNodeIndex(f) - 1);
			Field nextNode = f.getLine().getElementAt(f.getLine().getNodeIndex(f) + 1);
			//if there is one node
			if (f.getLine().numOfNodes() == 1) {
				//draw circle (line start)
				g.fillOval(panelWidth/2 - 4 , panelHeight/2 - 4, 8, 8);
				if (previousNode == null) {
					LineRollBack fRollBack = f.getTable().getRollBack();
					DrawnLine prevLine = fRollBack.read(fRollBack.size()-1);
					if (f.getLine().isStartNode(f) &&  prevLine != null) {
						if (prevLine.isEndNode(f)) {
							int connectedIdx = prevLine.getNodeIndex(f) - 1;
							if (prevLine.getElementAt(connectedIdx) != null)
								lineDrawHelper(g, prevLine.getElementAt(connectedIdx));
						}
						else if (prevLine.isStartNode(f)) {
							int connectedIdx = prevLine.getNodeIndex(f) + 1;
							if (prevLine.getElementAt(connectedIdx) != null)
								lineDrawHelper(g, prevLine.getElementAt(connectedIdx));
						}
					}
				}
			//if there are more than 1 nodes
			} else {
				if (previousNode == null) {
					LineRollBack fRollBack = f.getTable().getRollBack();
					DrawnLine prevLine = fRollBack.read(fRollBack.size()-1);
					if (f.getLine().isStartNode(f) && f.getLine().isEndNode(f)) {
						lineDrawHelper(g, f.getLine().getElementAt(f.getLine().numOfNodes()-2));
					//if we start at the end of a previous line, then connect them still
					} else if (f.getLine().isStartNode(f) &&  prevLine != null) {
						if (prevLine.isEndNode(f)) {
							int connectedIdx = prevLine.getNodeIndex(f) - 1;
							lineDrawHelper(g, prevLine.getElementAt(connectedIdx));
						}
						else if (prevLine.isStartNode(f)) {
							int connectedIdx = prevLine.getNodeIndex(f) + 1;
							lineDrawHelper(g, prevLine.getElementAt(connectedIdx));
						}
					}
					g.fillOval(panelWidth/2 - 4 , panelHeight/2 - 4, 8, 8);
				}
				else {
					lineDrawHelper(g, previousNode);
				}
				//draw line in the direction of next node
				//nextNode can be null
				if (nextNode == null) {
					//in this case, it is the end node, so draw a circle
					g.fillOval(panelWidth/2 - 4 , panelHeight/2 - 4, 8, 8);
				} else {
					lineDrawHelper(g, nextNode);
				}	
			}
		}
	}
}
