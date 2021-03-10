package com.webcheckers.application;

import com.webcheckers.model.Piece;
import com.webcheckers.model.Space;

public class GameController {
    public static void initializeBoard(Space[][] board) {
        for (int col = 0; col < board.length; col++) {
            if (col % 2 == 1) {
                board[0][col].setPiece(Piece.Type.SINGLE, Piece.Color.WHITE);
                board[0][col].setState(Space.State.OCCUPIED);
                board[2][col].setPiece(Piece.Type.SINGLE, Piece.Color.WHITE);
                board[2][col].setState(Space.State.OCCUPIED);
                board[6][col].setPiece(Piece.Type.SINGLE, Piece.Color.RED);
                board[6][col].setState(Space.State.OCCUPIED);
            } else {
                board[1][col].setPiece(Piece.Type.SINGLE, Piece.Color.WHITE);
                board[1][col].setState(Space.State.OCCUPIED);
                board[5][col].setPiece(Piece.Type.SINGLE, Piece.Color.RED);
                board[1][col].setState(Space.State.OCCUPIED);
                board[7][col].setPiece(Piece.Type.SINGLE, Piece.Color.RED);
                board[7][col].setState(Space.State.OCCUPIED);
            }
        }
    }
}
