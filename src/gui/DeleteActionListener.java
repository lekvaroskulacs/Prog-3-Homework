package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import program.Table;

public class DeleteActionListener implements ActionListener{

	Table t;
	
	JFrame f;
	
	public DeleteActionListener(Table t, JFrame f) {
		this.t = t;
		this.f = f;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		t.deleteLine();
		f.repaint();
	}

}
