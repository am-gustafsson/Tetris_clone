// Object-Oriented Programming IT366 Spring Term 2019
// Högskolan i Skövde
// Assignment 4: Game Project - Tetris Clone
// by: Alexander Gustafsson (b18alegu)
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

import tetrominoes.*;

public class Game extends IO {
	/******************
	 * Member constants
	 *****************/
	private final static int UPDATE_INTERVAL = 75;	// The time in milliseconds between game refreshes
	
	public final static Color BOARD_BLOCK_COLOR = Color.white;	// Color of the blocks on the board
	public final static Color TETROMINO_COLOR = new Color(250, 114, 104);	// Pantone color of the year 2019 (^_^)
	public final static Color INTERFACE_COLOR = TETROMINO_COLOR;	// Interface main color
	public final static Color INSTRUCTION_COLOR = Color.white;		// Color for the instructions
	
	
	
	/******************
	 * Member variables
	 *****************/
	private Tetromino mTetromino, mNextTetromino;	// Current and next Tetromino
	private int mXPos, mYPos;	// Current position of the mTetromino on the board (in blocks)
	private int mFrameCounter;	// Counts the number of calls to 
	
	private boolean mInGame = false;	// Keeps track of the game's status
	private int mScore;	// Current score (number of full rows removed)
	private Board mBoard;	// The games board, keeping track of all the blocks
	private Timer mTimer;	// Timer to handle the update interval of the game loop
	
	
	
	/*************
	 * Constructor
	 ************/
	public Game() {
		// Initialize member variables
		mBoard = new Board();
		mFrameCounter = 0;
		
		// Set up mTimer so it can send timer events at UPDATE_INTERVAL
		mTimer = new Timer(UPDATE_INTERVAL, this);
		
		// Initialize the game loop
		initGame();
	}
	
	
	
	/***************************
	 * initGame()
	 * Initializes the game loop
	 **************************/
	private void initGame() {
		// Change status of mInGame boolean to true
		mInGame = true;
		
		// Reset the current score
		mScore = 0;
		
		// Create tetrominoes (current and next)
		mTetromino = createTetromino();
		mNextTetromino = createTetromino();
		
		// Calculate starting position (mXPos and mYPos) of mTetromino
		calculateInitialPosition();
		
		// Make sure the board is empty
		mBoard.clear();
		
		
		// Start the timer to start sending ActionEvents
		mTimer.start();
	}
	
	
	
	/*****************************************
	 * createTetromino()
	 * Creates a new Tetromino of random shape
	 ****************************************/
	private Tetromino createTetromino() {
		Tetromino shape;
		
		// There are seven different shapes so generate a random number between 0 and 6
		int randomNumber = (int)(Math.random() * 7);
		
		// Create the new shape. Note, I don't use a case of 6 and instead use default
		// to create the Z shape just to get rid of annoying error messages in eclipse
		switch(randomNumber) {
			case 0: shape = new I(); break;
			case 1: shape = new J(); break;	
			case 2: shape = new L(); break;
			case 3: shape = new O(); break;
			case 4: shape = new S(); break;
			case 5: shape = new T(); break;
			default:shape = new Z(); break;
		}
		
		return shape;
	}
	
	
	
	/*****************************************************************************************
	 * calculateInitialPosition()
	 * Makes sure the falling tetromino (mTetromino) gets correct starting position. Basically
	 * ensure that only one row of the block will be drawn on the first refresh cycle and that
	 * it is centered in the x-axis as well
	 ****************************************************************************************/
	private void calculateInitialPosition() {
		// X Position
		mXPos = mTetromino.getFirstColumn() * -1;	// 1: offset mXPos from emply blocks in the matrix
		mXPos += Board.BOARD_WIDTH / 2;				// 2: Add half size of the board to set start pos from center
		mXPos -= mTetromino.getWidth() / 2;			// 3: Remove half the width of the Tetromino to center it
		
		// Y Position
		mYPos = mTetromino.getLastRow() * -1;
	}
	
	
	
	/********************************************
	 * paintComponent(Graphics gfx)
	 * Overriden method that updates the graphics
	 *******************************************/
	@Override
    public void paintComponent(Graphics gfx) { 
        super.paintComponent(gfx);
        
        // Prepare variables for drawing the interface
        int yPosDiviserLine = Board.BOARD_HEIGHT * Board.BLOCK_SIZE + 1;
        int yPosScore = yPosDiviserLine + 20;
        int yPosInstructionsRow1 = yPosDiviserLine + 104;
        int yPosInstructionsRow2 = yPosInstructionsRow1 + 14;
        String score = new String("Score: " + mScore);
        String instructionsRow1 = new String("Movement: \u2190 \u2193 \u2192");
        String instructionsRow2 = new String("Roation: Z and X");
        
        // Draw interface
        drawHorizontalLine(gfx, yPosDiviserLine, INTERFACE_COLOR);
        drawTextMessage(gfx, score, 6, yPosScore, INTERFACE_COLOR);
        drawTextMessage(gfx, instructionsRow1, 40, yPosInstructionsRow1, INSTRUCTION_COLOR);
        drawTextMessage(gfx, instructionsRow2, 43, yPosInstructionsRow2, INSTRUCTION_COLOR);
        
        // Use correct subroutine to update the graphics depending on if in the game or not
        if(mInGame) drawGame(gfx);
        else drawSplash(gfx);
    }
	
	
	
	/*****************************************************
	 * drawGame(Graphics gfx)
	 * Subroutine for the overriden paintComponenet method
	 * that is called when in a game
	 ****************************************************/
	private void drawGame(Graphics gfx) {
		// These variables will be used throughout this method 
		int xPos, yPos;
		
		// Draw the blocks on the board
		for(int row = 0; row < Board.BOARD_HEIGHT; row++) {
			for(int col = 0; col < Board.BOARD_WIDTH; col++) {
				if(mBoard.isFilled(col, row)) {
					xPos = col * Board.BLOCK_SIZE;
					yPos = row * Board.BLOCK_SIZE;
					drawBlock(gfx, xPos, yPos, Board.BLOCK_SIZE, BOARD_BLOCK_COLOR);
				}
			}
		}
		
		// Draw falling tetromino
		xPos = mXPos * Board.BLOCK_SIZE;
		yPos = mYPos * Board.BLOCK_SIZE;
		drawTetromino(gfx, mTetromino, xPos, yPos, Board.BLOCK_SIZE, TETROMINO_COLOR);
		
		// Draw next tetromino on the interface part of the windows
		xPos = (Board.BOARD_WIDTH - 6) * Board.BLOCK_SIZE;
		yPos = (Board.BOARD_HEIGHT) * Board.BLOCK_SIZE;
		drawTetromino(gfx, mNextTetromino, xPos, yPos, Board.BLOCK_SIZE, TETROMINO_COLOR);
	}
	
	
	
	/*****************************************************************************************
	 * drawSplash(Graphics gfx)
	 * Subroutine for the overriden paintComponenet method
	 * that is called when a game is over and user is being asked if (s)he wants to play again
	 ****************************************************************************************/
	private void drawSplash(Graphics gfx) {
		String gameOver = new String("GAME OVER");
		String pressSpace = new String("Press space to play again");
		
		drawTextMessage(gfx, gameOver, 60, 100, INTERFACE_COLOR);
		drawTextMessage(gfx, pressSpace, 15, 120, INSTRUCTION_COLOR);
	}
	
	
	
	/**********************************************************************
	 * actionPerformed(...)
	 * This method gets called every UPDATE_INTERVAL and does two things:
	 * 1) Decides if it should run the game loop or show a game over splash
	 * 2) Refreshes the graphics with a call to repaint()
	 *********************************************************************/
	@Override
	public void actionPerformed(ActionEvent e) {
		// Increase the number of frames (important for the game loop)
		mFrameCounter++;
		
		// Check if the game is still on, if so runGame(), otherwise runSplash()
		if(mInGame) runGame(); else runSplash();
		
		// Repaint at every tick from mTimer
		repaint();
	}
	
	
	
	/******************************************************************
	 * runGame()
	 * This method runs every refresh cycle of the game (mInGame = true)
	 **********************************************************''******/
	private void runGame() {
		// Check for horizontal movement input
		if(mKeyLeft  && mBoard.isMovePossible(mTetromino, mXPos - 1, mYPos)) mXPos--;
		if(mKeyRight && mBoard.isMovePossible(mTetromino, mXPos + 1, mYPos)) mXPos++;
		
		// If the down key is pressed, move the Tetromino one extra step this cycle
		if(mKeyDown && mBoard.isMovePossible(mTetromino, mXPos, mYPos + 1)) mYPos++;
		
		// Handle clockwise rotation
		if(mKeyX) {
			mTetromino.rotateClockwise();
			
			// If roation was not possible, change it back
			if(!mBoard.isMovePossible(mTetromino, mXPos, mYPos))
				mTetromino.rotateAnticlockwise();
			else
				mKeyZ = false;	// Reset the key so it only spin once per key press
		}
		
		// Handle anticlockwise rotation
		if(mKeyZ) {
			mTetromino.rotateAnticlockwise();
					
			// If roation was not possible, change it back
			if(!mBoard.isMovePossible(mTetromino, mXPos, mYPos))
				mTetromino.rotateClockwise();
			else
				mKeyZ = false;	// Reset the key so it only spin once per key press
		}
		
		// Check if the tetromino can move one step down, otherwise put the tetrmonio on the
		// board and create a new one. Note this is only tested once every three frames to make
		// controls smoother and also making it easier to move tetrominoes to the edges of the
		// screen. Conclusion: Smoother (since UPDATE_INTERVAL can be increased) and more fun
		if(mFrameCounter % 3 == 0) {	
			if(mBoard.isMovePossible(mTetromino, mXPos, mYPos + 1)) {
				mYPos++;
			}
			// And if the tetromino can't go any futher do the following
			else {
				// 1: Put the tetromino on the board
				mBoard.putTetrominoOnBoard(mTetromino, mXPos, mYPos);
			
				// 2: Remove any full rows and add the number of the score
				mScore += mBoard.updateRows();
			
				// 3: Check if the game is over, if so change state of mInGame
				if(mBoard.isGameOver()) {
					mInGame = false;
				}
			
				// 4: Setup the new tetromino, and also the next one
				mTetromino = mNextTetromino;
				calculateInitialPosition();
				mNextTetromino = createTetromino();
			}
		}
	}
	
	
	
	/*******************************************************************************
	 * runSplash()
	 * This method runs every refresh when a game is lost and is awaiting input from
	 * the player to start again (mInGame = false)
	 ******************************************************************************/
	private void runSplash() {
		// If space is pressed, restart the game
		if(mKeySpace) initGame();
	}
}
