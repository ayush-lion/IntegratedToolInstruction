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
public class Frame {

	private int posX;
	private int posY;
	private int width;
	private int height;
	private int frameVerticalWidth;
	private int frameHorizontalWidth;
	private boolean doWeNeedToDisplayTotalCount;
	private Image image;
	private boolean switchable;
	
	public Frame() {
		switchable = Boolean.FALSE;
	}
	
	public Frame(int posX, int posY, int width, int height) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
	}
	
	public void drawFrame(Graphics g) {
		if(!isSwitchable())
			g.drawImage(image, this.posX, this.posY, this.getWidth(), this.getHeight(), null);
	}
	
	public void highlight(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(this.posX + 5, this.posY + 5, 10, this.height - 10);
		g.fillRect(this.posX + 5, this.posY + 5, this.width - 10, 10);
		
		g.fillRect(this.posX + 5, this.height - 15, this.width - 10, 10);
		g.fillRect(this.width - 15, this.posY + 5, 10, this.height - 10);
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
	 * @return the frameVerticalWidth
	 */
	public int getFrameVerticalWidth() {
		return frameVerticalWidth;
	}

	/**
	 * @param frameVerticalWidth the frameVerticalWidth to set
	 */
	public void setFrameVerticalWidth(int frameVerticalWidth) {
		this.frameVerticalWidth = frameVerticalWidth;
	}

	/**
	 * @return the frameHorizontalWidth
	 */
	public int getFrameHorizontalWidth() {
		return frameHorizontalWidth;
	}

	/**
	 * @param frameHorizontalWidth the frameHorizontalWidth to set
	 */
	public void setFrameHorizontalWidth(int frameHorizontalWidth) {
		this.frameHorizontalWidth = frameHorizontalWidth;
	}

	/**
	 * @return the doWeNeedToDisplayTotalCount
	 */
	public boolean canWeDisplayTotalCount() {
		return doWeNeedToDisplayTotalCount;
	}

	/**
	 * @param doWeNeedToDisplayTotalCount the doWeNeedToDisplayTotalCount to set
	 */
	public void setDoWeNeedToDisplayTotalCount(boolean doWeNeedToDisplayTotalCount) {
		this.doWeNeedToDisplayTotalCount = doWeNeedToDisplayTotalCount;
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
