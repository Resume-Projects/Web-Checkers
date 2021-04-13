package com.webcheckers.model;


public class SavedMove {

    private final Space[][] board = new Space[CheckersGame.BOARD_SIZE][CheckersGame.BOARD_SIZE];

    public SavedMove(Space[][] currentBoard, Piece.Color activeColor) {
        // copy over the current layout of the board to keep track for replay mode
        for( int i = 0; i < CheckersGame.BOARD_SIZE ; i++ ) {
            for( int j = 0; j < CheckersGame.BOARD_SIZE ; j++ ) {
                board[i][j] = currentBoard[i][j];
            }
        }
    }
}
