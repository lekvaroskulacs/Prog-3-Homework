package gui;

import javax.swing.JPanel;

import program.Field;
import program.Table;

import java.awt.*;

abstract public class TablePanel extends JPanel {
	
	protected Field f;
	
	public Field getField() {
		return f;
	}
}
