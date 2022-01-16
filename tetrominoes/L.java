// Object-Oriented Programming IT366 Spring Term 2019
// Högskolan i Skövde
// Assignment 4: Game Project - Tetris Clone
// by: Alexander Gustafsson (b18alegu)
package tetrominoes;

public class L extends Tetromino {
	/***********************************************************************
	 * "L"-Tetromino Constructor
	 * 
	 * Makes sure the mBlockMatrix is initialized with the correct shape and
	 * that block [2][2] is the center of the tetromino
	 **********************************************************************/
	public L() {
		mBlockMatrix =  new char[][] {
				{0, 0, 0, 0, 0},
				{0, 0, 1, 0, 0},
				{0, 0, 1, 0, 0},
				{0, 0, 1, 1, 0},
				{0, 0, 0, 0, 0},
		};
	}
}