package graphics;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utils.Utils;


/**
 * The SelectionPanel class is one Panel in the main application. This includes the 'browse' button
 * and input field. This class also communicates to the DataPanel to let it know when the user has
 * input a file or string.
 * @author Gunnar Arnesen
 *
 */
@SuppressWarnings("serial")
public class SelectionPanel extends JPanel {

	private InputNotifier notifier;

	/**
	 * Creates a new SelctionPanel.
	 * @param listener
	 */
	public SelectionPanel(InputNotifier notifier) {
		this.notifier = notifier;
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
		input.setPreferredSize(new Dimension(425, chooseFile.getPreferredSize().height));
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
					Utils.log("Input", input.getText());
					notifier.onEnter(input.getText());
					input.setText("");
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
