package com.hasahmed.simplesnake;

/**
 * Created by Hasan Y Ahmed on 12/21/17.
 */

import java.awt.*;

class Frills {
    static final int ARROW_X = 25;
    static final int ARROW_Y = 35;
    static final int BOARDER_WIDTH = 10;
    static void drawBorder(Graphics g){

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Snake.WIDTH, BOARDER_WIDTH);                            //top
        g.fillRect(0, Snake.PLAY_AREA_X, Snake.WIDTH, BOARDER_WIDTH);            //bottom
        g.fillRect(0, 0, BOARDER_WIDTH, Snake.HEIGHT);                           //left
        g.fillRect((Snake.WIDTH - BOARDER_WIDTH) - Snake.windowsWidthAdjust, 0, BOARDER_WIDTH, Snake.HEIGHT); //right
    }
    static void drawStartScreen(Graphics g){
        //TITLE
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(new Font("AndaleMono", Font.BOLD, 40));
        g.setColor(Color.BLACK);
        g2.drawString("SIMPLE SNAKE", 58, 60);
        //INSTRUCTIONS
        g.setFont(new Font("AndaleMono", Font.BOLD, 12));
        g.drawString("Press LEFT, RIGHT or UP Arrow Key to start", 60, 350);
        g.drawString("ESC to quit", 175, 365);
        g.drawString("P to pause", 175, 380);
    }
    static void drawPauseGraphics(Graphics g){
        String d = KeyboardHandler.saveSpeed;
        g.setFont(new Font("AndaleMono", Font.BOLD, 20));
        g.setColor(Color.GREEN);
        if (d.equals("UP"))         g.drawString("\u2191", ARROW_X, ARROW_Y);
        else if(d.equals("DOWN"))   g.drawString("\u2193", ARROW_X, ARROW_Y);
        else if(d.equals("LEFT"))   g.drawString("\u2190", ARROW_X, ARROW_Y);
        else                                 g.drawString("\u2192", ARROW_X, ARROW_Y);
    }
}
