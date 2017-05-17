package tetris;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;;

public class TetrisMain extends Canvas implements Runnable, KeyListener {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Tetris");
		frame.setSize(400, 540);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		TetrisMain tm = new TetrisMain();
		frame.add(tm);
		frame.setVisible(true);
		
	}

	public void run() {
		
	}
	
	public void keyPressed(KeyEvent e){
		
	}
	
	public void keyTyped(KeyEvent e){
		
	}
	
	public void keyReleased(KeyEvent e){
		
	}
	
}
