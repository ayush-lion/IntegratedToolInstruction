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
public class Bead {

	/* Holds long values */
	private long beadValue;

	/* Holds int values */
	private int posX;
	private int ePosY;
	private int hPosY;
	private int width;
	private int height;

	/* Holds bead image values */
	private Image earthImage;
	private Image heavenImage;

	/* Holds Finger Image */
	private Image rightThumbFingerImagePath;
	private Image rightIndexFingerImagePath;
	private Image leftThumbFingerImagePath;
	private Image leftIndexFingerImagePath;

	/* Holds Bead's boolean values */
	private boolean switchable;
	private boolean leftIndex;
	private boolean leftThumb;
	private boolean rightIndex;
	private boolean rightThumb;
	private boolean displayFingerImage;

	/**
	 * Draw beads
	 */
	public void drawBead(Graphics g) {
		if (isSwitchable()) {
			g.drawImage(heavenImage, posX, hPosY, width, height, null);
		} else {
			g.drawImage(earthImage, posX, ePosY, width, height, null);
		}
	}

	/**
	 * Draw Finger
	 */
	public void drawFinger(Graphics g) {
		if (isSwitchable()) {
			drawFingerImage(g, hPosY);
		} else {
			drawFingerImage(g, ePosY);
		}
	}

	/**
	 * Method is responsible to highlight bead
	 */
	public void highlight(Graphics g) {
		g.setColor(Color.CYAN);
		if(isSwitchable()) {
			g.fillOval(posX, hPosY, width, height);
		} else {
			g.fillOval(posX, ePosY, width, height);
		}
	}

	/**
	 * Method is responsible to draw finger
	 */
	private void drawFingerImage(Graphics g, int yPosition) {
		if (isDisplayFingerImage()) {
			if (isLeftIndex()) {
				g.drawImage(getLeftIndexFingerImagePath(), posX - (width / 2), yPosition + (height / 2), width, height,
						null);
			} else if (isLeftThumb()) {
				g.drawImage(getLeftThumbFingerImagePath(), posX - (width / 2), yPosition + (height / 2), width, height,
						null);
			} else if (isRightIndex()) {
				g.drawImage(getRightIndexFingerImagePath(), posX + (width / 2), yPosition + (height / 2), width, height,
						null);
			} else if (isRightThumb()) {
				g.drawImage(getRightThumbFingerImagePath(), posX + (width / 2), yPosition + (height / 2), width, height,
						null);
			}
		}
	}

	/**
	 * @return the beadValue
	 */
	public long getBeadValue() {
		return beadValue;
	}

	/**
	 * @param beadValue
	 *            the beadValue to set
	 */
	public void setBeadValue(long beadValue) {
		this.beadValue = beadValue;
	}

	/**
	 * @return the beadPosX
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * @param beadPosX
	 *            the beadPosX to set
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	 * @return the beadEarthPosY
	 */
	public int getEPosY() {
		return ePosY;
	}

	/**
	 * @param beadEarthPosY
	 *            the beadEarthPosY to set
	 */
	public void setEPosY(int ePosY) {
		this.ePosY = ePosY;
	}

	/**
	 * @return the beadHeavenPosY
	 */
	public int getHPosY() {
		return hPosY;
	}

	/**
	 * @param beadHeavenPosY
	 *            the beadHeavenPosY to set
	 */
	public void setHPosY(int hPosY) {
		this.hPosY = hPosY;
	}

	/**
	 * @return the earthImage
	 */
	public Image getEarthImage() {
		return earthImage;
	}

	/**
	 * @param earthImage
	 *            the earthImage to set
	 */
	public void setEarthImage(Image earthImage) {
		this.earthImage = earthImage;
	}

	/**
	 * @return the heavenImage
	 */
	public Image getHeavenImage() {
		return heavenImage;
	}

	/**
	 * @param heavenImage
	 *            the heavenImage to set
	 */
	public void setHeavenImage(Image heavenImage) {
		this.heavenImage = heavenImage;
	}

	/**
	 * @return the switchable
	 */
	public boolean isSwitchable() {
		return switchable;
	}

	/**
	 * @param switchable
	 *            the switchable to set
	 */
	public void setSwitchable(boolean switchable) {
		this.switchable = switchable;
	}

	/**
	 * @return the beadWidth
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param beadWidth
	 *            the beadWidth to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the beadHeight
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param beadHeight
	 *            the beadHeight to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the rightThumbFingerImagePath
	 */
	public Image getRightThumbFingerImagePath() {
		return rightThumbFingerImagePath;
	}

	/**
	 * @param rightThumbFingerImagePath
	 *            the rightThumbFingerImagePath to set
	 */
	public void setRightThumbFingerImagePath(Image rightThumbFingerImagePath) {
		this.rightThumbFingerImagePath = rightThumbFingerImagePath;
	}

	/**
	 * @return the rightIndexFingerImagePath
	 */
	public Image getRightIndexFingerImagePath() {
		return rightIndexFingerImagePath;
	}

	/**
	 * @param rightIndexFingerImagePath
	 *            the rightIndexFingerImagePath to set
	 */
	public void setRightIndexFingerImagePath(Image rightIndexFingerImagePath) {
		this.rightIndexFingerImagePath = rightIndexFingerImagePath;
	}

	/**
	 * @return the leftThumbFingerImagePath
	 */
	public Image getLeftThumbFingerImagePath() {
		return leftThumbFingerImagePath;
	}

	/**
	 * @param leftThumbFingerImagePath
	 *            the leftThumbFingerImagePath to set
	 */
	public void setLeftThumbFingerImagePath(Image leftThumbFingerImagePath) {
		this.leftThumbFingerImagePath = leftThumbFingerImagePath;
	}

	/**
	 * @return the leftIndexFingerImagePath
	 */
	public Image getLeftIndexFingerImagePath() {
		return leftIndexFingerImagePath;
	}

	/**
	 * @param leftIndexFingerImagePath
	 *            the leftIndexFingerImagePath to set
	 */
	public void setLeftIndexFingerImagePath(Image leftIndexFingerImagePath) {
		this.leftIndexFingerImagePath = leftIndexFingerImagePath;
	}

	/**
	 * @return the leftIndex
	 */
	public boolean isLeftIndex() {
		return leftIndex;
	}

	/**
	 * @param leftIndex
	 *            the leftIndex to set
	 */
	public void setLeftIndex(boolean leftIndex) {
		this.leftIndex = leftIndex;
	}

	/**
	 * @return the leftThumb
	 */
	public boolean isLeftThumb() {
		return leftThumb;
	}

	/**
	 * @param leftThumb
	 *            the leftThumb to set
	 */
	public void setLeftThumb(boolean leftThumb) {
		this.leftThumb = leftThumb;
	}

	/**
	 * @return the rightIndex
	 */
	public boolean isRightIndex() {
		return rightIndex;
	}

	/**
	 * @param rightIndex
	 *            the rightIndex to set
	 */
	public void setRightIndex(boolean rightIndex) {
		this.rightIndex = rightIndex;
	}

	/**
	 * @return the rightThumb
	 */
	public boolean isRightThumb() {
		return rightThumb;
	}

	/**
	 * @param rightThumb
	 *            the rightThumb to set
	 */
	public void setRightThumb(boolean rightThumb) {
		this.rightThumb = rightThumb;
	}

	/**
	 * @return the displayFingerImage
	 */
	public boolean isDisplayFingerImage() {
		return displayFingerImage;
	}

	/**
	 * @param displayFingerImage
	 *            the displayFingerImage to set
	 */
	public void setDisplayFingerImage(boolean displayFingerImage) {
		this.displayFingerImage = displayFingerImage;
	}

}
