package com.hasahmed.simplesnake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;


public class Snake implements World{
    private static String version = "2.0.1";
    boolean displayStartScreen = true;
    static int PLAY_AREA_X = 400;
    static int PLAY_AREA_Y = 400;
    private static JFrame frame;
//    private static int preLoop = 10010; //loop the game a lot so that it will be faster?
    private static int preLoop = 1; //loop the game a lot so that it will be faster?
    private int STARTING_LENGTH = 5;
    private boolean paused = false;
    boolean endCalled = false; // keep end() from being called every tic
    int RADIUS = 5;
    int FOOD_GROWTH = 3;
    static Integer resetSpeed = 75;
    static int HEIGHT = 432; //needs to be this because of the size of the size of OSX top of window
    static int WIDTH = PLAY_AREA_X + Frills.BOARDER_WIDTH;
    Color BODY_COLOR = Color.BLACK;
    Color FOOD_COLOR = Color.ORANGE;
    boolean drawMode = false;
    boolean gameOver = false;
    Score score = new Score();
    Body body = new Body(RADIUS, BODY_COLOR, STARTING_LENGTH);
    Food food = new Food(FOOD_COLOR, body);
    Circle head;
    Point saveSpeed;

    int scoreWrittenToFile = WriteScore.getScoreFromFile();

    void reset(){
        endCalled = false;
        displayStartScreen = true;
        KeyboardHandler.setDir(KeyboardHandler.Dir.STOP);
        drawMode = false;
        gameOver = false;
        score = new Score();
        body = new Body(RADIUS, BODY_COLOR, 5);
        food = new Food(FOOD_COLOR, body);
        scoreWrittenToFile = WriteScore.getScoreFromFile();
    }

    //update
    public void update() {
        KeyboardHandler.setDir(KeyboardHandler.dirls[0]);
        KeyboardHandler.dirlsRemoveFirst();

//variables
        head = body.get(0);
        KeyboardHandler.keyOpp = 0;
        if (head.isOutOfBounds() || body.headOverlappingBody()){
            body.redHead();
            gameOver = true;
            if (!endCalled) end();
        }
//updatePos
        if (!paused && !gameOver && KeyboardHandler.getDir() != KeyboardHandler.Dir.STOP) body.updatePos();

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

    public void mousePressed(MouseEvent e) {}



    void pause(){
        if (!paused){
            body.redHead();
            saveSpeed = KeyboardHandler.getSpeed();
            KeyboardHandler.saveSpeed = KeyboardHandler.getDir().value;
            paused = true;
            KeyboardHandler.setDir(KeyboardHandler.Dir.STOP);
        }
        else{
            body.blackHead();
            paused = false;
            KeyboardHandler.speed.setLocation(saveSpeed);
        }
    }


    void closeWindow(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public void keyPressed(KeyEvent e){
        KeyboardHandler.handleKeyPressed(e, this);
    }

    void end(){
        endCalled = true;
        WriteScore.writeToFile(score.getScore().toString());
    }
    void init(){
        update();
        body.headOverlappingBody();
        head.isOutOfBounds();
    }

    public static void main(String[] args) throws IOException {
        if(args.length > 0) {
            if(args[0].compareTo("--version") == 0){
                System.out.println(version);
                return;
            }
            else{
                resetSpeed = Integer.parseInt(args[0]);
            }
        }
        WriteScore.init();
        System.setProperty("sun.java2d.opengl", "True");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        //BigBang
        Snake s = new Snake();
        BigBang game = new BigBang(resetSpeed, s);

        //JFrame
        frame = new JFrame("Simple Snake");
        frame.getContentPane().add(game);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setFocusable(true);
        frame.addKeyListener(game);
        game.addMouseListener(game);
        frame.setVisible(true);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(dim.width/2 - frame.getSize().width /2, dim.height/2 - frame.getSize().height/2);
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setResizable(false);
        for (; preLoop >= 0; preLoop--){ //this is here so that java will make the code in init native...
            // Is it actually faster? Who knows?
            s.init();
        }

        game.begin();
    }

}
