package org.example.game_classes.cell;

import org.example.game_classes.Side;

public class Cell {
    private Side condition;

    public Cell() {
        condition = null;
    }

    public Side getCondition() {
        return condition;
    }

    public void setCondition(Side condition) {
        this.condition = condition;
    }

    public String getConditionString() {
        return switch (condition) {
            case BlACK -> "B";
            case WHITE -> "W";
            case null -> " ";
        };

    }
}
