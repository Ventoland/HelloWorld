package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.*;

import main.Game;

public abstract class Enemy extends Entity {
	protected int aniIndex, enemyState, enemyType;
	protected int aniTick, aniSpeed = 12;
	protected boolean firstUpdate = true;
	protected boolean inAir = false;
	protected float fallSpeed;
	protected float gravity = 0.04f * Game.SCALE;
	protected float walkSpeed = 0.3f * Game.SCALE;
	protected int walkDir = LEFT;
	protected boolean facingRight = true;
	protected int tileY;
	protected float attackDistance = Game.TILES_SIZE;
	protected int maxHealth;
	protected int currentHealth;
	protected boolean active = true;
	protected boolean attackChecked;


	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		initHitBox(x, y, width, height);
		maxHealth = GetMaxHealth(enemyType);
		currentHealth = maxHealth;
	}
	
	protected void firstUpdateCheck(int[][] lvlData) {
		if(!IsEntityOnFloor(hitbox, lvlData)) 
			inAir = true;
		firstUpdate = false;
	}
	
	
	protected void updateInAir(int[][] lvlData) {
		if(CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.y += fallSpeed;
			fallSpeed += gravity;
				
		} else {
			inAir = false;
			hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
			tileY = (int)(hitbox.y / Game.TILES_SIZE);
		} 
	}
	
	protected void move(int[][] lvlData) {
		float xSpeed = 0;
		
		if(walkDir == LEFT)
			xSpeed = -walkSpeed;
		else
			xSpeed = walkSpeed;
		
		if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
			if(IsFloor(hitbox, xSpeed, lvlData)) {
				hitbox.x += xSpeed;
				return;
			}
		
		changeWalkDir();
	}
	
	protected void turnTowardsThePlayer(Player player) {
		if(player.hitbox.x > hitbox.x)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}
	
	protected boolean canSeeThePlayer(int[][] lvlData, Player player) {
		int playerTileY = (int)(player.getHitBox().y / Game.TILES_SIZE);
		if(playerTileY == tileY)
			if(isPlayerInRange(player)) {
				if(isSightClear(lvlData, hitbox, player.hitbox, tileY))
					return true;
			}
		return false;
	}
	
	
	protected boolean isPlayerInRange(Player player) {
		int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
		return absValue <= attackDistance * 5;
	}
	
	
	protected boolean isPlayerCloseForAttack(Player player) {
		int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
		return absValue <= attackDistance;
	}
	

	protected void newState(int enemyState) {
		this.enemyState = enemyState;
		aniTick = 0;
		aniIndex = 0;
	}
	
	
	public void hurt(int amount) {
		currentHealth -= amount;
		if(currentHealth <= 0)
			newState(DEAD);
		else
			newState(IDLE);
		
	}
	
	protected void checkEnemyHit(Rectangle2D.Float attackBox, Player player) {
		if(attackBox.intersects(player.hitbox))
			player.changeHealth(-GetEnemyDmg(enemyType));
		attackChecked = true;
		
	}
	
	
	protected void updateAnimationTick() {
		aniTick++;
		
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
				aniIndex = 0;
				
				switch(enemyState) {
				case ATTACKING -> enemyState = IDLE;
				case DEAD -> active = false;
				}
				
			}
		}
	}
	
		
	protected void changeWalkDir() {
		if(walkDir == LEFT)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}
	
	public void resetEnemy() {
		hitbox.x = x;
		hitbox.y = y;
		firstUpdate = true;
		currentHealth = maxHealth;
		newState(IDLE);
		active = true;
		fallSpeed = 0;
		
		
	}
	
	
	

	public int getAniIndex() {
		return aniIndex;
	}
	public int getEnemyState() {
		return enemyState;
	}

	public int getWalkDir() {
		return walkDir;
	}

	public void setWalkDir(int walkDir) {
		this.walkDir = walkDir;
	}

	public int getAniSpeed() {
		return aniSpeed;
	}

	public void setAniSpeed(int aniSpeed) {
		this.aniSpeed = aniSpeed;
	}
	
	public boolean isActive() {
		return active;
	}
}
