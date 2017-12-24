package com.hasahmed.simplesnake;

/*
 * Created by Hasan Y Ahmed on 12/23/17.
 */

class Constants {

    private final static int FRILL_DIV_RATIO = 40;
    private final static int BLOB_DIV_RATIO = 80;

    final static String VERSION = "2.0.1";

    final static int RESET_SPEED = 75;

    //food
    final static int FOOD_GROWTH = 3;

    //Pause Arrow
    final static int ARROW_BASE_X = 15;
    final static int ARROW_BASE_Y = 25;

    // snake body
    final static int STARTING_SNAKE_BODY_LENGTH = 5;


    //
    final static int SCREEN_UPPER_BOARDER_OFFSET = 25;


    static int getFrillThickness(int windowSize){
        return windowSize / FRILL_DIV_RATIO;
    }
    static int getBlobRadius(int playAreaSize){
        return playAreaSize / BLOB_DIV_RATIO;
    }
}
