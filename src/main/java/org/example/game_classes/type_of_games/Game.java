package org.example.game_classes.type_of_games;

import org.example.game_classes.Side;
import org.example.game_classes.cell.Cell;

import java.util.Scanner;

public class Game {
    protected Cell[][] board;
    protected Side turn;
    protected Scanner scanner;
    protected SideWinner winner;
    private int turns;

    public Game(int boardSize,Side turn) {
        scanner = new Scanner(System.in);
        turns = 0;
        board = new Cell[boardSize][boardSize];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell();
            }
        }
        this.turn = turn;

    }
    public void printBoard() {
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
            winner = checkWinner();
            if(winner != null) {
                break;
            }

            if (turn == Side.BlACK){
                System.out.println("Ход черных, поставьте фишку");
            }
            if (turn == Side.WHITE){
                System.out.println("Ход белых, поставьте фишку");
            }
            String command = scanner.nextLine().toUpperCase();

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
                        if (y>=board.length || x>=board[y].length || x<0 || y<0){
                            System.out.println("Выход за рамки board");
                            continue;
                        }
                        if(board[y][x].getCondition()== Side.BlACK || board[y][x].getCondition()== Side.WHITE){
                            System.out.println("Клетка не пуста");
                            continue;
                        }

                        if (turn == Side.BlACK){
                            board[y][x].setCondition(Side.BlACK);
                            turn = Side.WHITE;
                        }else {
                            board[y][x].setCondition(Side.WHITE);
                            turn = Side.BlACK;
                        }


                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: X и Y должны быть числами");
                    }
                } else {
                    System.out.println("Неверный формат команды");
                }
            }


        }
        sayWinner();


    }
    protected void sayWinner(){
        switch(winner){
            case BLACK:
                System.out.println("Game finished. Black wins");
                break;
            case WHITE:
                System.out.println("Game finished. White wins");
                break;
            case NOBODY:
                System.out.println("Game finished. Draw");
        }
    }

    public void printHelp() {
        System.out.println("HELP - выдача всех команд");
        System.out.println("EXIT - выход из программы");
        System.out.println("MOVE X, Y - выставление фишки на координаты x,y");
    }

    protected SideWinner checkWinner(){
        turns++;
        if(turns> (board.length)*(board.length)){
            return SideWinner.NOBODY;
        }

        for (int i = 0; i < board.length-1; i++) {


            for (int j = 0; j < board[i].length-1; j++) {

                for (int step = 1; step+i<board.length && step+j<board.length; step++){
                    if(board[i][j].getCondition() == Side.BlACK){
                        if(board[i+step][j].getCondition() == Side.BlACK
                                && board[i][j+step].getCondition() == Side.BlACK
                                && board[i+step][j+step].getCondition() == Side.BlACK){
                            return SideWinner.BLACK;
                        }
                    }
                    if(board[i][j].getCondition() == Side.WHITE){
                        if(board[i+step][j].getCondition() == Side.WHITE
                                && board[i][j+step].getCondition() == Side.WHITE
                                && board[i+step][j+step].getCondition() == Side.WHITE){
                            return SideWinner.WHITE;
                        }
                    }
                }
                //можеты быть можно сделать более оптимизировано
                for (int step = 2; i+step<board.length && j+step/2<board.length && j-step/2 >=0; step+=2){
                    if(board[i][j].getCondition() == Side.BlACK){
                        if(board[i+step][j].getCondition() == Side.BlACK
                                && board[i+step/2][j+step/2].getCondition() == Side.BlACK
                                && board[i+step/2][j-step/2].getCondition() == Side.BlACK){
                            return SideWinner.BLACK;
                        }
                    }
                    if(board[i][j].getCondition() == Side.WHITE){
                        if(board[i+step][j].getCondition() == Side.WHITE
                                && board[i+step/2][j+step/2].getCondition() == Side.WHITE
                                && board[i+step/2][j-step/2].getCondition() == Side.WHITE){
                            return SideWinner.WHITE;
                        }
                    }
                }



            }

        }

        return null;
    }
    private enum SideWinner{
        WHITE,BLACK,NOBODY
    }

}
