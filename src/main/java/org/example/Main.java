package org.example;

import org.example.game_classes.Game;
import org.example.game_classes.GameCondition;

import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args){
        System.setOut(new java.io.PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setProperty("file.encoding", "UTF-8");
        Game game = new Game(5, GameCondition.TURN_BLACK);
        game.gamePlay();

    }
}