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
		
		t.startLine(t.getFieldAt(0, 2));
		t.addLinePiece(t.getFieldAt(0, 1));
		t.addLinePiece(t.getFieldAt(0, 0));
		t.addLinePiece(t.getFieldAt(1, 0));
		t.addLinePiece(t.getFieldAt(1, 1));
		t.addLinePiece(t.getFieldAt(1, 2));
	
		
		t.startLine(t.getFieldAt(3, 0));
		t.addLinePiece(t.getFieldAt(2, 0));
		t.addLinePiece(t.getFieldAt(1, 0));
		
		
		
		
		
		/*
		//set line back to private once done testing
		Field[] f = t.line.getElements(0, t.line.numOfNodes()-1);
		for (Field asd : f) {
			System.out.println(asd.toString());
		}
		*//*
		t.startLine(t.getFieldAt(3, 0));
		t.addLinePiece(t.getFieldAt(3, 1));
		t.addLinePiece(t.getFieldAt(2, 1));
		t.addLinePiece(t.getFieldAt(1, 1));
		
		t.line.connectWith(t.rollBack.getLast());
		*/
		Field[] f = t.line.getElements(0, t.line.numOfNodes()-1);
		for (Field asd : f) {
			System.out.println(asd.toString());
		}
		
	}
}
