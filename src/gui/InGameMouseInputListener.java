package gui;

import java.awt.Container;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

import program.Field;

//should only be added to TablePanel
public class InGameMouseInputListener implements MouseListener, MouseMotionListener {
	
	static private boolean drawingBlocked = false;
	
	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

		TablePanel eventPanel = (TablePanel) e.getComponent();
		Field startNode = eventPanel.getField();
		startNode.getTable().startLine(startNode);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	
		TablePanel eventPanel = (TablePanel) e.getComponent();
		eventPanel.getField().getTable().endLine();
		drawingBlocked = false;

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//this mouselistener should only be added to TablePanel
		if ((e.getModifiersEx() & e.BUTTON1_DOWN_MASK) != 0 && drawingBlocked == false) {
			TablePanel eventPanel = (TablePanel) e.getComponent();
			Field nextNode = eventPanel.getField();
			if (!nextNode.getTable().addLinePiece(nextNode)) {
				drawingBlocked = true;
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}
