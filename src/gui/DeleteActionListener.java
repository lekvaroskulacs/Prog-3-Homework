package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import program.Table;

/**
 * An action listener that generates an event, when the current line
 * from the table needs to be deleted (when the user clicks "Delete").
 *
 */
public class DeleteActionListener implements ActionListener{

	private Table t;
	
	private JFrame f;
	
	public DeleteActionListener(Table t, JFrame f) {
		this.t = t;
		this.f = f;
	}
	
	/**
	 * Deletes the current line from the table.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		t.deleteLine();
		f.repaint();
	}

}
