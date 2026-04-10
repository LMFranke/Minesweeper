package br.lucasfranke.model;

public class Square {

    public int row;
    public int col;
    public int bombs;
    public boolean isAlreadySeen = false;
    public boolean color = false;
    public boolean isSafeSquare = false;
    public boolean isBomb = false;
    public boolean isAlreadyShowed = false;
    public boolean isFlagged = false;
    public SquareObject squareObject;

    public Square(int row, int col, boolean color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Square{" +
                "bombs=" + bombs +
                ", col=" + col +
                ", row=" + row +
                '}';
    }
}
