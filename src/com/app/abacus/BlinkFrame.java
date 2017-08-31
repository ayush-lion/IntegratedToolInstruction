/**
 * 
 */
package com.app.abacus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.app.abacus.panel.AbacusPanel;

/**
 * @author prashant.joshi (198joshi@gmail.com)
 * @version 10-Aug-2017
 */
public class BlinkFrame {

	/* Constants */
	private static final int BLINKING_RATE = 1000; // in ms

	private AbacusPanel panel;
	private Frame frame;
	private boolean startBlink;

	/* Holds timer Instance */
	private Timer timer = null;
	
	public BlinkFrame(AbacusPanel panel, Frame frame) {
		this.panel = panel;
		this.frame = frame;
		this.startBlink = Boolean.FALSE;
	}
	
	/**
	 * Method is responsible to start blinking
	 */
	public void startBlink() {
		this.startBlink = Boolean.TRUE;
		timer = new Timer(BLINKING_RATE, new TimerListener(panel, frame));
		timer.setInitialDelay(0);
		timer.start();
	}
	
	/**
	 * Method is responsible to stop the blinking
	 */
	public void stopBlink() {
		this.startBlink = Boolean.FALSE;
		frame.setSwitchable(Boolean.FALSE);
		panel.repaint(frame.getPosX(), frame.getPosY(), frame.getWidth(), frame.getHeight());
	}
	
	private class TimerListener implements ActionListener {
		private Frame frame;
		private AbacusPanel panel;
		private boolean switchOver = Boolean.TRUE;

		public TimerListener(AbacusPanel panel, Frame frame) {
			this.panel = panel;
			this.frame = frame;
			switchOver = Boolean.TRUE;
		}

		public void actionPerformed(ActionEvent e) {
			if (startBlink) {
				frame.setSwitchable(switchOver);
				panel.repaint(frame.getPosX(), frame.getPosY(), frame.getWidth(), frame.getHeight());
				switchOver = !switchOver;
			} 
		}

	}
}
