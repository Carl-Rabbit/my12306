package com.dbpp.my12306.otherTests;


import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ConsoleTest {
	public static void main(String[] args) {
		JFrame frame = new JFrame("CMD Text Area");
		frame.setBounds(200, 200, 700, 400);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		CmdTextArea textArea = new CmdTextArea();
		textArea.addKeyListener(textArea);
		textArea.addCaretListener(textArea);
		textArea.setFont(new Font("宋体", Font.PLAIN, 14));
		frame.add(textArea, BorderLayout.CENTER);
		frame.setVisible(true);

		textArea.append("Please input commend<type 'exit' to exit>:");
		textArea.requestFocus();
		textArea.setCaretPosition(textArea.getText().length());
	}
}

class CmdTextArea extends JTextArea implements KeyListener,
		CaretListener {

	private static final long serialVersionUID = 1L;
	private StringBuffer textBuffer = new StringBuffer();
	private int currentDot = -1;
	private boolean isAllowedInputArea = false;
	private int currentKeyCode = 0;
	private boolean isConsume = false;

	public CmdTextArea() {
		super();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (isConsume) {
			e.consume();
			return;
		}

		// 以'enter'键结束命令输入
		if (currentKeyCode == KeyEvent.VK_ENTER) {
			String input = this.getText().substring(textBuffer.length(),
					this.getText().length() - 1);
			textBuffer.append(input);
			textBuffer.append("\n");
			if (input.equals("exit")) {
				this.append("Bye.");
				System.exit(0);
			} else {
				this.append(input);
				this.append("\n");
				this.append("Please input commend<type 'exit' to exit>:");
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
			return;
		}
	}

	/**
	 * 检查是否要使用输入事件
	 *
	 * @param e
	 * @return
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
