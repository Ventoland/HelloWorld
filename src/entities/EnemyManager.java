package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.EnemyConstants.*;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.Directions.*;


public class EnemyManager {
	private Playing playing;
	private BufferedImage[][] mark1Arr;
	private ArrayList<Mark1> marks1 = new ArrayList<>();
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs(this.marks1);
		addEnemies();
	}
	
	private void addEnemies() {
		marks1 = LoadSave.GetMarks1();
		
	}

	public void update(int[][] lvlData, Player player) {
		for(Mark1 m : marks1) {
			if(m.isActive())
				m.update(lvlData, player);
		}
	}
	public void draw(Graphics g, int xLvlOffSett) {
		drawMarks(g, xLvlOffSett);
	}
	

	private void drawMarks(Graphics g, int xLvlOffSett) {
		for(Mark1 m : marks1) {
			if(m.isActive()) {
			g.drawImage(mark1Arr[m.getEnemyState()][m.getAniIndex()], 
					(int)( m.getHitBox().x - xLvlOffSett - MARK1_DRAWOFFSET_X + m.flipX()), 
					(int) ((m.getHitBox().y) - (2.7 * MARK1_DRAWOFFSET_Y)), 
					(int)(1.5 * MARK1_WIDTH * m.flipW()), 
					(int)(1.5 * MARK1_HEIGHT), 
					null);
					//m.drawHitBox(g, xLvlOffSett);
			//m.drawAttackBox(g, xLvlOffSett);
			}
		}
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for(Mark1 m : marks1) 
			if(m.isActive())
			if(attackBox.intersects(m.getHitBox())) {
				m.hurt(10);
				return;
			}
			
		
	}
	

	private void loadEnemyImgs(ArrayList<Mark1> marks1) {
		mark1Arr = new BufferedImage[4][13];
		BufferedImage temp;
			temp = LoadSave.GetSpriteAtlas(LoadSave.ENEMY1_ATLAS_LEFT);
		
		for(int i = 0; i < mark1Arr.length; i++)
			for(int j = 0; j < mark1Arr[i].length; j++) {
				mark1Arr[i][j] = temp.getSubimage(j * MARK1_WIDTH_DEFAULT, i * MARK1_HEIGHT_DEFAULT, MARK1_WIDTH_DEFAULT, MARK1_HEIGHT_DEFAULT);
				
			}
	}
	
	public void resetAllEnemies() {
		for(Mark1 m : marks1)
			m.resetEnemy();
	}
	
	
	
}
