// Object-Oriented Programming IT366 Spring Term 2019
// Högskolan i Skövde
// Assignment 4: Game Project - Tetris Clone
// by: Alexander Gustafsson (b18alegu)
package tetrominoes;

public abstract class Tetromino {
	/***********
	 * Constants
	 **********/
	public final static int MATRIX_SIZE = 5;	// The width and height of the matrix that holds the shape 
	
	
	
	/******************
	 * Member variables
	 *****************/
	protected char mBlockMatrix[][];	// The matrix that contains the shape of the Tetromino
	
	
	
	/*************
	 * Constructor
	 ************/
	public Tetromino() {
		mBlockMatrix = new char[MATRIX_SIZE][MATRIX_SIZE];
	}

	
	
	/************
	 * getWidth()
	 ***********/
	public int getWidth() {
		int width = 0;
		
		// Cycle through all columns looking for blocks. Add 1 to width per column with a filled block
		for(int col = 0; col < MATRIX_SIZE; col++) {
			for(int row = 0; row < MATRIX_SIZE; row++) {
				if(mBlockMatrix[row][col] == 1) {
					width++;
					
					// Make sure only 1 block per row is counted
					break;
				}
			}
		}
		
		return width;
	}

	
	
	/**************
	 * getLastRow()
	 *************/
	public int getLastRow() {
		for(int row = (MATRIX_SIZE - 1); row > 0; row--) {
			for(int col = 0; col < MATRIX_SIZE; col++) {
				if(mBlockMatrix[row][col] == 1)
					return row;
			}
		}
	
		// If no row with an element is found... (not supposed to happen!)
		return -1;
	}
	
	
	
	/******************
	 * getFirstColumn()
	 *****************/
	public int getFirstColumn() {
		for(int col = 0; col < MATRIX_SIZE; col++) {
			for(int row = 0; row < MATRIX_SIZE; row++) {
				if(mBlockMatrix[row][col] == 1)
					return col;
			}
		}
		
		// If no column with an element is found... (not supposed to happen!)
		return -1;
	}
	
	
	
	/********************************************************
	 * isTetrominoFilledAt(col, row)
	 * Returns true if the Tetromino is filled at asked index
	 *******************************************************/
	public boolean isTetrominoFilledAt(int col, int row) {
		if(mBlockMatrix[row][col] == 1) return true;
		else return false;
	}
	
	
	
	/******************
	 * Rotation methods
	 *****************/
	public void rotateClockwise() {
		char updatedMatrix[][] = new char[MATRIX_SIZE][MATRIX_SIZE];
		
		// Cycle through all elements and update the temporary matrix updateMatrix with
		// values read in a clockwise manner from mBlockMatrix
		for(int i = 0; i < MATRIX_SIZE; i++) {
			for(int j = 0; j < MATRIX_SIZE; j++) {
				updatedMatrix[i][(MATRIX_SIZE - 1) - j] = mBlockMatrix[j][i];
			}
		}
		
		// Update mBlockMatrix
		mBlockMatrix = updatedMatrix;
	}
	
	public void rotateAnticlockwise() {
		char updatedMatrix[][] = new char[MATRIX_SIZE][MATRIX_SIZE];
		
		// Cycle through all elements and update the temporary matrix updateMatrix with
		// values read in a anticlockwise manner from mBlockMatrix
		for(int i = 0; i < MATRIX_SIZE; i++) {
			for(int j = 0; j < MATRIX_SIZE; j++) {
				updatedMatrix[(MATRIX_SIZE - 1) - i][j] = mBlockMatrix[j][i];
			}
		}
	
		// Update mBlockMatrix
		mBlockMatrix = updatedMatrix;
	}
}