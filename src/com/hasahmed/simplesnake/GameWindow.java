package com.hasahmed.simplesnake;

/**
 * Created by Hasan Y Ahmed on 12/23/17.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame {
    Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
    private static final float TRANSLUCENT_OPACITY = 0.6f;
    private static final float OPAQUE_OPACITY = 1.0f;
    private boolean translucent = false;
    private final Insets decorationInsets = new Insets(0, 0, 0, 0);


    GameWindow(String windowTitle){
        super(windowTitle);
        this.setUndecorated(false);
        this.setOpacity(OPAQUE_OPACITY);
        this.setResizable(false);
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setResizable(false);
        this.setFocusable(true);
        this.setVisible(true);
        SwingUtilities.invokeLater(() -> {
            Insets in = this.getInsets();
            decorationInsets.set(in.top, in.left, in.bottom, in.right);
            this.setSize(Snake.WIDTH, Snake.HEIGHT);
        });
    }

    void addGame(BigBang game){
        this.getContentPane().add(game);
        this.addKeyListener(game);
        game.addMouseListener(game);
    }

    void setBackgroundColor(Color color){
        this.getContentPane().setBackground(color);
    }

    void center() {
        this.setLocation(screenDimensions.width/2 - this.getSize().width /2, screenDimensions.height/2 - this.getSize().height/2);
    }

    void closeWindow(){
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    void toggleTranslucent(){
        translucent = !translucent;
        this.dispose();
        this.setOpacity(OPAQUE_OPACITY);
        this.setUndecorated(translucent);
        this.setOpacity(translucent ? TRANSLUCENT_OPACITY : OPAQUE_OPACITY);
        this.setVisible(true);
        SwingUtilities.invokeLater(() -> {
            if (!translucent) {
                // Decorated: this is the proper baseline. Keep outer size fixed and
                // remember the decoration insets so the undecorated state can match.
                Insets in = this.getInsets();
                decorationInsets.set(in.top, in.left, in.bottom, in.right);
                this.setSize(Snake.WIDTH, Snake.HEIGHT);
            } else {
                // Undecorated: shrink by the decoration insets so the client area
                // matches the decorated layout.
                this.setSize(
                    Snake.WIDTH - decorationInsets.left - decorationInsets.right,
                    Snake.HEIGHT - decorationInsets.top - decorationInsets.bottom);
            }
            this.revalidate();
            this.repaint();
        });
    }
}
