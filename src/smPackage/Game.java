package smPackage;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Random;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;

public class Game extends GraphicsProgram {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 1024;
	static final int HEIGHT = 600;
	public final int LEVEL_LENGTH = 10000; 
	public static double groundLevel;   // the level at which mario starts
	static GObject[] pipes = new GObject[15];
	static GImage[] coins = new GImage[100];
	static GImage[] enemies = new GImage[10]; 
	
	public void init(){
		setSize(WIDTH, HEIGHT);
		
		marioMoveRight[0] = "image/marioright.png";         //These are all the adresses of mario states
		marioMoveRight[1] = "image/mariom1right.png";
		marioMoveRight[2] = "image/mariom2right.png";
		marioMoveLeft[0] = "image/marioleft.png";
		marioMoveLeft[1] = "image/mariom1left.png";
		marioMoveLeft[2] = "image/mariom2left.png";
		
		pipes = new GObject[17];
		
		groundLevel = getHeight() - 215;
		
		while(skyX<=LEVEL_LENGTH){
			sky.add(Environment.drawSky(skyX));   //firstly we draw a sky until we reach level length 
			skyX+=76.8;   //this is the width of the sky actually, everytime we add a new skyPart we add it on updated X which is skyX 
		}
		while(cloudX<=LEVEL_LENGTH){					//the same way we add clouds
			sky.add(Environment.drawCloud(cloudX));
			cloudX+=150;
		}
		add(sky);
		
		while(groundX<LEVEL_LENGTH){
			ground.add(Environment.drawGround(groundX, getHeight()-149));
			groundX+=76.8;
		}
		add(ground);
		
		int x=0;
		for(int i=0; i<pipes.length; i++){
			x+= 400+r.nextInt(400);
			pipes[i] = Pipe.drawPipe(x);
			add(pipes[i]);
		}
		
		int k=0;
		for(int i=0; i<lives.length; i++){
			lives[i] = new GImage("image/lives.jpg");
			lives[i].scale(0.1);
			lives[i].setLocation(20+k, 20);
			add(lives[i]);
			k+=40;
		}
		
		scoreBoard = new GLabel(Integer.toString(score));
		scoreBoard.setFont("Monospaced-Bold-40");
		scoreBoard.setLocation(getWidth()-150, 50);
		scoreBoard.setColor(Color.BLACK);
		add(scoreBoard);
		
		GImage coinMark = new GImage("image/coin.png");
		coinMark.setLocation(getWidth()-450, 20);
		coinMark.scale(0.2);
		add(coinMark);
		
		coinCount = new GLabel(Integer.toString(currentCoin));
		coinCount.setFont("Monospaced-Bold-40");
		coinCount.setLocation(getWidth()-400, 50);
		coinCount.setColor(Color.black);
		add(coinCount);
		
		mario = Mario.drawMario();
		add(mario);
		
		int t=0;
		for(int i=0; i<enemies.length; i++){
			enemies[i] = Enemy.drawEnemy(400+t);
			t+= 700 + r.nextInt(700);
			add(enemies[i]);
		}
		
		int p=0;
		for(int i=0; i<coins.length; i++){
			coins[i] = Coin.drawCoin(200+p);
			p+=90+r.nextInt(100);
			add(coins[i]);
		}
		
		enemyMove.start();
		coinDetection.start();
	
		addKeyListeners();
	}
	
	Thread coinDetection = new Thread(new Runnable(){
		public void run(){
			while(!gameOver()){
				pause(100);
				Coin.pickUpCoin();
			}
		}
	});
	
	Thread enemyMove = new Thread(new Runnable(){
		public void run(){
			while(!gameOver()){
				pause(40);
				Enemy.moveAround(2);
			}
		}
	});
	
	public void keyPressed(KeyEvent e){
		if(gameOver()==false){
				if(Enemy.isJumpingOver(pipes[currentPipe]) && currentPipe<pipes.length-1) currentPipe++;
				if(Enemy.isJumpingOver(enemies[currentEnemy]) && currentEnemy<enemies.length-1) currentEnemy++;
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					isRightDir=true;
					if(!Enemy.isCrossing(pipes[currentPipe])){
						Mario.marioMoveForward();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					isRightDir=false;
					if(!Enemy.isCrossing(pipes[currentPipe])){
						Mario.marioMoveBackward();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_UP){
					Sound.play("sound/mrj.wav");
					lastDir = mario.getImage();
					mario.setImage(marioJump);
					Mario.resize(marioScale);
					jumpSpeed=v;
					Thread jump = new Thread(new Runnable(){
						public void run(){
								marioJump();
							}
						});jump.start();
				}
		}else {
			setSize(0, 0);
			new GameOver().start();
		}
	}

	public void keyReleased(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			mario.setImage(marioRight);
			Mario.resize(marioScale);
			animIndex=0;
			v=0;
			a=0;
			isRunning=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			mario.setImage(marioLeft);
			Mario.resize(marioScale);
			animIndex=0;
			v=0;
			a=0;
			isRunning=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_UP){
			mario.setImage(lastDir);
			Mario.resize(marioScale);
		}
	}
	
	public void marioJump(){
		isJumping = true;
		if(mario.getX()<300){
			for(int gforce=0; gforce<=30; gforce++){
				pause(40);
				if(isRunning=true){
					mario.move(jumpSpeed, -15+gforce);
				}
			    else mario.move(0, -15+gforce);
				if(Pipe.isUnderMario(pipes[currentPipe])) break;
			}
		}else for(int gforce=0; gforce<=30; gforce++){
				pause(40);
					if(isRunning==true){
						mario.move(0, -15+gforce);
							if(jumpSpeed>0){
								ground.move(-jumpSpeed, 0);
								Pipe.moveAllPipes();
								Enemy.moveAllEnemies();
								sky.move(-jumpSpeed/8, 0);
								Coin.moveAllCoins();
							}
					}else mario.move(0, -15+gforce);
					if(Pipe.isUnderMario(pipes[currentPipe])) break;
			 } isJumping = false;
	}
	
	public static void updateScore(double x){
			score+=x;
			scoreBoard.setLabel(Integer.toString(score));
	}
	
	public static boolean gameOver(){
		if(lives[0].isVisible()) return false;
		return true;
	}
	
	static GImage[] lives = new GImage[5];
	static int currentLive = 5;
	static int currentCoin = 0;
	static int coinCounts = 0;
	static int currentPipe = 0;
	static int currentEnemy = 0;
	static GLabel coinCount;
	public static Random r = new Random();
	 static int animIndex=0; 
	static GCompound sky = new GCompound();
	static GCompound ground = new GCompound();
	static GImage mario;
	static double marioScale=0.4;
	static String[] marioMoveRight = new String[3];
	static String[] marioMoveLeft = new String[3];
	static String marioRight = "image/marioright.png";
	static String marioLeft = "image/marioleft.png";
	static String marioJump = "image/mariojump.png";
	private static GLabel scoreBoard;
	static int score = 0;
	static int powerUpDur = 0;
	private double groundX=0;
	private double skyX=0;
	private double cloudX=0;
	 static double v=0;
	 static double a=0;
	 static int c = 0;
	 static boolean isRunning = false;
	 static boolean isJumping = false;
	 static double jumpSpeed=7;
	 Image lastDir;
	 static boolean isRightDir = true;
}