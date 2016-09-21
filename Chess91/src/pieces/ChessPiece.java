package pieces;

import java.util.ArrayList;
import java.util.List;

import board.ChessBoard;
import board.FileRank;

/**
 * ChessPiece is the superclass to all pieces. Possess the common 
 * characteristics and behavior.
 *  
 * @author Omar Khalil
 * @author Michelle Hwang
 *
 */
public class ChessPiece {
	
	FileRank fr = new FileRank();
	
	public String color;
	public String type;
	protected String row;
	protected String col;
	
	/**
	 * Initializes a ChessPiece object.
	 * 
	 * @param color Color of the piece - either black or white
	 * @param type A label or symbol that identifies the piece
	 * @param row The row coordinate of the piece
	 * @param col The column coordinate of the piece
	 */
	public ChessPiece(String color, String type, String row, String col) {
		this.color = color;
		this.type = type;
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Returns the color field.
	 * 
	 * @return color Color of piece
	 */
	public String getColor() {
		return color;
	}
	
	/**
	 * Sets the value of the color field.
	 * 
	 * @param color Color of piece
	 */
	public void setColor(String color) {
		this.color = color;
	}
	
	/**
	 * Returns the type of the chess piece.
	 * 
	 * @return type Type of piece
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the type of the chess piece.
	 * 
	 * @param type Type of piece
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Returns the row where the piece is located.
	 * 
	 * @return row Row number
	 */
	public String getRow() {
		return row;
	}
	
	/**
	 * Sets the row.
	 * 
	 * @param row Row number
	 */
	public void setRow(String row) {
		this.row = row;
	}
	
	/**
	 * Returns the column where the piece is located.
	 * 
	 * @return col Column letter
	 */
	public String getCol() {
		return col;
	}
	
	/**
	 * Sets the column.
	 * 
	 * @param col Column letter
	 */
	public void setCol(String col) {
		this.col = col;
	}
	
	/**
	 * Returns fields of the object as a string
	 * 
	 * @return piece String description of piece
	 */
	public String toString() {
		return this.getColor() + " " + this.getType() + " " + this.getRow() + " " + this.getCol();
	}
	
	/**
	 * Returns a list of Strings detailing the chess piece's legal
	 * diagonal moves. The piece cannot move diagonally over another
	 * object if that object is in its path.
	 * 
	 * @param color Color of the piece
	 * @param board ChessBoard object
	 * @return moves A list of possible diagonal moves
	 */
	public List<String> possibleDiagonal(String color, ChessBoard board) {
		ChessPiece p;
		List<String> moves = new ArrayList<String>();

		String row = this.getRow();
		String col = this.getCol();
		String pos;
		
		int rowNum = Integer.parseInt(row);
		int colNum = fr.letterToNumber(col.charAt(0));
		//System.out.println(this);
		
		// stop when
			// 1 - piece of same color is found, do not include this coordinate
			// 2 - opponent piece is found, include this coordinate
	
		// get left increasing diagonal
		int j = rowNum + 1;
		int k = colNum - 1;
		while (j < 9 && k > 0) {
			p = board.getPiece(j + "", fr.numberToLetter(k));
			if (p != null) {
				if (p.getColor().equals(color)) {
					// do not include
				} else {
					pos = j + " " + fr.numberToLetter(k);
					moves.add(pos);
				}
				break;
			} else {
				pos = j + " " + fr.numberToLetter(k);
				moves.add(pos);
			
				j++;
				k--;
			}
		}
		
		// get right increasing diagonal
		j = rowNum + 1;
		k = colNum + 1;
		while (j < 9 && k < 9) {
			p = board.getPiece(j + "", fr.numberToLetter(k));
			if (p != null) {
				if (p.getColor().equals(color)) {
					// do not include
				} else {
					pos = j + " " + fr.numberToLetter(k);
					moves.add(pos);
				}
				break;
			} else {
				pos = j + " " + fr.numberToLetter(k);
				moves.add(pos);
			
				j++;
				k++;
			}
		}
		
		// get right decreasing diagonal
		j = rowNum - 1;
		k = colNum + 1;
		while (j > 0 && k < 9) {
			p = board.getPiece(j + "", fr.numberToLetter(k));
			if (p != null) {
				if (p.getColor().equals(color)) {
					// do not include
				} else {
					pos = j + " " + fr.numberToLetter(k);
					moves.add(pos);
				}
				break;
			} else {
				pos = j + " " + fr.numberToLetter(k);
				moves.add(pos);
			
				j--;
				k++;
			}
		}
		
		// get left decreasing diagonal
		j = rowNum - 1;
		k = colNum - 1;
		while (j > 0 && k > 0) {
			p = board.getPiece(j + "", fr.numberToLetter(k));
			if (p != null) {
				if (p.getColor().equals(color)) {
					// do not include
				} else {
					pos = j + " " + fr.numberToLetter(k);
					moves.add(pos);
				}
				break;
			} else {
				pos = j + " " + fr.numberToLetter(k);
				moves.add(pos);
			
				j--;
				k--;
			}
		}
		
		return moves;
	}
	
	/**
	 * Returns a list of Strings detailing the chess piece's legal
	 * forward/backward and sideways moves. The piece cannot jump 
	 * over and object if that object is in its path. 
	 * 
	 * @param color Color of the piece
	 * @param board ChessBoard object
	 * @return moves A list of possible moves
	 */
	public List<String> possibleXY(String color, ChessBoard board) {
		List<String> moves = new ArrayList<String>();

		String row = this.getRow();
		String col = this.getCol();
		String pos;
		
		int rowNum = Integer.parseInt(row);
		int colNum = fr.letterToNumber(col.charAt(0));
		
		// stop when
			// 1 - piece of same color is found, do not include this coordinate
			// 2 - opponent piece is found, include this coordinate
		
		// get coordinates of increasing row number
		int j = rowNum + 1;
		while (j < 9) {
			ChessPiece p = board.getPiece(j + "", col);
			if (p != null) {
				if (p.getColor().equals(color)) {
					// do not include
				} else {
					pos = j + " " + col;
					moves.add(pos);
				}
				break;
			} else {
				pos = j + " " + col;
				moves.add(pos);

				j++;
			}
		}
		
		j = rowNum - 1;
		// get coordinates of decreasing row number
		while (j > 0) {
			ChessPiece p = board.getPiece(j + "", col);
			if (p != null) {
				if (p.getColor().equals(color)) {
					// do not include
				} else {
					pos = j + " " + col;
					moves.add(pos);
				}
				break;
			} else {
				pos = j + " " + col;
				moves.add(pos);
				j--;
			}
		}
		
		// get coordinates of increasing col number
		int k = colNum + 1;
		while (k < 9) {
			ChessPiece p = board.getPiece(row, fr.numberToLetter(k));
			if (p != null) {
				if (p.getColor().equals(color)) {
					// do not include
				} else {
					pos = row + " " + fr.numberToLetter(k);
					moves.add(pos);
				}
				break;
			} else {
				pos = row + " " + fr.numberToLetter(k);
				moves.add(pos);

				k++;
			}
		}
		
		k = colNum - 1;
		// get coordinates of decreasing row number
		while (k > 0) {
			ChessPiece p = board.getPiece(row, fr.numberToLetter(k));
			if (p != null) {
				if (p.getColor().equals(color)) {
					// do not include
				} else {
					pos = row + " " + fr.numberToLetter(k);
					moves.add(pos);
				}
				break;
			} else {
				pos = row + " " + fr.numberToLetter(k);
				moves.add(pos);
				k--;
			}
		}
		return moves;

	}
	
	/**
	 * Returns a list of Strings detailing the chess piece's legal
	 * moves.
	 * 
	 * @param color Color of the piece
	 * @param board ChessBoard object
	 * @return moves A list of possible moves
	 */
	public List<String> possibleMoves(String color, ChessBoard board) {
		List<String> moves = new ArrayList<String>();
		return moves;
	}
	
	/**
	 * Checks if a specified move is legal for a specific chess piece
	 * by checking if the specified move exists in the list of all 
	 * possible moves
	 * 
	 * @param row Row number
	 * @param col Column letter
	 * @param board ChessBoard object
	 * @return True, if the move is legal, false otherwise
	 */
	public boolean validPieceMove(String row, String col, ChessBoard board) {
		return false;
	}

}
