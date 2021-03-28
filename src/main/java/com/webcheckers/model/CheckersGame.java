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

    private LinkedList<Piece> redPieces;
    private LinkedList<Piece> whitePieces;

    private Queue<Move> moves;

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
        redPieces = new LinkedList<>();
        whitePieces = new LinkedList<>();

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
        GameController.initializeBoard(board, redPieces, whitePieces);
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

    public void removePiece(Piece piece) {
        if (piece.getColor() == Piece.Color.RED) {
            redPieces.remove(piece);
        } else {
            whitePieces.remove(piece);
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
        int deltaCol = Math.abs(start.getCell() - end.getCell());
        if (deltaCol != 2) {
            return false;
        } else if (activeColor == Piece.Color.RED && end.getRow() + 2 == start.getRow()) {
            Space opponent;
            if (start.getCell() > end.getCell()) {
                opponent = board[end.getRow() + 1][end.getCell() + 1];
            } else {
                opponent = board[end.getRow() + 1][end.getCell() - 1];
            }
            return opponent.getState() == Space.State.OCCUPIED && opponent.getPieceColor() == Piece.Color.WHITE &&
                    board[end.getRow()][end.getCell()].getState() == Space.State.OPEN;
        } else if (activeColor == Piece.Color.WHITE && end.getRow() - 2 == start.getRow()) {
            Space opponent;
            if (start.getCell() > end.getCell()) {
                opponent = board[end.getRow() + 1][end.getCell() - 1];
            } else {
                opponent = board[end.getRow() + 1][end.getCell() + 1];
            }
            return opponent.getState() == Space.State.OCCUPIED && opponent.getPieceColor() == Piece.Color.RED &&
                    board[end.getRow()][end.getCell()].getState() == Space.State.OPEN;
        }
        return false;
    }

    /**
     * Checks for other valid jumps
     */
    private void checkForJumps(Position p) {
        int row = p.getRow();
        int col = p.getCell();
        if (activeColor == Piece.Color.RED) {
            if (col - 2 >= 0 && row + 2 >= 0 &&
                    board[row - 1][col - 1].getState() == Space.State.OCCUPIED &&
                    board[row - 2][col - 2].getState() == Space.State.OPEN) {
                Position newEnd = new Position(row + 2, col - 2);
                if (isJump(p, newEnd)) {
                    moves.add(new Move(p, newEnd));
                }
            } else if (col + 2 < board.length && row + 2 < board.length &&
                    board[row + 1][col + 1].getState() == Space.State.OCCUPIED &&
                    board[row + 2][col + 2].getState() == Space.State.OPEN) {
                Position newEnd = new Position(row + 2, col + 2);
                if (isJump(p, newEnd)) {
                    moves.add(new Move(p, newEnd));
                }
            }
        }
    }

    private void makeJump(Position start, Position end)  {
        if (activeColor == Piece.Color.RED) {
            if (start.getCell() > end.getCell()) {
                removePiece(board[start.getRow() - 1][start.getCell() - 1].getPiece());
                board[start.getRow() - 1][start.getCell() - 1].setState(Space.State.OPEN);
            } else {
                removePiece(board[start.getRow() - 1][start.getCell() + 1].getPiece());
                board[start.getRow() - 1][start.getCell() + 1].setState(Space.State.OPEN);
            }
        } else {
            if (start.getCell() > end.getCell()) {
                removePiece(board[end.getRow() - 1][start.getCell() - 1].getPiece());
                board[end.getRow() - 1][start.getCell() - 1].setState(Space.State.OPEN);
            } else {
                removePiece(board[end.getRow() - 1][start.getCell() + 1].getPiece());
                board[end.getRow() - 1][start.getCell() + 1].setState(Space.State.OPEN);
            }
        }
    }

    //Called from PostValidateMoveRoute (and maybe backup move)
    public Message saveAttemptedMove(Move attemptedMove) {
        Position start = attemptedMove.getStart();
        Position end = attemptedMove.getEnd();
        int deltaCol = Math.abs(start.getCell() - end.getCell());
        boolean jump = isJump(start, end);
        boolean isValidMove = ((deltaCol == 1) &&
                ((activeColor == Piece.Color.RED && end.getRow() + 1 == start.getRow()) ||
                        (activeColor == Piece.Color.WHITE && end.getRow() - 1 == start.getRow()))) || jump;
        if (isValidMove) {
            moves.add(attemptedMove);
            return Message.info("Valid move");
        } else {
            return Message.error("Not a valid move");
        }
    }

    //Called from GameManager when PostSubmitTurnRoute tells it to
    public Message applyAttemptedMove() {
        Position startMove = moves.peek().getStart();
        Position endMove = moves.peek().getEnd();
        moves.remove();
        Piece pieceBeingMoved = board[startMove.getRow()][startMove.getCell()].getPiece();
        board[startMove.getRow()][startMove.getCell()] = new Space(startMove.getCell(), Space.State.OPEN);
        board[endMove.getRow()][endMove.getCell()] = new Space(endMove.getCell(), pieceBeingMoved);
        if (isJump(startMove, endMove)) {
            makeJump(startMove, endMove);
        }
        //Flip the active color
        if (activeColor == Piece.Color.RED)
            activeColor = Piece.Color.WHITE;
        else
            activeColor = Piece.Color.RED;
        return Message.info("Move applied");
    }

    public Message resetAttemptedMove() {
        moves = null;
        return Message.info("Attempted move was removed");
    }
}
