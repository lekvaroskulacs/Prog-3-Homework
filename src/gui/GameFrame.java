package gui;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import program.Table;

import java.io.*;

public class GameFrame extends JFrame{
	
	Container contentPane;
	
	public GameFrame() {
		super("Masyu");
		setSize(1920, 1080);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].setFullScreenWindow(this); <-- Fullscreen
		setExtendedState(JFrame.MAXIMIZED_BOTH);  	// *
		setUndecorated(true);						// <-- Windowed Fullscreen
		contentPane = getContentPane();
		
		mainMenuSetup();
	}
	
	public void mainMenuSetup() {
		contentPane.removeAll();
		contentPane.repaint();
		contentPane.revalidate();
		this.setJMenuBar(null);
		
		Image backgroundImage;
		try {
			backgroundImage = ImageIO.read(new File("background_image.png"));
			setContentPane(new JPanel(new BorderLayout()) {
				@Override
				public void paintComponent(Graphics g) {
					g.drawImage(backgroundImage, 0, 0, null);
				}
			});
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		contentPane = getContentPane();
		
		JButton chooseLevel = new JButton("Choose Level");
		JButton howToPlay = new JButton("How To Play");
		JButton exit = new JButton("Exit");
		
		exit.setBackground(new Color(193, 158, 158));
		chooseLevel.setBackground(new Color(193, 158, 158));
		howToPlay.setBackground(new Color(193, 158, 158));
		exit.setFont(new Font("Rockwell", Font.PLAIN, 40));
		chooseLevel.setFont(new Font("Rockwell", Font.PLAIN, 40));
		howToPlay.setFont(new Font("Rockwell", Font.PLAIN, 40));
		
		JPanel[][] panelHolder = new JPanel[4][3];
		
		setLayout(new GridLayout(4, 3));
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 3; ++j) {
				panelHolder[i][j] = new JPanel();
				contentPane.add(panelHolder[i][j]);
				panelHolder[i][j].setOpaque(false);
			}
		}
		
		panelHolder[1][1].setLayout(new GridLayout(2, 1, 0, 10));
		panelHolder[1][1].add(chooseLevel);
		panelHolder[1][1].add(howToPlay);

		panelHolder[3][1].setLayout(new GridLayout(2, 1, 0, 10));
		panelHolder[3][1].add(exit);
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});
		
		chooseLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				chooseLevelSetup();
			}
		});
		
		howToPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				howToPlaySetup();
			}
		});
		
	}
	
	public void howToPlaySetup() {
		contentPane.removeAll();
		contentPane.repaint();
		contentPane.revalidate();
		this.setJMenuBar(null);
		contentPane.setLayout(new GridLayout(4, 4, 0, 0));
		JPanel[][] panelHolder = new JPanel[4][6];
		
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 6; ++j) {
				panelHolder[i][j] = new JPanel();
				contentPane.add(panelHolder[i][j]);
				panelHolder[i][j].setOpaque(false);
			}
		}
		
		JButton back = new JButton("Back");
		panelHolder[3][4].setLayout(new GridLayout(2, 1, 0, 0));
		panelHolder[3][4].add(back);
		
		back.setBackground(new Color(193, 158, 158));
		back.setFont(new Font("Rockwell", Font.PLAIN, 40));
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				mainMenuSetup();
			}
		});
		
		contentPane.repaint();
	}
	
	public void chooseLevelSetup() {
		contentPane.removeAll();
		contentPane.repaint();
		contentPane.revalidate();
		this.setJMenuBar(null);
		
		contentPane.setLayout(new GridBagLayout());
		
		GridBagConstraints backButtonConstraint = new GridBagConstraints();
		backButtonConstraint.gridx = 3;
		backButtonConstraint.gridy = 3;
		backButtonConstraint.ipady = 0;
		backButtonConstraint.fill = GridBagConstraints.HORIZONTAL;
		backButtonConstraint.insets = new Insets(0, 20, 30, 20);
		JButton backButton = new JButton("Back");
		backButton.setFont(new Font("Rockwell", Font.PLAIN, 40));
		backButton.setBackground(new Color(193, 158, 158));
		contentPane.add(backButton, backButtonConstraint);
		
		JTextArea[] texts = new JTextArea[3];
		for (int y = 0; y < 3; ++y) {
			GridBagConstraints textConstraint = new GridBagConstraints();
			textConstraint.gridx = 0;
			textConstraint.gridy = y;
			textConstraint.ipadx = 150;
			textConstraint.ipady = 115;
			textConstraint.anchor = GridBagConstraints.PAGE_END;
			textConstraint.insets = new Insets(100, 0, 0, 0);
			switch (y) {
			case 0: texts[y] = new JTextArea("Easy:"); break;
			case 1: texts[y] = new JTextArea("Medium:"); break;
			case 2: texts[y] = new JTextArea("Hard:"); break;
			}
			texts[y].setEditable(false);
			texts[y].setOpaque(false);
			texts[y].setFont(new Font("Rockwell", Font.PLAIN, 40));
			texts[y].setForeground(new Color(255, 255, 255));
			contentPane.add(texts[y], textConstraint);
		}
		
		JButton[][] buttons = new JButton[3][3];
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 3; ++x) {
				int level = y*3 + x+1;
				GridBagConstraints buttonConstraint = new GridBagConstraints();
				buttonConstraint.gridx = x + 1;
				buttonConstraint.gridy = y;
				buttonConstraint.ipadx = 150;
				buttonConstraint.ipady = 75;
				buttonConstraint.insets = new Insets(0, 20, 50, 20);
				buttons[x][y] = new JButton("Level " + level);
				buttons[x][y].setBackground(new Color(193, 158, 158));
				buttons[x][y].setFont(new Font("Rockwell", Font.PLAIN, 40));
				contentPane.add(buttons[x][y], buttonConstraint);
				
				buttons[x][y].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						gameSetup(level);
					}
				});
			}
		}
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				mainMenuSetup();
			}
		});
		
	}
	
	public void gameSetup(int level) throws IllegalArgumentException {
		contentPane.removeAll();
		contentPane.repaint();
		contentPane.revalidate();
		contentPane.setLayout(new BorderLayout());
		//set the layout
		JPanel centerPanel = new JPanel();
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new GridBagLayout());
		centerPanel.setOpaque(false);
		//initialize table
		Table t = new Table();
		switch (level) {
		case 1: t = new Table("gamesave1.dat"); break;
		case 2: break;
		case 3: break;
		case 4: break;
		case 5: break;
		case 6: break;
		case 7: break;
		case 8: break;
		case 9: break;
		default: throw new IllegalArgumentException("Not a level from 1-9");
		}
		//Jmenu
		JMenuSetup(t);
		//Back button
		JPanel southPanel = new JPanel();
		contentPane.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new GridBagLayout());
		JButton back = new JButton("Back");
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.EAST;
		constraints.ipadx = 200;
		constraints.ipady = 50;
		southPanel.add(back, constraints);
		southPanel.setOpaque(false);
		back.setFont(new Font("Rockwell", Font.PLAIN, 40));
		back.setBackground(new Color(193, 158, 158));
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				chooseLevelSetup();
			}
		});
		//game table panels 
		int x = t.getWidth();
		int y = t.getHeight();
		
		TablePanel[][] panels = new TablePanel[x][y];
		for (int j = 0; j < y; ++j) {
			for (int i = 0; i < x; ++i) {
				GridBagConstraints panelConstraint = new GridBagConstraints();
				panelConstraint.gridx = i;
				panelConstraint.gridy = j;
				panelConstraint.ipadx = 50;
				panelConstraint.ipady = 50;
				panels[i][j] = t.getFieldAt(i, j).getPanel();
				panels[i][j].addMouseListener(new InGameMouseInputListener());
				panels[i][j].addMouseMotionListener(new InGameMouseInputListener());
				centerPanel.add(panels[i][j], panelConstraint);
			}
		}
	}
	
	private void JMenuSetup(Table t) {
		//add JMenu
		JMenuBar menuBar = new JMenuBar();
		JMenu options = new JMenu("Options");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem delete = new JMenuItem("Delete");
		JMenuItem undo = new JMenuItem("Undo");
		options.add(save);
		options.add(delete);
		options.add(undo);
		menuBar.add(options);
		this.setJMenuBar(menuBar);
		
		//JMenu actionListeners
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				t.undo();
				repaint();
			}
		});
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				t.deleteLine();
				repaint();
			}
		});
	}		
}
