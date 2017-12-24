package com.hasahmed.simplesnake;

/**
 * Created by Hasan Y Ahmed on 12/21/17.
 */

/**
 * KeyboardHandler: Really not a good name for this class. It mostly handles the button pressing logic as well
 * as the array that remembers the button presses
 */

import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyboardHandler {
    Point speed = new Point(0, 0);
    int keyOpp = 0;
    int speedIncrement;

    KeyboardHandler(Globals globals){
        this.speedIncrement = globals.blobRadius * 2;
    }
    /**
     * dirls: A 2 element list the records array key presses. This is so that when 2 array keys are pressed
     * in the same 'tick' the second one isn't lost
     */
    Dir[] dirls = new Dir[2];
    String saveSpeed;


    /**
     * Finds the first non-null position of the list and inserts there
     * @param d The direction to be added to the list
     */
    void add(Dir d){
        for (int i = 0; i < dirls.length; i++){
            if (dirls[i] == null){
                dirls[i] = d;
                break;
            }
        }
    }
    /**
     * removes the 0th element of the dirls Array, and moves the 1th element to its place, replacing the 1th
     * element with null
     */
    void dirlsRemoveFirst(){
        dirls[0] = dirls[1];
        dirls[1] = null;
    }
    enum Dir{
        UP("UP"), DOWN("DOWN"), LEFT("LEFT"), RIGHT("RIGHT"), STOP("STOP");
        String value;
        Dir(String v){
            value = v;
        }
    }

    void setDir(Dir d){
        keyOpp++;
        if (d == Dir.UP){
            if(this.getDir() != Dir.DOWN)
                speed.setLocation(0, -speedIncrement);
        }

        else if (d == Dir.DOWN){
            if(this.getDir() != Dir.UP)
                speed.setLocation(0, speedIncrement);
        }

        else if (d == Dir.LEFT) {
            if(this.getDir() != Dir.RIGHT)
                speed.setLocation(-speedIncrement, 0);
        }

        else if (d == Dir.RIGHT) {
            if(this.getDir() != Dir.LEFT)
                speed.setLocation(speedIncrement, 0);
        }

        else if (d == Dir.STOP) speed.setLocation(0, 0);
    }
    Dir getDir(){
        if (speed.equals(new Point(0, -speedIncrement))) return Dir.UP;
        else if (speed.equals(new Point(0, speedIncrement))) return Dir.DOWN;
        else if (speed.equals(new Point(-speedIncrement, 0))) return Dir.LEFT;
        else if (speed.equals(new Point(speedIncrement, 0))) return Dir.RIGHT;
        return Dir.STOP;
    }
    Point getSpeed(){
        if (speed.equals(new Point(0, -speedIncrement))) return new Point(0, -speedIncrement);
        else if (speed.equals(new Point(0, speedIncrement))) return new Point(0, speedIncrement);
        else if (speed.equals(new Point(-speedIncrement, 0))) return new Point(-speedIncrement, 0);
        else if (speed.equals(new Point(speedIncrement, 0))) return new Point(speedIncrement, 0);
        return new Point(0, 0);
    }
    void right(){
        add(Dir.RIGHT);
        if(getDir() == Dir.LEFT)
            if(dirls[0] == Dir.RIGHT)
                dirlsRemoveFirst();
    }
    void left(){
        add(Dir.LEFT);
        if(getDir() == Dir.RIGHT)
            if(dirls[0] == Dir.LEFT)
                dirlsRemoveFirst();
    }
    void up(){
        add(Dir.UP);
        if(        getDir() == Dir.DOWN
                && dirls[0] == Dir.UP)
            dirlsRemoveFirst(); //execute
    }
    void down(){
        if (dirls[keyOpp] != Dir.UP
                && getDir() != Dir.STOP)
            add(Dir.DOWN); //execute

        if(getDir() == Dir.UP
                && dirls[0] == Dir.DOWN)
            dirlsRemoveFirst(); //execute
    }
    void handleKeyPressed(KeyEvent e, Snake game) {
        if (!game.gameOver && this.keyOpp < 2){

            if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                right();
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                left();
            }

            else if (e.getKeyCode() == KeyEvent.VK_UP){
                up();
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN){
                down();
            }
            else if (e.getKeyCode() == KeyEvent.VK_P && !game.displayStartScreen){
                game.pause();
            }
            else if (e.getKeyCode() == KeyEvent.VK_Q){
                game.closeWindow();
            }
            else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                game.closeWindow();
            }
        }

        else if (e.getKeyCode() == KeyEvent.VK_R){
            game.reset();
        }

        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_Q){
            game.closeWindow();
        }

        if (       e.getKeyCode() == KeyEvent.VK_UP
                || e.getKeyCode() == KeyEvent.VK_DOWN
                || e.getKeyCode() == KeyEvent.VK_LEFT
                || e.getKeyCode() == KeyEvent.VK_RIGHT){
            game.displayStartScreen = false;
        }
    } // end keyPressed
}
