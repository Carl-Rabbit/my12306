package com.dbpp.my12306.cclient;

import javax.swing.*;
import java.awt.*;

public class HelpDialog extends JDialog {
	public HelpDialog(JFrame owner, String msg) {
		super(owner, "Help", true);

		setPreferredSize(new Dimension(500, 400));

		JLabel label = new JLabel("Help Information", JLabel.CENTER);
		label.setFont(new Font("", Font.PLAIN, 20));
		label.setPreferredSize(new Dimension(0, 40));
		add(label, BorderLayout.NORTH);

		// add msg to center

		add(new JScrollPane(new JTextArea(msg){{
			this.setEditable(false);
			this.setFont(new Font("Lucida Console", Font.PLAIN, 16));

			this.setForeground(new Color(10,10,10));

			this.setLineWrap(true);        // soft-wrap
			this.setWrapStyleWord(true);
				}}),
				BorderLayout.CENTER);

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
