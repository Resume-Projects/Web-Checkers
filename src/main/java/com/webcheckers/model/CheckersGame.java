package com.webcheckers.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * A single Checkers game
 *
 * @author Danny Gardner
 */
public class CheckersGame {
    private static final Logger LOG = Logger.getLogger(CheckersGame.class.getName());

    private Space[][] board;

    public static final int BOARD_SIZE = 8;

    private Player redPlayer;
    private Player whitePlayer;

    public CheckersGame(Player redPlayer, Player whitePlayer) {
        LOG.fine("Game created");
        board = new Space[BOARD_SIZE][BOARD_SIZE];
        for(int row = 0; row < BOARD_SIZE; row++) {
            for(int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = new Space(col, null);
            }
        }

        board[0][0] = new Space(0, new Piece(Piece.Type.SINGLE, Piece.Color.RED));

        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
    }

    public Space[][] getBoard() {
        return board;
    }

    public BoardView getWhiteBoardView() {
        return new BoardView(board);
    }

    public BoardView getRedBoardView() {
        Space[][] tempBoard = new Space[8][8];
        for (int i = 0; i < board.length; i++) {
            tempBoard[i] = Arrays.copyOf(board[i], board[i].length);
        }

        Collections.reverse(Arrays.asList(tempBoard));
        for(int i = 0; i < 8; i++) {
            Collections.reverse(Arrays.asList(tempBoard[i]));
        }
        return new BoardView(tempBoard);
    }

    public void setRedPlayer(Player player) {
        redPlayer = player;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public void setWhitePlayer(Player player) {
        whitePlayer = player;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

}
