package com.hasahmed.simplesnake;

/*
 * Created by Hasan Y Ahmed on 12/23/17.
 */


public class Globals {
    int playAreaSize;
    int playAreaCenter;
    int blobRadius;
    int frillThickness;
    private int snakeStep;

    Globals(int windowSize){
        this.playAreaSize = windowSize - (Constants.getFrillThickness(windowSize) * 2);
        this.blobRadius = Constants.getBlobRadius(this.playAreaSize);
        this.frillThickness = Constants.getFrillThickness(windowSize);
    }

    int getPlayAreaCenter(){
        double d = blobRadius * (Math.ceil(Math.abs(this.playAreaSize/5)));
        return (int)d;
    }

    void setSnakeStep(int blobRadius){
        this.snakeStep = blobRadius * 2;
    }

    int getSnakeStep(){
        return this.snakeStep;
    }
}
