package smPackage;

import acm.graphics.GImage;

public class Mario {
	public static GImage drawMario(){
		GImage ma = new GImage(Game.marioRight);
		ma.scale(0.4);
		ma.setLocation(0, Game.groundLevel);
		return ma;
	}
	
	/**
	 * Moves mario forward and if reaches the middle it starts to move the ground and others instead
	 */
	public static void marioMoveForward(){
		Game.isRunning = true;
		jumpToGround();	//if needed it jumps to ground(if mario is on top of a pipe)
		Game.updateScore(Game.v);  //updates the score as it goes
		if(Game.mario.getX()<300){  //if mario does not pass the middle of the frame width, the mario moves	
			if(Game.a==0)Game.v=3;	 
			if(Game.a<0.5){    // all the speed and acceleration things
				Game.a+=0.05;
				Game.v+=Game.a;
			}	
			Game.jumpSpeed=Game.v;
			Game.mario.move(Game.v, 0);
			marioAnimRight(Game.animIndex);
			Game.animIndex++;
			if(Game.mario.getX()>Game.pipes[Game.c].getX()+Game.pipes[Game.c].getWidth()){
				Game.c++;
			}
			if(Game.animIndex>2) Game.animIndex=0;
		}
		else{
			if(Game.a==0)Game.v=6;
			if(Game.a<0.5){
				Game.a+=0.05;
				Game.v+=Game.a;
			}	
			Game.jumpSpeed=Game.v;
			Game.ground.move(-Game.v, 0);
			Pipe.moveAllPipes();
			Game.sky.move(-Game.v/8, 0);
			Enemy.moveAllEnemies();
			Coin.moveAllCoins();
			marioAnimRight(Game.animIndex);
			Game.animIndex++;
			if(Game.mario.getX()>Game.pipes[Game.c].getX()+Game.pipes[Game.c].getWidth()){
				Game.c++;
			}
			if(Game.animIndex>2) Game.animIndex=0;
		}
	}
	
	/**
	 * moves mario backwards
	 * Few of the codes are similar to marioMoveForward 
	 */
	public static void marioMoveBackward(){
		Game.isRunning=true;
		jumpToGround();
		if(Game.mario.getX()>=0){
			if(Game.a==0)Game.v=-6;
			if(Game.a<0.5){
				Game.a+=0.05;
				Game.v-=Game.a;
			}
			Game.jumpSpeed=Game.v;
			Game.mario.move(Game.v, 0);
			marioAnimLeft(Game.animIndex);
			Game.animIndex++;
			if(Game.animIndex>2) Game.animIndex=0;
		}
	}
	
	/**
	 * Sets image according to the array index given 
	 * @param index: is the array index which will be the addresses of different pictures which leads to animation
	 */
	public static void marioAnimRight(int index){
		Game.mario.setImage(Game.marioMoveRight[index]);
		resize(Game.marioScale);
	}
	public static void marioAnimLeft(int index){
		Game.mario.setImage(Game.marioMoveLeft[index]);
		resize(Game.marioScale);
	}
	
	/**
	 * Scales the mario according to given scale 
	 * @param scale: the scale given
	 */
	public static void resize(double scale){
		Game.mario.scale(scale);
	}
	
	/** 
	 * This method helps the mario to jump to ground if it is on top of pipe and move away from it
	 */
	public static void jumpToGround(){
		if(!Pipe.isUnderMario(Game.pipes[Game.currentPipe]) && Game.isJumping==false) Game.mario.setLocation(Game.mario.getX(), Game.groundLevel);
	}
}
