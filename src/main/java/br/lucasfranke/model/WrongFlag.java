package br.lucasfranke.model;

import java.awt.*;

public class WrongFlag extends SquareObject {

    public WrongFlag(int col, int row) {
        super(col, row);
        image = getImage("/image/wrong_flag");
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
