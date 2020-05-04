package com.dbpp.my12306.cclient;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog {
	public AboutDialog(JFrame owner) {
		super(owner, "About", true);

		this.setResizable(false);

		// add label to center

		JLabel label = new JLabel("<html><h2>my12306 Terminal<br>V0.0.1</h2><hr>" +
				"<h4>my12306 mock system<br>" +
				"Made by Carl rabbit & Lu Jichen.</h4></html>", JLabel.CENTER);
		label.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
		add(label, BorderLayout.CENTER);

		// OK button closes the dialog

		JButton ok = new JButton("  OK  ");
		ok.addActionListener(event -> setVisible(false));

		// add OK button to southern border

		JPanel panel = new JPanel();
		panel.add(ok);
		add(panel, BorderLayout.SOUTH);

		pack();
	}
}
