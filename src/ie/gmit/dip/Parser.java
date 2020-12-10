package ie.gmit.dip;

import java.io.*;

/**
 * A java class to do the parsing of a given file and encrypt or decrypt it
 * based on user choice
 * 
 * @author Sunoj Jose
 *
 */
public class Parser {
	private String keyWord;
	private Cypher c;
	private File f;

	/**
	 * method parse does encryption and decryption based on the argument of the
	 * parameter notEncoded
	 * 
	 * @param file
	 * @param cp
	 * @param notEncoded
	 */
	public void parse(String file, Cypher cp, boolean notEncoded) {
		keyWord = Menu.getKey();// accessing keyword from class Menu
		try {
			c = new Cypher(keyWord);
			this.c = cp;
			this.f = new File(file);
			FileWriter fw1 = new FileWriter("encoded.txt");
			FileWriter fw2 = new FileWriter("decoded.txt");

			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String line = null;
			// "br" reads the file line by line and each value is stored in "line"
			while ((line = br.readLine()) != null) {
				if (line.trim().equals("")) {
					// if empty line
					continue;
				} else {
					// if not an empty line
					String message;
					if (notEncoded) { // code lines for encoding the file
						message = c.messageToTransform(line, true);// invoking method to encode
						fw1.write(message + "\n");
					} else { // code lines for decoding file
						message = c.messageToTransform(line, false);// invoking method to decode
						fw2.write(message + "\n");
					}
				}
			}
			br.close();
			fw1.flush();
			fw1.close();
			fw2.flush();
			fw2.close();
		} catch (Exception e) {
			System.out.println("Message: " + e.getMessage());
			e.printStackTrace();
		}
	}
}