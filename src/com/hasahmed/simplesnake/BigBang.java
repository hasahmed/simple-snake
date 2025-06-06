package com.hasahmed.simplesnake;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class BigBang extends JComponent implements KeyListener, ActionListener, MouseListener {
    Timer timer;
    World world;

    BigBang(int delay, World world) {
        timer = new Timer(delay, this);
        this.world = world;
    }
    public void begin() {
        timer.start();
    }
    BigBang(World world) {
        this(1000, world);
    }
    public void paintComponent(Graphics g) {
        world.draw(g);
    }
    public void actionPerformed(ActionEvent e) {
        world.update();
        this.repaint();
    }
    public void keyPressed(KeyEvent e) {
        world.keyPressed(e);
    }
    public void keyTyped(KeyEvent e) { }
    public void keyReleased(KeyEvent e) { }
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }

}
