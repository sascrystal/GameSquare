package org.example.game_classes.bots;

import org.example.game_classes.Side;
import org.example.game_classes.cell.Cell;

public abstract class   Bot {
    protected Side side;
    public abstract void moveBot(Cell[][] board,Side botSide);


}
