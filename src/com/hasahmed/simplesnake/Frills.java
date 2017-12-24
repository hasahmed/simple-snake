package com.hasahmed.simplesnake;

/**
 * Created by Hasan Y Ahmed on 12/21/17.
 */

import java.awt.*;

class Frills {
    int playAreaSize;
    int frillThickness;

    final int arrowX = Constants.ARROW_BASE_X + frillThickness;
    final int arrowY = Constants.ARROW_BASE_Y + frillThickness;

    Frills(Globals globals) {
        this.playAreaSize = globals.playAreaSize;
        this.frillThickness = globals.frillThickness;
    }

    void drawBorder(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, playAreaSize + frillThickness, frillThickness);                                //top
        g.fillRect(0, playAreaSize + frillThickness, playAreaSize + frillThickness, frillThickness);   //bottom
        g.fillRect(0, 0, frillThickness, playAreaSize + frillThickness);                                 //left
        g.fillRect(playAreaSize, 0, frillThickness, frillThickness);                                     //right
    }
    void drawStartScreen(Graphics g){
        //TITLE
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(new Font("AndaleMono", Font.BOLD, 40));
        g.setColor(Color.BLACK);
        g2.drawString("SIMPLE SNAKE", 58, 60);
        //INSTRUCTIONS
        g.setFont(new Font("AndaleMono", Font.BOLD, 12));
        g.drawString("ARROW KEY to start", 145, 350);
        g.drawString("ESC to quit", 175, 365);
        g.drawString("P to pause", 175, 380);
    }
    void drawPauseGraphics(Graphics g, KeyboardHandler keyboardHandler){
        String d = keyboardHandler.saveSpeed;
        g.setFont(new Font("AndaleMono", Font.BOLD, 20));
        g.setColor(Color.GREEN);
        if (d.equals("UP")){
            g.drawString("\u2191", arrowX, arrowY);
        }
        else if(d.equals("DOWN")) g.drawString("\u2193", arrowX, arrowY);
        else if(d.equals("LEFT")) g.drawString("\u2190", arrowX, arrowY);
        else g.drawString("\u2192", arrowX, arrowY);
    }
}
