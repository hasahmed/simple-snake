package com.hasahmed.simplesnake;

/**
 * Created by Hasan Y Ahmed on 12/23/17.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame{
    Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();

    GameWindow(String windowTitle, int width, int height){
        super(windowTitle);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setResizable(false);
        this.setFocusable(true);
        this.setSize(width, height);
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
        this.setLocation(screenDimensions.width/2 - this.getSize().width /2, screenDimensions.height/2 - this.getSize().height/2);
    }

    void closeWindow(){
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
