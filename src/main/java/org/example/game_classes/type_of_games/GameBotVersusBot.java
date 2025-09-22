package org.example.game_classes.type_of_games;

import org.example.game_classes.Side;
import org.example.game_classes.bots.Bot;

import static org.example.game_classes.Side.BlACK;

public class GameBotVersusBot extends Game{
    private Side botSide1, botSide2;
    private Bot bot1, bot2;

    public GameBotVersusBot(int boardSize, Side turn, Side botSide1, Side botSide2, Bot bot1, Bot bot2) {
        super(boardSize, turn);
        this.botSide1 = botSide1;
        this.botSide2 = botSide2;
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
                if (botSide1 == BlACK) {
                    System.out.println("Ход черных, ход бота1");
                    bot1.moveBot(board,botSide1);
                    turn = Side.WHITE;

                } else {
                    System.out.println("Ход черных, ход бот2");
                    bot2.moveBot(board,botSide2);
                    turn = Side.WHITE;

                }


            } else {
                if (botSide1 == Side.WHITE) {
                    System.out.println("Ход белых, ход бота1");
                    bot1.moveBot(board,botSide1);
                    turn = BlACK;

                } else {
                    System.out.println("Ход белых, ход бота2");
                    bot2.moveBot(board,botSide2);
                    turn = BlACK;

                }

            }


        }
        sayWinner();
    }
}
