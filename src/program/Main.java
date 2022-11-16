package program;

import gui.*;
import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		/*
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		/*
		GameFrame frame = new GameFrame();
		frame.setVisible(true);
		*/
		Table t = new Table("gamesave1.dat");

		System.out.println(t.getFieldAt(2, 5).toString());
		
		t.startLine(t.getFieldAt(0, 2));
		t.addLinePiece(t.getFieldAt(1, 2));
		t.addLinePiece(t.getFieldAt(1, 1));
		
		Field[] f = t.line.getElements(0, t.line.numOfNodes()-1);
		for (Field asd : f) {
			System.out.println(asd.toString());
		}
		//System.out.println(t.line.getStart().toString());
		//System.out.println(t.line.getEnd().toString());
	}
}
