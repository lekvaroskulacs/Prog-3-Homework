package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuFrame extends JFrame{
	
	private JButton chooseLevel = new JButton("Choose Level");
	private JButton howToPlay = new JButton("How To Play");
	private JButton exit = new JButton("Exit");
	private JPanel[][] panelHolder = new JPanel[4][3];
	
	public MenuFrame() {
		super("Masyu");
		setSize(1280, 720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainMenuSetup();
		
	}
	
	public void mainMenuSetup() {
		setLayout(new GridLayout(4, 3));
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 3; ++j) {
				panelHolder[i][j] = new JPanel();
				add(panelHolder[i][j]);
			}
		}
		panelHolder[1][1].setLayout(new GridLayout(2, 1, 0, 10));
		panelHolder[1][1].add(chooseLevel);
		panelHolder[1][1].add(howToPlay);

		panelHolder[3][1].setLayout(new GridLayout(2, 1, 0, 10));
		panelHolder[3][1].add(exit);
	}
}
