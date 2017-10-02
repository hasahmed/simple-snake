package com.hasahmed.snakegame;

import java.awt.*;
import java.awt.event.*; 

interface World  {
  public void draw(Graphics g);
  public void update(); 
  public void keyPressed(KeyEvent e);
  public void mousePressed(MouseEvent e); 
}
