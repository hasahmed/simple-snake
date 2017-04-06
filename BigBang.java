import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class BigBang extends JComponent implements KeyListener, ActionListener, MouseListener, Runnable {
  Timer timer;
  Thread thread;
  World world;

  BigBang(int delay, World world) {
    timer = new Timer(delay, this); 
    this.world = world;
  } 
  public void run(){
      while(true){
        this.repaint();
        try{
            thread.sleep(10);
        }
        catch(InterruptedException e){
            System.out.println("Something happened with the thread");
        }
      }
  }
  public void start(){
      thread = new Thread(this, "Snake : Repaint");
      thread.start();
  }
  public void begin() {
    timer.start();  
    start();
  }
  BigBang(World world) {
    this(1000, world);
  }
  public void paintComponent(Graphics g) {
    world.draw(g);  
  }
  public void actionPerformed(ActionEvent e) {
    world.update(); 
    if (world.hasEnded()) timer.stop(); 
  //  this.repaint(); 
  }
  public void keyPressed(KeyEvent e) {
    world.keyPressed(e); 
    //this.repaint(); 
  } 
  public void keyTyped(KeyEvent e) { } 
  public void keyReleased(KeyEvent e) { } 

  public void mousePressed(MouseEvent e) { 
    world.mousePressed(e); 
    this.repaint(); 
  }
  public void mouseReleased(MouseEvent e) { } 
  public void mouseClicked(MouseEvent e) { } 
  public void mouseEntered(MouseEvent e) { } 
  public void mouseExited(MouseEvent e) { } 

}
