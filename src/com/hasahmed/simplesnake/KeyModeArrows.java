package com.hasahmed.simplesnake;

import java.awt.event.KeyEvent;

public class KeyModeArrows implements KeyMode {

    @Override
    public boolean testUp(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_UP;
    }

    @Override
    public boolean testDown(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_DOWN;
    }

    @Override
    public boolean testLeft(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_LEFT;
    }

    @Override
    public boolean testRight(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_RIGHT;
    }
}
