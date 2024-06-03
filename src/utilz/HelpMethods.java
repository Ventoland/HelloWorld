package utilz;

import java.awt.geom.Rectangle2D;

import main.Game;

public class HelpMethods {

	
	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
	    if (IsSolid(x, y, lvlData)) {
	        return false;
	    }
	    if (IsSolid(x + width, y, lvlData)) {
	        return false;
	    }
	    if (IsSolid(x, y + height, lvlData)) {
	        return false;
	    }
	    if (IsSolid(x + width, y + height, lvlData)) {
	        return false;
	    }
	    return true;
	}

	
	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * Game.TILES_SIZE;
		if(x < 0 || x >= maxWidth) {
			return true;
		}
		if(y < 0 || y >= Game.GAME_HEIGHT) {
			return true;
		}
		
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y /  Game.TILES_SIZE;
		
		return IsTileSolid((int)xIndex,(int)yIndex, lvlData);
	
 }
	
	public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
		int value = lvlData[yTile][xTile];
		
		if(value >= 12 || value < 0 || value != 4) 
			return true;
		return false;
	}
	
	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
	    int currentTile = (int) (hitbox.x / Game.TILES_SIZE);

	    if (xSpeed > 0) {
	        // Right
	        int tileXPos = (currentTile + 1) * Game.TILES_SIZE;
	        return tileXPos - hitbox.width - 1;
	    } else {
	        // Left
	        return currentTile * Game.TILES_SIZE;
	    }
	}

	
	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int)(hitbox.y / Game.TILES_SIZE);
		if(airSpeed > 0) {
			//Falling - Touching floor
			int tileYPos = (int)(currentTile * 45.75);
			int yOffSet = (int)(Game.TILES_SIZE - hitbox.height);
			return tileYPos + yOffSet - 1;
		} else {
//			//Jumping
 			return currentTile * Game.TILES_SIZE;
		}
		
	}
	
	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
	    // Check the pixel below bottom left or bottom right
	    if(!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
	    	if(!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
	    		return false;
	    
	    return true;
	}
	
	
	public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
		if(xSpeed > 0)
			return IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData);
		else
		return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
		
	}
	
	
	
	public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
		for(int i = 0; i < xEnd - xStart; i++) {
			if(IsTileSolid(xStart + i, y, lvlData)) 
				return false;
			if(!IsTileSolid(xStart + i, y + 1, lvlData)) 
				return false;
			
		}
		return true;
	}

	public static boolean isSightClear(int[][] lvlData, Rectangle2D.Float firstHitBox, Rectangle2D.Float secondHitBox, int yTile) {
		int firstXTile = (int)(firstHitBox.x / Game.TILES_SIZE);
		int secondXTile = (int)(firstHitBox.x / Game.TILES_SIZE);
		
		if(firstXTile > secondXTile) 
			return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
		 else 
			return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);	
	}
	

	
}