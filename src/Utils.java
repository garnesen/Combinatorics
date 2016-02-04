import javax.swing.UIManager;

/**
 * A utility class to hold convenience methods.
 *    - Log methods to print out message with a tag
 *    - Method to change the look and feel of the application
 * @author Gunnar Arnesen
 *
 */
public class Utils {

	private static final String DEFAULT_TAG = "[P1]";

	private Utils() {}

	/**
	 * Prints the sent message with the default tag.
	 * @param message
	 */
	public static void log(String message) {
		StringBuilder sb = new StringBuilder();
		sb.append(DEFAULT_TAG).append(" ").append(message);
		System.out.println(sb);
	}

	/**
	 * Prints the sent message with the provided tag.
	 * @param tag
	 * @param message
	 */
	public static void log(String tag, String message) {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(tag).append("] ").append(message);
		System.out.println(sb);
	}

	/**
	 * Try to change the look and feel of the application to that of the users system.
	 */
	public static void setLookAndFeel () {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			log("Error", "Look and feel not changed.");
		}
	}

}
