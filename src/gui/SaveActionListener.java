package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import program.Table;

public class SaveActionListener implements ActionListener {

	Table t;
	
	JFrame f;
	
	int level;
	
	public SaveActionListener(Table t, JFrame f, int level) {
		this.t = t;
		this.f = f;
		this.level = level;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Table.serializeTable(t, level);
		JOptionPane.showMessageDialog(f.getContentPane(), "Saved", "Confirmation", JOptionPane.NO_OPTION);
	}

}
