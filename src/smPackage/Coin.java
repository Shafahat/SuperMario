package smPackage;

import acm.graphics.GImage;

public class Coin {
	
	/**
	*Draws a coin and scales it and then set its location properly
	*@param  
	*@x: The x axis location of coin being drawn
	*/
	public static GImage drawCoin(double x){
		GImage coin = new GImage("image/coin.png");
		coin.setLocation(x, Game.enemies[Game.currentEnemy].getY()-10);
		coin.scale(0.2);
		return coin;	
	}
	
	/**
	 * Moves all the coins while the mario is running
	 */
	public static void moveAllCoins(){
		Thread mAC = new Thread(new Runnable(){
			public void run(){
				for(int i=0; i<Game.coins.length; i++){
					Game.coins[i].move(-Game.v, 0);
				}
			}
		});
		mAC.start();	
	}
	
	/**
	 * Lets mario pick up the current coin in front of it, then look for the other one
	 */
	public static void pickUpCoin(){
		if(Enemy.isCrossing(Game.coins[Game.currentCoin])){
			Game.coins[Game.currentCoin].setVisible(false);
			if(Game.currentCoin<49){
				Game.currentCoin++;
				Game.coinCounts++;
			}
			Game.coinCount.setLabel(Integer.toString(Game.coinCounts));
		}else if(Enemy.isJumpingOver(Game.coins[Game.currentCoin])){
			if(Game.currentCoin<49){
				Game.currentCoin++;
			}
		}
	}
}
