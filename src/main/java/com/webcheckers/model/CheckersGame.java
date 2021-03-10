package com.webcheckers.model;

import com.webcheckers.application.GameController;

import java.util.Arrays;
import java.util.Collections;
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

    /**
     * The CheckersGame data type
     *
     * @param redPlayer the player with the red pieces
     * @param whitePlayer the player with the white pieces
     */
    public CheckersGame(Player redPlayer, Player whitePlayer) {
        LOG.fine("Game created");
        board = new Space[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = new Space(col, Space.State.OPEN);
            }
        }

        GameController.initializeBoard(board);

        //board[0][0] = new Space(0, new Piece(Piece.Type.SINGLE, Piece.Color.RED));

        boardView = new BoardView(board);
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
    }

    /**
     * Get the board of the game
     *
     * @return the game board
     */
    public Space[][] getBoard() {
        return board;
    }

    /**
     * Get the board for the red player
     *
     * @return the game board
     */
    public BoardView getRedBoardView() {
        return new BoardView(board);
    }

    /**
     * Get the board for the white player.
     * This reverses the order of all the rows of the board from BoardView.
     *
     * @return the game board.
     */
    public BoardView getWhiteBoardView() {
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

    /**
     * Sets the board of the red player
     *
     * @param player the player of the red pieces
     */
    public void setRedPlayer(Player player) {
        redPlayer = player;
    }

    /**
     * Gets the player of the red pieces
     *
     * @return the player of the red pieces
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     * Sets the player of the white pieces
     *
     * @param player the player of the white pieces
     */
    public void setWhitePlayer(Player player) {
        whitePlayer = player;
    }

    /**
     * Gets the player of the white pieces
     *
     * @return the player of the white pieces
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

}
