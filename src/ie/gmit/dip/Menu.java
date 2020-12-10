package ie.gmit.dip;

import java.util.*;

/**
 * The Menu class acts as a user interface of the application
 * 
 * @author Sunoj Jose
 *
 */
public class Menu {
	private Scanner sc;
	private static String keyWord;
	private String cipher, file;
	private boolean keepRunning = true;
	private boolean keyNotSet = true;

	/**
	 * when invoked from the main() this method invokes menuOptions() which presents
	 * different choices to the user the program execution continues based on the
	 * user choices.
	 */
	public void show() {
		sc = new Scanner(System.in);
		boolean encryption;
		while (keepRunning) {
			menuOptions(); // listing the menu when invoked
			int selected = Integer.parseInt(sc.nextLine());
			if (selected == 1) { // Setting Keyword
				addKey();
			} else if (selected == 2) {// Message to Encrypt
				encryption = true;
				addMessage(encryption);
			} else if (selected == 3) { // File to Encrypt
				encryption = true;
				addFile(encryption);
			} else if (selected == 4) { // Decrypt a Message
				encryption = false;
				addMessage(encryption);
			} else if (selected == 5) { // Decrypt a File
				encryption = false;
				addFile(encryption);
			} else if (selected == 6) { // Exit from the menu
				keepRunning = false;
			} else { // Invalid input
				System.out.println("Invalid choice!");
			}
		}
	}

	/**
	 * a list of different choices to the user
	 */
	private void menuOptions() {

		System.out.println("##################################");
		System.out.println("######## The ADFGVX cypher #######");
		System.out.println("##################################");
		System.out.println("(1) Enter Keyword.");
		System.out.println("(2) Enter Message to Encrypt.");
		System.out.println("(3) Enter a File to Encrypt.");
		System.out.println("(4) Enter a Message to Decrypt");
		System.out.println("(5) Enter a File to Decrypt");
		System.out.println("(6) Quit.");
		System.out.println("Select an option [1-6]: ");
	}

	/**
	 * a method to add key to encrypt and decrypt
	 */
	private void addKey() {
		System.out.println("Enter a Keyword: ");
		String text = sc.nextLine();
		setKey(text);
		keyNotSet = false;
	}

	public static void setKey(String text) {
		keyWord = text;
	}

	public static String getKey() {
		return keyWord;
	}

	/**
	 * a method used to pass the message for encryption or decryption according to
	 * the value of parameter encoding and prints the result.
	 * 
	 * @param encoding
	 */
	private void addMessage(boolean encoding) {

		if (!keyNotSet) {
			try {
				Cypher cp = new Cypher(keyWord);
				if (encoding) {
					System.out.println("Enter a Message to Encrypt: ");
					String message = sc.nextLine();
					cipher = cp.messageToTransform(message, true);// invoking the method to encrypt
					System.out.println("Encrypted Message is: " + cipher);

				} else {
					System.out.println("Enter Encrypted Message to decrypt: ");
					String message = sc.nextLine();
					cipher = cp.messageToTransform(message, false);// invoking the method to decrypt
					System.out.println("Decrypted Message is: " + cipher);
				}

			} catch (Exception e) {
				System.out.println("Message: " + e.getMessage());
				e.printStackTrace();
			}

		} else {
			System.out.println("Please add key first...");
			addKey();
		}
	}

	/**
	 * a similar method as above but for dealing with a file
	 * 
	 * @param encoding
	 */
	private void addFile(boolean encoding) {

		if (!keyNotSet) {
			try {
				Cypher cp = new Cypher(keyWord);
				Parser ps = new Parser();
				if (encoding) {
					System.out.println("Enter a file name/path to Encrypt: ");
					String message = sc.nextLine();
					file = message;
					ps.parse(file, cp, true); // invoking method to encrypt file
				} else {
					System.out.println("Enter Encrypted file name/path to Decrypt: ");
					String message = sc.nextLine();
					file = message;
					ps.parse(file, cp, false); // invoking method to decrypt file
				}
			} catch (Exception e) {
				System.out.println("Message: " + e.getMessage());
				e.printStackTrace();
			}

		} else {
			System.out.println("Please add key first...");
			addKey();
		}
	}
}
