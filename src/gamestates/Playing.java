package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import entities.EnemyManager;
import entities.Player;
import gamestates.Gamestate;
import levels.LevelBackgroundAni;
import levels.LevelManager;
import main.Game;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;

public class Playing extends State implements StateMethods {
	
	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private LevelBackgroundAni levelBackgroundAni;
	private boolean paused = false;
	private PauseOverlay pauseOverlay;
	private GameOverOverlay gameOverOverlay;
	private LevelCompletedOverlay levelCompletedOverlay;
	
	private int xLvlOffSett;
	private int leftBorder = (int)(0.2 * Game.GAME_WIDTH);
	private int rightBorder  = (int)(0.8 * Game.GAME_WIDTH);
	private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
	private int maxTilesOffSet = lvlTilesWide - Game.TILES_IN_WIDTH;
	private int maxLvlOffSetX = maxTilesOffSet * Game.TILES_SIZE;
	
	private boolean gameOver;
	private boolean lvlCompleted = true;
	
	
	public Playing(Game game) {
		super(game);
		initClasses();

	}
	
	private void initClasses() {
		levelBackgroundAni = new LevelBackgroundAni();
		levelManager = new LevelManager(game);
		enemyManager = new EnemyManager(this);
		player = new Player(190, (int)(Game.GAME_HEIGHT / 1.952), (int)(128 * Game.SCALE), (int)(128 * Game.SCALE), this);
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
		levelCompletedOverlay = new LevelCompletedOverlay(this);
	}
	
	@Override
	public void update() {
		if(paused) {
			pauseOverlay.update();
		} else if (lvlCompleted) {
			levelCompletedOverlay.update();
		} else if(!gameOver) {
			levelManager.update();
			levelBackgroundAni.update();
			player.update();
			enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
			
			checkCloseToBorder();
		}
					
	}

	private void checkCloseToBorder() {
		int playerX = (int)(player.getHitBox().x);
		int diff = playerX - xLvlOffSett;
		
		if(diff > rightBorder)
			xLvlOffSett += diff - rightBorder;
		else if(diff < leftBorder)
			xLvlOffSett += diff - leftBorder;
		if(xLvlOffSett > maxLvlOffSetX)
			xLvlOffSett = maxLvlOffSetX;
		else if(xLvlOffSett < 0)
			xLvlOffSett = 0;
	}

	@Override
	public void draw(Graphics g) {
		levelManager.draw(g, xLvlOffSett);
		levelBackgroundAni.render(g);
		player.render(g, xLvlOffSett);
		enemyManager.draw(g , xLvlOffSett);
		
		if(paused)
			pauseOverlay.draw(g);
		
		else if(gameOver) {
			gameOverOverlay.draw(g);
		} else if(lvlCompleted)
			levelCompletedOverlay.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!gameOver) {
		if(paused)
			pauseOverlay.mousePressed(e);
		else if(lvlCompleted)
			levelCompletedOverlay.mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!gameOver) {
		if(paused)
			pauseOverlay.mouseReleased(e);
		else if(lvlCompleted)
			levelCompletedOverlay.mouseReleased(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!gameOver) {
		if(paused)
			pauseOverlay.mouseMoved(e);
		else if(lvlCompleted)
			levelCompletedOverlay.mouseMoved(e);
		}
		
	}
	
	public void mouseDragged(MouseEvent e) {
		if(!gameOver)
		if(paused)
			pauseOverlay.mouseDragged(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(gameOver)
			gameOverOverlay.KeyPressed(e);
		else
		switch(e.getKeyCode()) {

		case KeyEvent.VK_LEFT:
			player.setLeft(true);
			break;
		case KeyEvent.VK_RIGHT:
			player.setRight(true);
			break;
		case KeyEvent.VK_Q:
			player.setAttacking1(true);
			break;
		case KeyEvent.VK_W:
			player.setAttacking2(true);
			break;
		case KeyEvent.VK_E:
			player.setAttacking3(true);
			break;
		case KeyEvent.VK_R:
			player.setAttacking4(true);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(true);
			break;
		case KeyEvent.VK_ESCAPE:
			paused = !paused;
			break;
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!gameOver)
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			player.setLeft(false);
			break;
		case KeyEvent.VK_RIGHT:
			player.setRight(false);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(false);
			break;
			
			
	}
		
	}
	
	public void unpauseGame() {
		paused = false;
	}
	
	
	public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	
	public Player getPlayer() {
		return player;
	}

	public void resetAll() {
		// TODO: RESET ALL
		gameOver = false;
		paused = false;
		player.resetAll();
		enemyManager.resetAllEnemies();
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	

	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyManager.checkEnemyHit(attackBox);
		
	}
}
