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
public class BlinkBeam {

	/* Constants */
	private static final int BLINKING_RATE = 1000; // in ms

	private AbacusPanel panel;
	private Beam beam;
	private boolean startBlink;

	/* Holds timer Instance */
	private Timer timer = null;
	
	public BlinkBeam(AbacusPanel panel, Beam beam) {
		this.panel = panel;
		this.beam = beam;
		this.startBlink = Boolean.FALSE;
	}
	
	/**
	 * Method is responsible to start blinking
	 */
	public void startBlink() {
		this.startBlink = Boolean.TRUE;
		timer = new Timer(BLINKING_RATE, new TimerListener(panel, beam));
		timer.setInitialDelay(0);
		timer.start();
	}
	
	/**
	 * Method is responsible to stop the blinking
	 */
	public void stopBlink() {
		this.startBlink = Boolean.FALSE;
		beam.setSwitchable(Boolean.FALSE);
		panel.repaint(beam.getPosX(), beam.getPosY(), beam.getWidth(), beam.getHeight());
	}
	
	private class TimerListener implements ActionListener {
		private Beam beam;
		private AbacusPanel panel;
		private boolean switchOver = Boolean.TRUE;
		

		public TimerListener(AbacusPanel panel, Beam beam) {
			this.panel = panel;
			this.beam = beam;
			switchOver = Boolean.TRUE;
		}

		public void actionPerformed(ActionEvent e) {
			if (startBlink) {
				beam.setSwitchable(switchOver);
				panel.repaint(beam.getPosX(), beam.getPosY(), beam.getWidth(), beam.getHeight());
				switchOver = !switchOver;
			} 
		}

	}
}
