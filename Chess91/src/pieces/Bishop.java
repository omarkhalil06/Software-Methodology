package pieces;

import java.util.List;

import board.ChessBoard;
import board.FileRank;

/**
 * Bishop comprises the characteristics and behavior of the 
 * bishop chess piece. Extends and inherits the ChessPiece
 * class.
 *  
 * @author Omar Khalil
 * @author Michelle Hwang
 *
 */
public class Bishop extends ChessPiece {

	FileRank fr = new FileRank();
	
	/**
	 * Initializes the Bishop object.
	 * 
	 * @param color Color of the piece - either black or white
	 * @param type A label or symbol that identifies the piece, "bB" or "wB"
	 * @param row The row coordinate of the piece
	 * @param col The column coordinate of the piece
	 */
	public Bishop(String color, String type, String row, String col) {
		super(color, type, row, col);
	}
	
	/**
	 * Returns a list of all possible moves this piece can go to. 
	 *  
	 * @param color The color of the Bishop object
	 * @param board ChessBoard object
	 * @return moves A list detailing all possible moves of the piece 
	 */
	public List<String> possibleMoves(String color, ChessBoard board) {
		List<String> moves = possibleDiagonal(color, board);
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
