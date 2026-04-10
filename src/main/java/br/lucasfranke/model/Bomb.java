package br.lucasfranke.model;

import java.awt.*;

public class Bomb extends SquareObject {

    public Bomb(int col, int row) {
        super(col, row);
        image = getImage("/image/bomb");
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }

}
