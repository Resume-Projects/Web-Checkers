package com.webcheckers.application;

import com.webcheckers.model.Piece;
import com.webcheckers.model.Space;

import java.util.LinkedList;

/**
 * The GameController class
 */
public class GameController {
    /**
     * Creates a new board class
     * @param board an double array to initialize the board
     */
    public static void initializeBoard(Space[][] board, LinkedList<Piece> redPieces, LinkedList<Piece> whitePieces) {
        for (int col = 0; col < board.length; col++) {
            if (col % 2 == 1) {
                // First piece in col
                board[0][col].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
                whitePieces.add(board[0][col].getPiece());
                // Second piece
                board[2][col].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
                whitePieces.add(board[2][col].getPiece());
                // Third piece
                board[6][col].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.RED));
                redPieces.add(board[6][col].getPiece());
            } else {
                // First piece in col
                board[1][col].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
                whitePieces.add(board[1][col].getPiece());
                // Second piece
                board[5][col].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.RED));
                redPieces.add(board[5][col].getPiece());
                // Third piece
                board[7][col].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.RED));
                redPieces.add(board[7][col].getPiece());
            }
        }
    }
}
