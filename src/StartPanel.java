import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StartPanel extends JPanel {
	
	String[] buttonNames;
	ActionListener listener;
	
	public StartPanel(ActionListener listener, String... buttonNames) {
		this.buttonNames = buttonNames;
		this.setLayout(new GridBagLayout());
		this.listener = listener;
		createButtons();
	}
	
	private void createButtons() {
		Font font = new Font ("Ariel", Font.PLAIN, 15);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
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
