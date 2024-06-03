package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;
import static utilz.Constants.UI.PauseButtons.*;

public class SoundButton extends PauseButton {
	
	private BufferedImage[][] soundImages;
	private boolean mouseOver, mousePressed;
	private boolean muted;
	private int colIndex;

	public SoundButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		loadSoundImages();

	}

	private void loadSoundImages() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SOUND_BUTTONS);
		soundImages = new BufferedImage[2][3];
		for(int i = 0; i < soundImages.length; i++)
			for(int j = 0; j < soundImages[i].length; j++)
				soundImages[i][j] = temp.getSubimage(j * SOUND_SIZE_DEFAULT, i * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
	}
	
	public void update() {
		if(muted)
			colIndex = 2;
		else 
			colIndex = 0;
		
		if(mouseOver)
			colIndex = 1;
		if(mousePressed)
			colIndex = 2;
	}
	
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}
	
	public void drawMusicButton(Graphics g) {
		g.drawImage(soundImages[0][colIndex], x, y, width, height, null);
	}
	
	public void drawSfxButton(Graphics g) {
		g.drawImage(soundImages[1][colIndex], x, y, width, height, null);
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

	public boolean isMuted() {
		return muted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}

}
