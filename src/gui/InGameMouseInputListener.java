package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

import program.Field;

/**
 * The mouse listener that's added to the TablePanels.
 * Calls the correct Table functions on the corresponding user inputs.
 *
 */
public class InGameMouseInputListener implements MouseListener {
	
	private JFrame f;
	
	public InGameMouseInputListener(JFrame f) {
		this.f = f;
	}
	
	static private boolean drawingBlocked = false;

	/**
	 * Unimplemented.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		//don't do anything
	}

	/**
	 * Starts a line in the table, and opens a dialogue when necessary.
	 */
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

	/**
	 * Finishes the line in the table, and opens a dialogue when necessary.
	 */
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

	/**
	 * Adds a node to the line in the table, and opens a dialogue when necessary.
	 */
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

	/**
	 * Unimplemented.
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		//don't do anything
	}
}
