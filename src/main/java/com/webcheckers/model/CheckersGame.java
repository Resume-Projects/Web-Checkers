package com.webcheckers.model;

import com.webcheckers.application.GameController;
import com.webcheckers.util.Message;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

/**
 * A single Checkers game
 *
 * @author Danny Gardner
 */
public class CheckersGame {
    private static final Logger LOG = Logger.getLogger(CheckersGame.class.getName());

    private final Space[][] board;

    /**
     * The side length of a square checkers board
     */
    public static final int BOARD_SIZE = 8;

    private final Player redPlayer;
    private final Player whitePlayer;

    private Piece.Color activeColor;

    private Queue<Move> moves;

    private int numRedPieces;
    private int numWhitePieces;

    private boolean justJumped;

    /**
     * The CheckersGame data type
     *
     * @param redPlayer   the player with the red pieces
     * @param whitePlayer the player with the white pieces
     */
    public CheckersGame(Player redPlayer, Player whitePlayer) {
        LOG.fine("Game created");
        board = new Space[BOARD_SIZE][BOARD_SIZE];
        moves = new LinkedList<>();

        for (int col = 0; col < board.length; col++) {
            if (col % 2 == 1) {
                board[0][col] = new Space(col, Space.State.OCCUPIED);
                board[2][col] = new Space(col, Space.State.OCCUPIED);
                board[6][col] = new Space(col, Space.State.OCCUPIED);
                board[1][col] = new Space(col, Space.State.INVALID);
                board[3][col] = new Space(col, Space.State.INVALID);
                board[5][col] = new Space(col, Space.State.INVALID);
                board[7][col] = new Space(col, Space.State.INVALID);
                board[4][col] = new Space(col, Space.State.OPEN);
            } else {
                board[1][col] = new Space(col, Space.State.OCCUPIED);
                board[5][col] = new Space(col, Space.State.OCCUPIED);
                board[7][col] = new Space(col, Space.State.OCCUPIED);
                board[0][col] = new Space(col, Space.State.INVALID);
                board[2][col] = new Space(col, Space.State.INVALID);
                board[4][col] = new Space(col, Space.State.INVALID);
                board[6][col] = new Space(col, Space.State.INVALID);
                board[3][col] = new Space(col, Space.State.OPEN);
            }
        }
        GameController.initializeBoard(board);
        justJumped = false;
        this.numRedPieces = 12;
        this.numWhitePieces = 12;
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.activeColor = Piece.Color.RED;
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
        Row[] rows = new Row[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            rows[i] = new Row(i, board[i]);
        }
        return new BoardView(rows);
    }

    /**
     * Get the board for the white player.
     * This reverses the order of all the rows of the board from BoardView.
     *
     * @return the game board.
     */
    public BoardView getWhiteBoardView() {
        Row[] rows = new Row[BOARD_SIZE];
        for (int i = BOARD_SIZE - 1; i >= 0; i--) {
            Space[] tempSpaces = Arrays.copyOf(board[i], BOARD_SIZE);
            Collections.reverse(Arrays.asList(tempSpaces));
            rows[BOARD_SIZE - i - 1] = new Row(i, tempSpaces);
        }
        return new BoardView(rows);
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
     * Gets the player of the white pieces
     *
     * @return the player of the white pieces
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Piece.Color getActiveColor() {
        return activeColor;
    }

    //Called from PostValidateMoveRoute (and maybe backup move)
    public Message saveAttemptedMove(Move attemptedMove) {
        Position start = attemptedMove.getStart();
        Position end = attemptedMove.getEnd();

        if(isSimpleMove(start, end)) {
            if(!moves.isEmpty()) {
                return Message.error("You already moved");
            } else {
                moves.add(attemptedMove);
                return Message.info("Valid move");
            }
        } else if(isJump(start, end)) {
            if(!moves.isEmpty() && !justJumped) {
                return Message.error("You already made a simply move");
            } else {
                moves.add(attemptedMove);
                justJumped = true;
                return Message.info("Valid move");
            }
        } else {
            return Message.error("That space is too far away");
        }

    }

    //Called from GameManager when PostSubmitTurnRoute tells it to
    public Message applyAttemptedMoves() {
        while (moves.size() > 0) {
            Position startMove = moves.peek().getStart();
            Position endMove = moves.peek().getEnd();
            moves.remove();
            Piece pieceBeingMoved = board[startMove.getRow()][startMove.getCell()].getPiece();
            board[startMove.getRow()][startMove.getCell()] = new Space(startMove.getCell(), Space.State.OPEN);
            board[endMove.getRow()][endMove.getCell()] = new Space(endMove.getCell(), pieceBeingMoved);
            if (isJump(startMove, endMove)) {
                makeJump(startMove, endMove);
            }
        }

        if (activeColor == Piece.Color.RED)
            activeColor = Piece.Color.WHITE;
        else
            activeColor = Piece.Color.RED;
        return Message.info("Move applied");
    }

    public Message resetAttemptedMove() {
        moves.remove();
        return Message.info("Attempted move was removed");
    }

    private boolean isSimpleMove(Position start, Position end) {
        int deltaRow = end.getRow() - start.getRow();
        int deltaCol = end.getCell() - start.getCell();
        if(board[start.getRow()][start.getCell()].getPieceType() == Piece.Type.KING) {
            return Math.abs(deltaRow) == 1 && Math.abs(deltaCol) == 1;
        } else if(activeColor == Piece.Color.RED){
            return deltaRow == -1 && Math.abs(deltaCol) == 1;
        } else { //Not a king and active color is white
            return deltaRow == 1 && Math.abs(deltaCol) == 1;
        }
    }

    /**
     * determines if the move made was a jump
     *
     * @param start the starting position
     * @param end   the ending position
     * @return whether the move was a jump
     */
    private boolean isJump(Position start, Position end) {
        int deltaRow = end.getRow() - start.getRow();
        int deltaCol = end.getCell() - start.getCell();

        Space jumpedSpace = board[start.getRow() + (deltaRow / 2)][start.getCell() + (deltaCol / 2)];
        if(jumpedSpace.getState() != Space.State.OPEN)
            return false; //If a piece was not jumped over, return false right away

        if(Math.abs(deltaCol) != 2)
            return false; //To make a jump, the column must change by 2

        if(board[start.getRow()][start.getCell()].getPieceType() == Piece.Type.KING) {
            return Math.abs(deltaRow) == 2;
        } else if(activeColor == Piece.Color.RED) {
            return deltaRow == -2;
        } else { //Not a king and white player
            return deltaRow == 2;
        }

//        int deltaCol = Math.abs(start.getCell() - end.getCell());
//        if (deltaCol != 2) {
//            return false;
//        } else if (activeColor == Piece.Color.RED && end.getRow() + 2 == start.getRow()) {
//            Space opponent;
//            if (start.getCell() > end.getCell()) {
//                opponent = board[end.getRow() + 1][end.getCell() + 1];
//            } else {
//                opponent = board[end.getRow() + 1][end.getCell() - 1];
//            }
//            return opponent.getState() == Space.State.OCCUPIED && opponent.getPieceColor() == Piece.Color.WHITE;
//        } else if (activeColor == Piece.Color.WHITE && end.getRow() - 2 == start.getRow()) {
//            Space opponent;
//            if (start.getCell() > end.getCell()) {
//                opponent = board[end.getRow() - 1][end.getCell() + 1];
//            } else {
//                opponent = board[end.getRow() - 1][end.getCell() - 1];
//            }
//            return opponent.getState() == Space.State.OCCUPIED && opponent.getPieceColor() == Piece.Color.RED;
//        }
//        return false;
    }

    /**
     * Checks for other valid jumps
     */
    private boolean checkForJumps(Position p) {
        int row = p.getRow();
        int col = p.getCell();
        if (activeColor == Piece.Color.RED) {
            Position newEnd = new Position(row - 2, col - 2);
            if (isJump(p, newEnd)) {
                return true;
            } else {
                newEnd = new Position(row - 2, col + 2);
                return isJump(p, newEnd);
            }
        } else if (activeColor == Piece.Color.WHITE) {
            Position newEnd = new Position(row + 2, col - 2);
            if (isJump(p, newEnd)) {
                return true;
            } else {
                newEnd = new Position(row + 2, col + 2);
                return isJump(p, newEnd);
            }
        }
        return false;
    }

    /**
     * Applies the jump move and removes the opponent piece that was captured
     *
     * @param start the starting position of the jumping piece
     * @param end   the final position of the jumping piece
     */
    private void makeJump(Position start, Position end) {
        if (activeColor == Piece.Color.RED) {
            if (start.getCell() > end.getCell()) {
                board[end.getRow() + 1][end.getCell() + 1] = new Space(start.getCell() - 1, Space.State.OPEN);
            } else {
                board[end.getRow() + 1][end.getCell() - 1] = new Space(start.getCell() + 1, Space.State.OPEN);
            }
        } else {
            if (start.getCell() > end.getCell()) {
                board[end.getRow() - 1][end.getCell() + 1] = new Space(end.getCell() + 1, Space.State.OPEN);
            } else {
                board[end.getRow() - 1][end.getCell() - 1] = new Space(end.getCell() - 1, Space.State.OPEN);
            }
        }
    }
}
