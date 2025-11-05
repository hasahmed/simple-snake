package com.hasahmed.simplesnake;

import java.awt.event.KeyEvent;

public class KeyModeVim implements KeyMode {
    @Override
    public boolean testUp(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_K;
    }

    @Override
    public boolean testDown(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_J;
    }

    @Override
    public boolean testLeft(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_H;
    }

    @Override
    public boolean testRight(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_L;
    }
}
