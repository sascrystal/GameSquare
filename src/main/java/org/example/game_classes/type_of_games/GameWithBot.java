package org.example.game_classes.type_of_games;

import org.example.game_classes.Side;
import org.example.game_classes.bots.Bot;


public class GameWithBot extends Game {
    private Side botSide;
    private Bot bot;

    public GameWithBot(int boardSize, Side turn, Side botSide,Bot bot) {
        super(boardSize, turn);
        this.botSide = botSide;
        this.bot = bot;

    }


    @Override
    public void gamePlay() {
        while (winner == null) {
            printBoard();
            if (turn == Side.BlACK) {
                if (botSide == Side.BlACK) {
                    System.out.println("Ход черных, ход бота");
                    bot.moveBot(board,botSide);
                    turn = Side.WHITE;
                } else {
                    System.out.println("Ход черных, ход игрока");
                    playerController();
                    turn = Side.WHITE;
                }
                winner = checkWinner();
            } else {
                if (botSide == Side.WHITE) {
                    System.out.println("Ход белых, ход бота");
                    bot.moveBot(board,botSide);
                    turn = Side.BlACK;
                } else {
                    System.out.println("Ход белых, ход игрока");
                    playerController();
                    turn = Side.BlACK;
                }
                winner = checkWinner();
            }


        }
        switch (winner) {
            case BlACK:
                System.out.println("ПОБЕДА ЧЕРНЫХ");
                break;
            case WHITE:
                System.out.println("ПОБЕДА БЕЛЫХ");
                break;
        }

    }

    private void playerController() {
        while (true) {
            String command = scanner.nextLine();
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
                            break;
                        } else {
                            board[y][x].setCondition(Side.WHITE);
                            break;
                        }


                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: X и Y должны быть числами");
                    }
                } else {
                    System.out.println("Неверный формат команды");
                }

            }

        }
    }
}
