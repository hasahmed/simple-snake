package com.hasahmed.simplesnake;

import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class Snake implements World{
    private static String version = "2.0.1";
    boolean displayStartScreen = true;
    static int PLAY_AREA_X = 400;
    static int PLAY_AREA_Y = 400;
//    private static int preLoop = 10010; //loop the game a lot so that it will be faster?
    private static int preLoop = 2; //loop the game a lot so that it will be faster?
    private int STARTING_LENGTH = 5;
    private static GameWindow gameWindow;
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
    Body body;
    Food food;
    Circle head;
    Point saveSpeed;
    KeyboardHandler keyboardHandler;

    int scoreWrittenToFile = WriteScore.getScoreFromFile();

    Snake(){
        keyboardHandler = new KeyboardHandler(this);
        endCalled = false;
        displayStartScreen = true;
        keyboardHandler.setDir(KeyboardHandler.Dir.STOP);
        drawMode = false;
        gameOver = false;
        score = new Score();
        body = new Body(RADIUS,
                BODY_COLOR,
                STARTING_LENGTH,
                WIDTH / 2,
                HEIGHT/ 2,
                keyboardHandler);
        head = body.head;
        food = new Food(FOOD_COLOR, body);
        scoreWrittenToFile = WriteScore.getScoreFromFile();
    }

    void reset(){
        endCalled = false;
        displayStartScreen = true;
        keyboardHandler.setDir(KeyboardHandler.Dir.STOP);
        drawMode = false;
        gameOver = false;
        score = new Score();
        body = new Body(RADIUS,
                BODY_COLOR,
                STARTING_LENGTH,
                WIDTH / 2,
                HEIGHT/ 2,
                keyboardHandler);
        head = body.head;
        food = new Food(FOOD_COLOR, body);
        scoreWrittenToFile = WriteScore.getScoreFromFile();
    }

    //update
    public void update() {
        keyboardHandler.setDir(keyboardHandler.dirls[0]);
        keyboardHandler.dirlsRemoveFirst();

//variables
        head = body.get(0);
        keyboardHandler.keyOpp = 0;
        if (head.isOutOfBounds() || body.headOverlappingBody()){
            body.redHead();
            gameOver = true;
            if (!endCalled) end();
        }
//updatePos
        if (!paused && !gameOver && keyboardHandler.getDir() != KeyboardHandler.Dir.STOP) body.updatePos();
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
        if (paused) Frills.drawPauseGraphics(g, keyboardHandler);

    }//end draw

    public void mousePressed(MouseEvent e) {}



    void pause(){
        if (!paused){
            body.redHead();
            saveSpeed = keyboardHandler.getSpeed();
            keyboardHandler.saveSpeed = keyboardHandler.getDir().value;
            paused = true;
            keyboardHandler.setDir(KeyboardHandler.Dir.STOP);
        }
        else{
            body.blackHead();
            paused = false;
            keyboardHandler.speed.setLocation(saveSpeed);
        }
    }


    void closeWindow(){
        gameWindow.closeWindow();
    }

    public void keyPressed(KeyEvent e){
        keyboardHandler.handleKeyPressed(e, this);
    }

    void end(){
        endCalled = true;
        WriteScore.writeToFile(score.getScore().toString());
    }
    void init(){
//        update();
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

        //BigBang
        Snake s = new Snake();
        BigBang game = new BigBang(resetSpeed, s);
        /* TODO: Need to figure out which order classes are instantiated in so as to figure out where variables
         * can be set. Such as the location of the frills and such
         */

        // gameWindow
        gameWindow = new GameWindow("Simple Snake", WIDTH, HEIGHT);
        gameWindow.addGame(game);
        gameWindow.setColor(Color.GRAY);
        gameWindow.center();

        for (; preLoop >= 0; preLoop--){ //this is here so that java will make the code in init native...
            // Is it actually faster? Who knows?
            s.init();
        }

        game.begin();
    }

}
