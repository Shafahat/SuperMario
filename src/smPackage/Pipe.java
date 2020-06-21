package smPackage;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class Pipe {
	public static GImage drawPipe(double x){
		GImage pipe = new GImage("image/pipet.png");
		pipe.setLocation(x, Game.HEIGHT-220);
		pipe.scale(0.4);
		return pipe;
	}
	
	/**
	 * Moves all the pipes around while mario running
	 */
	public static void moveAllPipes(){
		Thread mAP = new Thread(new Runnable(){
			public void run(){
				for(int i=0; i<Game.pipes.length; i++){
					Game.pipes[i].move(-Game.v, 0);
				}
			}
		});
		mAP.start();	
	}	
	
	/**
	 * Checks if a certain object is under mario's feet
	 */
	public static boolean isUnderMario(GObject object){
		if(object.getX()-30<Game.mario.getX() && object.getX()+object.getWidth()>Game.mario.getX() && object.getY()-75<Game.mario.getY()) return true;
		return false;
	}
}
