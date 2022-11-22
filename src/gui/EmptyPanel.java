package gui;

import java.awt.Color;
import java.awt.Graphics;

import program.Field;
import program.Table;

public class EmptyPanel extends TablePanel {

	public EmptyPanel(Field field) {
		f = field;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.fillRect(4, 4, getWidth() - 9, getHeight() - 9);
	}
	
}
