package smPackage;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import javax.swing.JButton;

import acm.graphics.GImage;

import acm.program.GraphicsProgram;

public class Menu extends GraphicsProgram {

  private static final long serialVersionUID = 1L;
  public static final int menuWidth = 784;
  public static final int menuHeight = 650;

  public void init(){
    setSize(menuWidth, menuHeight);
    add(new JButton("START"), BorderLayout.SOUTH);
    add(new JButton("EXIT"), BorderLayout.SOUTH);
    addActionListeners();
    GImage background = new GImage("image/menuback.jpg");
    background.scale(0.6);
    add(background);
  }

  public void actionPerformed(ActionEvent e) {

    String cmd = e.getActionCommand();

    if (cmd.equals("START")) {
      setSize(0, 0);
      new Game().start();
    }
    if (cmd.equals("EXIT")) exit();
  }

  public static void main(String[] args) {
    new Menu().start();

  }
}
