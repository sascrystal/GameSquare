package org.example.game_classes.bots;

import org.example.game_classes.Side;
import org.example.game_classes.cell.Cell;

public abstract class Bot {
    protected Side side;

    public Bot(Side side) {
        this.side = side;
    }

    public abstract void moveBot(Cell[][] board);

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }
}
