package ie.gmit.dip;

/**
 * The main class of the application
 * 
 * @author Sunoj Jose
 *
 */
public class Runner {
	/**
	 * main invokes show() method of menu instance which lists the choices to user
	 * and the program execution continues based on user choices.
	 * It terminates if the user enter 6.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Menu menu = new Menu();
		try {
			menu.show();
		} catch (Exception e) {
			System.out.println("Message: " + e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("Cleaning up...");
		}
	}
}
