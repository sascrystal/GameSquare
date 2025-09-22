package org.example.game_classes.bots;

import org.example.game_classes.Side;
import org.example.game_classes.cell.Cell;

import java.util.Random;

public class BotRandom extends Bot {
    public BotRandom(Side side) {
        super(side);
    }

    @Override
    public void moveBot(Cell[][] board) {
        int row, col;

        do {
            Random rand = new Random();
            row = rand.nextInt(board.length);
            col = rand.nextInt(board[row].length);
        } while (board[col][row].getCondition() != null);

        board[col][row].setCondition(side);

        if (side == Side.BlACK) {
            System.out.println("B( " + row + ", " + col + ")");
        } else {
            System.out.println("W(" + row + ", " + col + ")");


        }

    }
}