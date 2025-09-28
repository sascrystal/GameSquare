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
    private final int[][] winningCellsCoordinates;

    public Game(int boardSize, Side turn) {
        scanner = new Scanner(System.in);
        turns = 0;
        winningCellsCoordinates = new int[4][4];
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
            System.out.print(i + "   ");
        }
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.print(i);
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(" [" + board[i][j].getConditionString() + "]");
            }
            System.out.println();
        }

    }

    public void gamePlay() {
        while (winner == null) {
            printBoard();
            winner = checkWinner();
            if (winner != null) {
                break;
            }

            if (turn == Side.BlACK) {
                System.out.println("Ход черных, поставьте фишку");
            }
            if (turn == Side.WHITE) {
                System.out.println("Ход белых, поставьте фишку");
            }
            String command = scanner.nextLine().toUpperCase();

            if (command.startsWith("HELP")) {
                printHelp();
                continue;
            }
            if (command.startsWith("EXIT")) {
                System.exit(0);
            }
            if (command.startsWith("MOVE")) {
                int x, y;
                String[] parts = command.split("\\s+");

                if (parts.length >= 3 && parts[0].equalsIgnoreCase("MOVE")) {
                    try {

                        x = Integer.parseInt(parts[1].replace(",", ""));
                        y = Integer.parseInt(parts[2].replace(",", ""));
                        if (y >= board.length || x >= board[y].length || x < 0 || y < 0) {
                            System.out.println("Выход за рамки board");
                            continue;
                        }
                        if (board[y][x].getCondition() == Side.BlACK || board[y][x].getCondition() == Side.WHITE) {
                            System.out.println("Клетка не пуста");
                            continue;
                        }

                        if (turn == Side.BlACK) {
                            board[y][x].setCondition(Side.BlACK);
                            turn = Side.WHITE;
                        } else {
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

    protected void sayWinner() {
        switch (winner) {
            case BLACK:
                System.out.println("Game finished. Black wins");
                printWinningCells();
                break;
            case WHITE:
                System.out.println("Game finished. White wins");
                printWinningCells();
                break;
            case NOBODY:
                System.out.println("Game finished. Draw");
        }
        System.out.println();

    }

    protected void printWinningCells() {
        System.out.println("Winning cells: ");
        for (int i = 0; i < 4; i++) {
            System.out.print("(" + winningCellsCoordinates[i][0] + "," + winningCellsCoordinates[i][1] + ")");
        }
    }

    public void printHelp() {
        System.out.println("HELP - выдача всех команд");
        System.out.println("EXIT - выход из программы");
        System.out.println("MOVE X, Y - выставление фишки на координаты x,y");
    }

    protected SideWinner checkWinner() {
        turns++;
        if (turns > (board.length) * (board.length)) {
            return SideWinner.NOBODY;
        }

        for (int y1 = 0; y1 < board.length - 1; y1++) {
            for (int x1 = 0; x1 < board[y1].length - 1; x1++) {
                Side side = board[y1][x1].getCondition();
                for (int lengthOfCube = 0; side != null
                        && y1 + lengthOfCube < board.length; lengthOfCube++) {

                    for (int positionOnCube = 0,
                         x2 = x1 + 1 + lengthOfCube - positionOnCube,
                         y2 = y1 + positionOnCube,
                         x3 = x1 + 1 + lengthOfCube - positionOnCube * 2,
                         y3 = y1 + 1 + lengthOfCube,
                         x4 = x1 - positionOnCube,
                         y4 = y1 + 1 + lengthOfCube - positionOnCube;

                         positionOnCube <= lengthOfCube
                            && y2 < board.length
                            && y3 < board.length
                            && x3 >= 0
                            && y4 < board.length
                            && x4 >= 0;

                         positionOnCube++,
                                 x2 = x1 + 1 + lengthOfCube - positionOnCube,
                                 y2 = y1 + positionOnCube,
                                 x3 = x1 + 1 + lengthOfCube - positionOnCube * 2,
                                 y3 = y1 + 1 + lengthOfCube,
                                 x4 = x1 - positionOnCube,
                                 y4 = y1 + 1 + lengthOfCube - positionOnCube) {
                        if(x3 >= board.length || x2 >= board.length){
                            continue;
                        }




                        if (board[y2][x2].getCondition() == side
                                && board[y3][x3].getCondition() == side
                                && board[y4][x4].getCondition() == side) {

                            winningCellsCoordinates[0][0] = x1;
                            winningCellsCoordinates[0][1] = y1;
                            winningCellsCoordinates[1][0] = x2;
                            winningCellsCoordinates[1][1] = y2;
                            winningCellsCoordinates[2][0] = x3;
                            winningCellsCoordinates[2][1] = y3;
                            winningCellsCoordinates[3][0] = x4;
                            winningCellsCoordinates[3][1] = y4;


                            switch (side) {
                                case BlACK -> {
                                    return SideWinner.BLACK;
                                }
                                case WHITE -> {
                                    return SideWinner.WHITE;
                                }
                            }
                        }

                    }


                }


            }




        }
        return null;
    }

    private enum SideWinner {
        WHITE, BLACK, NOBODY
    }

}
