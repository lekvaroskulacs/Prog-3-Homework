package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import program.Table;

/**
 * An action listener that generates an event, when the last
 * line piece drawn needs to be undone. (user clicks "Undo")
 *
 */
public class UndoActionListener implements ActionListener {

	private Table t;
	
	private JFrame f;
	
	public UndoActionListener(Table t, JFrame f) {
		this.t = t;
		this.f = f;
	}
	
	/**
	 * Undoes the last step.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		t.undo();
		f.repaint();
	}

}
