package com.hasahmed.simplesnake;

import java.awt.event.KeyEvent;

public class KeyModeWASD implements KeyMode{
    @Override
    public boolean testUp(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_W;
    }

    @Override
    public boolean testDown(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_S;
    }

    @Override
    public boolean testLeft(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_A;
    }

    @Override
    public boolean testRight(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_D;
    }
}
