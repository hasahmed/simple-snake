import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;


public class Snake implements World{
  static String version = "1.0.3";
  boolean displayStartScreen = true;
  int printNum = 0;
  static int PLAY_AREA_X = 400;
  static int PLAY_AREA_Y = 400;
  static JFrame frame;
  static BigBang game;
  static int preLoop = 10010;
  private int STARTING_LENGTH = 5;
  boolean paused = false;
  boolean endCalled = false; // keep end() from being called every tic
  Point speed = new Point(0, 0);
  int RADIUS = 5;
  int FOOD_GROWTH = 3;
  static Integer resetSpeed = 75;
  int INCVAL = RADIUS * 2;
  static int HEIGHT = 432;
  static int WIDTH = 410;
  Color BODY_COLOR = Color.BLACK;
  Color FOOD_COLOR = Color.ORANGE;
  boolean drawMode = false;
  boolean gameOver = false;
  Score score = new Score();
  Body body = new Body(RADIUS, BODY_COLOR, STARTING_LENGTH);
  Food food = new Food(FOOD_COLOR, body);
  Circle head;
  Point saveSpeed;
  int updateFuncCallCount = 0;

  int scoreWrittenToFile = WriteScore.getScoreFromFile();
  
  void reset(){
  endCalled = false;
  displayStartScreen = true;
  Globals.setDir(Globals.Dir.STOP);
  drawMode = false;
  gameOver = false;
  score = new Score();
  body = new Body(RADIUS, BODY_COLOR, 5);
  food = new Food(FOOD_COLOR, body);
  scoreWrittenToFile = WriteScore.getScoreFromFile();
  }
  
 //update
   public void update() {
       Globals.setDir(Globals.dirls[0]);
       Globals.chopHead();

//variables
    head = body.get(0);
    Globals.keyOpp = 0;
    if (head.isOutOfBounds() || body.headOverlappingBody()){
        body.redHead();
        gameOver = true;
        if (!endCalled) end();
    }
//updatePos
    if (!paused && !gameOver && Globals.getDir() != Globals.Dir.STOP) body.updatePos();

//drawMode
    if(drawMode) this.body.addCircle();

//foodCollision
    if(food.isOverlappingHead(head)){
        body.addCircle(FOOD_GROWTH);
        food.moveToRandomPos();
        score.addTo();
        while(food.hittingBody()) food.moveToRandomPos();
    }
} // end update

//draw
  public void draw(Graphics g){

      
      if (displayStartScreen){
          Frills.drawStartScreen(g);
      }
      if(!displayStartScreen)food.draw(g);
    Frills.drawBorder(g);
    body.draw(g);
    if (gameOver){
        if (score.getScore() > scoreWrittenToFile){
            int oldScore = scoreWrittenToFile;
            WriteScore.drawNewHighScoreMessage(g, oldScore, body.size());
        }
        else{
            WriteScore.drawNoHighScore(g, score.getScore(), scoreWrittenToFile, body.size());
        }
    }
  score.draw(g);
  if (paused) Frills.drawPauseGraphics(g);

  }//end draw

//notUsed
  public void mousePressed(MouseEvent e) {}

  public void keyPressed(KeyEvent e) {
    if (!gameOver && Globals.keyOpp < 2){

      if (e.getKeyCode() == KeyEvent.VK_RIGHT){
          Globals.add(Globals.Dir.RIGHT);
          if(Globals.getDir() == Globals.Dir.LEFT) 
            if(Globals.dirls[0] == Globals.Dir.RIGHT){
                Globals.chopHead();
            }
      }
      else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            Globals.add(Globals.Dir.LEFT);
            if(Globals.getDir() == Globals.Dir.RIGHT)
                if(Globals.dirls[0] == Globals.Dir.LEFT){
                    Globals.chopHead();
                }
      }
      
      else if (e.getKeyCode() == KeyEvent.VK_UP){
            Globals.add(Globals.Dir.UP);
            if(Globals.getDir() == Globals.Dir.DOWN && Globals.dirls[0] == Globals.Dir.UP) Globals.chopHead();
      }
      else if (e.getKeyCode() == KeyEvent.VK_DOWN){
        if (Globals.dirls[Globals.keyOpp] != Globals.Dir.UP && Globals.getDir() != Globals.Dir.STOP)
            Globals.add(Globals.Dir.DOWN);
            if(Globals.getDir() == Globals.Dir.UP && Globals.dirls[0] == Globals.Dir.DOWN) Globals.chopHead();
      }
      else if (e.getKeyCode() == KeyEvent.VK_P && !displayStartScreen){
              if (!paused){
                  body.redHead();
                  saveSpeed = Globals.getSpeed();
                  Globals.saveSpeed = Globals.getDir().value;
                  paused = true;
                  Globals.setDir(Globals.Dir.STOP);
          }
          else{
               body.blackHead();
               paused = false;
               Globals.speed.setLocation(saveSpeed);
          }
      }
      else if (e.getKeyCode() == KeyEvent.VK_Q){
          frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
      }
      else if (e.getKeyCode() == KeyEvent.VK_D){
      }
      else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
          frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
    } // if (!gameOver && Globals.keyOpp < 1) end

    else if (e.getKeyCode() == KeyEvent.VK_R){
        reset();
    }
    
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE ||
            e.getKeyCode() == KeyEvent.VK_Q
            ){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
    if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_LEFT
            || e.getKeyCode() == KeyEvent.VK_RIGHT){
        displayStartScreen = false;
    }
  } // end keyPressed
  
  void end(){
      endCalled = true;
      WriteScore.writeToFile(score.getScore().toString());
  }
  
    public boolean hasEnded() {
      return false; // never!! 
  }
    void init(){
        update();
        body.headOverlappingBody();
        head.isOutOfBounds();
    }
    
    public static void main(String[] args) throws IOException {
      try {
        if(args.length > 0){
            if(args[0].compareTo("--version") == 0){
                System.out.println(version);
                return;
            }
            else{
                resetSpeed = Integer.parseInt(args[0]);
            }
        }
      }
      catch(ArrayIndexOutOfBoundsException e){
        //do nothing
      }
        WriteScore.init();
        System.setProperty("sun.java2d.opengl", "True");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
      
      //BigBang 
      Snake s = new Snake();
        game = new BigBang(resetSpeed, s);
      //JFrame
      frame = new JFrame("Snake");
      frame.getContentPane().add(game);
      frame.getContentPane().setBackground(Color.GRAY);
      frame.setFocusable(true);
      frame.addKeyListener(game);
      game.addMouseListener(game);
//      frame.setUndecorated(true);
      frame.setVisible(true); 
      frame.setSize(WIDTH, HEIGHT);
      frame.setLocation(dim.width/2 - frame.getSize().width /2, dim.height/2 - frame.getSize().height/2);
      frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      frame.setResizable(false);
      
      for (; preLoop >= 0; preLoop--){
          s.init();
      }
      
      game.begin();
    }
  
}
