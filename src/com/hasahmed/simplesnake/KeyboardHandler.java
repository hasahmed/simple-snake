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
    static Point speed = new Point(0, 0);
    static int keyOpp = 0;
    /**
     * dirls: A 2 element list the records array key presses. This is so that when 2 array keys are pressed
     * in the same 'tick' the second one isn't lost
     */
    static Dir[] dirls = new Dir[2];
    static String saveSpeed;


    /**
     * Finds the first non-null position of the list and inserts there
     * @param d The direction to be added to the list
     */
    static void add(Dir d){
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
    static void dirlsRemoveFirst(){
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

    static void setDir(Dir d){
        keyOpp++;
        if (d == Dir.UP){
            if(KeyboardHandler.getDir() != Dir.DOWN)
                speed.setLocation(0, -10);
        }

        else if (d == Dir.DOWN){
            if(KeyboardHandler.getDir() != Dir.UP)
                speed.setLocation(0, 10);
        }

        else if (d == Dir.LEFT) {
            if(KeyboardHandler.getDir() != Dir.RIGHT)
                speed.setLocation(-10, 0);
        }

        else if (d == Dir.RIGHT) {
            if(KeyboardHandler.getDir() != Dir.LEFT)
                speed.setLocation(10, 0);
        }

        else if (d == Dir.STOP) speed.setLocation(0, 0);
    }
    static Dir getDir(){
        if (speed.equals(new Point(0, -10))) return Dir.UP;
        else if (speed.equals(new Point(0, 10))) return Dir.DOWN;
        else if (speed.equals(new Point(-10, 0))) return Dir.LEFT;
        else if (speed.equals(new Point(10, 0))) return Dir.RIGHT;
        return Dir.STOP;
    }
    static Point getSpeed(){
        if (speed.equals(new Point(0, -10))) return new Point(0, -10);
        else if (speed.equals(new Point(0, 10))) return new Point(0, 10);
        else if (speed.equals(new Point(-10, 0))) return new Point(-10, 0);
        else if (speed.equals(new Point(10, 0))) return new Point(10, 0);
        return new Point(0, 0);
    }
    static void right(){
        add(Dir.RIGHT);
        if(getDir() == Dir.LEFT)
            if(dirls[0] == Dir.RIGHT)
                dirlsRemoveFirst();
    }
    static void left(){
        add(Dir.LEFT);
        if(getDir() == Dir.RIGHT)
            if(dirls[0] == Dir.LEFT)
                dirlsRemoveFirst();
    }
    static void up(){
        add(Dir.UP);
        if(        getDir() == Dir.DOWN
                && dirls[0] == Dir.UP)
            dirlsRemoveFirst(); //execute
    }
    static void down(){
        if (dirls[keyOpp] != Dir.UP
                && getDir() != Dir.STOP)
            add(Dir.DOWN); //execute

        if(getDir() == Dir.UP
                && dirls[0] == Dir.DOWN)
            dirlsRemoveFirst(); //execute
    }
    static void handleKeyPressed(KeyEvent e, Snake game) {
        // TODO: setup seperate keypress configuration file,
        // would support alternate key modes, e.g. vim style
        // Would need to read a config from the environment,
        // preferably JSON
        if (!game.gameOver && KeyboardHandler.keyOpp < 2){
            if (
			    e.getKeyCode() == KeyEvent.VK_RIGHT ||
			    e.getKeyCode() == KeyEvent.VK_D ||
			    e.getKeyCode() == KeyEvent.VK_L
			    ){
                right();
            }
            else if (
			    e.getKeyCode() == KeyEvent.VK_LEFT ||
			    e.getKeyCode() == KeyEvent.VK_A ||
			    e.getKeyCode() == KeyEvent.VK_J
			    ) {
                left();
            }

            else if (
			    e.getKeyCode() == KeyEvent.VK_UP ||
			    e.getKeyCode() == KeyEvent.VK_W ||
			    e.getKeyCode() == KeyEvent.VK_I
			    ){
                up();
            }
            else if (
			    e.getKeyCode() == KeyEvent.VK_DOWN ||
			    e.getKeyCode() == KeyEvent.VK_S ||
			    e.getKeyCode() == KeyEvent.VK_K
			    ){
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
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_Q){
            game.closeWindow();
        }
        else if (game.gameOver){
            game.reset();
            return;
        }

        if (
		e.getKeyCode() == KeyEvent.VK_UP ||
                e.getKeyCode() == KeyEvent.VK_LEFT ||
                e.getKeyCode() == KeyEvent.VK_RIGHT ||

		e.getKeyCode() == KeyEvent.VK_A ||
                e.getKeyCode() == KeyEvent.VK_W ||
                e.getKeyCode() == KeyEvent.VK_D ||

		e.getKeyCode() == KeyEvent.VK_I ||
                e.getKeyCode() == KeyEvent.VK_J ||
                e.getKeyCode() == KeyEvent.VK_L
		){
            game.displayStartScreen = false;
        }
    } // end keyPressed
}
