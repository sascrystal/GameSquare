package org.example.game_classes;

import java.awt.*;

public class Cell {
    private CellCondition condition;

    public Cell() {
        this.condition =CellCondition.EMPTY;
    }

    public CellCondition getCondition() {
        return condition;
    }
    public String getConditionString() {
        return switch (condition) {
            case EMPTY -> " ";
            case BLACK -> "B";
            case WHITE -> "W";
        };

    }

    public void setCondition(CellCondition condition) {
        this.condition = condition;
    }
}
