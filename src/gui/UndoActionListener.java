package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import program.Table;

public class UndoActionListener implements ActionListener {

	Table t;
	
	JFrame f;
	
	public UndoActionListener(Table t, JFrame f) {
		this.t = t;
		this.f = f;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		t.undo();
		t.getFieldAt(0, 0).getPanel().getTopLevelAncestor().repaint();
	}

}
