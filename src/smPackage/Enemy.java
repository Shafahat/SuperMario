package smPackage;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class Enemy{
	
	public static GImage drawEnemy(double x){
		GImage ene = new GImage("image/mushroom.png");
		ene.scale(0.2);
		ene.setLocation(x, Game.mario.getY()+35);
		return ene;
	}
	
	/**
	 * moves the enemies as mario moves 
	 */
	public static void moveAllEnemies(){
		Thread mAE = new Thread(new Runnable(){
			public void run(){
				for(int i=0; i<Game.enemies.length; i++){
					Game.enemies[i].move(-Game.v, 0);
				}
			}
		});mAE.start();
	}
	
	/**
	 * This method helps the enemies to move around slowly
	 * @param speed: is the speed that enemy gonna move at
	 */
	public static void moveAround(double speed){
		if(isCrossing(Game.enemies[Game.currentEnemy])){
			if(Game.currentLive>0){	
				if(health==0 || health==5){
					health=5;
					Game.currentLive--;
				}
				health--;
				Game.lives[Game.currentLive].setVisible(false);
			}
		}
		else Game.enemies[Game.currentEnemy].move(speed, 0);
	}
	
	/**
	 * Checks if an object is on the same point as the Mario
	 * @param object: is the object which is gonna be checked if satisfies the condition
	 * @return true if is crossing , false if not
	 */
	public static boolean isCrossing(GObject object){ 
		if(object.getX()-20<Game.mario.getX() && object.getX()+object.getWidth()>Game.mario.getX() &&  object.getY()-Game.mario.getHeight()<Game.mario.getY()) return true;
		return false;
	}
	
	/**
	 * checks if an object is at the same X point with mario but not the Y point
	 * @param object: the object which is gonna be checked
	 * @return true or false
	 */
	public static boolean isJumpingOver(GObject object){
		if(object.getX()+object.getWidth()+20<Game.mario.getX()) return true;
		return false;
	}
	
	private static int health = 5;
}



