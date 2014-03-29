package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class NodeUI extends JPanel {

	/**
	 * Create the panel.
	 */
	LabelImageScalable icon;
	JLabel name;

	public NodeUI() {
		setLayout(null);

		name = new JLabel("New label");
		name.setBounds(10, 36, 104, 14);
		add(name);

		icon = new LabelImageScalable();

		icon.setBounds(38, 17, 46, 14);
		icon.setImage("node.png");
		add(icon);

	}
}
