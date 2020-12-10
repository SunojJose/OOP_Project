package ie.gmit.dip;

import java.util.*;

/**
 * A class contains methods to do the encoding and decoding
 * 
 * @author Sunoj Jose
 *
 */
public class Cypher {
	private String text;
	private char[][] plain;
	private char[][] cipher;
	private int col;
	private int row;
	private String key;
	private static final char[] adfgvx = { 'A', 'D', 'F', 'G', 'V', 'X' };
	private static final char special = '*';
	private static final char[][] matrix = { { 'P', 'H', '0', 'Q', 'G', '6' },

			{ '4', 'M', 'E', 'A', '1', 'Y' },

			{ 'L', '2', 'N', 'O', 'F', 'D' },

			{ 'X', 'K', 'R', '3', 'C', 'V' },

			{ 'S', '5', 'Z', 'W', '7', 'B' },

			{ 'J', '9', 'U', 'T', 'I', '8' }

	};

	/**
	 * The constructor does the settings of keyword
	 * 
	 * @param text
	 * @throws Exception
	 */
	public Cypher(String text) throws Exception {
		setKey(text);// invoking method to set the keyword
	}

	/**
	 * Common method for encryption and decryption
	 * 
	 * @param text
	 * @param notEncoded
	 * @return
	 * @throws Exception
	 */
	public String messageToTransform(String text, boolean notEncoded) throws Exception {
		if (!isValidMessage(text, notEncoded))
			throw new Exception("Not a valid message!Please try again...");

		return notEncoded ? encode(text) : decode(text);
	}

	/**
	 * the method encode does the columnar transposition of the outcome of
	 * polyEncode()
	 * 
	 * @param plainText
	 * @return
	 */
	private String encode(String plainText) {

		String s = polyEncode(plainText);// method to do polybius encoding using adfgvx
		plain = getPlainArray(s);// creating the plain array
		StringBuilder sb = new StringBuilder();
		char[] keySorted = key.toCharArray();
		Arrays.sort(keySorted);
		// doing columnar transposition
		for (int a = 0; a < col; a++) {
			for (int b = 0; b < col; b++) {
				// comparing the first row of plain with sorted keyword
				if (keySorted[a] == plain[0][b]) {

					for (int c = 1; c < row; c++) {
						sb.append(plain[c][b]); // appending the matching element
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * doing polybius encoding uses the original plain text and encode them using
	 * chars of adfgvx
	 */
	private String polyEncode(String plain) {

		StringBuilder sb = new StringBuilder();
		char[] text = plain.toUpperCase().toCharArray();
		for (int k = 0; k < text.length; k++) {
			char c = text[k];
			// finding out the char at (i,j) of matrix corresponds to 'c'
			// then find char at i, and char at j of adfgvx and appends to sb
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					if (matrix[i][j] == c) {
						sb.append(adfgvx[i]).append(adfgvx[j]);
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * final decoding, uses the transpositioned cipher text this method uses
	 * polybius decoding technique
	 * 
	 * @param cipher
	 * @return
	 */

	private String decode(String cipher) {

		String transpos = colDecode(cipher);// doing transposition
		char[] text = transpos.toCharArray();
		StringBuilder sb = new StringBuilder(text.length);
		String s = new String(adfgvx);
		for (int i = 0; i < text.length; i += 2) {
			// taking from s the index of the char at i
			int row = s.indexOf(text[i]);
			// taking from s the index of the char at j
			int col = s.indexOf(text[i + 1]);
			sb.append(matrix[row][col]); // appending the corresponding element of matrix

		}
		return sb.toString();
	}

	/**
	 * columnar transposition before decoding this method uses the original
	 * ciphertext
	 * 
	 * @param cipherText
	 * @return
	 */

	private String colDecode(String cipherText) {

		this.text = cipherText;
		// creating the cipher array
		cipher = getDecodedArray(text);
		StringBuilder sb = new StringBuilder();
		char[][] temp = new char[row][col];// array for final decryption
		// first row of temp is chars of key
		for (int m = 0; m < 1; m++) {
			for (int n = 0; n < col; n++) {
				temp[m][n] = key.charAt(n);
			}
		}
		// doing columnar transposition
		// forming the remaining rows by simply copying chars of cipher from
		// first row based on the condition
		for (int x = 0; x < col; x++) {
			for (int y = 0; y < col; y++) {
				// checking first row elements for identical chars
				// copying cipher to temp if true
				if (temp[0][x] == cipher[0][y]) {
					for (int z = 1; z < row; z++) {
						temp[z][x] = cipher[z][y];
					}
				}
			}
		}
		// formation of string by iterating through temp row and column
		for (int i = 1; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (temp[i][j] == special) {// finding out and eliminating the special char
					sb.append("");
				} else {
					sb.append(temp[i][j]);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * method to form plain array matrix
	 * 
	 * @param text
	 * @return
	 */
	private char[][] getPlainArray(String text) {
		col = key.length();
		int len = (int) Math.ceil((double) text.length() / key.length());
		row = len + 1; // +1 is an extra row for key chars
		char[][] array = new char[row][col];
		// first row is set to chars of key
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < col; j++) {
				array[i][j] = key.charAt(j);
			}
		}
		// amending the rest of the rows
		int number = 0; // number is used as counter variable for 'chars of text'
		for (int x = 1; x < row; x++) {
			for (int y = 0; y < col; y++) {
				if (text.length() == number) { // checking whether it reached end of text
					array[x][y] = special;// special char for null char at the end cells of the grid
					number--;
				} else {
					// fill the cells with chars of text at position "number"
					array[x][y] = text.charAt(number);
				}
				number++;
			}
		}
		return array;
	}

	/**
	 * method to form cipher matrix
	 * 
	 * @param text
	 * @return
	 */
	private char[][] getDecodedArray(String text) {

		col = key.length();
		int len = (int) Math.ceil((double) text.length() / key.length());// determining the row length
		row = len + 1; // +1 is for an extra row for storing the key chars
		char[] keySorted = key.toCharArray();
		Arrays.sort(keySorted);// sorting key chars alphabetically

		char[][] array = new char[row][col];

		// fixing the first row of array with sorted chars of key
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < col; j++) {
				array[i][j] = keySorted[j];
			}
		}
		int number = 0;// counter for chars of given string of cipher text
		// amending the cipher chars to array
		for (int a = 0; a < col; a++) {
			for (int b = 1; b < row; b++) {
				array[b][a] = text.charAt(number);
				number++;
			}
		}
		return array;
	}

	/**
	 * setter method for key
	 * 
	 * @param text
	 * @throws Exception
	 */
	public void setKey(String text) throws Exception {

		char[] org = text.trim().toUpperCase().toCharArray();
		// ensuring key is not empty
		if (org.length == 0)
			throw new Exception("Key must not be empty");
		// key chars must be unique to perform the process
		// the following code block ensure the uniqueness of key chars
		for (int i = 0; i < org.length - 1; i++) {
			for (int j = i + 1; j < org.length; j++) {
				if (org[i] == org[j])
					throw new Exception("Key is not valid; must have unique chars");
			}
		}
		// if the key is valid
		this.key = text.trim().toUpperCase();
	}

	/**
	 * Method to test the validity of message
	 * 
	 * @param plain
	 * @param notEncoded
	 * @return
	 */
	private boolean isValidMessage(String plain, boolean notEncoded) {

		if (plain == null) {
			return false;
		}
		int count = 0;
		if (notEncoded) {
			for (int i = 0; i < plain.length(); i++) {

				if ((plain.charAt(i) >= 'a') || (plain.charAt(i) <= 'z')) {
					count++;
				} else if ((plain.charAt(i) >= 'A') || (plain.charAt(i) <= 'Z')) {
					count++;
				} else if ((plain.charAt(i) >= '0') || (plain.charAt(i) <= '9')) {
					count++;
				} else {
					count = 0;
				}

			}

		} else {
			char[] text = plain.trim().toUpperCase().toCharArray();
			for (char a : text) {
				for (char b : adfgvx) {
					if (a == b) {
						count++;
					}
				}
			}
		}

		return count != 0;
	}

}
