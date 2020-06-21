package smPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.program.GraphicsProgram;

public class GameOver extends GraphicsProgram {

  private static final long serialVersionUID = 1L;
  public static final int menuWidth = 784;
  public static final int menuHeight = 650;

  public void init(){
    setSize(menuWidth, menuHeight);
    add(new JButton("REPLAY"), BorderLayout.SOUTH);
    add(new JButton("EXIT"), BorderLayout.SOUTH);
    addActionListeners();
    
    gameOverL = new GLabel("GAME OVER");
    gameOverL.setLocation(menuWidth/4, -60);
    gameOverL.setFont("Monospaced-Bold-60");
    gameOverL.setColor(Color.BLACK);
    add(gameOverL);
    
    score = new GLabel("Score: " + Game.score);
    score.setLocation(-200, menuHeight/2);
    score.setFont("Monospaced-Bold-30");
    score.setColor(Color.WHITE);
    add(score);
    
    coinScore = new GLabel("Coins: " + Game.coinCounts);
    coinScore.setLocation(menuWidth, menuHeight/2);
    coinScore.setFont("Monospaced-Bold-30");
    coinScore.setColor(Color.YELLOW);
    add(coinScore);
    
    deadMario = new GImage("image/dead.png");
    deadMario.setLocation(menuWidth/2-50, menuHeight-240);
    deadMario.scale(0.3);
    add(deadMario);
    
    Sound.play("sound/gameover.wav");
    
    textAnim.start();
    deadJump.start();
    changeColor.start();
  }

  /**
   * moves all the texts
   */
  Thread textAnim = new Thread(new Runnable(){
	  public void run(){
		  for(int i=0; i<300; i++){
			  pause(5);
			  gameOverL.move(0, 1);
		  }
		  for(int i=0; i<200; i++){
			  pause(5);
			  score.move(2, 0);
		  }
		  for(int i=0; i<170; i++){
			  pause(5);
			  coinScore.move(-2, 0);
		  }
	  }
  });
  
  Thread changeColor = new Thread(new Runnable(){
	  public void run(){
		  for(int i=x; i>0; i--){
			  pause(8);
			  x--;
			  back = new Color(255, x, 45);
			  setBackground(back);
		  }
	  }
  });
  
  Thread deadJump = new Thread(new Runnable(){
	  public void run(){
		  for(int gforce=0; gforce<70; gforce++){
			  pause(40);
			  deadMario.move(0,-20+gforce);
		  }
	  }
  });
  
  public void actionPerformed(ActionEvent e) {

    String cmd = e.getActionCommand();

    if (cmd.equals("REPLAY")) {
      setSize(0, 0);
      Game.currentCoin = 0;
      Game.score = 0;
      new Game().start();
    }
    if (cmd.equals("EXIT")) exit();
    
  }
  
  private GLabel score;
  private GLabel coinScore;
  private GImage deadMario;
  private GLabel gameOverL;
  private int x=255;
  private Color back;
}