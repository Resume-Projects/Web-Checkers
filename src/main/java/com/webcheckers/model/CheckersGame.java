package com.webcheckers.model;

import com.webcheckers.application.GameController;
import com.webcheckers.util.Message;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
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

    protected enum State {
        PLAYING,
        RESIGNED
    }

    private State state;

    private final Player redPlayer;
    private final Player whitePlayer;

    private Player winner;
    private Player loser;

    private Piece.Color activeColor;

    //I needed to make it a LinkedList so I can check the back of the queue
    private LinkedList<Move> movesQueue;

    private int numRedPieces;
    private int numWhitePieces;

    private boolean justJumped;
    private boolean justKinged;

    /**
     * The CheckersGame data type
     *
     * @param redPlayer   the player with the red pieces
     * @param whitePlayer the player with the white pieces
     */
    public CheckersGame(Player redPlayer, Player whitePlayer) {
        LOG.fine("Game created");
        board = new Space[BOARD_SIZE][BOARD_SIZE];
        movesQueue = new LinkedList<>();

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
        this.justJumped = false;
        this.justKinged = false;
        this.numRedPieces = 12;
        this.numWhitePieces = 12;
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.activeColor = Piece.Color.RED;
        this.winner = null;
        this.loser = null;
        this.state = State.PLAYING;
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

        Position firstPosition;
        if (movesQueue.isEmpty()) {
            firstPosition = start;
        } else {
            firstPosition = movesQueue.getFirst().getStart();
        }
        Piece.Type movedPieceType = board[firstPosition.getRow()][firstPosition.getCell()].getPieceType();

        if (justKinged) {
            return Message.error("You reached the end of the board. You cannot make any more moves");
        } else if (isValidJump(start, end, movedPieceType)) {
            if (!movesQueue.isEmpty() && !justJumped) {
                return Message.error("You already made a simple move");
            } else {
                movesQueue.add(attemptedMove);
                justJumped = true;
                checkForKings(movedPieceType);
                return Message.info("Valid move");
            }
        } else if (isValidSimpleMove(start, end, movedPieceType)) {
            if (!movesQueue.isEmpty()) {
                return Message.error("You already made a move");
            } else if (isJumpPossible()) {
                return Message.error("There is a jump you must make");
            } else {
                movesQueue.add(attemptedMove);
                checkForKings(movedPieceType);
                return Message.info("Valid move");
            }
        } else {
            return Message.error("Move is too far");
        }
    }

    //Called from GameManager when PostSubmitTurnRoute tells it to
    public Message applyAttemptedMoves() {
        if (justJumped && jumpCanBeContinued()) {
            return Message.error("You must continue your jump");
        }

        while (!movesQueue.isEmpty()) {
            Move currentMove = movesQueue.remove();
            applyMove(currentMove);
        }

        justJumped = false;
        justKinged = false;
        if (activeColor == Piece.Color.RED)
            activeColor = Piece.Color.WHITE;
        else
            activeColor = Piece.Color.RED;
        return Message.info("Move applied");
    }

    public Message resetAttemptedMove() {
        Move removedMove = movesQueue.remove();
        justKinged = false;
        //If the player moved their piece to jump, then took their move back, then they no longer just jumped
        //If the size of the queue used to be bigger than 1, then they must have already jumped before
        //since the size of the queue can only be one if a simple move was made
        if (movesQueue.isEmpty() && Math.abs(removedMove.getStart().getRow() - removedMove.getEnd().getRow()) > 1) {
            justJumped = false;
        }
        return Message.info("Attempted move was removed");
    }

    private boolean isValidSimpleMove(Position start, Position end, Piece.Type pieceType) {
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
    private boolean isValidJump(Position start, Position end, Piece.Type pieceType) {
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

    private boolean isJumpPossible() {
        //If the player has the choice between a jump and a simple move, he must jump
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col].getState() == Space.State.OCCUPIED && board[row][col].getPieceColor() == activeColor) {
                    if (jumpCanBeMade(row, col))
                        return true;
                }
            }
        }

        return false;
    }

    private boolean jumpCanBeContinued() {
        Space[][] boardCopy = new Space[BOARD_SIZE][];
        for (int i = 0; i < BOARD_SIZE; i++) {
            boardCopy[i] = Arrays.copyOf(board[i], BOARD_SIZE);
        }
        //All of the moves need to be applied before we can check if a jump is still possible
        for (Move move : movesQueue) {
            applyMove(move);
        }
        int endingRow = movesQueue.getLast().getEnd().getRow();
        int endingCell = movesQueue.getLast().getEnd().getCell();
        boolean moveCanBeContinued = jumpCanBeMade(endingRow, endingCell);

        //Reset the board after it changed
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[i] = Arrays.copyOf(boardCopy[i], BOARD_SIZE);
        }

        return moveCanBeContinued;
    }

    private boolean jumpCanBeMade(int pieceRow, int pieceCol) {
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

    private void applyMove(Move move) {
        Position start = move.getStart();
        Position end = move.getEnd();
        int deltaRow = end.getRow() - start.getRow();
        int deltaCol = end.getCell() - start.getCell();

        if (Math.abs(deltaRow) > 1) { //It was a jump move
            Space openSpace = new Space(start.getCell() + (deltaCol / 2), Space.State.OPEN);
            board[start.getRow() + (deltaRow / 2)][start.getCell() + (deltaCol / 2)] = openSpace;

            if (activeColor == Piece.Color.RED)
                numWhitePieces--;
            else
                numRedPieces--;
        }

        //We do this stuff whether or not it was a jump
        Piece movedPiece;
        //If the piece reaches the end, make it a king (even if it was already a king)
        if ((activeColor == Piece.Color.RED && end.getRow() == 0) ||
                activeColor == Piece.Color.WHITE && end.getRow() == BOARD_SIZE - 1) {
            movedPiece = new Piece(Piece.Type.KING, activeColor);
        } else {
            movedPiece = board[start.getRow()][start.getCell()].getPiece();
        }

        board[start.getRow()][start.getCell()] = new Space(start.getCell(), Space.State.OPEN);
        board[end.getRow()][end.getCell()] = new Space(end.getCell(), movedPiece);
    }

    //If the piece that just moved reached the end of the board, it must become a king
    //and the justKinged variable is set to true
    private void checkForKings(Piece.Type pieceMovedType) {
        Position endPosition = movesQueue.getLast().getEnd();
        if (pieceMovedType == Piece.Type.KING)
            return; //The piece was already a king. Don't need to do anything

        //If the piece reached the other end of the board, make it a king
        if ((activeColor == Piece.Color.RED && endPosition.getRow() == 0) ||
                activeColor == Piece.Color.WHITE && endPosition.getRow() == BOARD_SIZE - 1) {
            justKinged = true;
        }
    }

    public Piece.Color getPlayerColor(Player player) {
        if (player.equals(redPlayer)) {
            return Piece.Color.RED;
        } else if (player.equals(whitePlayer)) {
            return Piece.Color.WHITE;
        } else {
            return null;
        }
    }

    public boolean resignGame(Player player) {
        // If there is no active turn
        if (activeColor == null) {
            return false;
        }
        // Can only resign if it is their turn
        else if (activeColor == getPlayerColor(player)) {
            loser = player;
            state = State.RESIGNED;
            if (redPlayer.equals(player)) {
                winner = whitePlayer;
            } else {
                winner = redPlayer;
            }
            return true;
        }
        // Otherwise it is not their turn or something's wrong
        return false;
    }

    public boolean isResigned() {
        return state == State.RESIGNED;
    }

    public Player getWinner() {
        return this.winner;
    }

    public Player getLoser() {
        return this.loser;
    }
}
