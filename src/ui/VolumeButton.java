package ui;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;
import static utilz.Constants.UI.VolumeButtons.*;

public class VolumeButton extends PauseButton{
	
	private BufferedImage imgs;
	private BufferedImage slider;
	private int index = 0;
	private boolean mouseOver, mousePressed;
	private int buttonX, minX, maxX;

	public VolumeButton(int x, int y, int width, int height) {
		super(x + width / 2, y, VOLUME_WIDTH, height);
		bounds.x -= VOLUME_WIDTH / 2;
		buttonX = x + width / 2;
		minX = x+ VOLUME_WIDTH / 2;;
		maxX = x + width - VOLUME_WIDTH / 2;;
		loadImgs();
	}
	
	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.VOLUME_BUTTONS);
		imgs = temp.getSubimage(0, VOLUME_DEFAULT_HEIGHT, VOLUME_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
		
		slider = temp.getSubimage(0, 0, SLIDER_DEFAULT_WIDTH, SLIDER_DEFAULT_HEIGHT);
			
		
		
	}

	public void updade() {
		index = 0;
		if(mouseOver)
			index = 1;
		if(mousePressed)
			index = 2;
		
	}
	
	public void draw(Graphics g) {
		g.drawImage(slider, x - width * 3, y, SLIDER_WIDTH, height, null);
		g.drawImage(imgs, buttonX - VOLUME_WIDTH / 2, y, VOLUME_WIDTH, height, null);
	}
	
	public void changeX(int x) {
		if(x < minX)
			buttonX = minX;
		else if(x > maxX)
			buttonX = maxX;
		else 
			buttonX = x;
		
		bounds.x = buttonX - VOLUME_WIDTH / 2;
	}
	
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	

}
