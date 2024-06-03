package utilz;

import main.Game;

public class Constants {
	
	
	public static class EnemyConstants{
		
		public static final int MARK1 = 0;
	
		public static final int RUNNING = 1;
		public static final int ATTACKING = 2;
		public static final int IDLE = 0;
		public static final int DEAD = 3;
		
		public static final int MARK1_WIDTH_DEFAULT = 64;
		public static final int MARK1_HEIGHT_DEFAULT = 64;
		
		public static final int MARK1_WIDTH = (int)(MARK1_WIDTH_DEFAULT * Game.SCALE);
		public static final int MARK1_HEIGHT = (int)(MARK1_HEIGHT_DEFAULT * Game.SCALE);
		
		public static final int MARK1_DRAWOFFSET_X = (int)(25 * Game.SCALE);
		public static final int MARK1_DRAWOFFSET_Y = (int)(13 * Game.SCALE);
		
		public static int GetSpriteAmount(int enemy_type, int enemy_state) {
			
			switch(enemy_type) {
			case MARK1: 
				switch(enemy_state) {
				case IDLE:
					return 10;
				case RUNNING:
				case ATTACKING:
					return 12;
				case DEAD:
					return 13;
				}
			}
			
			return 0;
		}
		
		public static int GetMaxHealth(int enemy_type) {
			switch(enemy_type) {
			case MARK1:
				return 20;
			default: 
				return 1;
				
			}
		}
		
		public static int GetEnemyDmg(int enemy_type) {
			switch(enemy_type) {
			case MARK1:
				return 15;
			default: 
				return 0;
				
			}
		}
		
	}
	
	public static class UI{
			public static class Buttons {
				public static final int B_WIDTH_DEFAULT = 140;
				public static final int B_HEIGHT_DEFAULT = 56;
				public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT * Game.SCALE);
				public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT * Game.SCALE);
			}
			
			public static class PauseButtons{
				public static final int SOUND_SIZE_DEFAULT = 64;
				public static final int SOUND_SIZE = (int)(SOUND_SIZE_DEFAULT * Game.SCALE);
			}
			
			public static class URMButtons {
				public static final int URM_DEFAULT_SIZE = 64;
				public static final int URM_SIZE = (int)(URM_DEFAULT_SIZE * Game.SCALE);
			}
			
			public static class VolumeButtons{
				public static final int VOLUME_DEFAULT_WIDTH = 32;
				public static final int VOLUME_DEFAULT_HEIGHT = 32;
				public static final int SLIDER_DEFAULT_WIDTH = 192;
				public static final int SLIDER_DEFAULT_HEIGHT = 32;
 				
				public static final int VOLUME_WIDTH = (int)(VOLUME_DEFAULT_WIDTH * Game.SCALE);
				public static final int VOLUME_HEIGHT = (int)(VOLUME_DEFAULT_HEIGHT * Game.SCALE);
				public static final int SLIDER_WIDTH = (int)(SLIDER_DEFAULT_WIDTH * Game.SCALE);
				public static final int SLIDER_HEIGHT = (int)(SLIDER_DEFAULT_HEIGHT * Game.SCALE);
			}
	}
	
	public static class Directions{
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
		
		
	}

	public static class PlayerConstants{
		public static final int RUNNING = 0;
		public static final int STOP = 1;
		public static final int JUMP = 2;
		public static final int FALLING = 3;
		public static final int HURT = 4;
		public static final int POWER1 = 5;
		public static final int POWER2 = 6;
		public static final int POWER3 = 7;
		public static final int POWER4 = 8;
		public static final int FASTPOWER = 9;
		public static final int FASTPOWER4 = 10;
		public static final int DEAD = 11;
		
		public static int GetSpriteAmount(int player_action) {
			
			switch(player_action) {
				case RUNNING:
					return 4;
				case STOP:
				case JUMP:
					return 3;
				case FALLING:
					return 10;
				case HURT: 
					return 6;
				case POWER1: 
					return 25;
				case POWER2:
					return 38;
				case POWER3:
					return 34;
				case POWER4:
					return 29;
				case FASTPOWER:
					return 9;
				case FASTPOWER4:
					return 6;
				case DEAD: 
					return 80;
				default: 
					return 1;
			}
		}
		
	}
	
	public static class MapConstants{
		public static final int CRIO = 0;
		public static final int COMP = 1;
		public static final int DOOR = 2;
		public static final int ROBOT = 3;
		public static final int WINDOW = 4;
		public static final int CRIOFALL = 5;
		public static final int LAMP = 6;
		public static final int ROBOTPLAT = 7;
		
		public static int GetSpriteAmount(int map_action) {
			
			switch(map_action) {
				case CRIO:
				case COMP:
				case DOOR:
					return 8;
				case ROBOT:
					return 52;
				case CRIOFALL: 
					return 73;
				case WINDOW:
					return 12;
				case LAMP:
					return 9;
				case ROBOTPLAT:
					return 1;
				default: 
					return 1;
			}
		}
		
	}
	
	
}
