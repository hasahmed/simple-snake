// Circle.java 
import java.util.ArrayList;
import java.awt.*; 
import java.util.Random;
import java.util.Arrays;

class Circle {
//instace variables
  int keyOpp = 0;
  Point pos = new Point(0, 0);
  Point speed = new Point(0, -10);
  Point lastPos = new Point(0, 0);
  int radius;
  Color color;
  
  
//constructor 
  Circle(int x, int y, int radius, Color color) {
    this.pos.setLocation(x, y);
    this.radius = radius;
    this.color = color;
    this.lastPos.setLocation(x - this.speed.x, y - this.speed.y);
  }

  Circle(Circle c){
    this.pos.setLocation(c.pos.x, c.pos.y);
    this.radius = c.radius;
    this.color = c.color;
    this.lastPos.setLocation(c.lastPos.x, c.lastPos.y);
  }
  void draw(Graphics g) {
    g.setColor(this.color); 
    g.fillOval(this.pos.x - this.radius, this.pos.y - this.radius, this.radius * 2, this.radius * 2); 
    //g.setColor(this.color); 
    g.setColor(Color.BLACK);
    g.drawOval(this.pos.x - this.radius, this.pos.y - this.radius, this.radius * 2, this.radius * 2); 
  }
    void setPos(int x, int y){
      this.lastPos.x = this.pos.x;
      this.lastPos.y = this.pos.y;
      this.pos.x = x;
      this.pos.y = y;
  }

  boolean isOutOfBounds(){
      int x = this.pos.x;
      int y = this.pos.y;

      if (x > Snake.PLAY_AREA_X - 5 || x < 0 + 10 || y > Snake.PLAY_AREA_Y - 5 || y <= 0 + 5) return true;
      return false;
  }

  void outOfBoundsRePos(){
    if (this.pos.x > 400 - (this.radius * 2))
      this.pos.x = 0 + (this.radius * 2);
    else if (this.pos.y > 400 - (this.radius * 2)){
      this.pos.y = 0 + (this.radius * 2);
    }
    else if (this.pos.y < 0 + (this.radius * 2))
      this.pos.y = 400 - (this.radius * 2);
    else if (this.pos.x < 0 + (this.radius * 2))
      this.pos.x = 400 - (this.radius * 2);
  }
  int getX(){
    return this.pos.x;
  }
  int getY() {
    return this.pos.y;
  }
  Point getPos(){
    return this.pos;
  }
  Point getLastPos(){
    return this.lastPos;
  }  
  // 1. load direction into setNextDir;
  // 2. setNextDir loads into currentDir
  // 3. On UPdate, move head in Dir direction, reset currentDir;
  // 
  String getDir(){
    if(this.speed.y == -10) return "UP";
    else if(this.speed.y == 10) return "DOWN";
    else if(this.speed.x == -10) return "LEFT";
    else if(this.speed.x == 10) return "RIGHT";
    return "NA";
  }
  void stop(){
    this.speed.setLocation(0, 0);
  }
  
  
  
} // end circle 
///////////////////////////////////////////////////////////////////////////////////////////
//BODY//


class Body extends ArrayList<Circle>{
//instance variables
  Circle head;
  Color bodyColor;

  
//constructors
  public Body(int radius, Color color, int length){
    this.bodyColor = bodyColor;
    this.head = new Circle((Snake.PLAY_AREA_X/2) + 15, (Snake.PLAY_AREA_Y /2) + 15, radius, color);
    this.add(head);
    fillWithCircles(length);
  }
///

  
//methods

//fillWithCircles(int numOfCircles)
//fills this (the Body) with a number of circles, and sets their locations such that they correctly stack vertically
//Used at the begining of the game and that is all
  void fillWithCircles(int numOfCircles) {
    for (int i = 1; i < numOfCircles + 1; i++) {
      this.add(new Circle(this.get(i - 1).pos.x, this.get(i - 1).pos.y + this.get(i - 1).radius * 2, head.radius, bodyColor));
    }
  }//end fillWithCircles
  

//addCircle
  void addCircle(){
    this.add(new Circle(this.get(this.size() - 1).pos.x, this.get(this.size() - 1).pos.y, head.radius, bodyColor));
  }

//addCircle
  void addCircle(int num){
    for (int i = 0; i < num; i++){
        this.add(new Circle(this.get(this.size() - 1).pos.x, this.get(this.size() - 1).pos.y, head.radius, bodyColor));
    }
  } //end addCircle


//updatePos(): updates the position of the snake, by adding a new circle to the front of the 
//body, and removing one from the back. 

  void updatePos(){
    Circle ref = this.get(0);
    this.add(0, new Circle(ref.getX() + Globals.speed.x, ref.getY() + Globals.speed.y, ref.radius, ref.color));
    this.remove(this.get(this.size() -1));
  }//end updatePos()

//rainbowUpdatePos();
//this updates the position of the body, the same as updatePos(), but, each new circle it creates has a random
//color
  void rainbowUpdatePos(){
      Random randy = new Random();
      Circle ref = this.get(0);
    //this.add(0, new Circle(ref.getX() + Globals.speed.x, ref.getY() + Globals.speed.y, ref.radius, ref.color));
    //random rainbow colors
    this.add(0, new Circle(ref.getX() + Globals.speed.x, ref.getY() + Globals.speed.y, ref.radius, new Color(randy.nextInt(255),
                    randy.nextInt(255), randy.nextInt(255))));

    this.remove(this.get(this.size() -1));
  }

  
//headOverlappingBody
//
//checks to see if the 0th element in the list is in the same location as the ith element
// in the list. If it is it removes the ith element, so that the redHead() method (turing the
// head of the body red, is visible
  boolean headOverlappingBody(){
    for (int i = 1; i < this.size(); i++) 
        if (this.get(0).pos.equals(this.get(i).pos)){
            //this.remove(i);
            this.get(i).pos = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
            return true;
        }
    return false;
  }//end headOverlappingBody

  void redHead(){
      this.get(0).color = Color.RED;
  }
  void blackHead(){
      this.get(0).color = Color.BLACK;
  }
  void draw(Graphics g){
      for (int i = 0; i < this.size(); i++) this.get(i).draw(g);
  }
} // end circle array

class Food extends Circle{
  Body body;
  Food(Color color, Body b){
    super(0, 0, b.head.radius, color);
    this.moveToRandomPos();
    this.body = b;
    //test trying to work out a bug where the food spawns in the lower left hand corner
    this.pos = new Point(0, 400);
    while(this.isOutOfBounds()) moveToRandomPos();
  }
    boolean isOverlappingHead(Circle h){
    if (this.pos.equals(h.pos)) return true;
    return false;
  }
    void moveToRandomPos(){
      Random randy = new Random();
      int x = randy.nextInt(Snake.PLAY_AREA_X / 10);
      int y = randy.nextInt(Snake.PLAY_AREA_Y / 10);
      this.pos.setLocation((x * 10) + 15, (y * 10) + 15);
      while(this.isOutOfBounds()) moveToRandomPos();
      //if(this.hittingBody())
    }
    
    boolean hittingBody(){
      for(int i = 0; i < this.body.size(); i++){
        if(body.get(i).pos.equals(this.pos)) return true;
      }
      return false;
    }
    
}

class Globals{
  public static Point speed = new Point(0, 0);
  public static int keyOpp = 0;
  static Dir[] dirls = new Dir[2];
  public static String saveSpeed;

  
//add(Dir d)
//adds a Dir d to the first non-null position in dirls
  static void add(Dir d){
      for (int i = 0; i < dirls.length; i++){
          if (dirls[i] == null){
              dirls[i] = d;
              break;
          }
      }
  }

// remove(int i)
// removes the ith element from dirls
  static void remove(int i){
      dirls[i] = null;
  }

//chopHead()
// removes the 0th element of the dirls Array, and moves the 1th element to its place, replacing the 1th 
// element with null
  static void chopHead(){
      dirls[0] = dirls[1];
      dirls[1] = null;
  }
  public static enum Dir{
    UP("UP"), DOWN("DOWN"), LEFT("LEFT"), RIGHT("RIGHT"), STOP("STOP");
    String value;
    private Dir(String v){
        value = v;
    }
  }

  public static void setDir(Dir d){
    keyOpp++;
    if (d == Dir.UP){
        if(Globals.getDir() != Dir.DOWN)
            speed.setLocation(0, -10);
    }

    else if (d == Dir.DOWN){
        if(Globals.getDir() != Dir.UP)
            speed.setLocation(0, 10);
    }

    else if (d == Dir.LEFT) {
        if(Globals.getDir() != Dir.RIGHT)
            speed.setLocation(-10, 0);
    }

    else if (d == Dir.RIGHT) {
        if(Globals.getDir() != Dir.LEFT)
            speed.setLocation(10, 0);
    }

    else if (d == Dir.STOP) speed.setLocation(0, 0);
  }
  public static Dir getDir(){
    if (speed.equals(new Point(0, -10))) return Dir.UP;
    else if (speed.equals(new Point(0, 10))) return Dir.DOWN;
    else if (speed.equals(new Point(-10, 0))) return Dir.LEFT;
    else if (speed.equals(new Point(10, 0))) return Dir.RIGHT;
    return Dir.STOP;
  }
  public static Point getSpeed(){
    if (speed.equals(new Point(0, -10))) return new Point(0, -10);
    else if (speed.equals(new Point(0, 10))) return new Point(0, 10);
    else if (speed.equals(new Point(-10, 0))) return new Point(-10, 0);
    else if (speed.equals(new Point(10, 0))) return new Point(10, 0);
    return new Point(0, 0);
  }
}

class Frills{

    static void drawBorder(Graphics g){
        int BOARDER_WIDTH = 10;
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Snake.WIDTH, BOARDER_WIDTH); //top
        g.fillRect(0, Snake.PLAY_AREA_X, Snake.WIDTH, BOARDER_WIDTH); //bottom?
        g.fillRect(0, 0, BOARDER_WIDTH, Snake.HEIGHT); //left
        g.fillRect(Snake.WIDTH - BOARDER_WIDTH, 0, BOARDER_WIDTH, Snake.HEIGHT); //right
    }
    static void drawStartScreen(Graphics g){
    //TITLE
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(new Font("AndaleMono", Font.BOLD, 40));
        g.setColor(Color.BLACK);
        g2.drawString("SIMPLE SNAKE", 58, 60);
    //INSTRUCTIONS
        g.setFont(new Font("AndaleMono", Font.BOLD, 12));
        g.drawString("ARROW KEY to start", 145, 350);
        g.drawString("ESC to quit", 175, 365);
        g.drawString("P to pause", 175, 380);
    }
    static void drawPauseGraphics(Graphics g){
        String d = Globals.saveSpeed;
        g.setFont(new Font("AndaleMono", Font.BOLD, 20));
        g.setColor(Color.GREEN);
        if (d.equals("UP")){
            g.drawString("\u2191", 25, 35);
        }
        else if(d.equals("DOWN")) g.drawString("\u2193", 25, 35);
        else if(d.equals("LEFT")) g.drawString("\u2190", 25, 35);
        else g.drawString("\u2192", 25, 35);
    }
}


