package entities;

import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class Mark1 extends Enemy {
	
	//AtackBox
		private Rectangle2D.Float attackBox;
		private int attackBoxOffSetX;
		
		
    public Mark1(float x, float y) {
        super(x, y, MARK1_WIDTH, MARK1_HEIGHT, MARK1);
        initHitBox(x, y, (int) ((1.5 * (24 * Game.SCALE))), (int) (1.1 * (54 * Game.SCALE)));
        initAttackBox();
    }
    
    private void initAttackBox() {
		attackBox = new Rectangle2D.Float((int)x,(int)(y + 33), (int)(30 * Game.SCALE), (int)(16 * Game.SCALE));
		attackBoxOffSetX = (int)(Game.SCALE * 20);
	}

	public void update(int[][] lvlData, Player player) {
		updateBehavior(lvlData, player);
		updateAnimationTick();
		updateAttackBox();
	}
	
	private void updateAttackBox() {
		if(walkDir == RIGHT) {
			attackBox.x = hitbox.x + hitbox.width-8 - attackBoxOffSetX;
		} else if(walkDir == LEFT) {
			attackBox.x = hitbox.x - hitbox.width-7 + attackBoxOffSetX;
		}
		
		attackBox.y = hitbox.y + (Game.SCALE * 2);
		
	}

	private void updateBehavior(int[][] lvlData, Player player) {
		if(firstUpdate) 
			firstUpdateCheck(lvlData);
		if(inAir)
			updateInAir(lvlData); 
		
		else {
			switch(enemyState) {
			case IDLE:
				newState(RUNNING);
				break;
			case RUNNING:
				setAniSpeed(12);
				int temp = getAniSpeed();
				if(canSeeThePlayer(lvlData, player)) {
					turnTowardsThePlayer(player);
				if(isPlayerCloseForAttack(player)) {
					newState(ATTACKING);
				}
				}
				setAniSpeed(temp);
				move(lvlData);
			break;
			case ATTACKING:
				setAniSpeed(25);
				
				if(aniIndex == 0)
					attackChecked = false;
				
				if((aniIndex == 7 || aniIndex == 8) && !attackChecked)
					checkEnemyHit(attackBox, player);
					
			}
			
		}
	}
	
	

	public void drawAttackBox(Graphics g, int xLvlOffSett) {
		g.setColor(Color.red);
		g.drawRect((int)(attackBox.x - xLvlOffSett),(int)(attackBox.y + 33), (int)attackBox.width, (int)attackBox.height);
	}
    
    
    public int flipX() {
    	if(walkDir == RIGHT)
    		return width;
    	else 
    		return 0;
    }
    
    public int flipW() {
    	if(walkDir == RIGHT)
    		return -1;
    	else 
    		return 1;
    	
    }

	

	
}
