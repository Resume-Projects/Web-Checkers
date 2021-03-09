package com.webcheckers.model;

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
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
    }

    public Space[][] getBoard() {
        return board;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }
}
