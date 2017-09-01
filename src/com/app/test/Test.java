package com.app.test;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test extends JPanel {

	String s = "Aayush \n vats";
	int count = 0;

	private void drawString(Graphics g, String text, int x, int y) {
		count++;
		System.out.println(s);
		System.out.println(count);
		for (String line : text.split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}

	public void paintComponent(Graphics g) {
		//super.paintComponent(g);
		drawString(g, s, 20, 20);
		g.setFont(g.getFont().deriveFont(20f));
		drawString(g, s, 120, 120);
	}

	public static void main(String s[]) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new Test());
		f.setSize(220, 220);
		f.setVisible(true);
	}
}