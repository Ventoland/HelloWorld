package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.Game;
import entities.*; 
import static utilz.Constants.EnemyConstants.*;

public class LoadSave {
	
	public static final String PLAYER_ATLAS = "animations2.png";
	public static final String LEVEL_ATLAS = "map1.png";
//	public static final String LEVEL_ONE_DATA = "level_one_data.png";
	public static final String LEVEL_ONE_DATA = "level_one_data_long.png";
	public static final String LEVEL_ONE_ANIMATION_BLOCKS = "MapOneAnimatedBlocks.png";
	public static final String MENU_BUTTONS = "MenuButtons.png";
	public static final String MENU_BACKGROUND = "MenuBackground.png";
	public static final String PAUSE_BACKGROUND = "PauseBackground.png";
	public static final String SOUND_BUTTONS = "SoundButtons.png";
	public static final String URM_BUTTONS = "PauseButtons.png";
	public static final String VOLUME_BUTTONS = "MenuSoundDraggableButton.png";
	public static final String ENEMY1_ATLAS_LEFT = "Mark1_Enemy.png";
	public static final String ENEMY1_ATLAS_RIGHT = "Mark1_Enemy_Right.png";
	public static final String STATUS_BAR = "Life_Bar.png";
	public static final String COMPLETED_IMG = "NextLvl.png";
	
	
	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		try {
			img = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}
	
	public static ArrayList<Mark1> GetMarks1(){
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		ArrayList<Mark1> list = new ArrayList<>();
		
		for(int j = 0; j < img.getHeight(); j++) 
			for(int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if(value == MARK1) 
					list.add(new Mark1(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
			}
		return list;
	}
	
	public static int[][] GetLevelData(){
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		int [][] lvlData = new int[img.getHeight()][img.getWidth()];
		
		for(int j = 0; j < img.getHeight(); j++) 
			for(int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if(value >= 12) {
					value = 0;
				}
				lvlData[j][i] = value;
			}
			
		return lvlData;

	}


}
