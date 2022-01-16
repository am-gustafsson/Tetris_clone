// Object-Oriented Programming IT366 Spring Term 2019
// Högskolan i Skövde
// Assignment 4: Game Project - Tetris Clone
// by: Alexander Gustafsson (b18alegu)
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import tetrominoes.*;

public abstract class IO extends JPanel implements  ActionListener {
	/******************
	 * Member variables
	 *****************/
	protected boolean mKeyDown, mKeyLeft, mKeyRight, mKeySpace, mKeyZ, mKeyX = false;
	private Font mFont;
	
	
	
	/********************************************************************
	 * Constructor
	 * Setups the JPanel for GFX in the game and a key listener for input
	 *******************************************************************/
	public IO() {
		// Input - Keyboard
		addKeyListener(new KeyboardInputListener());
		
		// Setup the font to be used in this game
		mFont = new Font("Verdana", Font.BOLD, 12);
		this.setFont(mFont);
		
		// Output - JPanel
		setBackground(Color.black);
		setFocusable(true);
		
		int width = Board.BOARD_WIDTH * Board.BLOCK_SIZE;
		int height = Board.BOARD_HEIGHT * Board.BLOCK_SIZE + Board.INTERFACE_HEIGHT;
		setPreferredSize(new Dimension(width, height));
	}
	
	
	
	/********************************************************
	 * drawBlock(Graphics gfx, xPos, yPos, size, Color color)
	 * Draws a square block on to the screen at (xPos, yPos)
	 *******************************************************/	
	protected void drawBlock(Graphics gfx, int xPos, int yPos, int size, Color color) {
		gfx.setColor(color);
		gfx.fillRect(xPos, yPos, size, size);
	}
	
	
	
	/************************************************************
	 * drawTetromino(Graphics gfx, xPos, yPos, size, Color color)
	 * Draws a tetromino on to the screen
	 ***********************************************************/	
	protected void drawTetromino(Graphics gfx, Tetromino shape, int xPos, int yPos, int size, Color color) {		
		// Go through all elements in the tetromino and only draw the ones that are not empty
		for(int row = 0; row < Tetromino.MATRIX_SIZE; row++) {
			for(int col = 0; col < Tetromino.MATRIX_SIZE; col++) {
				if(shape.isTetrominoFilledAt(col, row)) {
					int blockX = col * Board.BLOCK_SIZE + xPos;
					int blockY = row * Board.BLOCK_SIZE + yPos;
					drawBlock(gfx, blockX, blockY, size, color);
				}
			}
		}
	}
	
	

	/*********************************************************
	 * drawHorizontalLine(Graphics gfx, yPos, Color color)
	 * Draws a horizontal line from edge to edge of the screen
	 ********************************************************/
	protected void drawHorizontalLine(Graphics gfx, int yPos, Color color) {
		int xLeftEdge = 0;
		int xRightEdge = getSize().width;
		
		gfx.setColor(color);
		gfx.drawLine(xLeftEdge, yPos, xRightEdge, yPos);
	}
	
	
	
	/*****************************************************************************
	 * drawText()
	 * Draws a text message at the screen. To keep it simple the font and size are
	 * fixed. Color can be changed though
	 ****************************************************************************/
	protected void drawTextMessage(Graphics gfx, String message, int xPos, int yPos, Color color) {
		gfx.setColor(color);
		gfx.drawString(message, xPos, yPos);
	}
	
	
	
	/*****************************************
	 * Customized keyboard-listener
	 * Updates the status of the relevant keys
	 ****************************************/
	private class KeyboardInputListener extends KeyAdapter {
		@Override
        public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				mKeyDown = true;
				break;
			
			case KeyEvent.VK_LEFT:
				mKeyLeft = true;
				break;
				
			case KeyEvent.VK_RIGHT:
				mKeyRight = true;
				break;
				
			case KeyEvent.VK_SPACE:
				mKeySpace = true;
				break;
				
			case KeyEvent.VK_X:
				mKeyX = true;
				break;
				
			case KeyEvent.VK_Z:
				mKeyZ = true;
				break;
			}
		}
		
		@Override
        public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				mKeyDown = false;
				break;
			
			case KeyEvent.VK_LEFT:
				mKeyLeft = false;
				break;
				
			case KeyEvent.VK_RIGHT:
				mKeyRight = false;
				break;
				
			case KeyEvent.VK_SPACE:
				mKeySpace = false;
				break;
				
			case KeyEvent.VK_X:
				mKeyX = false;
				break;
				
			case KeyEvent.VK_Z:
				mKeyZ = false;
				break;
			}
		}
	}
}