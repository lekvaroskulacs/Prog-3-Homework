package gui;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
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
		//Amugy gridbaglayouttal kene, majd a leirast egy nagyobb jscrollpane-be belebaszni
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
		
	}
		
}
