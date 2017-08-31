package com.app.instruction;

import java.awt.Graphics;
import java.awt.Image;

public class ActorStudent {
	
	private Image actor_image;
	 private int id;
	 private boolean isthinking;
	 private int posX;
	 private int posY;
	 private int width;
	 private int height;

	 
	 
	 public void drawActor(Graphics g) {
		g.drawImage(actor_image, posX, posY, width, height, null);
	}
	 
	

	 public int getId() {
		return this.id;
	}
	 
	 
	 
	 public void setId(int id) {
		this.id = id;
	}
	 
	 
	
	

	public Image getActor_image() {
		return this.actor_image;
	}




	public void setActor_image(Image actor_image) {
		this.actor_image = actor_image;
	}




	public int getPosX() {
		return this.posX;
	}




	public void setPosX(int posX) {
		this.posX = posX;
	}




	public int getPosY() {
		return this.posY;
	}




	public void setPosY(int posY) {
		this.posY = posY;
	}




	public int getWidth() {
		return this.width;
	}




	public void setWidth(int width) {
		this.width = width;
	}




	public int getHeight() {
		return this.height;
	}




	public void setHeight(int height) {
		this.height = height;
	}




	public ActorStudent()
	{
		
	}

}
