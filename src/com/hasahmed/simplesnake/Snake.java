package com.hasahmed.simplesnake;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


public class Snake implements World {
    private static final String version = "2.0.1";
    boolean displayStartScreen = true;
    static int PLAY_AREA_X = 400;
    static int PLAY_AREA_Y = 400;

//    private static int preLoop = 10010; //loop the game a lot so that it will be faster?
    private final int STARTING_LENGTH = 5;
    private static GameWindow gameWindow;
    private boolean paused = false;
    boolean endCalled = false; // keep end() from being called every tic
    int RADIUS = 5;
    int FOOD_GROWTH = 3;
    static Integer frameDelay = 75;
    static int HEIGHT = 438; //osx //needs to be this because of the size of the size of OSX top of window
    // static int HEIGHT = 449; //windows //needs to be this because of the size of the size of OSX top of window
    static int WIDTH = PLAY_AREA_X + Frills.BOARDER_WIDTH;
    static int windowsWidthAdjust = 0;
    static int windowsHeightAdjust = 0;
    Color BODY_COLOR = Color.BLACK;
    Color FOOD_COLOR = Color.ORANGE;
    boolean gameOver = false;
    int gameOverDelayMs = 500;
    boolean gameOverDelayIsComplete = false;
    boolean delayedExecutorCalled = false;

    Score score = new Score();
    Body body = new Body(RADIUS, BODY_COLOR, STARTING_LENGTH);
    Food food = new Food(FOOD_COLOR, body);
    Circle head;
    Point saveSpeed;
    int scoreWrittenToFile = WriteScore.getScoreFromFile();

    public Snake(){
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {

            windowsHeightAdjust = 34;
            windowsWidthAdjust = 22;

            Snake.HEIGHT += windowsHeightAdjust;
            Snake.WIDTH += windowsWidthAdjust;
        }
    }

    /**
     * Reset the game to default start screen
     */
    void reset(){
        endCalled = false;
        displayStartScreen = true;
        KeyboardHandler.setDir(KeyboardHandler.Dir.STOP);
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

        //foodCollision
        if(food.isOverlappingHead(head)){
            body.addCircle(FOOD_GROWTH);
            food.moveToRandomPos();
            score.addTo();
            while(food.hittingBody()) food.moveToRandomPos();
        }
        // Game Over condition
        if (gameOver && !gameOverDelayIsComplete && !delayedExecutorCalled) {
            delayedExecutorCalled = true;
        CompletableFuture.delayedExecutor(gameOverDelayMs, TimeUnit.MILLISECONDS).execute(() -> {
            gameOverDelayIsComplete = true;
            delayedExecutorCalled = false;
        });
    }
} // end update

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
        gameWindow.closeWindow();
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
        // OpenGL as renderer
        System.setProperty("sun.java2d.opengl", "True");
        if(args.length > 0) {
            if(args[0].compareTo("--version") == 0){
                System.out.println(version);
                return;
            }
            else{
                frameDelay = Integer.parseInt(args[0]);
            }
        }

        WriteScore.init();

        //BigBang
        Snake s = new Snake();
        BigBang game = new BigBang(frameDelay, s);
        /* TODO: Need to figure out which order classes are instantiated in so as to figure out where variables
         * can be set. Such as the location of the frills and such
         */

        // gameWindow
        gameWindow = new GameWindow("Simple Snake");
        gameWindow.addGame(game);
        gameWindow.setBackgroundColor(Color.GRAY);
        gameWindow.setSize(WIDTH, HEIGHT);
        gameWindow.center();

        s.init();
        game.begin();

    }

}
