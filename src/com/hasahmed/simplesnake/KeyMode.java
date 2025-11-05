package com.hasahmed.simplesnake;

import java.awt.event.KeyEvent;

public interface KeyMode {
    public boolean testUp(KeyEvent e);
    public boolean testDown(KeyEvent e);
    public boolean testLeft(KeyEvent e);
    public boolean testRight(KeyEvent e);
}
