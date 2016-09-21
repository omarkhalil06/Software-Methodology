package board;

/**
 * FileRank implements methods used to convert the letter
 * in a coordinate to a number, which is then useful in 
 * implementing moves, retrieval of chess pieces, etc. 
 *  
 * @author Omar Khalil
 * @author Michelle Hwang
 */
public class FileRank {
	
	/**
	 * Converts a letter to a number.
	 * 
	 * @param ch The letter to be converted
	 * @return The corresponding integer value
	 */
	public int letterToNumber(char ch) {
		int n = 0;
		switch (ch) {
		case 'a':
			n = 1;
			break;
		case 'b':
			n = 2;
			break;
		case 'c':
			n = 3;
			break;
		case 'd':
			n = 4;
			break;
		case 'e':
			n = 5;
			break;
		case 'f':
			n = 6;
			break;
		case 'g':
			n = 7;
			break;
		case 'h':
			n = 8;
			break;
		default:
			n = 9;
			break;
		}
		
		return n;
	}
	
	/**
	 * Converts a number to a letter.
	 * 
	 * @param n The integer to be converted
	 * @return Returns the corresponding letter in a string
	 */
	public String numberToLetter(int n) {
		String s;
		switch (n) {
		case 1:
			s = "a";
			break;
		case 2:
			s = "b";
			break;
		case 3:
			s = "c";
			break;
		case 4:
			s = "d";
			break;
		case 5:
			s = "e";
			break;
		case 6:
			s = "f";
			break;
		case 7:
			s = "g";
			break;
		case 8:
			s = "h";
			break;
		default:
			s = "z";
			break;
		}
		
		return s;
	}
	
	

}
