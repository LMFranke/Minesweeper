package br.lucasfranke.model;

import java.awt.*;

import static br.lucasfranke.Board.HALF_SQUARE_SIZE;
import static br.lucasfranke.Board.SQUARE_SIZE;

public class NumberOfBomb extends SquareObject {

    private int bombs;
    private boolean color;

    public NumberOfBomb(int col, int row, int bombs, boolean color) {
        super(col, row);
        this.bombs = bombs;
        this.color = color;
    }

    @Override
    public void draw(Graphics2D g) {
        
        int x = col * SQUARE_SIZE;
        int y = row * SQUARE_SIZE;
        
        g.setColor(color ? new Color(220, 152, 79) : new Color(225, 177, 129));
        g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
        g.setFont(new Font("Times New Roman", Font.BOLD, 26));

        int adjustX = (int) (HALF_SQUARE_SIZE * 0.75);
        int adjustY = (int) (HALF_SQUARE_SIZE * 1.3);

        switch (bombs) {
            case 1: {
                g.setColor(new Color(79, 140, 220));
                g.drawString("1", x + adjustX, y + adjustY);
                break;
            }
            case 2: {
                g.setColor(new Color(119, 250, 79));
                g.drawString("2", x + adjustX, y + adjustY);
                break;
            }
            case 3: {
                g.setColor(new Color(168, 11, 56));
                g.drawString("3", x + adjustX, y + adjustY);
                break;
            }
            case 4: {
                g.setColor(new Color(30, 44, 197));
                g.drawString("4", x + adjustX, y + adjustY);
                break;
            }
            case 5: {
                g.setColor(new Color(197, 130, 30));
                g.drawString("5", x + adjustX, y + adjustY);
                break;
            }
            case 6: {
                g.setColor(new Color(30, 197, 191));
                g.drawString("6", x + adjustX, y + adjustY);
                break;
            }
            case 7: {
                g.setColor(new Color(5, 0, 0));
                g.drawString("7", x + adjustX, y + adjustY);
                break;
            }
            case 8: {
                g.setColor(new Color(190, 192, 223));
                g.drawString("8", x + adjustX, y + adjustY);
                break;
            }
        }

    }
}
