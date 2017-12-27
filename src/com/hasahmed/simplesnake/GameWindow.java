package com.hasahmed.simplesnake;

/**
 * Created by Hasan Y Ahmed on 12/23/17.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame{
    private Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
    int windowSize;

    GameWindow(String windowTitle){
        super(windowTitle);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setResizable(true);
        this.setFocusable(true);
        windowSize = getBiggestPossiblePlayAreaSize();
        this.setSize(windowSize, windowSize);
    }

    void addGame(BigBang game){
        this.getContentPane().add(game);
        this.addKeyListener(game);
        game.addMouseListener(game);
    }

    void setColor(Color color){
        this.getContentPane().setBackground(color);
    }

    void center() {
        this.setLocation(
                screenDimensions.width/2 - this.getSize().width /2,
                screenDimensions.height/2 - this.getSize().height/2
                );
    }

    void closeWindow(){
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
    int getBiggestPossiblePlayAreaSize(){
        return Math.min((int)screenDimensions.getWidth(), (int)screenDimensions.getHeight()) - Constants.SCREEN_UPPER_BOARDER_OFFSET;
    }
}
