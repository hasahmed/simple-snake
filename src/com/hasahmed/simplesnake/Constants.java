package com.hasahmed.simplesnake;

/*
 * Created by Hasan Y Ahmed on 12/23/17.
 */

class Constants {

    private final static int FRILL_DIV_RATIO = 40;
    private final static int BLOB_DIV_RATIO = 80;

    static int getFrillThickness(int windowSize){
        return windowSize / FRILL_DIV_RATIO;
    }
    static int getBlobRadius(int playAreaSize){
        return playAreaSize / BLOB_DIV_RATIO;
    }
}
