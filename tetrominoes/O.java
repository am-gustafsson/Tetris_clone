// Object-Oriented Programming IT366 Spring Term 2019
// Högskolan i Skövde
// Assignment 4: Game Project - Tetris Clone
// by: Alexander Gustafsson (b18alegu)
package tetrominoes;

public class O extends Tetromino {
	/***********************************************************************
	 * "O"-Tetromino Constructor
	 * 
	 * Makes sure the mBlockMatrix is initialized with the correct shape and
	 * that block [2][2] is the center of the tetromino
	 **********************************************************************/
	public O() {
		mBlockMatrix =  new char[][] {
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0},
				{0, 0, 1, 1, 0},
				{0, 0, 1, 1, 0},
				{0, 0, 0, 0, 0},
		};
	}
	
	/********************************************************************
	 * Override the rotation methods for the "O"-tetromino to do nothing,
	 * since the shape is constant. @Override is used, just in case I
	 * change the name of the methods in Tetromino.java later
	 *******************************************************************/
	@Override
	public void rotateClockwise() {}
	@Override
	public void rotateAnticlockwise() {}
}