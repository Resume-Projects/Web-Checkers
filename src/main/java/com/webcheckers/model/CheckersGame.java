package com.webcheckers.model;

import com.webcheckers.application.GameController;

import java.util.logging.Logger;

/**
 * A single Checkers game
 *
 * @author Danny Gardner
 */
public class CheckersGame {
    private static final Logger LOG = Logger.getLogger(CheckersGame.class.getName());

    private Space[][] board;
    private BoardView boardView;

    public static final int BOARD_SIZE = 8;

    private Player redPlayer;
    private Player whitePlayer;

    public CheckersGame(Player redPlayer, Player whitePlayer) {
        LOG.fine("Game created");
        board = new Space[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = new Space(col, null);
            }
        }

        GameController.initializeBoard(board);


        //board[0][0] = new Space(0, new Piece(Piece.Type.SINGLE, Piece.Color.RED));

        boardView = new BoardView(board);
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
    }

    public Space[][] getBoard() {
        return board;
    }

    public BoardView getBoardView() {
        return boardView;
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
