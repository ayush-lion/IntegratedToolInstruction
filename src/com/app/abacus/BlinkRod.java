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
public class BlinkRod {

	/* Constants */
	private static final int BLINKING_RATE = 1000; // in ms

	private AbacusPanel panel;
	private Rod rod;
	private boolean startBlink;

	/* Holds timer Instance */
	private Timer timer = null;
	
	public BlinkRod(AbacusPanel panel, Rod rod) {
		this.panel = panel;
		this.rod = rod;
		this.startBlink = Boolean.FALSE;
	}
	
	/**
	 * Method is responsible to start blinking
	 */
	public void startBlink() {
		this.startBlink = Boolean.TRUE;
		timer = new Timer(BLINKING_RATE, new TimerListener(panel, rod));
		timer.setInitialDelay(0);
		timer.start();
	}
	
	/**
	 * Method is responsible to stop the blinking
	 */
	public void stopBlink() {
		this.startBlink = Boolean.FALSE;
		rod.setSwitchable(Boolean.FALSE);
		panel.repaint(rod.getPosX(), rod.getPosY(), rod.getWidth(), rod.getHeight());
	}
	
	private class TimerListener implements ActionListener {
		private Rod rod;
		private AbacusPanel panel;
		private boolean switchOver = Boolean.TRUE;

		public TimerListener(AbacusPanel panel, Rod rod) {
			this.panel = panel;
			this.rod = rod;
			switchOver = Boolean.TRUE;
		}

		public void actionPerformed(ActionEvent e) {
			if (startBlink) {
				rod.setSwitchable(switchOver);
				panel.repaint(rod.getPosX(), rod.getPosY(), rod.getWidth(), rod.getHeight());
				switchOver = !switchOver;
			} 
		}

	}
}
