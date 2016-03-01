package graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JPanel;

import utils.Utils;

/**
 * A panel that is used on the start of the application. It builds the buttons
 * that link to each sub-project in the project.
 * @author Gunnar Arnesen
 *
 */
@SuppressWarnings("serial")
public class StartPanel extends JPanel {
	
	private String[] buttonNames;
	private ActionListener listener;
	
	/**
	 * Create a new StartPanel
	 * @param listener the listener for the buttons
	 * @param buttonNames the names of each button
	 */
	public StartPanel(ActionListener listener, String... buttonNames) {
		this.buttonNames = buttonNames;
		this.setLayout(new GridBagLayout());
		this.listener = listener;
		createButtons();
	}
	
	/**
	 * Creates a button for each name sent in the constructor with the sent listener
	 */
	private void createButtons() {
		// Log the button created
		StringBuilder sb = new StringBuilder();
		sb.append("Buttons: ");
		Arrays.stream(buttonNames).forEach(name -> sb.append("[" + name + "]"));
		Utils.log("Start", sb.toString());
		
		// Set the font and insets
		Font font = new Font ("Ariel", Font.PLAIN, 15);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		
		// Loop through each button name and build the button
		for (int i = 0; i < buttonNames.length; i++) {
			final JButton button = new JButton(buttonNames[i]);
			button.setPreferredSize(new Dimension(200, 50));
			button.setFont(font);
			button.addActionListener(listener);
			gbc.gridx = 0;
			gbc.gridy = i;
			this.add(button, gbc);
		}
	}

}
