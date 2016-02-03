import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SelectionPanel extends JPanel {
	
	public SelectionPanel() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		createSelection();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		repaint();
	}
	
	private void createSelection() {
		final JButton chooseFile = new JButton("Browse");
		chooseFile.setPreferredSize(new Dimension(100, 30));
		final JTextField input = new JTextField("(Enter a file path or valid graph input)");
		input.setPreferredSize(new Dimension(350, chooseFile.getPreferredSize().height));
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
