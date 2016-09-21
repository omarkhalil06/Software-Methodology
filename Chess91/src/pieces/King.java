package pieces;

import java.util.ArrayList;
import java.util.List;

import board.ChessBoard;
import board.FileRank;

/**
 * King comprises the characteristics and behavior of the 
 * king chess piece. Extends and inherits the ChessPiece
 * class.
 *  
 * @author Omar Khalil
 * @author Michelle Hwang
 *
 */
public class King extends ChessPiece {

	FileRank fr = new FileRank();
	
	/**
	 * Initializes the King object.
	 * 
	 * @param color Color of the piece - either black or white
	 * @param type A label or symbol that identifies the piece, "bK" or "wK"
	 * @param row The row coordinate of the piece
	 * @param col The column coordinate of the piece
	 */
	public King(String color, String type, String row, String col) {
		super(color, type, row, col);
	}
	
	/**
	 * Returns a list of all possible moves this piece can go to. 
	 *  
	 * @param color The color of the King object
	 * @param board ChessBoard object
	 * @return moves A list detailing all possible moves of the piece 
	 */
	public List<String> possibleMoves(String color, ChessBoard board) {
		List<String> moves = new ArrayList<String>();
		String row = this.getRow();
		String col = this.getCol();
		String pos;
		int r, c;
		
		// move up 1
		r = Integer.parseInt(row) + 1;
		row = Integer.toString(r);
		if (board.isValidMove(row, col)) {
			pos = row + " " + col;
			moves.add(pos);
		}
		
		// move down 1
		row = this.getRow();
		col = this.getCol();
		r = Integer.parseInt(row) - 1;
		row = Integer.toString(r);
		if (board.isValidMove(row, col)) {
			pos = row + " " + col;
			moves.add(pos);
		}
		
		// move left 1
		row = this.getRow();
		col = this.getCol();
		c = fr.letterToNumber(col.charAt(0)) - 1;
		col = fr.numberToLetter(c);
		if (board.isValidMove(row, col)) {
			pos = row + " " + col;
			moves.add(pos);
		}
		
		// move right 1
		row = this.getRow();
		col = this.getCol();
		c = fr.letterToNumber(col.charAt(0)) + 1;
		col = fr.numberToLetter(c);
		if (board.isValidMove(row, col)) {
			pos = row + " " + col;
			moves.add(pos);
		}

		// move up 1 right 1
		row = this.getRow();
		col = this.getCol();
		r = Integer.parseInt(row) + 1;
		row = Integer.toString(r);
		c = fr.letterToNumber(col.charAt(0)) + 1;
		col = fr.numberToLetter(c);
		if (board.isValidMove(row, col)) {
			pos = row + " " + col;
			moves.add(pos);
		}
		
		// move up 1 left 1
		row = this.getRow();
		col = this.getCol();
		r = Integer.parseInt(row) + 1;
		row = Integer.toString(r);
		c = fr.letterToNumber(col.charAt(0)) - 1;
		col = fr.numberToLetter(c);
		if (board.isValidMove(row, col)) {
			pos = row + " " + col;
			moves.add(pos);
		}
		
		// move down 1 left 1
		row = this.getRow();
		col = this.getCol();
		r = Integer.parseInt(row) - 1;
		row = Integer.toString(r);
		c = fr.letterToNumber(col.charAt(0)) - 1;
		col = fr.numberToLetter(c);
		if (board.isValidMove(row, col)) {
			pos = row + " " + col;
			moves.add(pos);
		}
		
		// move down 1 right 1
		row = this.getRow();
		col = this.getCol();
		r = Integer.parseInt(row) - 1;
		row = Integer.toString(r);
		c = fr.letterToNumber(col.charAt(0)) + 1;
		col = fr.numberToLetter(c);
		if (board.isValidMove(row, col)) {
			pos = row + " " + col;
			moves.add(pos);
		}
		
		// castling moves
		row = this.getRow();
		col = this.getCol();
		if (color.equals("white")) {
			if (row.equals("1") && col.equals("e")) {
				
				if (!board.isEmpty(board, "1", "a")) {
					if (board.isEmpty(board, "1", "b") && board.isEmpty(board, "1", "c") && board.isEmpty(board, "1", "d")
							&& board.getPiece("1", "a").type.equals("wR")) {
						c = fr.letterToNumber(col.charAt(0)) - 2;
						col = fr.numberToLetter(c);
						pos = row + " " + col;
						moves.add(pos);
					}
				}
				
				if (!board.isEmpty(board, "1", "h")) {
					if (board.isEmpty(board, "1", "f") && board.isEmpty(board, "1", "g") 
							&& board.getPiece("1", "h").type.equals("wR")) {
						col = this.getCol();
						c = fr.letterToNumber(col.charAt(0)) + 2;
						col = fr.numberToLetter(c);
						pos = row + " " + col;
						moves.add(pos);
					}
				}
			}
		} else {
			if (row.equals("8") && col.equals("e")) {
				
				if (!board.isEmpty(board, "8", "a")) {
					if (board.isEmpty(board, "8", "b") && board.isEmpty(board, "8", "c") && board.isEmpty(board, "8", "d")
							&& board.getPiece("8", "a").type.equals("bR")) {
						c = fr.letterToNumber(col.charAt(0)) - 2;
						col = fr.numberToLetter(c);
						pos = row + " " + col;
						moves.add(pos);
					}
				}

				if (!board.isEmpty(board, "8", "h")) {
					if (board.isEmpty(board, "8", "f") && board.isEmpty(board, "8", "g")
							&& board.getPiece("8", "h").type.equals("bR")) {
						col = this.getCol();
						c = fr.letterToNumber(col.charAt(0)) + 2;
						col = fr.numberToLetter(c);
						pos = row + " " + col;
						moves.add(pos);
					}
				}
			}
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
