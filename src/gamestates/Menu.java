package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends State implements StateMethods {
	
	private MenuButton[] buttons = new MenuButton[3];
	private BufferedImage backgroundImage;
	private int menuX, menuY, menuWidth, menuHeight;

	public Menu(Game game) {
		super(game);
		loadButtons();
		loadBackground();
	}

	private void loadBackground() {
		backgroundImage = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
		menuWidth = (int) (Game.GAME_WIDTH);
		menuHeight = (int) (Game.GAME_HEIGHT);
		menuX = Game.GAME_WIDTH/ 2 - menuWidth / 2;
		menuY = 0;
	}

	private void loadButtons() {
		buttons[0] = new MenuButton((int)(Game.GAME_WIDTH / 5.3), (int) (230 * Game.SCALE), 0, Gamestate.PLAYING);
		buttons[1] = new MenuButton((int)(Game.GAME_WIDTH / 5.3), (int) (300 * Game.SCALE), 1, Gamestate.OPTIONS);
		buttons[2] = new MenuButton((int)(Game.GAME_WIDTH / 5.3), (int) (370 * Game.SCALE), 2, Gamestate.QUIT);
		
	}

	@Override
	public void update() {
		for(MenuButton mb : buttons)
			mb.update();
		
	}

	@Override
	public void draw(Graphics g) {
		
		g.drawImage(backgroundImage, menuX, menuY, menuWidth , menuHeight, null);
		
		for(MenuButton mb : buttons)
			mb.draw(g);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(MenuButton mb : buttons) {
			if(isIn(e,mb)) {
				mb.setMousePressed(true);
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(MenuButton mb : buttons) {
			if(isIn(e,mb)) {
				if(mb.isMousePressed())
				   mb.applyGamestate();
				break;
			}
		}
		resetButtons();
	}

	private void resetButtons() {
		for(MenuButton mb : buttons) {
			mb.resetBools();
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(MenuButton mb : buttons) 
			mb.setMouseOver(false);
		
		for(MenuButton mb : buttons) {
			if(isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) 
			Gamestate.state = Gamestate.PLAYING;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
