package com.webcheckers.application;

import com.webcheckers.model.Piece;
import com.webcheckers.model.Space;

public class GameController {
    public static void initializeBoard(Space[][] board) {
        for (int col = 0; col < board.length; col++) {
            if (col % 2 == 1) {
                // First piece in col
                board[0][col].setPiece(Piece.Type.SINGLE, Piece.Color.WHITE);

                // Second piece
                board[2][col].setPiece(Piece.Type.SINGLE, Piece.Color.WHITE);

                // Third piece
                board[6][col].setPiece(Piece.Type.SINGLE, Piece.Color.RED);

            } else {
                // First piece in col
                board[1][col].setPiece(Piece.Type.SINGLE, Piece.Color.WHITE);

                // Second piece
                board[5][col].setPiece(Piece.Type.SINGLE, Piece.Color.RED);

                // Third piece
                board[7][col].setPiece(Piece.Type.SINGLE, Piece.Color.RED);
            }
        }
    }
}
