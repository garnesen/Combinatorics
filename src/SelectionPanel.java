import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The SelectionPanel class is one Panel in the main application. This includes the 'browse' button
 * and input field. This class also communicates to the DataPanel to let it know when the user has
 * input a file or string.
 * @author Gunnar Arnesen
 *
 */
@SuppressWarnings("serial")
public class SelectionPanel extends JPanel {

	private DataPanel listener;

	/**
	 * Creates a new SelctionPanel.
	 * @param listener
	 */
	public SelectionPanel(DataPanel listener) {
		this.listener = listener;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		createSelection();
	}

	/**
	 * This method sets up the UI for the panel by building a button and input field.
	 */
	private void createSelection() {
		// Button that will open the file browser
		final JButton chooseFile = new JButton("Browse");
		chooseFile.setPreferredSize(new Dimension(100, 30));
		// Text field for user input
		final JTextField input = new JTextField("(Enter a file path or valid graph input)");
		input.setPreferredSize(new Dimension(375, chooseFile.getPreferredSize().height));
		// File chooser to get the selected file
		final JFileChooser fc = new JFileChooser();
		chooseFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int retVal = fc.showOpenDialog(SelectionPanel.this);
				if (retVal == JFileChooser.APPROVE_OPTION) {
					input.setText(fc.getSelectedFile().getPath());
				}
			}
		});
		input.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Utils.log("Input: " + input.getText());
					File inputFile = new File(input.getText());
					if (inputFile.exists()) {
						if (!listener.input(inputFile)) {
							Utils.log("Halting graph creation.");
							return;
						}
					} else {
						if (!listener.input(input.getText())) {
							Utils.log("Halting graph creation.");
							return;
						}
					}
					input.setText("");
					listener.create();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
		});
		this.add(chooseFile);
		this.add(input);
	}

}
