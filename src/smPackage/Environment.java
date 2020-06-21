package smPackage;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRect;

public class Environment {
	//all these methods helps to draw ground , sky, and the clouds(clouds will be thrown to canvas kinda  randomly)
	
	public static GImage drawGround(double x, double y){
		GImage groundPart = new GImage("image/ground.jpg", x, y);
		groundPart.scale(0.15);
		return groundPart;
	}
	
	public static GRect drawSky(double x){
		GRect skyPart = new GRect(x, 0, 76.8, Game.HEIGHT);
		skyPart.setFilled(true);
		skyPart.setColor(new Color(63, 191, 255));
		return skyPart;
	}
	
	public static GImage drawCloud(double x){
		GImage cloud = new GImage("image/cloud.png", x+Game.r.nextInt(90), Game.HEIGHT/4-Game.r.nextInt(90));
		cloud.scale(1);
		return cloud;
	}
}
