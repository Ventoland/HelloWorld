package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import utilz.LoadSave.*;
import static utilz.Constants.UI.URMButtons.*;

public class LevelCompletedOverlay {
	
	private Playing playing;
	private UrmButton menu, next;
	private BufferedImage img;
	private int bgX,bgY,bgW,bgH;
	

		public LevelCompletedOverlay(Playing playing) {
			this.playing = playing;
			initImg();
			initButtons();
		}

		private void initButtons() {
			int menuX = (int) (325 * Game.SCALE);
			int nextX = (int) (430 * Game.SCALE);
			int y = (int) (245 * Game.SCALE);
			
			menu = new UrmButton(nextX, y, URM_SIZE,URM_SIZE, 0);
			next = new UrmButton(menuX, y, URM_SIZE,URM_SIZE, 2);
			
		}

		private void initImg() {
			img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMG);
			bgW = (int) ((img.getWidth() * Game.SCALE) / 1.5);
			bgH = (int) ((img.getHeight() * Game.SCALE) / 1.5);
			bgX = Game.GAME_WIDTH / 2 - bgW / 2;
			bgY = (int) (95 * Game.SCALE);
			
		}
		
		public void draw(Graphics g) {
			g.drawImage(img, bgX, bgY, bgW, bgH, null);
			next.draw(g);
			menu.draw(g);
		}
		
		public void update() {
			next.update();
			menu.update();
		}
		
		private boolean IsIn(UrmButton b, MouseEvent e) {
			return b.getBounds().contains(e.getX(), e.getY());
		}
		
		public void mouseMoved(MouseEvent e) {
			next.setMouseOver(false);
			menu.setMouseOver(false);
			
			if(IsIn(menu, e)) {
				menu.setMouseOver(true);
			} else if(IsIn(next, e)) {
				next.setMouseOver(true);
			}
		}
		
		public void mouseReleased(MouseEvent e) {
			if(IsIn(menu, e)) {
				if(menu.isMousePressed())
				System.out.println("Menu");
			} else if(IsIn(next, e)) {
				if(next.isMousePressed())
					System.out.println("Next");
			}
			
			menu.resetBools();
			next.resetBools();
		}
		
		
		
		
		public void mousePressed(MouseEvent e) {
			if(IsIn(menu, e)) {
				menu.setMousePressed(true);
			} else if(IsIn(next, e)) {
				next.setMousePressed(true);
			}
		
		}
}