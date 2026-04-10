package br.lucasfranke;

import br.lucasfranke.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {

    public static final int WIDTH = 1100;
    public static final int HEIGHT = 800;
    private final int BOMB_NUMBER = 40;
    private final int FPS = 30;

    private boolean isFirstClick = true;
    private boolean isStarted = false;
    private boolean isWin = false;
    private boolean isStartGame = false;
    private boolean isItsOver = false;
    private boolean showWrongFlag = false;

    private int segCounter = 0;

    private final ActionController actionController = new ActionController();
    private final Board board = new Board();
    private Thread mainThread;

    Square[][] matrixBoard = new Square[Board.MAX_ROW][Board.MAX_COL];

    public GamePanel() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);

        addMouseListener(actionController);
        addMouseMotionListener(actionController);

        fillBoard();
        createTimer();
    }

    public void startGame() {
        mainThread = new Thread(this);
        mainThread.start();
    }

    private void createTimer() {
        Timer timer = new Timer(1000, e -> {
            if (isStarted) {
                segCounter++;
            }
        });
        timer.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (mainThread != null && mainThread.isAlive()) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {

        if (isWin) {
            mainThread = null;
        }

        if (isItsOver && !showWrongFlag) {
            showWrongFlag();
            showWrongFlag = true;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (isItsOver && actionController.pressed) {
            resetGame();
        } else if (actionController.pressed) {

            Square square = findSquare();

            if (square == null) {
                return;
            }

            if (isFirstClick) {
                square.isSafeSquare = true;
                isFirstClick = false;
                fillBoard();
                isStarted = true;
            }

            if (actionController.isRightButtonPressed && !(square.squareObject instanceof NumberOfBomb)) {
                flagSquare(square);
                return;
            }

            if (square.isBomb) {
                square.squareObject = new Bomb(square.col, square.row);
                isItsOver = true;
                isStarted = false;
                return;
            }
            unhideSquare(square, !square.isAlreadySeen);
            verifyWin();
        }
    }

    private void verifyWin() {
        int counter = Board.MAX_COL * Board.MAX_ROW - BOMB_NUMBER;
        for (int i = 0; i < Board.MAX_ROW; i++) {
            for (int j = 0; j < Board.MAX_COL; j++) {
                Square square = matrixBoard[i][j];
                if (square.squareObject instanceof NumberOfBomb) {
                    counter--;
                    if (counter == 0) {
                        isWin = true;
                    }
                }
            }
        }
    }

    private void showWrongFlag() {
        for (int i = 0; i < Board.MAX_ROW; i++) {
            for (int j = 0; j < Board.MAX_COL; j++) {
                Square square = matrixBoard[i][j];
                if (square.squareObject instanceof Flag && !square.isBomb) {
                    square.squareObject = new WrongFlag(square.col, square.row);
                }
            }
        }

    }

    private Square findSquare() {
        for (int row = 0; row < Board.MAX_ROW; row++) {
            for (int col = 0; col < Board.MAX_COL; col++) {
                Square square = matrixBoard[row][col];
                if (square.col == actionController.x / Board.SQUARE_SIZE && square.row == actionController.y / Board.SQUARE_SIZE && !square.isAlreadyShowed) {
                    return square;
                }
            }
        }
        return null;
    }

    private void unhideSquare(Square square, boolean seenTarget) {
        if (square.isSafeSquare && square.isAlreadySeen != seenTarget) {
            square.isAlreadySeen = seenTarget;

            if (square.row - 1 >= 0) {
                unhideSquare(matrixBoard[square.row - 1][square.col], seenTarget);
            }
            if (square.row + 1 < Board.MAX_ROW) {
                unhideSquare(matrixBoard[square.row + 1][square.col], seenTarget);
            }
            if (square.col - 1 >= 0) {
                unhideSquare(matrixBoard[square.row][square.col - 1], seenTarget);
            }
            if (square.col + 1 < Board.MAX_COL) {
                unhideSquare(matrixBoard[square.row][square.col + 1], seenTarget);
            }
            if (square.col + 1 < Board.MAX_COL && square.row + 1 < Board.MAX_ROW) {
                unhideSquare(matrixBoard[square.row + 1][square.col + 1], seenTarget);
            }
            if (square.col - 1 >= 0 && square.row - 1 >= 0) {
                unhideSquare(matrixBoard[square.row - 1][square.col - 1], seenTarget);
            }
            if (square.col + 1 < Board.MAX_COL && square.row - 1 >= 0) {
                unhideSquare(matrixBoard[square.row - 1][square.col + 1], seenTarget);
            }
            if (square.col - 1 >= 0 && square.row + 1 < Board.MAX_ROW) {
                unhideSquare(matrixBoard[square.row + 1][square.col - 1], seenTarget);
            }

        }
        if (!square.isBomb) {
            square.squareObject = new NumberOfBomb(square.col, square.row, square.bombs, square.color);
        }
    }

    private void flagSquare(Square square) {
        square.isFlagged = !square.isFlagged;
        square.squareObject = square.isFlagged
                ? new Flag(square.col, square.row)
                : null;

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillBoard() {
        isItsOver = false;
        isWin = false;
        isStartGame = true;
        segCounter = 0;

        boolean color = true;
        for (int row = 0; row < Board.MAX_ROW; row++) {
            for (int col = 0; col < Board.MAX_COL; col++) {
                matrixBoard[row][col] = new Square(row, col, color);
                color = !color;
            }
            color = !color;
        }

        if (isFirstClick) {
            return;
        }

        int bombCounter = 0;
        Random rand = new Random();
        while (bombCounter < BOMB_NUMBER) {

            int row = rand.nextInt(0, Board.MAX_ROW);
            int col = rand.nextInt(0, Board.MAX_COL);
            Square square = matrixBoard[row][col];
            if (!square.isBomb && !square.isSafeSquare) {
                square.isBomb = true;
                bombCounter++;
            }

        }

        for (int row = 0; row < Board.MAX_ROW; row++) {
            for (int col = 0; col < Board.MAX_COL; col++) {
                Square square = matrixBoard[row][col];
                if (howMuchBomb(square) == 0) {
                    square.isSafeSquare = true;
                }
            }
        }

    }

    private int howMuchBomb(Square square) {
        int result = 0;

        if (square.row - 1 >= 0 && square.col - 1 >= 0 && matrixBoard[square.row - 1][square.col - 1].isBomb) {
            result++;
        }
        if (square.col - 1 >= 0 && matrixBoard[square.row][square.col - 1].isBomb) {
            result++;
        }
        if (square.col - 1 >= 0 && square.row + 1 < Board.MAX_ROW && matrixBoard[square.row + 1][square.col - 1].isBomb) {
            result++;
        }

        if (square.row - 1 >= 0 && matrixBoard[square.row - 1][square.col].isBomb) {
            result++;
        }
        if (square.row + 1 < Board.MAX_ROW && matrixBoard[square.row + 1][square.col].isBomb) {
            result++;
        }

        if (square.row - 1 >= 0 && square.col + 1 < Board.MAX_COL && matrixBoard[square.row - 1][square.col + 1].isBomb) {
            result++;
        }
        if (square.col + 1 < Board.MAX_COL && matrixBoard[square.row][square.col + 1].isBomb) {
            result++;
        }
        if (square.row + 1 < Board.MAX_ROW && square.col + 1 < Board.MAX_COL && matrixBoard[square.row + 1][square.col + 1].isBomb) {
            result++;
        }

        square.bombs = result;

        return result;
    }

    private void resetGame() {
        isFirstClick = true;
        showWrongFlag = false;
        fillBoard();
    }

    @Override
    protected synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        board.draw(g2d);

        if (isWin) {
            g2d.drawString("Congratulations you win!! ", 840, 150);
        }

        if (isItsOver) {
            g2d.drawString("You lose! Clicked on an bomb! ", 840, 150);
        }

        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.setColor(Color.WHITE);
        g.drawString("Seconds: " + segCounter, 840, 250);

        for (int row = 0; row < Board.MAX_ROW; row++) {
            for (int col = 0; col < Board.MAX_COL; col++) {
                Square square = matrixBoard[row][col];
                if (square.squareObject != null) {
                    square.squareObject.draw(g2d);
                }
            }
        }

        if (isStartGame) {
            g2d.setColor(Color.black);
            g2d.fillRect(840, 125, 200, 100);
            isStartGame = false;
        }

    }
}
