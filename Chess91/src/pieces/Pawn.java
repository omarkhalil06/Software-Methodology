package pieces;

import java.util.ArrayList;
import java.util.List;

import board.ChessBoard;
import board.FileRank;

/**
 * Pawn comprises the characteristics and behavior of the 
 * pawn chess piece. Extends and inherits the ChessPiece
 * class.
 *  
 * @author Omar Khalil
 * @author Michelle Hwang
 *
 */
public class Pawn extends ChessPiece {
	
	FileRank fr = new FileRank();
	
	/**
	 * Marker for whether the pawn has moved forward by 2
	 * from its starting position. True, if it has, false
	 * otherwise. 
	 */
	private boolean forward;

	/**
	 * Initializes the Pawn object.
	 * 
	 * @param color Color of the piece - either black or white
	 * @param type A label or symbol that identifies the piece, "bp" or "wp"
	 * @param row The row coordinate of the piece
	 * @param col The column coordinate of the piece
	 */
	public Pawn(String color, String type, String row, String col) {
		super(color, type, row, col);
	}
	
	/**
	 * Returns boolean forward value.
	 * 
	 * @return forward Value of forward
	 */
	public boolean getForward() {
		return this.forward;
	}
	
	/**
	 * Sets the boolean value of the forward field.
	 * 
	 * @param b True, if the pawn has just moved forward by 2,
	 * false otherwise
	 */
	public void setForward(boolean b) {
		this.forward = b;
	}
	
	/**
	 * Returns a list of all possible attack moves this piece can go to. 
	 *  
	 * @param color The color of the Pawn object
	 * @param board ChessBoard object
	 * @return moves A list detailing all possible attacks of the piece 
	 */
	public List<String> possibleAttacks(String color, ChessBoard board) {
		List<String> moves = new ArrayList<String>();
		String row = this.getRow();
		String col = this.getCol();
		String pos;
		int r, c;
		
		/**
		 * Returns a list of all possible attack moves this piece can go to.
		 */
		if (color.equals("white")) {
			// move diagonal up left to attack
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
			
			// move diagonal up right to attack
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
		} else {
			// move diagonal up left to attack
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

			// move diagonal up right to attack
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
		}
		return moves;
	}
	
	/**
	 * Returns a list of all possible moves this piece can go to. 
	 *  
	 * @param color The color of the Pawn object
	 * @param board ChessBoard object
	 * @return moves A list detailing all possible moves of the piece 
	 */
	public List<String> possibleMoves(String color, ChessBoard board) {
		List<String> moves = new ArrayList<String>();
		String row = this.getRow();
		String col = this.getCol();
		String pos;
		int r, c;
		
		ChessPiece p;
		
		if (color.equals("white")) {
			// move up 1
			r = Integer.parseInt(row) + 1;
			row = Integer.toString(r);
			if (board.isValidMove(row, col)) {
				p = board.getPiece(row, col);
				if (p == null) {
					pos = row + " " + col;
					moves.add(pos);	
				}
			}		
			
			// move up 2 -- only if at start
			row = this.getRow();
			col = this.getCol();
			if (row.equals("2")) {
				r = Integer.parseInt(row) + 2;
				row = Integer.toString(r);
				if (board.isValidMove(row, col)) {
					p = board.getPiece(row, col);
					
					// forward 2 is available
					if (p == null) {
						int r2 = Integer.parseInt(row) - 1;
						String row2 = Integer.toString(r2);
						p = board.getPiece(row2, col);
						
						// forward 1 is available
						if (p == null) {
							pos = row + " " + col;
							moves.add(pos);	
						}	
					}
				}
			}
			
			// move diagonal up left to attack
			row = this.getRow();
			col = this.getCol();
			r = Integer.parseInt(row) + 1;
			row = Integer.toString(r);
			c = fr.letterToNumber(col.charAt(0)) - 1;
			col = fr.numberToLetter(c);
			if (board.isValidMove(row, col) && (board.getPiece(row, col) != null) && (board.getPiece(row, col).color.equals("black"))) {
				pos = row + " " + col;
				moves.add(pos);
			}
			
			// move diagonal up right to attack
			row = this.getRow();
			col = this.getCol();
			r = Integer.parseInt(row) + 1;
			row = Integer.toString(r);
			c = fr.letterToNumber(col.charAt(0)) + 1;
			col = fr.numberToLetter(c);
			if (board.isValidMove(row, col) &&  (board.getPiece(row, col) != null) &&(board.getPiece(row, col).color.equals("black"))) {
				pos = row + " " + col;
				moves.add(pos);
			}
			
		} else {
			// move down 1
			r = Integer.parseInt(row) - 1;
			row = Integer.toString(r);
			if (board.isValidMove(row, col)) {
				p = board.getPiece(row, col);
				if (p == null) {
					pos = row + " " + col;
					moves.add(pos);	
				}
			}

			// move up 2 -- only if at start
			row = this.getRow();
			col = this.getCol();
			if (row.equals("7")) {
				r = Integer.parseInt(row) - 2;
				row = Integer.toString(r);
				if (board.isValidMove(row, col)) {
					p = board.getPiece(row, col);
					
					// forward 2 is available
					if (p == null) {
						int r2 = Integer.parseInt(row) + 1;
						String row2 = Integer.toString(r2);
						p = board.getPiece(row2, col);
						
						// forward 1 is available
						if (p == null) {
							pos = row + " " + col;
							moves.add(pos);	
						}	
					}
				}
			}
			
			// move diagonal up left to attack
			row = this.getRow();
			col = this.getCol();
			r = Integer.parseInt(row) - 1;
			row = Integer.toString(r);
			c = fr.letterToNumber(col.charAt(0)) - 1;
			col = fr.numberToLetter(c);
			if (board.isValidMove(row, col) &&  (board.getPiece(row, col) != null) &&(board.getPiece(row, col).color.equals("white"))) {
				pos = row + " " + col;
				moves.add(pos);
			}

			// move diagonal up right to attack
			row = this.getRow();
			col = this.getCol();
			r = Integer.parseInt(row) - 1;
			row = Integer.toString(r);
			c = fr.letterToNumber(col.charAt(0)) + 1;
			col = fr.numberToLetter(c);
			if (board.isValidMove(row, col) &&  (board.getPiece(row, col) != null) &&(board.getPiece(row, col).color.equals("white"))) {
				pos = row + " " + col;
				moves.add(pos);
			}
		}
		
		List<String> moves2 = getEnpassant(color, board);
		moves.addAll(moves2);
		
		return moves;
	}
	
	/**
	 * Checks if the en passant criteria is satisfied and if so, returns a list
	 * of possible en passant moves.
	 * 
	 * @param color Color of pawn that will capture the opponent's
	 * @param board ChessBoard object
	 * @return Return a list of possible moves in en passant
	 */
	public List<String> getEnpassant(String color, ChessBoard board) {
		// check appropriate rows: row 4, col c for black, row 5 col c for white
		// check presence of opponent pawn
		// row 4, c+1 or c-1 has white pawn
		// row 5, c+1 or c-1 has black pawn
		// ** opponent's pawn must have just used the forward 2 move
		// if enpassant criteria is satisfied add coordinate to list (attack & possible)

		List<String> moves = new ArrayList<String>();
		ChessPiece p;
		String row = this.getRow();
		String col = this.getCol();
		String pos = null;
		int c;

		if (color.equals("white")) {
			if (row.equals("5")) {
				c = fr.letterToNumber(col.charAt(0)) + 1;
				if (c < 9) {
					col = fr.numberToLetter(c);
					if (board.getPiece(row, col) != null) {
						p = board.getPiece(row, col);
						//System.out.println(p);

						if (p.getColor().equals("black") && p instanceof Pawn
								&& board.getPiece((Integer.parseInt(row) + 1) + "", col) == null) {
							if (((Pawn) p).getForward()) {
								// enpassant OK
								pos = Integer.toString(Integer.parseInt(row) + 1) + " " + col;
								//System.out.println(pos);
								moves.add(pos);	
							}
						}
					}	
				}
				c = fr.letterToNumber(this.getCol().charAt(0)) - 1;
				if (c > 0) {
					col = fr.numberToLetter(c);
					if (board.getPiece(row, col) != null) {
						p = board.getPiece(row, col);
						//System.out.println(p);

						if (p.getColor().equals("black") && p instanceof Pawn 
								&& board.getPiece((Integer.parseInt(row) + 1) + "", col) == null) {
							if (((Pawn) p).getForward()) {
								// enpassant OK
								pos = Integer.toString(Integer.parseInt(row) + 1) + " " + col;
								//System.out.println(pos);

								moves.add(pos);
							}
						}
					}	
				}
			}
		} else {
			if (row.equals("4")) {
				c = fr.letterToNumber(col.charAt(0)) + 1;
				if (c < 9) {
					col = fr.numberToLetter(c);
					if (board.getPiece(row, col) != null) {
						p = board.getPiece(row, col);
						//System.out.println(p);
						if (p.getColor().equals("white") && p instanceof Pawn 
								&& board.getPiece((Integer.parseInt(row) - 1) + "", col) == null) {
							//System.out.println(((Pawn) p).getForward());
							if (((Pawn) p).getForward()) {
								// enpassant OK
								pos = Integer.toString(Integer.parseInt(row) - 1) + " " + col;
								//System.out.println(pos);

								moves.add(pos);
							}
						}
					}	
				}
				c = fr.letterToNumber(this.getCol().charAt(0)) - 1;
				if (c > 0) {
					col = fr.numberToLetter(c);
					if (board.getPiece(row, col) != null) {
						p = board.getPiece(row, col);
						//System.out.println(p);
						if (p.getColor().equals("white") && p instanceof Pawn 
								&& board.getPiece((Integer.parseInt(row) - 1) + "", col) == null) {
							if (((Pawn) p).getForward()) {
								// enpassant OK
								pos = Integer.toString(Integer.parseInt(row) - 1) + " " + col;
								//System.out.println(pos);

								moves.add(pos);
							}
						}
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
