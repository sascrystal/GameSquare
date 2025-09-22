package org.example.game_classes;

import java.util.Scanner;

public class Game {
    private Cell[][] board;
    private GameCondition condition;
    private Scanner scanner;
    public Winner winner;

    public Game(int boardSize,GameCondition condition) {
        scanner = new Scanner(System.in);
        board = new Cell[boardSize][boardSize];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell();
            }
        }
        this.condition = condition;

    }
    private void printBoard() {
        System.out.print("   ");
        for (int i = 0; i < board.length; i++) {
            System.out.print(i+ "   ");
        }
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.print(i);
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(" ["+board[i][j].getConditionString()+"]");
            }
            System.out.println();
        }

    }
    public void gamePlay(){
        while (winner == null){
            printBoard();
            if (condition == GameCondition.TURN_BLACK){
                System.out.println("Ход черных, поставьте фишку");
            }
            if (condition == GameCondition.TURN_WHITE){
                System.out.println("Ход белых, поставьте фишку");
            }
            String command = scanner.nextLine();
            if(command.startsWith("HELP")){
                printHelp();
                continue;
            }
            if(command.startsWith("EXIT")){
                System.exit(0);
            }
            if(command.startsWith("MOVE")){
                int x, y;
                String[] parts = command.split("\\s+");

                if (parts.length >= 3 && parts[0].equalsIgnoreCase("MOVE")) {
                    try {

                        x = Integer.parseInt(parts[1].replace(",", ""));
                        y = Integer.parseInt(parts[2].replace(",", ""));
                        if (condition == GameCondition.TURN_BLACK){
                            board[y][x].setCondition(CellCondition.BLACK);
                            condition = GameCondition.TURN_WHITE;
                        }else {
                            board[y][x].setCondition(CellCondition.WHITE);
                            condition = GameCondition.TURN_BLACK;
                        }

                        winner = checkWinner();

                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: X и Y должны быть числами");
                    }
                } else {
                    System.out.println("Неверный формат команды");
                }
            }

        }
        switch(winner){
            case BLACK:
                System.out.println("ПОБЕДА ЧЕРНЫХ");
                break;
                case WHITE:
                    System.out.println("ПОБЕДА БЕЛЫХ");
                    break;
        }
    }

    private void printHelp() {
        System.out.println("HELP - выдача всех команд");
        System.out.println("EXIT - выход из программы");
        System.out.println("MOVE X, Y - выставление фишки на координаты x,y");
    }
    public enum Winner{
        BLACK, WHITE
    }
    private Winner checkWinner(){
        for (int i = 0; i < board.length-1; i++) {
            for (int j = 0; j < board[i].length-1; j++) {
                if(board[i][j].getCondition() == CellCondition.BLACK){
                    if(board[i+1][j].getCondition() == CellCondition.BLACK
                            && board[i][j+1].getCondition() == CellCondition.BLACK
                            && board[i+1][j+1].getCondition() == CellCondition.BLACK){
                        return Winner.BLACK;
                    }
                }
                if(board[i][j].getCondition() == CellCondition.WHITE){
                    if(board[i+1][j].getCondition() == CellCondition.WHITE
                            && board[i][j+1].getCondition() == CellCondition.WHITE
                            && board[i+1][j+1].getCondition() == CellCondition.WHITE){
                        return Winner.WHITE;
                    }
                }
            }
        }
        return null;
    }

}
