package br.lucasfranke;

import java.awt.*;

public class Board {

    public static int MAX_COL = 20;
    public static int MAX_ROW = 20;

    public static final int SQUARE_SIZE = 40;
    public static final int HALF_SQUARE_SIZE = SQUARE_SIZE / 2;

    public void draw(Graphics2D g) {
        boolean switchColor = true;
        for (int row = 0; row < MAX_ROW; row++) {
            for (int col = 0; col < MAX_COL; col++) {
                g.setColor(switchColor
                        ? new Color(168, 210, 125)
                        : new Color(93, 175, 70));

                g.fillRect(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                switchColor = !switchColor;
            }
            switchColor = !switchColor;
        }
    }

}
