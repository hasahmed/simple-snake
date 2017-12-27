package com.hasahmed.simplesnake;

import java.awt.*;
import java.awt.event.*;
import java.io.*;


// A blob is what one snake component is called

public class Snake implements World{
    boolean displayStartScreen = true;
//    private static int preLoop = 10010; //loop the game a lot so that it will be faster?
    private static int preLoop = 2; //loop the game a lot so that it will be faster?
    private static GameWindow gameWindow;
    private boolean paused = false;
    boolean endCalled = false; // keep end() from being called every tic
    Frills frills;
//    static int HEIGHT = 432; //needs to be this because of the size of the size of OSX top of window
//    static int WIDTH = playAreaSize + Frills.boarderWidth;
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
    static Globals globals;

    int scoreWrittenToFile = WriteScore.getScoreFromFile();

    Snake(Globals gbl){
        this.globals = gbl;
        frills = new Frills(globals);
        keyboardHandler = new KeyboardHandler(globals);
        endCalled = false;
        displayStartScreen = true;
        keyboardHandler.setDir(KeyboardHandler.Dir.STOP);
        drawMode = false;
        gameOver = false;
        score = new Score();
        body = new Body(
                globals.blobRadius,
                BODY_COLOR,
                Constants.STARTING_SNAKE_BODY_LENGTH,
                globals.getPlayAreaCenter(),
                globals.getPlayAreaCenter(),
                keyboardHandler,
                globals);
        head = body.head;
        food = new Food(FOOD_COLOR, body, globals);
        scoreWrittenToFile = WriteScore.getScoreFromFile();
    }

    void reset(){
        endCalled = false;
        displayStartScreen = true;
        keyboardHandler.setDir(KeyboardHandler.Dir.STOP);
        drawMode = false;

        gameOver = false;
        score = new Score();
        body = new Body(
                globals.blobRadius,
                BODY_COLOR,
                Constants.STARTING_SNAKE_BODY_LENGTH,
                globals.getPlayAreaCenter(),
                globals.getPlayAreaCenter(),
                keyboardHandler,
                globals);
        head = body.head;
        food = new Food(FOOD_COLOR, body, globals);
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
            body.addCircle(Constants.FOOD_GROWTH);
            food.moveToRandomPos();
            score.addTo();
            while(food.hittingBody()) food.moveToRandomPos();
        }
    } // end update

    //draw
    public void draw(Graphics g){
        if (displayStartScreen){
            frills.drawStartScreen(g);
        }
        if(!displayStartScreen)food.draw(g);
        frills.drawBorder(g);
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
        if (paused) frills.drawPauseGraphics(g, keyboardHandler);

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
        update();
        body.headOverlappingBody();
        head.isOutOfBounds();
    }

    public static void main(String[] args) throws IOException {
        System.setProperty("sun.java2d.opengl", "True");
        WriteScore.init();
        gameWindow = new GameWindow("Simple Snake");
        globals = new Globals(gameWindow.windowSize);
        Snake snake = new Snake(globals);
        BigBang game = new BigBang(Constants.RESET_SPEED, snake);

        // gameWindow
        gameWindow.addGame(game);
        gameWindow.setColor(Color.GRAY);
        gameWindow.center();
        snake.init();

        game.begin();
    }
}
