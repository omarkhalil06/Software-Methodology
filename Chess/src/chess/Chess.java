package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import board.ChessBoard;

import pieces.ChessPiece;
import pieces.King;
import pieces.Pawn;

public class Chess {

	/**
	 * Main method used to run chess program
	 * 
	 * @author Omar Khalil
	 * @author Michelle Hwang
	 */
	public static void main(String[] args) {

		ChessBoard chess = new ChessBoard();
		chess.labelBoard();
		chess.displayBoard();

		boolean inputOK = false;
		boolean draw = false;
		Scanner sc = new Scanner(System.in);
		String from, to, input;
		String extra = "";
		String oldRow, oldCol, newRow, newCol;

		List<String> whitePieces = new ArrayList<String>();
		List<String> blackPieces = new ArrayList<String>();

		while(true) {

			// gets input from white
			do {
				System.out.print("White's move: ");
				input = sc.nextLine();
				
				// white resigns
				if (input.compareTo("resign") == 0) {
					System.out.println("White resigns. Black wins.");
					sc.close();
					System.exit(0);
				} 
				
				// white accepts draw
				if ((input.compareTo("draw") == 0) && draw) {
					System.out.println("Draw");
					sc.close();
					System.exit(0);
				} 
					
				draw = false;
				
				// input error
				while (input.length() < 5) {
					System.out.println("Invalid input, try again");
					System.out.print("White's move: ");
					input = sc.nextLine();
				}
				
				from = input.substring(0,2).toLowerCase();
				to = input.substring(3,5).toLowerCase();
				if (input.length() > 5) {
					extra = input.substring(6);
				}
				
				// System.out.println(from + " " + to + " " + extra);
				
				// checks that input matches "e3" "a5" format
				if (chess.checkInput(from, to)) {
					oldRow = from.substring(1);
					oldCol = from.substring(0,1).toLowerCase();
					newRow = to.substring(1);
					newCol = to.substring(0,1).toLowerCase();
					
					// white accepts draw
					if (extra.equals("draw?")) {
						draw = true;
					}
					
					// CHECK IF IN BOUNDS
					if (chess.isValidMove(oldRow, oldCol)
							&& chess.isValidMove(newRow, newCol)) {

						// CHECK IF VALID "FROM" COORDINATE
						if (chess.getPiece(oldRow, oldCol) != null) {
							ChessPiece origin = chess.getPiece(oldRow, oldCol);

							// CHECK IF WHITE PIECE
							whitePieces = chess.getCurrentPieces("white");
							if (whitePieces.contains(chess.getPiece(oldRow, oldCol).type)) {

								// CHECK IF VALID "TO" COORDINATE
								if (origin.validPieceMove(newRow, newCol, chess)) {
									ChessPiece destination = chess.getPiece(newRow, newCol);

									// spot not occupied --> valid, claim spot
									if (destination == null) {

										if (origin instanceof Pawn) {
											Pawn p = (Pawn) origin;
											if (p.getEnpassant("white", chess).contains(newRow + " " + newCol)) {
												chess.move(origin, newRow, newCol);
												chess.removeEnpassant(p);
											} else if (newRow.equals("8")) {
												chess.move(origin, newRow, newCol);
												chess.promotion(origin, extra);
											} else {
												chess.move(origin, newRow, newCol);
											}
										} else if (origin instanceof King) {
											if (!chess.castling("white", newRow, newCol, chess)) {
												chess.move(origin, newRow, newCol);
											}
										} else {
											chess.move(origin, newRow, newCol);
										}

										inputOK = true;
										// spot occupied by opponent --> valid, capture, reclaim spot
									} else if (destination.getColor().compareTo("black") == 0) {
										System.out.println("Captured " + destination.getType());
										if (origin instanceof Pawn && newRow.equals("8")) {
											chess.move(origin, newRow, newCol);
											chess.promotion(origin, extra);
										} else {
											chess.move(origin, newRow, newCol);
										}
										inputOK = true;
										// spot occupied by own piece --> invalid
									} else {
										System.out.println("Illegal move, try again (Destination already occupied)");
										draw = false;
									}
								} else {
									System.out.println("Illegal move, try again");
									draw = false;
								}
							} else {
								System.out.println("Illegal move, try again (Must pick white piece)");
								draw = false;
							}
						} else {
							System.out.println("Illegal move, try again (Cannot retrieve piece)");
							draw = false;
						}

					} else {
						System.out.println("Illegal move, try again (One or more coordinates is not in bounds)");
						draw = false;
					}
				} else {
					System.out.println("Invalid input, try again");
					draw = false;
				}
				extra = ""; // reset
			} while (!inputOK);

			inputOK = false; // reset
			
			// display & update board
			System.out.println();
			chess.displayBoard();

			// checks if white has captured king
			chess.captureKing("black", chess);
			
			// check if stalemate
			chess.stalemate("black", chess);

			// checks if white has checked black --> announce to black
			if (chess.checkmate("black", chess)) {
				System.out.println("Checkmate");
				sc.close();
				System.exit(0);
			} else {
				chess.check("black", chess);
			}

			// get input from black
			do {
				System.out.print("Black's move: ");
				input = sc.nextLine();

				// white resigns
				if (input.compareTo("resign") == 0) {
					System.out.println("Black resigns. White wins.");
					sc.close();
					System.exit(0);
				}
				
				// black accepts draw
				if ((input.compareTo("draw") == 0) && draw) {
					System.out.println("Draw");
					sc.close();
					System.exit(0);
				} 
					
				draw = false;
				
				// input error
				while (input.length() < 5) {
					System.out.println("Invalid input, try again");
					System.out.print("Blacks's move: ");
					input = sc.nextLine();
				}

				from = input.substring(0,2).toLowerCase();
				to = input.substring(3,5).toLowerCase();
				if (input.length() > 5) {
					extra = input.substring(6);
				}
				// System.out.println(from + " " + to + " " + extra);

				// checks that input matches "e3" "a5" format
				if (chess.checkInput(from, to)) {
					oldRow = from.substring(1);
					oldCol = from.substring(0,1).toLowerCase();
					newRow = to.substring(1);
					newCol = to.substring(0,1).toLowerCase();

					// black requests draw
					if (extra.equals("draw?")) {
						draw = true;
					}
					
					// CHECK IF IN BOUNDS
					if (chess.isValidMove(oldRow, oldCol)
							&& chess.isValidMove(newRow, newCol)) {

						// CHECK IF VALID "FROM" COORDINATE
						if (chess.getPiece(oldRow, oldCol) != null) {
							ChessPiece origin = chess.getPiece(oldRow, oldCol);

							// CHECK IF BLACK PIECE
							blackPieces = chess.getCurrentPieces("black");
							if (blackPieces.contains(chess.getPiece(oldRow, oldCol).type)) {

								// CHECK IF VALID "TO" COORDINATE
								if (origin.validPieceMove(newRow, newCol, chess)) { 
									ChessPiece destination = chess.getPiece(newRow, newCol);

									// spot not occupied --> valid, claim spot
									if (destination == null) {

										if (origin instanceof Pawn) {
											Pawn p = (Pawn) origin;
											if (p.getEnpassant("black", chess).contains(newRow + " " + newCol)) {
												chess.move(origin, newRow, newCol);
												chess.removeEnpassant(p);
											} else if (newRow.equals("1")) {
												chess.move(origin, newRow, newCol);
												chess.promotion(origin, extra);
											} else {
												chess.move(origin, newRow, newCol);
											}
										} else if (origin instanceof King) { 
											if (!chess.castling("black", newRow, newCol, chess)) {
												chess.move(origin, newRow, newCol);
											}
										} else {
											chess.move(origin, newRow, newCol);
										}

										inputOK = true;
										// spot occupied by opponent --> valid, capture, reclaim spot
									} else if (destination.getColor().compareTo("white") == 0) {
										System.out.println("Captured " + destination.getType());
										if (origin instanceof Pawn && newRow.equals("1")) {
											chess.move(origin, newRow, newCol);
											chess.promotion(origin, extra);
										} else {
											chess.move(origin, newRow, newCol);
										}
										inputOK = true;
										// spot occupied by own piece --> invalid
									} else {
										System.out.println("Illegal move, try again (Destination already occupied)");
										draw = false;
									}
								} else {
									System.out.println("Illegal move, try again");
									draw = false;
								}
							} else {
								System.out.println("Illegal move, try again (Must pick black piece)");
								draw = false;
							}
						} else {
							System.out.println("Illegal move, try again (Cannot retrieve piece)");
							draw = false;
						}
					} else {
						System.out.println("Illegal move, try again (One or more coordinates is not in bounds)");
						draw = false;
					}
				} else {
					System.out.println("Invalid input, try again");
					draw = false;
				}
				extra = ""; // reset
			} while (!inputOK);

			inputOK = false; // reset

			System.out.println();
			chess.displayBoard();

			// checks if black has captured king
			chess.captureKing("white", chess);

			// check if stalemate
			chess.stalemate("white", chess);
			
			// checks if white has checked black --> announce to black
			if (chess.checkmate("white", chess)) {
				System.out.println("Checkmate");
				sc.close();
				System.exit(0);
			} else {
				chess.check("white", chess);
			}

		}

	}

}
