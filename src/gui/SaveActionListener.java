package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import program.Table;

/**
 * An action listener that generates an event, when the
 * Table needs to be saved. (user clicks "Save")
 *
 */
public class SaveActionListener implements ActionListener {

	private Table t;
	
	private JFrame f;
	
	private int level;
	
	public SaveActionListener(Table t, JFrame f, int level) {
		this.t = t;
		this.f = f;
		this.level = level;
	}
	
	/**
	 * Saves the table, and shows a confirmation dialogue.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Table.serializeTable(t, level);
		JOptionPane.showMessageDialog(f.getContentPane(), "Saved", "Confirmation", JOptionPane.NO_OPTION);
	}

}
