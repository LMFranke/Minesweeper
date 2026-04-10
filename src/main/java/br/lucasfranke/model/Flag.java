package br.lucasfranke.model;

import java.awt.*;

public class Flag extends SquareObject {

    public Flag(int col, int row) {
        super(col, row);
        image = getImage("/image/red_flag");
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
