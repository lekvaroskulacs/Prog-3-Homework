package gui;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import program.DrawnLine;
import program.Field;
import program.LineRollBack;
import program.Table;

import java.io.*;
import java.util.Scanner;

/**
 * The frame of the application.
 *
 */
public class GameFrame extends JFrame{
	
	private Container contentPane;
	
	private Table t = new Table();
	
	public GameFrame() {
		super("Masyu");
		setSize(1600, 900);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].setFullScreenWindow(this); <-- Fullscreen
		//setExtendedState(JFrame.MAXIMIZED_BOTH);  	// *
		//setUndecorated(true);						// <-- Windowed Fullscreen
		contentPane = getContentPane();
		
		mainMenuSetup();
	}
	
	/**
	 * Loads the main menu view.
	 */
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
	
	/**
	 * Loads the how to play view.
	 */
	public void howToPlaySetup() {
		contentPane.removeAll();
		contentPane.repaint();
		contentPane.revalidate();
		this.setJMenuBar(null);
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.insets = new Insets(100, 0, 0, 0);
		JTextArea text = new JTextArea();
		JScrollPane jsp = new JScrollPane(text);
		JButton back = new JButton("Back");
		contentPane.add(back, constraints);
		constraints.gridy = 0;
		constraints.ipadx = 700;
		constraints.ipady = 400;
		contentPane.add(jsp, constraints);
		
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		String content = "";
		try {
			Scanner sc = new Scanner(new File("howtoplay.txt"));
			content = sc.useDelimiter("\\Z").next();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		text.setBackground(Color.gray);
		text.setFont(new Font("Rockwell", Font.PLAIN, 20));
		text.setText(content);
		
		back.setBackground(new Color(193, 158, 158));
		back.setFont(new Font("Rockwell", Font.PLAIN, 40));
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				mainMenuSetup();
			}
		});
		
		contentPane.repaint();
	}
	
	/**
	 * Loads the choose level view.
	 */
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
			textConstraint.anchor = GridBagConstraints.PAGE_END;
			textConstraint.insets = new Insets(0, 0, 60, 0);
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
				buttonConstraint.insets = new Insets(0, 20, 50, 20);
				buttons[x][y] = new JButton("Level " + level);
				buttons[x][y].setBackground(new Color(193, 158, 158));
				buttons[x][y].setFont(new Font("Rockwell", Font.PLAIN, 40));
				contentPane.add(buttons[x][y], buttonConstraint);
				
				buttons[x][y].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						int answer = JOptionPane.showConfirmDialog(null, "Load saved state?", "Question", JOptionPane.YES_NO_CANCEL_OPTION);
						if (answer == 1)
							gameSetup(level, false);
						if (answer == 0)
							gameSetup(level, true);
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
	
	/**
	 * Loads the game view.
	 * @param level the level to load.
	 * @param deserialize whether to load a previous save or not.
	 * @throws IllegalArgumentException if the specified level doesn't exist.
	 */
	public void gameSetup(int level, boolean deserialize) throws IllegalArgumentException {
		contentPane.removeAll();
		contentPane.repaint();
		contentPane.revalidate();
		contentPane.setLayout(new BorderLayout());
		//set the layout
		JPanel centerPanel = new JPanel();
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new GridBagLayout());
		centerPanel.setOpaque(false);
		//table setup
		if (!deserialize)
			t = new Table("leveldata" + File.separator + "level" + level + ".dat");
		else {
			try {
				Table.deserializeTable(t, level);
			} catch(FileNotFoundException fnfe) {
				JOptionPane.showMessageDialog(contentPane, "No save file yet for this level!", "File not found", JOptionPane.ERROR_MESSAGE);
				chooseLevelSetup();
				return;
			} 
		}
		//Jmenu
		JMenuSetup(t, level);
		//Back button
		backButtonSetup();
		//game table panels 
		int x = t.getWidth();
		int y = t.getHeight();
		//table gui
		TablePanel[][] panels = new TablePanel[x][y];
		for (int j = 0; j < y; ++j) {
			for (int i = 0; i < x; ++i) {
				GridBagConstraints panelConstraint = new GridBagConstraints();
				panelConstraint.gridx = i;
				panelConstraint.gridy = j;
				panelConstraint.ipadx = 50;
				panelConstraint.ipady = 50;
				panels[i][j] = t.getFieldAt(i, j).getPanel();
				//remove previous mouse listeners
				MouseListener[] m = panels[i][j].getMouseListeners();
				for (MouseListener ml : m) {
					panels[i][j].removeMouseListener(ml);
				}
				InGameMouseInputListener listener = new InGameMouseInputListener(this);
				panels[i][j].addMouseListener(listener);
				centerPanel.add(panels[i][j], panelConstraint);
			}
		}
	}
	
	/**
	 * Sets up the JMenu.
	 * @param t the Table to give to the action listeners.
	 * @param level the level to give to SaveActionListener.
	 */
	private void JMenuSetup(Table t, int level) {
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
		undo.addActionListener(new UndoActionListener(t, this));
		delete.addActionListener(new DeleteActionListener(t, this));
		save.addActionListener(new SaveActionListener(t, this, level));
	}
	
	/**
	 * Sets up the back button in the game view.
	 */
	private void backButtonSetup() {
		JPanel southPanel = new JPanel();
		contentPane.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new GridLayout(2, 5, 30, 10));
		JButton back = new JButton("Back");
		for (int i = 0; i < 10; ++i) {
			if (i != 2) {	
				JPanel filler = new  JPanel();
				filler.setOpaque(false);
				southPanel.add(filler);
			} else 
				southPanel.add(back);
		}
		southPanel.setOpaque(false);
		back.setFont(new Font("Rockwell", Font.PLAIN, 40));
		back.setBackground(new Color(193, 158, 158));
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				chooseLevelSetup();
			}
		});
	}
}
