package br.lucasfranke;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ActionController extends MouseAdapter {

    public int x;
    public int y;

    public boolean pressed;
    public boolean isRightButtonPressed = false;

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        isRightButtonPressed = e.getButton() == MouseEvent.BUTTON3;
        x = e.getX();
        y = e.getY();
        pressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        isRightButtonPressed = false;
        pressed = false;
    }

}
