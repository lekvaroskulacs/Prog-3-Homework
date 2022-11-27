package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

import program.Field;

//should only be added to TablePanel
public class InGameMouseInputListener implements MouseListener {
	
	JFrame f;
	
	public InGameMouseInputListener(JFrame f) {
		this.f = f;
	}
	
	static private boolean drawingBlocked = false;

	@Override
	public void mouseClicked(MouseEvent e) {
		//don't do anything
	}

	@Override
	public void mousePressed(MouseEvent e) {

		TablePanel eventPanel = (TablePanel) e.getComponent();
		Field startNode = eventPanel.getField();
		String message = startNode.getTable().startLine(startNode);
		if (message != null)
			JOptionPane.showMessageDialog(f, 
					message.substring(message.indexOf('\n')+1, message.length()), 
					message.substring(0, message.indexOf('\n')), 
					JOptionPane.PLAIN_MESSAGE);
		f.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	
		TablePanel eventPanel = (TablePanel) e.getComponent();
		if (!drawingBlocked) {
			String message = eventPanel.getField().getTable().endLine();
			if (message != null)
				JOptionPane.showMessageDialog(f, 
						message.substring(message.indexOf('\n')+1, message.length()), 
						message.substring(0, message.indexOf('\n')), 
						JOptionPane.PLAIN_MESSAGE);
		}
		drawingBlocked = false;
		f.repaint();

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//this mouse listener should only be added to TablePanel
		if ((e.getModifiersEx() & e.BUTTON1_DOWN_MASK) != 0 && drawingBlocked == false) {
			TablePanel eventPanel = (TablePanel) e.getComponent();
			Field nextNode = eventPanel.getField();
			String message = nextNode.getTable().addLinePiece(nextNode);
			if (message == "end") {
				drawingBlocked = true;
			}
			f.repaint();
			if (message != null && Character.isUpperCase(message.charAt(0))) {
				JOptionPane.showMessageDialog(f, 
						message.substring(message.indexOf('\n')+1, message.length()), 
						message.substring(0, message.indexOf('\n')), 
						JOptionPane.PLAIN_MESSAGE);
			}			
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//don't do anything
	}
}
