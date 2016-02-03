/**
 * A utility class for printing messages. Running the jar in the command line
 * allows the user to see the print statements. This could be used for printing
 * errors, processes, statistics, etc.
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
}
