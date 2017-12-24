package com.hasahmed.simplesnake;// Circle.java
import java.util.ArrayList;
import java.awt.*; 
import java.util.Random;

class Circle {
    //instace variables
    Point pos = new Point(0, 0);
    Point speed = new Point(0, -10);
    Point lastPos = new Point(0, 0);
    int radius;
    Color color;
    Globals globals;

    //constructor
    Circle(int x, int y, int radius, Color color, Globals globals) {
        this.pos.setLocation(x, y);
        this.radius = radius;
        this.color = color;
        this.lastPos.setLocation(x - this.speed.x, y - this.speed.y);
        this.globals = globals;
    }
    void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval(this.pos.x - this.radius, this.pos.y - this.radius, this.radius * 2, this.radius * 2);
        g.setColor(Color.BLACK);
        g.drawOval(this.pos.x - this.radius, this.pos.y - this.radius, this.radius * 2, this.radius * 2);
    }
    boolean isOutOfBounds(){
        int x = this.pos.x;
        int y = this.pos.y;
        if (x > globals.playAreaSize - globals.frillThickness || // right-wall
                x < globals.blobRadius * 2                    || // left-wall
                y > globals.playAreaSize - globals.blobRadius || // bottom-wall
                y <= globals.blobRadius * 2) return true;        // top-wall
        return false;
    }
    int getX(){
        return this.pos.x;
    }
    int getY() {
        return this.pos.y;
    }

} // end circle 
///////////////////////////////////////////////////////////////////////////////////////////



//BODY//
class Body extends ArrayList<Circle>{
    //instance variables
    Circle head;
    Color bodyColor;
    KeyboardHandler keyboardHandler;
    Globals globals;


    //constructors
    public Body(int radius,
                Color color,
                int length,
                int startX,
                int startY,
                KeyboardHandler keyboardHandler,
                Globals globals){
        this.globals = globals;
        this.head = new Circle(startX, startY, radius, color, globals);
        this.add(head);
        this.keyboardHandler = keyboardHandler;
        fillWithCircles(length);
    }

    /**
     * fills this (the Body) with a number of circles, and sets their locations such that they correctly stack vertically
     * Used at the begining of the game and that is all
     * @param numOfCircles the number of circles to fill the body with
     */
    void fillWithCircles(int numOfCircles) {
        for (int i = 1; i < numOfCircles + 1; i++) { //loop numOfCircles except skip head because its already there
            this.add(
                    new Circle(
                            this.get(i - 1).pos.x,
                            this.get(i - 1).pos.y + this.get(i - 1).radius * 2,
                            head.radius,
                            bodyColor,
                            globals)
            );
        }
    }

    /**
     * add circle to the end of the body
     */
    void addCircle(){
        this.add(
                new Circle(
                        this.get(
                                this.size() - 1).pos.x,
                                this.get(this.size() - 1).pos.y,
                                head.radius,
                                bodyColor,
                                globals
                )
        );
    }

    /**
     * Add multiple circles to the snake
     * @param num the number of circles to add
     */
    void addCircle(int num){
        for (int i = 0; i < num; i++){
            this.add(
                    new Circle(
                            this.get(this.size() - 1).pos.x,
                            this.get(this.size() - 1).pos.y,
                            head.radius,
                            bodyColor,
                            globals
                    )
            );
        }
    }
    /**
     * Updates the position of the snake.
     * This is done by removing moving the tail of the snake, and appending a new head
     */
    void updatePos(){
        Circle ref = this.get(0);
        this.add(
                0,
                new Circle(
                        ref.getX() + keyboardHandler.speed.x,
                        ref.getY() + keyboardHandler.speed.y,
                        ref.radius,
                        ref.color,
                        globals
                )
        );
        this.remove(this.get(this.size() -1));
    }

    /**
     * Exact same as updatePos except with a little more flavor
     */
    void rainbowUpdatePos(){
        Random randy = new Random();
        Circle ref = this.get(0);
        this.add(
                0,
                new Circle(
                        ref.getX() + keyboardHandler.speed.x,
                        ref.getY() + keyboardHandler.speed.y,
                        ref.radius,
                        new Color(
                                randy.nextInt(255),
                                randy.nextInt(255),
                                randy.nextInt(255)
                        ),
                        globals
                )
        );
        this.remove(this.get(this.size() -1));
    }


    //headOverlappingBody
//
//checks to see if the 0th element in the list is in the same location as the ith element
// in the list. If it is it removes the ith element, so that the redHead() method (turing the
// head of the body red, is visible

    /**
     * @return returns true if the head is colliding with the body
     */
    boolean headOverlappingBody(){
        for (int i = 1; i < this.size(); i++)
            if (this.get(0).pos.equals(this.get(i).pos)){
                this.get(i).pos = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
                return true;
            }
        return false;
    }
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
    Food(Color color, Body b, Globals globals){
        super(0, 0, b.head.radius, color, globals);
        this.moveToRandomPos();
        this.body = b;
        this.pos = new Point(0, 400);
        while(this.isOutOfBounds()) moveToRandomPos();
    }
    boolean isOverlappingHead(Circle h){
        return this.pos.equals(h.pos);
    }

    /**
     * Move the food to a random position
     */
    void moveToRandomPos() {
        // TODO: make the random movement correct
        Random randy = new Random();
        int x = randy.nextInt(globals.playAreaSize / (globals.blobRadius * 2));
        int y = randy.nextInt(globals.playAreaSize / (globals.blobRadius * 2));
        this.pos.setLocation((x * 10) + 15, (y * 10) + 15); //why + 15?
        while(this.isOutOfBounds()) moveToRandomPos();
    }

    boolean hittingBody(){
        for(int i = 0; i < this.body.size(); i++){
            if(body.get(i).pos.equals(this.pos)) return true;
        }
        return false;
    }

}

