// Object-Oriented Programming IT366 Spring Term 2019
// Högskolan i Skövde
// Assignment 4: Game Project - Tetris Clone
// by: Alexander Gustafsson (b18alegu)
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main extends JFrame {
	/*********************************************
	 * Constructor
	 * Launches the game and configures the JFrame
	 ********************************************/
	public Main() {
		add(new Game());
		
		setTitle("Tetris");
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	
	/***************
	 * main() method
	 **************/
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
            JFrame window = new Main();
            window.setVisible(true);
        });
	}
}