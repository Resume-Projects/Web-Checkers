package com.webcheckers.application;

import com.webcheckers.model.*;

import java.util.Arrays;
import java.util.LinkedList;

public class MoveChecker {
    public static boolean playerCanMove(int BOARD_SIZE, Space[][] board, Piece.Color activeColor) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col].getPiece() != null && board[row][col].getPieceColor() == activeColor) {
                    if (jumpCanBeMade(row, col, board, BOARD_SIZE, activeColor))
                        return true;

                    boolean canMove;
                    if (board[row][col].getPieceType() == Piece.Type.KING) {
                        canMove =
                                (row - 1 >= 0 && col - 1 >= 0 && board[row - 1][col - 1].getPiece() == null) ||
                                (row - 1 >= 0 && col + 1 < BOARD_SIZE && board[row - 1][col + 1].getPiece() == null) ||
                                (row + 1 < BOARD_SIZE && col - 1 >= 0 && board[row + 1][col - 1].getPiece() == null) ||
                                (row + 1 < BOARD_SIZE && col + 1 < BOARD_SIZE && board[row + 1][col + 1].getPiece() == null);
                    } else if (activeColor == Piece.Color.RED) {
                        canMove =
                                (row - 1 >= 0 && col - 1 >= 0 && board[row - 1][col - 1].getPiece() == null) ||
                                (row - 1 >= 0 && col + 1 < BOARD_SIZE && board[row - 1][col + 1].getPiece() == null);
                    } else { //White and not a king
                        canMove =
                                (row + 1 < BOARD_SIZE && col - 1 >= 0 && board[row + 1][col - 1].getPiece() == null) ||
                                (row + 1 < BOARD_SIZE && col + 1 < BOARD_SIZE && board[row + 1][col + 1].getPiece() == null);
                    }

                    if (canMove)
                        return true;
                }
            }
        }
        return false;
    }

    public static boolean isValidSimpleMove(Position start, Position end, Piece.Type pieceType, Piece.Color activeColor) {
        int deltaRow = end.getRow() - start.getRow();
        int deltaCol = end.getCell() - start.getCell();

        if (Math.abs(deltaCol) != 1)
            return false;

        if (pieceType == Piece.Type.KING) {
            return Math.abs(deltaRow) == 1;
        } else if (activeColor == Piece.Color.RED) {
            return deltaRow == -1;
        } else { //Not a king and active color is white
            return deltaRow == 1;
        }
    }

    /**
     * determines if the move made was a jump
     *
     * @param start the starting position
     * @param end   the ending position
     * @return whether the move was a jump
     */
    public static boolean isValidJump(Position start, Position end, Piece.Type pieceType, Space[][] board, Piece.Color activeColor) {
        int deltaRow = end.getRow() - start.getRow();
        int deltaCol = end.getCell() - start.getCell();

        if (Math.abs(deltaRow) != 2 || Math.abs(deltaCol) != 2)
            return false; //To make a jump, the column must change by 2

        Space jumpedSpace = board[start.getRow() + (deltaRow / 2)][start.getCell() + (deltaCol / 2)];
        if (jumpedSpace.getState() == Space.State.OPEN || jumpedSpace.getPieceColor() == activeColor)
            return false; //If a piece was not jumped over or the piece jumped was friendly, return false right away

        if (pieceType == Piece.Type.KING) {
            return Math.abs(deltaRow) == 2;
        } else if (activeColor == Piece.Color.RED) {
            return deltaRow == -2;
        } else { //Not a king and white player
            return deltaRow == 2;
        }
    }

    /**
     * Determines if a jump is possible
     *
     * @return true is possible, false otherwise
     */
    public static boolean isJumpPossible(int BOARD_SIZE, Space[][] board, Piece.Color activeColor) {
        //If the player has the choice between a jump and a simple move, he must jump
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col].getState() == Space.State.OCCUPIED && board[row][col].getPieceColor() == activeColor) {
                    if (jumpCanBeMade(row, col, board, BOARD_SIZE, activeColor))
                        return true;
                }
            }
        }
        return false;
    }


    /**
     * Determines if a jump can be continued
     *
     * @return true if it can be continued, false otherwise
     */
    public static boolean jumpCanBeContinued(int BOARD_SIZE, CheckersGame game) {
        Space[][] board = game.getBoard();
        LinkedList<Move> movesQueue = game.getMoves();
        Space[][] boardCopy = new Space[BOARD_SIZE][];
        for (int i = 0; i < BOARD_SIZE; i++) {
            boardCopy[i] = Arrays.copyOf(board[i], BOARD_SIZE);
        }
        //All of the moves need to be applied before we can check if a jump is still possible
        for (Move move : movesQueue) {
            game.applyMove(move, true);
        }
        int endingRow = movesQueue.getLast().getEnd().getRow();
        int endingCell = movesQueue.getLast().getEnd().getCell();
        boolean moveCanBeContinued = jumpCanBeMade(endingRow, endingCell, board, BOARD_SIZE, game.getActiveColor());

        //Reset the board after it changed
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[i] = Arrays.copyOf(boardCopy[i], BOARD_SIZE);
        }

        return moveCanBeContinued;
    }

    /**
     * Determines if a jump can be made
     *
     * @param pieceRow The row the piece will land
     * @param pieceCol The Col the piece will land
     * @return true if it can land, false otherwise
     */
    public static boolean jumpCanBeMade(int pieceRow, int pieceCol, Space[][] board, int BOARD_SIZE, Piece.Color activeColor) {
        //This code is disgusting but I don't think I can make it much cleaner
        Piece.Type pieceType = board[pieceRow][pieceCol].getPieceType();
        if (pieceType == Piece.Type.KING) {
            for (int rowOffset : new int[]{-2, 2}) {
                for (int colOffset : new int[]{-2, 2}) {
                    boolean isPossibleJump =
                            (pieceRow + rowOffset < BOARD_SIZE && pieceRow + rowOffset >= 0) &&
                            (pieceCol + colOffset < BOARD_SIZE && pieceCol + colOffset >= 0) &&
                            (board[pieceRow + rowOffset][pieceCol + colOffset].getState() != Space.State.OCCUPIED) &&
                            (board[pieceRow + (rowOffset / 2)][pieceCol + (colOffset / 2)].getState() == Space.State.OCCUPIED) &&
                            (board[pieceRow + (rowOffset / 2)][pieceCol + (colOffset / 2)].getPieceColor() != activeColor);

                    if (isPossibleJump)
                        return false;
                }
            }
        } else if (activeColor == Piece.Color.RED) {
            for (int colOffset : new int[]{-2, 2}) {
                boolean isPossibleJump =
                        (pieceRow - 2 >= 0 && pieceCol + colOffset < BOARD_SIZE && pieceCol + colOffset >= 0) &&
                        (board[pieceRow - 2][pieceCol + colOffset].getState() != Space.State.OCCUPIED) &&
                        (board[pieceRow - 1][pieceCol + (colOffset / 2)].getState() == Space.State.OCCUPIED) &&
                        (board[pieceRow - 1][pieceCol + (colOffset / 2)].getPieceColor() != activeColor);

                if (isPossibleJump)
                    return true;
            }
        } else { //Not a king and white
            for (int colOffset : new int[]{-2, 2}) {
                boolean isPossibleJump =
                        (pieceRow + 2 < BOARD_SIZE && pieceCol + colOffset < BOARD_SIZE && pieceCol + colOffset >= 0) &&
                        (board[pieceRow + 2][pieceCol + colOffset].getState() != Space.State.OCCUPIED) &&
                        (board[pieceRow + 1][pieceCol + (colOffset / 2)].getState() == Space.State.OCCUPIED) &&
                        (board[pieceRow + 1][pieceCol + (colOffset / 2)].getPieceColor() != activeColor);

                if (isPossibleJump)
                    return true;
            }
        }
        return false;
    }
}
