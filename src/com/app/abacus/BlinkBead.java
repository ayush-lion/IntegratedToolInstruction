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
public class BlinkBead {

	/* Constants */
	private static final int BLINKING_RATE = 1000; // in ms

	private AbacusPanel panel;
	private Bead bead;
	private boolean startBlink;
	private boolean beadCurrentState;

	/* Holds timer Instance */
	private Timer timer = null;
	
	public BlinkBead(AbacusPanel panel, Bead bead) {
		this.panel = panel;
		this.bead = bead;
		this.startBlink = Boolean.FALSE;
	}
	
	/**
	 * Method is responsible to start blinking
	 */
	public void startBlink() {
		this.startBlink = Boolean.TRUE;
		this.beadCurrentState = bead.isSwitchable();
		timer = new Timer(BLINKING_RATE, new TimerListener(panel, bead));
		timer.setInitialDelay(0);
		timer.start();
	}
	
	/**
	 * Method is responsible to stop the blinking
	 */
	public void stopBlink() {
		this.startBlink = Boolean.FALSE;
		bead.setSwitchable(beadCurrentState);
		if (bead.isSwitchable()) {
			panel.repaint(bead.getPosX(), bead.getHPosY(), bead.getWidth(), bead.getHeight());
		} else {
			panel.repaint(bead.getPosX(), bead.getEPosY(), bead.getWidth(), bead.getHeight());
		}
	}
	
	private class TimerListener implements ActionListener {
		private Bead bead;
		private AbacusPanel panel;
		private boolean switchOver = Boolean.TRUE;

		public TimerListener(AbacusPanel panel, Bead bead) {
			this.panel = panel;
			this.bead = bead;
			switchOver = new Boolean(!beadCurrentState);
		}

		public void actionPerformed(ActionEvent e) {
			if (startBlink) {
				if(switchOver) {
					bead.setSwitchable(switchOver);
					if (beadCurrentState) {
						panel.repaint(bead.getPosX(), bead.getHPosY(), bead.getWidth(), bead.getHeight());
					} else {
						panel.repaint(bead.getPosX(), bead.getEPosY(), bead.getWidth(), bead.getHeight());
					}
				} else {
					bead.setSwitchable(switchOver);
					if (beadCurrentState) {
						panel.repaint(bead.getPosX(), bead.getHPosY(), bead.getWidth(), bead.getHeight());
					} else {
						panel.repaint(bead.getPosX(), bead.getEPosY(), bead.getWidth(), bead.getHeight());
					}
				}
				switchOver = !switchOver;
			} 
		}

	}
}
