package com.webcheckers.model;

import com.webcheckers.application.GameController;

import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Logger;

/**
 * Represents a single WebCheckers game between two players
 *
 * @author Danny Gardner
 */
public class CheckersGame {
    private static final Logger LOG = Logger.getLogger(CheckersGame.class.getName());

    // Game Attributes

    private Space[][] board;
    private BoardView boardView;
    public static final int BOARD_SIZE = 8;
    private Player redPlayer;
    private Player whitePlayer;

    /**
     * Constructor for a Checkers Game
     *
     * @param redPlayer
     *      The starting player, player one, having red Pieces
     * @param whitePlayer
     *      Player two, having white Pieces
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

    public Space[][] getBoard() {
        return board;
    }

    public BoardView getRedBoardView() {
        return new BoardView(board);
    }

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

    // Player accessors

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
