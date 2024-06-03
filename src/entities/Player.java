package entities;

import static utilz.Constants.Directions.*;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import gamestates.Playing;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import main.Game;
import utilz.LoadSave;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 16;
    private int playerAction = STOP;
    private boolean moving = false, attacking1 = false, attacking2 = false, attacking3 = false, attacking4 = false;
    private boolean left, right, up, down, jump;
    private float playerSpeed = 1.0f;
    private int[][] lvlData;
    private float xDrawOfSet = 2 * (26 * Game.SCALE);
    private float yDrawOfSet = 2 * (35 * Game.SCALE);
    
    // Turn animations to the left
    private boolean facingLeft = false;
    
    // Jumping / Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed =  -1.85f * Game.SCALE; 
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;
    
    //Status Bar UI
    private BufferedImage statusBarImg;

	private int statusBarWidth = (int) (252 * Game.SCALE);
	private int statusBarHeight = (int) (115 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);

	private int healthBarWidth = (int) (152.4 * Game.SCALE);
	private int healthBarHeight = (int) (10 * Game.SCALE);
	private int healthBarXStart = (int) (76.5 * Game.SCALE);
	private int healthBarYStart = (int) (28 * Game.SCALE);

	private int maxHealth = 100;
	private int currentHealth = maxHealth;
	private int healthWidth = healthBarWidth;
	
	
	//AtackBox
	private Rectangle2D.Float attackBox;
	
	
	
	private int flipX = 0;
	private int flipW = 1;
	
	private boolean attackChecked = false;
	private Playing playing;
	

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        loadAnimations();
        initHitBox(x, y, 2 * (11 * Game.SCALE), (int)(2 * (28.5 * Game.SCALE)));
        initAttackBox();
    }

    private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int)(20 * Game.SCALE), (int)(20 * Game.SCALE));
		
		
	}

	public void update() {
    	updateHealthBar();
    	
		if(currentHealth <= 0) {
			playing.setGameOver(true);
			return;
		}
    	updateHealthBar();
    	updateAttackBox();
        updatePos();
        if(attacking1 || attacking2 || attacking3 || attacking4)
        checkAttack();
        updateAnimationTick();
        setAnimation();
    }

    private void checkAttack() {
		if(attackChecked || aniIndex != 1)
			return;
		//else if ((attacking1 && aniIndex !=)()()())
		attackChecked = true;
		playing.checkEnemyHit(attackBox);
	}

	private void updateAttackBox() {
		if(right) {
			attackBox.x = hitbox.x + hitbox.width + (int)(Game.SCALE * 10);
		} else if(left) {
			attackBox.x = hitbox.x - hitbox.width - (int)(Game.SCALE * 10);
		}
		
		attackBox.y = hitbox.y + (Game.SCALE * 10);
		
	}

	private void updateHealthBar() {
		healthWidth = (int)((currentHealth /(float)maxHealth) * healthBarWidth);
		
	}

	public void render(Graphics g, int lvlOffSet) {
        
            g.drawImage(animations[playerAction][aniIndex], 
            		(int) (hitbox.x - xDrawOfSet - lvlOffSet + flipX), 
            		(int) (hitbox.y - yDrawOfSet), 
            		width * flipW, height, null);
    
            
            //drawAttackBox(g, lvlOffSet);
            drawUI(g);

        
//		drawHitBox(g, lvlOffSett);

    }

    private void drawAttackBox(Graphics g, int lvlOffSetX) {
		g.setColor(Color.red);
		g.drawRect((int)attackBox.x - lvlOffSetX, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
		
	}

	private void drawUI(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart +statusBarY, healthWidth, healthBarHeight);
	}

	private void updateAnimationTick() {
        aniTick++;

        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking1 = false;
                attacking2 = false;
                attacking3 = false;
                attacking4 = false;
                attackChecked = false;
            }
        }
    }

    private void setAnimation() {
        int startAni = playerAction;

        if (attacking1) {
            playerAction = POWER1;
        } else if (attacking2) {
            playerAction = POWER2;
        } else if (attacking3) {
            playerAction = POWER3;
        } else if (attacking4) {
            playerAction = POWER4;
        } else if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = STOP;
        }
        
        if(inAir) {
        	if(airSpeed < 0) {
        		playerAction = JUMP;
        	} else {
        		playerAction = FALLING;
        	}
        }

        if (startAni != playerAction) {
            resetAniTick();
        }
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {
        moving = false;
        
        if(jump) 
        	jump();
        
        if (!left && !right && !inAir) {
            return;
        }
        
        if (left && right && inAir) {
            left = false;
            right = false;
        }
        if (attacking1 || attacking2 || attacking3 || attacking4) {
            return;
        }
        if(left && right)
        	return;

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
            flipX = width;
            flipW = -1;
        } 
        
        if (right) {
            xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1;
        }
        
        if(!inAir) 
        	if(!IsEntityOnFloor(hitbox, lvlData)) 
        		inAir = true;
        	
        		
        
        
        if(inAir) {
        	if(CanMoveHere(hitbox.x,hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
        		hitbox.y += airSpeed;
        		airSpeed += gravity;
        		updateXPos(xSpeed);
        	} else {
        		hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
        		if(airSpeed > 0) {
        			resetInAir();
        		} else {
        			airSpeed = fallSpeedAfterCollision;
        		}
        		updateXPos(xSpeed);
        	}
        }else {
        	updateXPos(xSpeed);
        }

        moving = true;
    }

    private void jump() {
		if(inAir)
			return;
		
		inAir = true;
		airSpeed = jumpSpeed;
		
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	private void updateXPos(float xSpeed) {
    	if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
          hitbox.x += xSpeed;

    	}else {
    		hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
    	}
	}
	
	
	public void changeHealth(int value) {
		currentHealth += value;
		
		if(currentHealth <= 0) {
			currentHealth = 0;
			//gameOver();
		} else if(currentHealth >= maxHealth) {
			currentHealth = maxHealth;
		}
	}
	

	private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[12][80];
        for (int j = 0; j < animations.length; j++) 
            for (int i = 0; i < animations[j].length; i++) 
                animations[j][i] = img.getSubimage(i * 64, j * 64, 64, 64);
            
        statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if(!IsEntityOnFloor(hitbox,lvlData)) {
        	inAir = true;
        }
    }

    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }


    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
    
    public void setJump(boolean jump) {
    	this.jump = jump;
    }

	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking1 = false;
		attacking2 = false;
		attacking3 = false;
		attacking4 = false;
		moving = false;
		playerAction = STOP;
		currentHealth = maxHealth;
		hitbox.x = x;
		hitbox.y = y;
		
		if(!IsEntityOnFloor(hitbox,lvlData)) {
        	inAir = true;
        }
		
	}

	public boolean isAttacking1() {
		return attacking1;
	}

	public void setAttacking1(boolean attacking1) {
		this.attacking1 = attacking1;
	}

	public boolean isAttacking2() {
		return attacking2;
	}

	public void setAttacking2(boolean attacking2) {
		this.attacking2 = attacking2;
	}

	public boolean isAttacking3() {
		return attacking3;
	}

	public void setAttacking3(boolean attacking3) {
		this.attacking3 = attacking3;
	}

	public boolean isAttacking4() {
		return attacking4;
	}

	public void setAttacking4(boolean attacking4) {
		this.attacking4 = attacking4;
	}
	
}
