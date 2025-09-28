package org.example.game_classes.bots;

import org.example.game_classes.Side;
import org.example.game_classes.cell.Cell;

import java.util.ArrayList;
import java.util.Random;

public class BotPriority extends Bot{
    private final static int GIVE_POINTS_FOR_0_FRIENDLY_PIECE = 5;
    private final static int GIVE_POINTS_FOR_1_FRIENDLY_PIECE = 20;
    private final static int GIVE_POINTS_FOR_2_FRIENDLY_PIECE = 40;
    private final static int GIVE_POINTS_FOR_0_ENEMY_PIECE = 3;
    private final static int GIVE_POINTS_FOR_1_ENEMY_PIECE = 15;
    private final static int GIVE_POINTS_FOR_2_ENEMY_PIECE = 55;
    public BotPriority(Side side) {
        super(side);
    }


    @Override
    public void moveBot(Cell[][] board) {
        int[][] cellsPriority = new int[board.length][board.length];
        Side enemySide = (side == Side.WHITE) ? Side.BlACK : Side.WHITE;
        for (int y1 = 0; y1 < board.length; y1++) {
            for (int x1 = 0; x1 < board[y1].length; x1++) {
                int[][] potentialCombination = new int[4][2];
                potentialCombination[0][0] = x1;
                potentialCombination[0][1] = y1;
                for (int lengthOfSquare = 0; y1 + lengthOfSquare < board.length; lengthOfSquare++) {
                    for (int positionOnSquare = 0;
                         positionOnSquare <= lengthOfSquare
                                 && potentialCombination[1][1] < board.length
                                 && potentialCombination[2][0] >= 0
                                 && potentialCombination[2][1] < board.length
                                 && potentialCombination[3][0] >= 0
                                 && potentialCombination[3][1] < board.length;
                         positionOnSquare++,
                                 potentialCombination[1][0] = x1 + 1 + lengthOfSquare - positionOnSquare,
                                 potentialCombination[1][1] = y1 + positionOnSquare,
                                 potentialCombination[2][0] = x1 + 1 + lengthOfSquare - positionOnSquare * 2,
                                 potentialCombination[2][1] = y1 + 1 + lengthOfSquare,
                                 potentialCombination[3][0] = x1 - positionOnSquare,
                                 potentialCombination[3][1] = y1 + 1 + lengthOfSquare - positionOnSquare) {

                        if (potentialCombination[2][0] >= board.length || potentialCombination[1][0] >= board.length) {
                            continue;
                        }
                        int countFriendlyPieces = countPiecesOfSide(potentialCombination, board, side);
                        int countEnemyPieces = countPiecesOfSide(potentialCombination, board, enemySide);
                        if (countFriendlyPieces == 3 || countEnemyPieces == 3) {
                            for (int[] coordinate : potentialCombination) {
                                if (board[coordinate[1]][coordinate[0]].getCondition() == null) {
                                    board[coordinate[1]][coordinate[0]].setCondition(side);
                                    return;
                                }
                            }
                        }
                        if (countEnemyPieces == 0) {
                            int givingPoints = switch (countFriendlyPieces) {
                                case 0 -> GIVE_POINTS_FOR_0_FRIENDLY_PIECE;
                                case 1 -> GIVE_POINTS_FOR_1_FRIENDLY_PIECE;
                                case 2 -> GIVE_POINTS_FOR_2_FRIENDLY_PIECE;
                                default -> 0;
                            };
                            cellsPriority[y1][x1] += givingPoints;
                        }
                        if (countFriendlyPieces == 0) {
                            int givingPoints = switch (countEnemyPieces) {
                                case 0 -> GIVE_POINTS_FOR_0_ENEMY_PIECE;
                                case 1 -> GIVE_POINTS_FOR_1_ENEMY_PIECE;
                                case 2 -> GIVE_POINTS_FOR_2_ENEMY_PIECE;
                                default -> 0;
                            };
                            cellsPriority[y1][x1] += givingPoints;

                        }


                    }
                }
            }
        }
        for (int y1 = 0; y1 < board.length; y1++) {
            for (int x1 = 0; x1 < board[y1].length; x1++) {
                if (board[y1][x1].getCondition() != null) {
                    cellsPriority[y1][x1] = -1;
                }
            }
        }
        ArrayList<int[]> allMaxPriorityIndexes = findAllMaxIndexes(cellsPriority);
        Random random = new Random();
        int index = random.nextInt(allMaxPriorityIndexes.size());
        int[] coordinateOfNextMove = allMaxPriorityIndexes.get(index);
        board[coordinateOfNextMove[1]][coordinateOfNextMove[0]].setCondition(side);
        if (side == Side.BlACK) {
            System.out.println("B( " + coordinateOfNextMove[0] + ", " + coordinateOfNextMove[1] + ")");
        } else {
            System.out.println("W(" + coordinateOfNextMove[0] + ", " + coordinateOfNextMove[1] + ")");


        }


    }

    @Override
    public Side getSide() {
        return super.getSide();
    }

    @Override
    public void setSide(Side side) {
        super.setSide(side);
    }
    private int countPiecesOfSide(int[][] coordinates, Cell[][] board, Side side) {
        int count = 0;
        for (int[] coordinate : coordinates) {
            if (board[coordinate[1]][coordinate[0]].getCondition() == side) {
                count++;
            }
        }
        return count;
    }

    private static ArrayList<int[]> findAllMaxIndexes(int[][] matrix) {
        ArrayList<int[]> maxIndexes = new ArrayList<>();

        if (matrix == null || matrix.length == 0) {
            return maxIndexes;
        }

        int maxValue = matrix[0][0];

        for (int[] ints : matrix) {
            for (int anInt : ints) {
                if (anInt > maxValue) {
                    maxValue = anInt;
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == maxValue) {
                    maxIndexes.add(new int[]{i, j});
                }
            }
        }

        return maxIndexes;
    }
}
