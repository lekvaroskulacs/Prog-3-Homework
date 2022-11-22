package gui;

import java.awt.Color;
import java.awt.Graphics;

import program.Field;
import program.Table;

public class BlackPearlPanel extends TablePanel {
	
	public BlackPearlPanel(Field field) {
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
		
		g.setColor(Color.BLACK);
		g.fillOval(10, 10, getWidth() - 21, getHeight() - 21);
	}
}
