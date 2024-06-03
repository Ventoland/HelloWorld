package levels;

import static utilz.Constants.MapConstants.GetSpriteAmount;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class LevelManager {
	private Game game;
	private BufferedImage[] levelSprite;
	private Level levelOne;
   


	
	public LevelManager(Game game) {
		this.game = game;

		importOutsideSprites();
		levelOne = new Level(LoadSave.GetLevelData());
		
	}
	

	private void importOutsideSprites() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
		levelSprite = new BufferedImage[12];
		
		for(int j = 0; j < 4; j++) {
			for(int i = 0; i < 3; i++) {
				int index = j*3 + i;
				levelSprite[index] = img.getSubimage(i * 64, j * 64, 64, 64);
			}
		}
		
	}

	public void draw(Graphics g, int lvlOffSet) {
		for(int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
			for(int i = 0; i < levelOne.getLevelData()[0].length; i++) {
				int index = levelOne.getSpriteIndex(i, j);
				g.drawImage(levelSprite[index],  Game.TILES_SIZE * i - lvlOffSet, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
		}
	}
	
	public void update() {

	}

	
	public Level getCurrentLevel() {
		return levelOne;
	}

}

