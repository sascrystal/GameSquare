package org.example.game_classes.type_of_games;

import org.example.game_classes.Side;
import org.example.game_classes.bots.Bot;

import static org.example.game_classes.Side.BlACK;

public class GameBotVersusBot extends Game{
    private final Bot bot1;
    private final Bot bot2;

    public GameBotVersusBot(int boardSize, Side turn, Bot bot1, Bot bot2) {
        super(boardSize, turn);
        this.bot1 = bot1;
        this.bot2 = bot2;

    }

    @Override
    public void gamePlay() {
        while (winner == null) {
            printBoard();
            winner = checkWinner();
            if(winner != null) {
                break;
            }
            if (turn == BlACK) {
                if (bot1.getSide() == BlACK) {
                    System.out.println("Ход черных, ход бота1");
                    bot1.moveBot(board);

                } else {
                    System.out.println("Ход черных, ход бот2");
                    bot2.moveBot(board);

                }
                turn = Side.WHITE;


            } else {
                if (bot1.getSide() == Side.WHITE) {
                    System.out.println("Ход белых, ход бота1");
                    bot1.moveBot(board);

                } else {
                    System.out.println("Ход белых, ход бота2");
                    bot2.moveBot(board);

                }
                turn = BlACK;

            }


        }
        sayWinner();
    }
}
