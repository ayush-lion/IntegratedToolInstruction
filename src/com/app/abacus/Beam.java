/**
 * 
 */
package com.app.abacus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 * @author prashant.joshi (198joshi@gmail.com)
 * @version 10-Aug-2017
 */
public class Beam {

	private int posX;
	private int posY;
	private int width;
	private int height;
	private Image image;
	private boolean switchable;
	
	public Beam() {
		switchable = Boolean.FALSE;
	}
	
	public Beam(int posX, int posY, int width, int height) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
	}
	
	public void drawBeam(Graphics g) {
		if(!isSwitchable())
			g.drawImage(image, this.posX, this.posY, this.getWidth(), this.getHeight(), null);
	}
	
	public void highlight(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(posX, posY, width, height);
	}
	
	/**
	 * @return the posX
	 */
	public int getPosX() {
		return posX;
	}
	/**
	 * @param posX the posX to set
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}
	/**
	 * @return the posY
	 */
	public int getPosY() {
		return posY;
	}
	/**
	 * @param posY the posY to set
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * @return the switchable
	 */
	public boolean isSwitchable() {
		return switchable;
	}

	/**
	 * @param switchable the switchable to set
	 */
	public void setSwitchable(boolean switchable) {
		this.switchable = switchable;
	}
	
}
