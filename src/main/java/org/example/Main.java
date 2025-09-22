package org.example;


import org.example.game_classes.Side;
import org.example.game_classes.bots.BotRandom;
import org.example.game_classes.type_of_games.Game;
import org.example.game_classes.type_of_games.GameWithBot;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        System.setOut(new java.io.PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setProperty("file.encoding", "UTF-8");
        System.out.println("Введите команду");
        Scanner scanner = new Scanner(System.in);
        Game game;
        while (true){
            String command = scanner.nextLine();
            command = command.toUpperCase();
            if(command.startsWith("HELP")){
                printHelp();
                continue;
            }
            if(command.startsWith("EXIT")){
                System.exit(0);
            }
            if(command.startsWith("GAME")){
                String[] parts = command.split("\\s+");
                Side firstSide, secondSide;
                int board = Integer.parseInt(parts[1].replace(",", ""));
                String firstType = parts[2].replace(",", "");
                String firstColor = parts[3].replace(",", "");
                String secondType = parts[4].replace(",", "");
                String secondColor = parts[5].replace(",", "");
                switch (firstColor){
                    case "WHITE":
                        firstSide = Side.WHITE;
                        break;
                    default: firstSide = Side.BlACK;
                }
                switch (secondColor){
                    case "WHITE":
                        secondSide = Side.WHITE;
                        break;
                    default: secondSide = Side.BlACK;
                }
                if (firstSide == secondSide) {
                    System.out.println("цвета и игроков одинаковы");
                    break;
                }

                if(firstType.equals("USER") && secondType.equals("USER")){
                    game = new Game(board,firstSide);
                }else if(firstType.equals("COMP") && secondType.equals("USER")) {
                    game = new GameWithBot(board, firstSide, firstSide, new BotRandom());
                }else if(firstType.equals("USER") && secondType.equals("COMP")) {
                    game = new GameWithBot(board, firstSide, secondSide, new BotRandom());
                }else {
                    System.out.println("Неизвестная команда");
                    break;
                }
                game.gamePlay();
                break;



            }

        }






    }
    private static void printHelp(){
        System.out.println("HELP- все команды");
        System.out.println("GAME N TYPE C, TYPE C  - включить игру где N - размер карты, type - типа игрока, C - цвет");
        System.out.println("EXIT - выйти из приложения");
    }
}