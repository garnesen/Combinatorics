/**
 * An interface for creating an alert that data has been entered.
 * This interface is used to alert a panel that the user has entered data on
 * the SelectionPanel.
 * @author Gunnar Arnesen
 *
 */
public interface InputNotifier {
	
	/**
	 * Ran when input is entered by the user.
	 */
	public void onEnter(String data);

}
