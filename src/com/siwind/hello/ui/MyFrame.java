package com.siwind.hello.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class MyFrame extends JFrame {
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 200;

	public MyFrame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(() -> {
			MyFrame frame = new MyFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}

}
