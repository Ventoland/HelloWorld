package levels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class LevelBackgroundAni {
	
	private BufferedImage[][] animationsMap;
	private int aniTick, aniIndex, aniSpeed = 15;
	
	
	public LevelBackgroundAni() {
		loadAnimations();
	}
	
	
	 public void render(Graphics g) {
		 	g.drawImage(animationsMap[0][aniIndex], (int) 260, (int) 200, (int)(128 * Game.SCALE), (int)(128 * Game.SCALE), null);
			g.drawImage(animationsMap[1][aniIndex], (int) 460, (int) 200, (int)(128 * Game.SCALE), (int)(128 * Game.SCALE), null);
			g.drawImage(animationsMap[2][aniIndex], (int) 800, (int) 200, (int)(128 * Game.SCALE), (int)(128 * Game.SCALE), null);	      
			g.drawImage(animationsMap[5][aniIndex], (int) 640, (int) 220, (int)(128 * Game.SCALE), (int)(128 * Game.SCALE), null);	
//			g.drawImage(animationsMap[6][aniIndex], (int) 1010, (int) 320, 256, 256, null);	
			g.drawImage(animationsMap[7][0], (int) 120, (int) 210, (int)(128 * Game.SCALE), (int)(128 * Game.SCALE), null);	
	    }
	
	
	private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ONE_ANIMATION_BLOCKS);

        animationsMap = new BufferedImage[8][73];
        for(int i = 0; i < animationsMap.length; i++) 
	        for (int j = 0; j < animationsMap[i].length; j++) {
	        	animationsMap[i][j] = img.getSubimage(j * 64, i * 64, 64, 64);    
	        }
        
    }
	
	private void updateAnimationTick() {
        aniTick++;

        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= 8) {
                aniIndex = 0;
            }
        }
    }
	
	public void update() {
		updateAnimationTick();
	}
	
    

}
