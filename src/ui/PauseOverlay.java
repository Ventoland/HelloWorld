package ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.*;
import static utilz.Constants.UI.PauseButtons.*;
import static utilz.Constants.UI.VolumeButtons.*;

public class PauseOverlay {
	
	private BufferedImage backgroundImg;
	private int bgX, bgY, bgW, bgH;
	private SoundButton musicButton, sfxButton;
	private UrmButton menuB, replayB, unpauseB;
	private Playing playing;
	private VolumeButton volumeButton;

	public PauseOverlay(Playing playing){
		this.playing = playing;
		loadBackground();
		createSoundButtons();
		crateUrmButtons();
		createVolumeButton();
	}
	
	private void createVolumeButton() {
		int vX = (int)(322 * Game.SCALE);
		int vY = (int)(318 * Game.SCALE);
		volumeButton = new VolumeButton(vX,vY, SLIDER_WIDTH, VOLUME_HEIGHT);
	}

	private void crateUrmButtons() {
		int menuX = (int)(295 * Game.SCALE);
		int replayX = (int)(383 * Game.SCALE);
		int unpauseX = (int)(472 * Game.SCALE);
		int bY = (int)(365 * Game.SCALE);
		
		menuB = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
		replayB = new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
		unpauseB = new UrmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
		
	}

	private void createSoundButtons() {
		int musicX = (int)(340 * Game.SCALE);
		int sfxX = (int)(434 * Game.SCALE);
		int soundY = (int) (215 * Game.SCALE);
		musicButton = new SoundButton(musicX, soundY, (int)(SOUND_SIZE / 1.25), (int)(SOUND_SIZE / 1.25));
		sfxButton = new SoundButton(sfxX, soundY, (int)(SOUND_SIZE / 1.25), (int)(SOUND_SIZE / 1.25));
	}

	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
		bgW =  (int) (Game.GAME_WIDTH);
		bgH =  (int) (Game.GAME_HEIGHT);
		bgX = Game.GAME_WIDTH / 2 - bgW / 2;
		bgY =  (int) (Game.GAME_HEIGHT / 2) - bgH / 2;
		
	}

	public void update() {
		sfxButton.update();
		musicButton.update();
		
		menuB.update();;
		replayB.update();
		unpauseB.update();
		volumeButton.updade();
	}
	
	public void draw(Graphics g) {
		// Background
		g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);
		
		// Sound Buttons
		musicButton.drawMusicButton(g);
		sfxButton.drawSfxButton(g);
		
		// URM Buttons
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);
		
		// Volume Slider
		volumeButton.draw(g);
	}
	
	public void mouseDragged(MouseEvent e) {
		if(volumeButton.isMousePressed()) {
			volumeButton.changeX(e.getX());
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if (isIn(e, musicButton))
			musicButton.setMousePressed(true);
		else if (isIn(e, sfxButton))
			sfxButton.setMousePressed(true);
		
		else if(isIn(e,menuB))
			menuB.setMousePressed(true);
		else if(isIn(e,replayB))
			replayB.setMousePressed(true);
		else if(isIn(e,unpauseB))
			unpauseB.setMousePressed(true);
		else if(isIn(e,volumeButton))
			volumeButton.setMousePressed(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, musicButton) && musicButton.isMousePressed()) {
			musicButton.setMuted(!musicButton.isMuted());
		} else if (isIn(e, sfxButton) && sfxButton.isMousePressed()) {
			sfxButton.setMuted(!sfxButton.isMuted());
		} else if (isIn(e, menuB) && menuB.isMousePressed()) {
			Gamestate.state = Gamestate.MENU;
			playing.unpauseGame();
		} else if (isIn(e, replayB) && replayB.isMousePressed()) {

			playing.resetAll();
			playing.unpauseGame();
			
		} else if (isIn(e, unpauseB) && unpauseB.isMousePressed()) {
			playing.unpauseGame();
		}
		
		musicButton.resetBools();
		sfxButton.resetBools();
		menuB.resetBools();
		replayB.resetBools();
		unpauseB.resetBools();
		volumeButton.resetBools();
	}

	public void mouseMoved(MouseEvent e) {
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);
		volumeButton.setMouseOver(false);
		
		if (isIn(e, musicButton))
			musicButton.setMouseOver(true);
		else if (isIn(e, sfxButton))
			sfxButton.setMouseOver(true);
		else if (isIn(e, menuB))
			menuB.setMouseOver(true);
		else if (isIn(e, replayB))
			replayB.setMouseOver(true);
		else if (isIn(e, unpauseB))
			unpauseB.setMouseOver(true);
		else if (isIn(e, volumeButton))
			volumeButton.setMouseOver(true);
	}
	
	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
}
