package com.dbpp.my12306.cclient;

import com.alee.laf.WebLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CmdFrame extends JFrame {
	private static final String banner = "my12306 shell V0.0.1\n" +
//			"You can run all primary options in this shell. " +
//			"At the same time, the web application has been launched.\n" +
			"If you have any doubt, please type -help.";
	public CmdTextArea textArea;
	public JScrollPane jsp;

	private CmdClient client;

	private AboutDialog aboutDialog;
	private HelpDialog helpDialog;

	public CmdFrame(String name, CmdClient client) {
		super(name);
		this.client = client;

		WebLookAndFeel.install();

		setBounds(200, 200, 700, 400);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		initTextArea();
		initMenu();
	}

	public void start() {
		setVisible(true);
		textArea.append(banner + "\n\n");
		textArea.append("> ");
		textArea.requestFocus();
		textArea.setCaretPosition(textArea.getText().length());
	}

	public void end() {
		client.cmdFrame = null;
		dispose();
	}

	private void initMenu() {
		JMenuBar bar = new JMenuBar();

		// setting part

		JMenu settingMenu = new JMenu("Menu");
		settingMenu.add(new AbstractAction("Larger font size") {
			@Override
			public void actionPerformed(ActionEvent e) {
				Font font = textArea.getFont();
				if (font.getSize() >= 100) return;
				textArea.setFont(new Font(font.getFontName(), font.getStyle(), font.getSize() + 2));
			}
		});
		settingMenu.add(new AbstractAction("Smaller font size") {
			@Override
			public void actionPerformed(ActionEvent e) {
				Font font = textArea.getFont();
				if (font.getSize() <= 4) return;
				textArea.setFont(new Font(font.getFontName(), font.getStyle(), font.getSize() - 2));
			}
		});
		settingMenu.addSeparator();
		settingMenu.add(new AbstractAction("Exit") {
			@Override
			public void actionPerformed(ActionEvent e) {
				end();
			}
		});
		bar.add(settingMenu);

		// help part

		JMenu helpMenu = new JMenu("Help");

		JMenuItem helpItem = new JMenuItem("Help");
		helpItem.setPreferredSize(new Dimension(100, 30));
		helpItem.addActionListener(event -> {
			if (helpDialog == null) // first time
				helpDialog = new HelpDialog(this, client.getUsage());
			helpDialog.setVisible(true); // pop up dialog
		});
		helpMenu.add(helpItem);

		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(event -> {
			if (aboutDialog == null) // first time
				aboutDialog = new AboutDialog(this);
			aboutDialog.setVisible(true); // pop up dialog
		});
		helpMenu.add(aboutItem);


		bar.add(helpMenu);

		add(bar, BorderLayout.NORTH);
	}

	private void initTextArea() {
		textArea = new CmdTextArea(this, client);

		textArea.addKeyListener(textArea);
		textArea.addCaretListener(textArea);
		textArea.setFont(new Font("Lucida Console", Font.PLAIN, 16));

		textArea.setForeground(new Color(10,10,10));

		textArea.setLineWrap(true);        // soft-wrap
		textArea.setWrapStyleWord(true);

		jsp = new JScrollPane(textArea);
		add(jsp, BorderLayout.CENTER);
	}
}
