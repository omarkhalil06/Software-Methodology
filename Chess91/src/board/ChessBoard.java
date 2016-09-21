package board;

import java.util.ArrayList;
import java.util.List;

import pieces.Bishop;
import pieces.ChessPiece;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

/**
 * The ChessBoard class implements the game board object, and methods helpful 
 * in displaying it, as well as for creating game events (check, checkmate, 
 * promotion, enpassant, etc.)
 * 
 * @author Omar Khalil
 * @author Michelle Hwang
 */
public class ChessBoard {	
	
	FileRank fr = new FileRank();
	String[][] board = new String[9][9];
	
	ArrayList<ChessPiece> black = new ArrayList<ChessPiece>();
	ArrayList<ChessPiece> white = new ArrayList<ChessPiece>();
	private int runningGame = 0;
	
	/**
	 * Constructor that initializes the chess board with its game
	 * pieces.
	 */
	public ChessBoard() {
		black.add(new King("black", "bK", "8","e"));
		black.add(new Pawn("black", "bp", "7", "a"));
		black.add(new Pawn("black", "bp", "7", "b"));
		black.add(new Pawn("black", "bp", "7", "c"));
		black.add(new Pawn("black", "bp", "7", "d"));
		black.add(new Pawn("black", "bp", "7", "e"));
		black.add(new Pawn("black", "bp", "7", "f"));
		black.add(new Pawn("black", "bp", "7", "g"));
		black.add(new Pawn("black", "bp", "7", "h"));
		black.add(new Rook("black", "bR", "8", "a"));
		black.add(new Knight("black", "bN", "8", "b"));
		black.add(new Bishop("black", "bB", "8", "c"));
		black.add(new Queen("black", "bQ", "8", "d"));
		black.add(new Bishop("black", "bB", "8", "f"));
		black.add(new Knight("black", "bN", "8", "g"));
		black.add(new Rook("black", "bR", "8", "h"));
		
		white.add(new King("white", "wK", "1","e"));
		white.add(new Pawn("white", "wp", "2", "a"));
		white.add(new Pawn("white", "wp", "2", "b"));
		white.add(new Pawn("white", "wp", "2", "c"));
		white.add(new Pawn("white", "wp", "2", "d"));
		white.add(new Pawn("white", "wp", "2", "e"));
		white.add(new Pawn("white", "wp", "2", "f"));
		white.add(new Pawn("white", "wp", "2", "g"));
		white.add(new Pawn("white", "wp", "2", "h"));
		white.add(new Rook("white", "wR", "1", "a"));
		white.add(new Knight("white", "wN", "1", "b"));
		white.add(new Bishop("white", "wB", "1", "c"));
		white.add(new Queen("white", "wQ", "1", "d"));
		white.add(new Bishop("white", "wB", "1", "f"));
		white.add(new Knight("white", "wN", "1", "g"));
		white.add(new Rook("white", "wR", "1", "h"));
		
		/*// for stalemate testing
		black.add(new King("black", "bK", "8","e"));
		white.add(new King("white", "wK", "7","c"));
		white.add(new Queen("white", "wQ", "5", "f"));*/
		
		
		/*// for checkmate testing
		black.add(new King("black", "bK", "8","e"));
		white.add(new King("white", "wK", "7","c"));
		white.add(new Queen("white", "wQ", "6", "f"));
		white.add(new Queen("white", "wQ", "1", "d"));*/
		
	}
	
	/**
	 * Labels rows and columns of chess board.
	 */
	public void labelBoard() {
		board[0][8] = "8"; board[1][8] = "7"; board[2][8] = "6";
		board[3][8] = "5"; board[4][8] = "4"; board[5][8] = "3";
		board[6][8] = "2"; board[7][8] = "1";
		
		board[8][0] = " a"; board[8][3] = " d"; board[8][6] = " g";
		board[8][1] = " b"; board[8][4] = " e"; board[8][7] = " h";
		board[8][2] = " c"; board[8][5] = " f"; 
	}
	
	/**
	 * Prints out the chess board.
	 */
	public void displayBoard() {
		
		int row = 0;
		int col = 0;

		// gives coordinates of each piece corr. to board 2D array
		if (runningGame == 0) {
			for(ChessPiece piece : white) {
				row = Math.abs(Integer.parseInt(piece.getRow()) - 8);
				col = Math.abs(fr.letterToNumber(piece.getCol().charAt(0)) - 1);
				board[row][col] = piece.type;
				//System.out.println(row + " " + col);
			}

			for(ChessPiece piece : black) {
				row = Math.abs(Integer.parseInt(piece.getRow()) - 8);
				col = Math.abs(fr.letterToNumber(piece.getCol().charAt(0)) - 1);
				board[row][col] = piece.type;
				//System.out.println(row + " " + col);
			}
		}

		for(int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j] == null) {
					if (i%2 == 0) {
						if (j%2 != 0) {
							System.out.print("## ");							
						} else {
							System.out.print("   ");
						}
					} else {
						if (j%2 == 0) {
							System.out.print("## ");
						} else {
							System.out.print("   ");							
						}
					}
				} else {
					System.out.print(board[i][j] + " ");
				}
			}
			System.out.println();
		}
		
		System.out.println();
		
	}
	
	/**
	 * Checks that user input matches expected format (ex. "e5 e4").
	 * 
	 * @param from String coordinate of origin
	 * @param to String coordinate of destination
	 * @return True if input is in correct format, false otherwise
	 */
	public boolean checkInput(String from, String to) {
		if (from.length() == 2 && to.length() == 2) {
			if (Character.isLetter(from.charAt(0)) && Character.isLetter(to.charAt(0))) {
				if (Character.isDigit(from.charAt(1)) && Character.isDigit(to.charAt(1))) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks if king has been captured and announces to user.
	 * 
	 * @param color Color of the player to be checked
	 * @param board ChessBoard object
	 */
	public void captureKing(String color, ChessBoard board) {
		if (color.equals("black")) {
			for (ChessPiece p : black) {
				if (p.getType().equals("bK")) {
					return;
				}
			}
		} else {
			for (ChessPiece p : white) {
				if (p.getType().equals("wK")) {
					return;
				}
			}
		}
		if (color.equals("white")) {
			System.out.println("Black wins");
		} else {
			System.out.println("White wins");
		}
		//System.out.println(color + "'s king has been captured. " + color + " loses.");
		System.exit(0);
	}
	
	/**
	 * Checks if the move is valid and not out of bounds or illegal.
	 * 
	 * @param row String row coordinate of destination
	 * @param col String column coordinate of destination
	 * @return True if the move is in bounds, false otherwise
	 */
	public boolean isValidMove(String row, String col) {
		int r = Integer.parseInt(row);
		int c = fr.letterToNumber(col.charAt(0));
		//System.out.println(r + " " + c);
		if ((c < 0 || c > 8) || (r < 1 || r > 8)) {
			//System.out.println("out of bounds");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Retrieves the ChessPiece object at (row, col).
	 * 
	 * @param row Row of object to be returned
	 * @param col Column of object to be returned
	 * @return The ChessPiece object located at the specified coordinate or null
	 */
	public ChessPiece getPiece(String row, String col) {
		if (!isValidMove(row, col)) {
			System.out.println("out of bounds");
			return null;
		}

		for(ChessPiece p : white) {
			if (p.getRow().compareTo(row) == 0 && p.getCol().compareTo(col) == 0) {
				return p;
			}
		}	
		for(ChessPiece p : black) {
			if (p.getRow().compareTo(row) == 0 && p.getCol().compareTo(col) == 0) {
				return p;
			}
		}
		return null;
	}
	
	

	/**
	 * Checks to see if there is stalemate, where king has no 
	 * available moves to go but is not in checkmate. 
	 * 
	 * @param color Color of player
	 * @param board Chessboard object
	 */
	public void stalemate(String color, ChessBoard board) {
		List<String> temp;
		List<String> k_moves = new ArrayList<String>();
		List<String> tempMoves = new ArrayList<String>();
	
		if (color.equals("white")) {
			// check if any other piece can move
			for (ChessPiece p : white) {
				if ((p.possibleMoves("white", board) != null) && !(p instanceof King)) {
					return;
				}
			}
			
			ChessPiece king = white.get(0);
			//System.out.println(king);
			temp = king.possibleMoves("white", board);
			for (String s : temp) {
				String row = s.substring(0,1);
				String col = s.substring(2);
				//System.out.println(row + " " + col);
				if (board.isEmpty(board, row, col)) {
					k_moves.add(s);
				} else if (board.getPiece(row, col).color.equals("black")) {
					k_moves.add(s);
				}
			}
			
			tempMoves.addAll(k_moves);
			//System.out.println("temp:" + tempMoves);
			
			String kingPos = king.getRow() + " " + king.getCol();
			
			for (String coord : tempMoves) {
				for (ChessPiece p : black) {
					if (p instanceof Pawn) {
						Pawn pawn = (Pawn) p;
						if (pawn.possibleAttacks("black", board).contains(kingPos)) {
							return;
						}
						if (pawn.possibleAttacks("black", board).contains(coord)) {
							k_moves.remove(coord);
							continue;
						}
					} else {
						if (p.possibleMoves("black", board).contains(kingPos)) {
							return;
						}
						if (p.possibleMoves("black", board).contains(coord)) {
							k_moves.remove(coord);
							continue;
						}
					}
				}
			}
			
		} else {
			// check if any other piece can move
			for (ChessPiece p : black) {
				if ((p.possibleMoves("black", board) != null) && !(p instanceof King)) {
					return;
				}
			}
			
			ChessPiece king = black.get(0);
			//System.out.println(king);
			temp = king.possibleMoves("black", board);
			for (String s : temp) {
				String row = s.substring(0,1);
				String col = s.substring(2);
				//System.out.println(row + " " + col);
				if (board.isEmpty(board, row, col)) {
					k_moves.add(s);
				} else if (board.getPiece(row, col).color.equals("white")) {
					k_moves.add(s);
				}
			}
			
			tempMoves.addAll(k_moves);
			//System.out.println("temp:" + tempMoves);
			
			String kingPos = king.getRow() + " " + king.getCol();
			
			for (String coord : tempMoves) {
				for (ChessPiece p : white) {
					if (p instanceof Pawn) {
						Pawn pawn = (Pawn) p;
						if (pawn.possibleAttacks("white", board).contains(kingPos)) {
							return;
						}
						if (pawn.possibleAttacks("white", board).contains(coord)) {
							k_moves.remove(coord);
							continue;
						}
					} else {
						if (p.possibleMoves("white", board).contains(kingPos)) {
							return;
						}
						if (p.possibleMoves("white", board).contains(coord)) {
							k_moves.remove(coord);
							continue;
						}
					}
				}
			}
		}
		
		// king has no where to move and no other piece can move
		if (k_moves.isEmpty()) {
			System.out.println("Stalemate");
			System.exit(0);
		} 
		
		//System.out.println("No stalemate");
	}
	
	/**
	 * Checks to see if one of the players is in checkmate and 
	 * announces the results.
	 * 
	 * @param color Color of the player to be tested that is in check
	 * @param board ChessBoard object
	 * @return True if checkmate, false otherwise
	 */
	public boolean checkmate(String color, ChessBoard board) {
		// can be in checkmate w/o check
		// generate possible moves of player 1's king
		// for each possible move
		// check if move is contained in opponent's all possible moves (all pieces)
		// match --> continue to king's next possible move
		// no match --> return false immediately

		List<String> k_moves;
		boolean match = false;

		if (color.equals("white")) {
			ChessPiece king = white.get(0);
			//System.out.println(king);
			k_moves = king.possibleMoves("white", board);
			k_moves.add(king.getRow() + " " + king.getCol());
			//System.out.println(k_moves);

			for (String coord : k_moves) {
				//System.out.println(coord);
				for (ChessPiece p : black) {
					//System.out.println(p);
					if (p instanceof Pawn) {
						Pawn pawn = (Pawn) p;
						//System.out.println(pawn.possibleAttacks("black", board));
						if (pawn.possibleAttacks("black", board).contains(coord)) {
							//System.out.println("Match found");
							match = true;
							continue;
						}
					} else {
						if (p.possibleMoves("black", board).contains(coord)) {
							//System.out.println("Match found");
							match = true;
							continue;
						}
					}
				}
				if (!match) {
					//System.out.println("No Checkmate");
					return false;
				} else {
					match = false; // reset
				}
			}
		} else {
			ChessPiece king = black.get(0);
			//System.out.println(king);
			k_moves = king.possibleMoves("black", board);
			k_moves.add(king.getRow() + " " + king.getCol());
			//System.out.println(k_moves);

			for (String coord : k_moves) {
				//System.out.println(coord);
				for (ChessPiece p : white) {
					//System.out.println(p);
					if (p instanceof Pawn) {
						Pawn pawn = (Pawn) p;
						//System.out.println(pawn.possibleAttacks("white", board));
						if (pawn.possibleAttacks("white", board).contains(coord)) {
							//System.out.println("Match found");
							match = true;
							continue;
						}
					} else {
						//System.out.println(p.possibleMoves("white", board));
						if (p.possibleMoves("white", board).contains(coord)) {
							//System.out.println("Match found");
							match = true;
							continue;
						}
					}
				}
				if (!match) {
					//System.out.println("No Checkmate");
					return false;
				} else {
					match = false; // reset
				}
			}
		}
		//System.out.println("Checkmate");

		return true;
	}

	/**
	 * Checks to see if one of the players is in check and announces
	 * the results.
	 * 
	 * @param color Color of the player to be tested that is in check
	 * @param board ChessBoard object
	 */
	public void check(String color, ChessBoard board) {
		
		// algorithm
		// get coordinate of player 1's king
		// go through player 2's pieces & generate list of possible moves + only pawn's attack moves
		// if list contains king's coordinates announce check

		if (color.equals("white")) {
			String k_row = white.get(0).getRow();	// KINGS HAVE TO BE FIRST IN ARRAYLIST
			String k_col = white.get(0).getCol();
			String coord = k_row + " " + k_col;
			//coord = "6 e";
			//System.out.println("White King is at " + coord);

			for (ChessPiece p : black) {				
				if (p instanceof Pawn) {
					Pawn pawn = (Pawn) p;
					if (pawn.possibleAttacks("black", board).contains(coord)) {
						System.out.println("Check");
						return;
					}
				} else {
					if (p.possibleMoves("black", board).contains(coord)) {
						System.out.println("Check");
						return;
					}
				}
			}
		} else {
			String k_row = black.get(0).getRow();
			String k_col = black.get(0).getCol();
			String coord = k_row + " " + k_col;
			//coord = "7 e";

			for (ChessPiece p : white) {
				if (p instanceof Pawn) {
					Pawn pawn = (Pawn) p;
					if (pawn.possibleAttacks("white", board).contains(coord)) {
						System.out.println("Check");
						return;
					}
				} else {
					if (p.possibleMoves("white", board).contains(coord)) {
						System.out.println("Check");
						return;
					}
				}
			}
		}
	
	}

	/**
	 * Moves both king and rook, checks if space is empty between king and rook
	 * 
	 * @param color Color of the player that wishes to castle
	 * @param row Row of destination for king to move
	 * @param col Col of destination for king to move
	 * @param b Chessboard object
	 */
	public boolean castling(String color, String row, String col, ChessBoard b) {
		if (color.equals("white")) {
			// king move is c 1, move rook to d 1
			if (row.equals("1") && col.equals("c") && (!b.isEmpty(b, "1", "e"))) {
				if (b.getPiece("1", "e").type.equals("wK")) {
					ChessPiece rook = b.getPiece("1", "a");
					rook.setRow(row);
					rook.setCol("d");
					board[7][3] = rook.type;
					board[7][0] = null;

					ChessPiece king = b.getPiece("1", "e");
					king.setRow(row);
					king.setCol(col);
					board[7][2] = king.type;
					board[7][4] = null;

					return true;
				}
				
			}

			// king move is g 1, move rook to f 1
			if (row.equals("1") && col.equals("g") && (!b.isEmpty(b, "1", "e"))) {
				if (b.getPiece("1", "e").type.equals("wK")) {
					ChessPiece rook = b.getPiece("1", "h");
					rook.setRow(row);
					rook.setCol("f");
					board[7][5] = rook.type;
					board[7][7] = null;

					ChessPiece king = b.getPiece("1", "e");
					king.setRow(row);
					king.setCol(col);
					board[7][6] = king.type;
					board[7][4] = null;

					return true;
				}

			}

		} else {
			// king move is c 8, move rook to d 8
			if (row.equals("8") && col.equals("c") && (!b.isEmpty(b, "8", "e"))) {
				if (b.getPiece("8", "e").type.equals("bK")) {
					ChessPiece rook = b.getPiece("8", "a");
					rook.setRow(row);
					rook.setCol("d");
					board[0][3] = rook.type;
					board[0][0] = null;

					ChessPiece king = b.getPiece("8", "e");
					king.setRow(row);
					king.setCol(col);
					board[0][2] = king.type;
					board[0][4] = null;

					return true;
				}
			}

			// king move is g 8, move rook to f 8
			if (row.equals("8") && col.equals("g") && (!b.isEmpty(b, "8", "e"))) {
				if (b.getPiece("8", "e").type.equals("bK")) {
					ChessPiece rook = b.getPiece("8", "h");
					rook.setRow(row);
					rook.setCol("f");
					board[0][5] = rook.type;
					board[0][7] = null;

					ChessPiece king = b.getPiece("8", "e");
					king.setRow(row);
					king.setCol(col);
					board[0][6] = king.type;
					board[0][4] = null;

					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks and see if a spot on the board contains a piece or not
	 * 
	 * @param board Chessboard object
	 * @param row Row of spot
	 * @param col Col of spot
	 * @return True if spot is null, false otherwise
	 */
	public boolean isEmpty(ChessBoard board, String row, String col) {
		if (board.getPiece(row, col) == null) {
			//System.out.println("empty at " + row + " " + col);
			return true;
		}
		return false;
	}

	/**
	 * Promotes a pawn to queen, knight, rook, or bishop once it
	 * reaches the 8th (for white) or 1st (for black) row. Assumes 
	 * that piece is a pawn and satisfies criteria for promotion.
	 * Gets the choice from the user and updates the board.
	 * 
	 * @param piece The piece to be promoted
	 */
	public void promotion(ChessPiece piece, String extra) {
		String col = piece.getCol();
		String row = piece.getRow();
		int r = Math.abs(Integer.parseInt(row) - 8);
		int c = Math.abs(fr.letterToNumber(col.charAt(0)) - 1);
		
		if (piece.getColor().equals("white") && piece.getRow().equals("8")) {
			white.remove(piece);

			switch(extra) {
			case "Q":
				piece = new Queen("white", "wQ", row, col);
				white.add(piece);
				break;
			case "N":
				piece = new Knight("white", "wN", row, col);
				white.add(piece);
				break;
			case "B":
				piece = new Bishop("white", "wB", row, col);
				white.add(piece);
				break;
			case "R":
				piece = new Rook("white", "wR", row, col);
				white.add(piece);
				break;
			case "":
				piece = new Queen("white", "wQ", row, col);
				white.add(piece);
				break;
			default:
				piece = new Queen("white", "wQ", row, col);
				white.add(piece);
				break;
			}
			board[r][c] = piece.type;

		} else if (piece.getColor().equals("black") && piece.getRow().equals("1")) {
			black.remove(piece);

			switch(extra) {
			case "Q":
				piece = new Queen("black", "bQ", row, col);
				black.add(piece);
				break;
			case "N":
				piece = new Knight("black", "bN", row, col);
				black.add(piece);
				break;
			case "B":
				piece = new Bishop("black", "bB", row, col);
				black.add(piece);
				break;
			case "R":
				piece = new Rook("black", "bR", row, col);
				black.add(piece);
				break;
			case "":
				piece = new Queen("black", "bQ", row, col);
				black.add(piece);
				break;
			default:
				piece = new Queen("black", "wQ", row, col);
				black.add(piece);
				break;
			}
			board[r][c] = piece.type;
			
		}
		
		//board[Math.abs(Integer.parseInt(piece.getRow()) - 8)][Math.abs(fr.letterToNumber(piece.getCol().charAt(0)) - 1)] = piece.getType();
	}

	/**
	 * Moves a piece to a new position and updates the board.
	 * 
	 * @param piece ChessPiece to be moved
	 * @param row Row coordinate of the destination
	 * @param col Column coordinate of the destination
	 */
	public void move(ChessPiece piece, String row, String col) {
		//System.out.println("moving from " + piece.getRow() + " " + piece.getCol() + " to " + row + " " + col);

		int oldR = Math.abs(Integer.parseInt(piece.getRow()) - 8);
		int oldC = Math.abs(fr.letterToNumber(piece.getCol().charAt(0)) - 1);

		int newR = Math.abs(Integer.parseInt(row) - 8);
		int newC = Math.abs(fr.letterToNumber(col.charAt(0)) - 1);

		// move piece from old pos to new pos 
		// make sure legal
		// follows from isValidMove in each piece type
		// update board 
		
		ChessPiece p = getPiece(row, col);
		if (p != null) {
			if (piece.color.equals("white")) {
				black.remove(p);
			} else {
				white.remove(p);
			}
		}
		
		runningGame = 1;
		piece.setRow(row);
		piece.setCol(col);
		board[newR][newC] = piece.type;
		board[oldR][oldC] = null;
		//System.out.println(piece.getRow() + " " + piece.getCol());
		
		if (piece instanceof Pawn) {
			if (Math.abs(newR - oldR) == 2) {
				((Pawn) piece).setForward(true);
				return;
			}
			if (((Pawn) piece).getForward()) {
				((Pawn) piece).setForward(false);
			}
		}
	}
	
	/**
	 * Executes the capture of the pawn after an en passant is
	 * identified and updates the chess board.
	 * 
	 * @param piece The piece that has just been moved
	 */
	public void removeEnpassant(ChessPiece piece) {
		if (piece.getColor().equals("white")) {
			int row = Integer.parseInt(piece.getRow()) - 1;
			ChessPiece p = this.getPiece(Integer.toString(row), piece.getCol());
			System.out.println("Enpassant: Captured " + p.getType());
			black.remove(p);
			runningGame = 1;
			board[Math.abs(row - 8)][Math.abs(fr.letterToNumber(piece.getCol().charAt(0)) - 1)] = null;
			//System.out.println(Math.abs(row - 8) + " " + Math.abs(fr.letterToNumber(piece.getCol().charAt(0)) - 1));
		} else {
			int row = Integer.parseInt(piece.getRow()) + 1;
			ChessPiece p = this.getPiece(Integer.toString(row), piece.getCol());
			System.out.println("Enpassant: Captured " + p.getType());
			white.remove(p);
			runningGame = 1;
			board[Math.abs(row - 8)][Math.abs(fr.letterToNumber(piece.getCol().charAt(0)) - 1)] = null;
			//System.out.println(Math.abs(row - 8) + " " + Math.abs(fr.letterToNumber(piece.getCol().charAt(0)) - 1));
		}
	}
	
	/**
	 * Retrieves a list of pieces a player currently has.
	 * 
	 * @param color
	 * @return currentPieces
	 */
	public List<String> getCurrentPieces(String color) {
		List<String> currentPieces = new ArrayList<String>();
		
		if (color.equals("white")) {
			for (ChessPiece p : white) {
				currentPieces.add(p.type);
			}
		} else {
			for (ChessPiece p : black) {
				currentPieces.add(p.type);
			}
		}
		return currentPieces;
	}

}
