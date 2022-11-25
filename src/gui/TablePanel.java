package gui;



import javax.swing.JPanel;

import program.Field;
import program.LineRollBack;
import program.Table;

import java.awt.*;
import program.Direction;
import program.DrawnLine;

abstract public class TablePanel extends JPanel {
	
	protected Field f;
	
	public Field getField() {
		return f;
	}
	
	private void lineDrawHelper(Graphics g, Field dir) {
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
	
	protected void paintLine(Graphics g) {
		g.setColor(Color.GRAY);
		if (f.hasLine()) {
			int panelWidth = getWidth()-1;
			int panelHeight = getHeight()-1;
			
			if (f.getLine() == null)
				return;
			
			Field previousNode = f.getLine().getElementAt(f.getLine().getNodeIndex(f) - 1);
			Field nextNode = f.getLine().getElementAt(f.getLine().getNodeIndex(f) + 1);
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
			} else {
				if (previousNode == null) {
					if (f.getLine().isStartNode(f) && f.getLine().isEndNode(f)) {
						lineDrawHelper(g, f.getLine().getElementAt(f.getLine().numOfNodes()-2));
					}
					//if we start at the end of a previous line, then connect them still
					LineRollBack fRollBack = f.getTable().getRollBack();
					DrawnLine prevLine = fRollBack.read(fRollBack.size()-1);
					if (f.getLine().isStartNode(f) &&  prevLine != null) {
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
