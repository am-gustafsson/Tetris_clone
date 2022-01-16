// Object-Oriented Programming IT366 Spring Term 2019
// Högskolan i Skövde
// Assignment 4: Game Project - Tetris Clone
// by: Alexander Gustafsson (b18alegu)
import java.awt.Color;

import tetrominoes.Tetromino;

public class Board {
	/***********
	 * Constants
	 **********/
	public final static int BOARD_WIDTH = 10;	// Board width in blocks
	public final static int BOARD_HEIGHT = 24;	// Board height in blocks
	public final static int BLOCK_SIZE = 20;	// Width and height of the blocks in pixels
	public final static int INTERFACE_HEIGHT = 130;	// Extra space in the window for information
	
	
	
	/*********************************************************
	 * Private enumeration to handle the all the blocks status 
	 ********************************************************/
	private enum Block { FREE, FILLED };
	
	
	
	/******************
	 * Member variables
	 *****************/
	private Block mBlockMatrix[][];
	
	
	
	/*************
	 * Constructor
	 ************/
	public Board() {
		mBlockMatrix = new Block[BOARD_HEIGHT][BOARD_WIDTH];
	}
	
	
	/*******************************************************
	 * Clear() method
	 * Makes sure all blocks in mBlockMatrix are set to FREE
	 ******************************************************/
	public void clear() {
		for(int row = 0; row < BOARD_HEIGHT; row++) {
			for(int col = 0; col < BOARD_WIDTH; col++) {
				mBlockMatrix[row][col] = Block.FREE;
			}
		}
	}
	
	
	
	/****************************************************************************
	 * isFilled(xPos, yPos)
	 * Returns true if the board is filled at requested position, otherwise false
	 ***************************************************************************/
	public boolean isFilled(int xPos, int yPos) {
		if(mBlockMatrix[yPos][xPos] == Block.FILLED) return true;
		else return false;
	}
	
	
	
	/**************************************************************************
	 * isFree(xPos, yPos)
	 * Returns true if the board is free at requested position, otherwise false
	 *************************************************************************/
	public boolean isFree(int xPos, int yPos) {
		if(mBlockMatrix[yPos][xPos] == Block.FREE) return true;
		else return false;
	}
	
	
	
	/**************************************************************************
	 * isMovePossible(Tetromino tetro, testX, testY)
	 * Tests if a move is possible or not. The testX or testY should be current
	 * position of tetro, plus or minus one, to test the move
	 *************************************************************************/
	public boolean isMovePossible(Tetromino tetro, int testX, int testY) {
		// bCol & bRow: board column & row
		// tCol & tRow: tetromino column & row
		for(int bCol = testX, tCol = 0; bCol < (testX + Tetromino.MATRIX_SIZE); bCol++, tCol++) {
			for(int bRow = testY, tRow = 0; bRow < (testY + Tetromino.MATRIX_SIZE); bRow++, tRow++) {
				// 1: Check if the block is outside the board
				if(bCol < 0 || bCol > (BOARD_WIDTH - 1) || (bRow > (BOARD_HEIGHT - 1))) {
					if(tetro.isTetrominoFilledAt(tCol, tRow))
						return false;
				}
				
				// 2: Check if the block of the tetromino has collided with a block on the board
				if(bRow >= 0) {
					if(tetro.isTetrominoFilledAt(tCol, tRow) && (mBlockMatrix[bRow][bCol] == Block.FILLED))
						return false;
				}
			}
		}
		
		// No collision!
		return true;
	}
	
	
	
	/*************************************************************************
	 * putTetrominoOnBoard(Tetromino tetro, xPos, yPos)
	 * This method stores the blocks of the specified tetromino onto the board
	 ************************************************************************/
	public void putTetrominoOnBoard(Tetromino tetro, int xPos, int yPos) {
		// bCol & bRow: board column & row
		// tCol & tRow: tetromino column & row
		for(int bCol = xPos, tCol = 0; bCol < (xPos + Tetromino.MATRIX_SIZE); bCol++, tCol++) {
			for(int bRow = yPos, tRow = 0; bRow < (yPos + Tetromino.MATRIX_SIZE); bRow++, tRow++) {
				// Only change the state of the blocks which the tetromino cover and also perform
				// safety checks that the blocks are within the board
				if(tetro.isTetrominoFilledAt(tCol, tRow) &&
						(bCol >= 0 && bCol < BOARD_WIDTH) &&
						(bRow >= 0 && bRow < BOARD_HEIGHT)) {
					
					mBlockMatrix[bRow][bCol] = Block.FILLED;
				}
			}
		}
	}
	
	
	
	/**********************************************************
	 * isGameOver()
	 * If any block at the top row is filled, then game is over
	 *********************************************************/
	public boolean isGameOver() {
		// Check first row
		for(int x = 0; x < BOARD_WIDTH; x++) {
			if(mBlockMatrix[0][x] == Block.FILLED) return true;
		}
		
		// Game is not over!!!
		return false;
	}
	
	
	
	/*********************************************************
	 * updateRows()
	 * Returns the number of filled rows and also deletes them
	 ********************************************************/
	public int updateRows() {
		int numberOfClearedRows = 0;
		
		for(int row = 0; row < BOARD_HEIGHT; row++) {
			int numberOfBlocksInRow = 0;
			for(int col = 0; col < BOARD_WIDTH; col++) {
				if(mBlockMatrix[row][col] == Block.FILLED) numberOfBlocksInRow++;
				else break;
			}
			
			// Check if the whole row is filled or not
			if(numberOfBlocksInRow == BOARD_WIDTH) {
				clearRowAt(row);
				numberOfClearedRows++;
			}
		}
		
		return numberOfClearedRows;
	}
	
	
	
	/******************************************
	 * clearRowAt(atRow)
	 * Moves all lines above atRow down one row
	 *****************************************/
	private void clearRowAt(int atRow) {
		// Clear the row
		for(int row = atRow; row > 0; row--) {
			for(int col = 0; col <BOARD_WIDTH; col++) {
				mBlockMatrix[row][col] = mBlockMatrix[row - 1][col];
			}
		}
		
		// Make sure the first row is free
		for(int col = 0; col < BOARD_WIDTH; col++) {
			mBlockMatrix[0][col] = Block.FREE;
		}
	}
}