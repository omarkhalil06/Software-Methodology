package pieces;

import java.util.ArrayList;
import java.util.List;

import board.ChessBoard;
import board.FileRank;

/**
 * Knight comprises the characteristics and behavior of the 
 * knight chess piece. Extends and inherits the ChessPiece
 * class.
 *  
 * @author Omar Khalil
 * @author Michelle Hwang
 *
 */
public class Knight extends ChessPiece {

	FileRank fr = new FileRank();
	
	/**
	 * Initializes the Knight object.
	 * 
	 * @param color Color of the piece - either black or white
	 * @param type A label or symbol that identifies the piece, "bN" or "wN"
	 * @param row The row coordinate of the piece
	 * @param col The column coordinate of the piece
	 */
	public Knight(String color, String type, String row, String col) {
		super(color, type, row, col);
	}
	
	/**
	 * Returns a list of all possible moves this piece can go to. 
	 *  
	 * @param color The color of the Knight object
	 * @param board
	 * @return moves A list detailing all possible moves of the piece 
	 */
	public List<String> possibleMoves(String color, ChessBoard board) {
		List<String> moves = new ArrayList<String>();
		String row = this.getRow();
		String col = this.getCol();
		String pos;
		int r, c;
		
		// move up 2 right 1
		r = Integer.parseInt(row) + 2;
		row = Integer.toString(r);
		c = fr.letterToNumber(col.charAt(0)) + 1;
		col = fr.numberToLetter(c);
		if (board.isValidMove(row, col)) {
			pos = row + " " + col;
			moves.add(pos);
		}
		
		// move up 2 left 1
		row = this.getRow();
		col = this.getCol();
		r = Integer.parseInt(row) + 2;
		row = Integer.toString(r);
		c = fr.letterToNumber(col.charAt(0)) - 1;
		col = fr.numberToLetter(c);
		if (board.isValidMove(row, col)) {
			pos = row + " " + col;
			moves.add(pos);
		}
		
		// move up 1 right 2
		row = this.getRow();
		col = this.getCol();
		r = Integer.parseInt(row) + 1;
		row = Integer.toString(r);
		c = fr.letterToNumber(col.charAt(0)) + 2;
		col = fr.numberToLetter(c);
		if (board.isValidMove(row, col)) {
			pos = row + " " + col;
			moves.add(pos);
		}
		
		// move down 1 right 2
		row = this.getRow();
		col = this.getCol();
		r = Integer.parseInt(row) - 1;
		row = Integer.toString(r);
		c = fr.letterToNumber(col.charAt(0)) + 2;
		col = fr.numberToLetter(c);
		if (board.isValidMove(row, col)) {
			pos = row + " " + col;
			moves.add(pos);
		}
		
		// move down 2 right 1
		row = this.getRow();
		col = this.getCol();
		r = Integer.parseInt(row) - 2;
		row = Integer.toString(r);
		c = fr.letterToNumber(col.charAt(0)) + 1;
		col = fr.numberToLetter(c);
		if (board.isValidMove(row, col)) {
			pos = row + " " + col;
			moves.add(pos);
		}
		
		// move down 2 left 1
		row = this.getRow();
		col = this.getCol();
		r = Integer.parseInt(row) - 2;
		row = Integer.toString(r);
		c = fr.letterToNumber(col.charAt(0)) - 1;
		col = fr.numberToLetter(c);
		if (board.isValidMove(row, col)) {
			pos = row + " " + col;
			moves.add(pos);
		}
		
		// move up 1 left 2
		row = this.getRow();
		col = this.getCol();
		r = Integer.parseInt(row) + 1;
		row = Integer.toString(r);
		c = fr.letterToNumber(col.charAt(0)) - 2;
		col = fr.numberToLetter(c);
		if (board.isValidMove(row, col)) {
			pos = row + " " + col;
			moves.add(pos);
		}
		
		// move down 1 left 2
		row = this.getRow();
		col = this.getCol();
		r = Integer.parseInt(row) - 1;
		row = Integer.toString(r);
		c = fr.letterToNumber(col.charAt(0)) - 2;
		col = fr.numberToLetter(c);
		if (board.isValidMove(row, col)) {
			pos = row + " " + col;
			moves.add(pos);
		}
		
		return moves;
	}
	
	/**
	 * Checks for all possible moves from possibleMoves(). If row and 
	 * col equals one of the moves, then return true. Otherwise, 
	 * return false.
	 * 
	 * @param row The row of the destination coordinate
	 * @param col The column of the destination coordinate
	 * @param board ChessBoard object
	 * @return Returns true if the specified coordinate is a valid move
	 */
	public boolean validPieceMove(String row, String col, ChessBoard board) {
		List<String> moves = this.possibleMoves(this.color, board);
		//System.out.println();
		for (String m : moves) {
			String r = m.substring(0,1);
			String c = m.substring(2);
			//System.out.println(r + " " + c);
			if (row.equals(r) && col.equals(c)) {
				return true;
			}
		}
		return false;
	}
	
}
