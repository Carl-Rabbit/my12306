package com.dbpp.my12306.cclient;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CmdTextArea extends JTextArea implements KeyListener,
		CaretListener {

	private static final long serialVersionUID = 1L;
	private StringBuffer textBuffer = new StringBuffer();
	private int currentDot = -1;
	private boolean isAllowedInputArea = false;
	private int currentKeyCode = 0;
	private boolean isConsume = false;

	private CmdFrame frame;
	private CmdClient client;

	public CmdTextArea(CmdFrame frame, CmdClient client) {
		super();
		this.frame = frame;
		this.client = client;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (isConsume) {
			e.consume();
			return;
		}

		if (currentKeyCode == KeyEvent.VK_ENTER) {
			String input = this.getText().substring(textBuffer.length(),
					this.getText().length() - 1);
			textBuffer.append(input);
			textBuffer.append("\n");

			if (input.isBlank()) {
				this.append("> ");
			} else if (input.equals("exit")) {
				this.append("Bye.");
				try {
					Thread.sleep(500);
				} catch (InterruptedException ex) {
					// never go here
					ex.printStackTrace();
				}
				System.out.println("Shell exit.");
				this.frame.end();
			} else {
				client.execute(input.split(" "));
				this.append(client.result);
				this.append("> ");
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		currentKeyCode = e.getKeyCode();
		isConsume = checkConsume(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (isConsume) {
			e.consume();
		}
	}

	/**
	 * check key event and consume if need.
	 *
	 * @param e the key event
	 * @return has been executed or not
	 */
	private boolean checkConsume(KeyEvent e) {
		if (!isAllowedInputArea) {
			e.consume();
			return true;
		}

		if ((currentKeyCode == KeyEvent.VK_BACK_SPACE
				|| currentKeyCode == KeyEvent.VK_ENTER
				|| currentKeyCode == KeyEvent.VK_UP || currentKeyCode == KeyEvent.VK_LEFT)
				&& currentDot == textBuffer.length()) {
			e.consume();
			return true;
		}

		return false;
	}

	@Override
	public void append(String message) {
		super.append(message);
		textBuffer.append(message);
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		currentDot = e.getDot();
		isAllowedInputArea = currentDot >= textBuffer.length();
	}
}

