package com.hasahmed.simplesnake;

import java.awt.event.KeyEvent;

public class KeyModeIJKL implements KeyMode {

    @Override
    public boolean testUp(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_I;
    }

    @Override
    public boolean testDown(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_K;
    }

    @Override
    public boolean testLeft(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_J;
    }

    @Override
    public boolean testRight(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_L;
    }
}
