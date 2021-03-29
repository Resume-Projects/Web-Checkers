package com.webcheckers.model;

import com.webcheckers.application.GameController;
import com.webcheckers.util.Message;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
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

    //I needed to make it a LinkedList so I can check the back of the queue
    private LinkedList<Move> movesQueue;

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

        Position firstPosition;
        if(movesQueue.isEmpty()) {
            firstPosition = start;
        } else {
            firstPosition = movesQueue.getFirst().getStart();
        }
        Piece.Type movedPieceType = board[firstPosition.getRow()][firstPosition.getCell()].getPieceType();

        if(isJump(start, end, movedPieceType)) {
            if(!movesQueue.isEmpty() && !justJumped) {
                return Message.error("You already made a simple move");
            } else {
                movesQueue.add(attemptedMove);
                justJumped = true;
                return Message.info("Valid move");
            }
        } else if(isSimpleMove(start, end, movedPieceType)) {
            if(!movesQueue.isEmpty()) {
                return Message.error("You already made a move");
            }else if(isJumpPossible()) {
                return Message.error("There is a jump you must make");
            } else {
                movesQueue.add(attemptedMove);
                return Message.info("Valid move");
            }
        } else {
            return Message.error("Move is too far");
        }
    }

    //Called from GameManager when PostSubmitTurnRoute tells it to
    public Message applyAttemptedMoves() {
        if(justJumped && jumpCanBeContinued()) {
            return Message.error("You must continue your jump");
        }

        while(!movesQueue.isEmpty()) {
            Move currentMove = movesQueue.remove();
            applyMove(currentMove);
        }

        justJumped = false;
        if (activeColor == Piece.Color.RED)
            activeColor = Piece.Color.WHITE;
        else
            activeColor = Piece.Color.RED;
        return Message.info("Move applied");
    }

    public Message resetAttemptedMove() {
        Move removedMove = movesQueue.remove();
        //If the player moved their piece to jump, then took their move back, then they no longer just jumped
        //If the size of the queue used to be bigger than 1, then they must have already jumped before
        //since the size of the queue can only be one if a simple move was made
        if(movesQueue.isEmpty() && Math.abs(removedMove.getStart().getRow() - removedMove.getEnd().getRow()) > 1) {
            justJumped = false;
        }
        return Message.info("Attempted move was removed");
    }

    private boolean isSimpleMove(Position start, Position end, Piece.Type pieceType) {
        int deltaRow = end.getRow() - start.getRow();
        int deltaCol = end.getCell() - start.getCell();

        if(Math.abs(deltaCol) != 1)
            return false;

        if(pieceType == Piece.Type.KING) {
            return Math.abs(deltaRow) == 1;
        } else if(activeColor == Piece.Color.RED){
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
    private boolean isJump(Position start, Position end, Piece.Type pieceType) {
        int deltaRow = end.getRow() - start.getRow();
        int deltaCol = end.getCell() - start.getCell();

        if(Math.abs(deltaRow) != 2 || Math.abs(deltaCol) != 2)
            return false; //To make a jump, the column must change by 2

        Space jumpedSpace = board[start.getRow() + (deltaRow / 2)][start.getCell() + (deltaCol / 2)];
        if(jumpedSpace.getState() == Space.State.OPEN || jumpedSpace.getPieceColor() == activeColor)
            return false; //If a piece was not jumped over or the piece jumped was friendly, return false right away

        if(pieceType == Piece.Type.KING) {
            return Math.abs(deltaRow) == 2;
        } else if(activeColor == Piece.Color.RED) {
            return deltaRow == -2;
        } else { //Not a king and white player
            return deltaRow == 2;
        }
    }

    private boolean isJumpPossible() {
        /*
        If the player just jumped and a jump is possible, then the player
        must make the jump
         */

        return false;
    }

    private boolean jumpCanBeContinued() {
        Space[][] boardCopy = new Space[BOARD_SIZE][];
        for(int i = 0; i < BOARD_SIZE; i++) {
            boardCopy[i] = Arrays.copyOf(board[i], BOARD_SIZE);
        }
        //All of the moves need to be applied before we can check if a jump is still possible
        for(Move move : movesQueue) {
            applyMove(move);
        }
        int endingRow = movesQueue.getLast().getEnd().getRow();
        int endingCell = movesQueue.getLast().getEnd().getCell();
        boolean moveCanBeContinued = jumpCanBeMade(endingRow, endingCell);

        //Reset the board after it changed
        for(int i = 0; i < BOARD_SIZE; i++) {
            board[i] = Arrays.copyOf(boardCopy[i], BOARD_SIZE);
        }

        return moveCanBeContinued;
    }

    private boolean jumpCanBeMade(int pieceRow, int pieceCol) {
        Piece.Type pieceType = board[pieceRow][pieceCol].getPieceType();
        if(pieceType == Piece.Type.KING) {
            for(int rowOffset : new int[] {-2, 2}) {
                for(int colOffset : new int[] {-2, 2}) {
                    boolean isPossibleJump = (pieceRow + rowOffset < BOARD_SIZE && pieceCol + colOffset < BOARD_SIZE) &&
                                             (board[pieceRow + rowOffset][pieceCol + colOffset].getState() != Space.State.OCCUPIED) &&
                                             (board[pieceRow + (rowOffset / 2)][pieceCol + (colOffset / 2)].getState() == Space.State.OCCUPIED) &&
                                             (board[pieceRow + (rowOffset / 2)][pieceCol + (colOffset / 2)].getPieceColor() != activeColor);

                    if(isPossibleJump)
                        return false;
                }
            }
        } else if(activeColor == Piece.Color.RED) {
            for(int colOffset : new int[] {-2, 2})  {
                boolean isPossibleJump = (pieceRow - 2 < BOARD_SIZE && pieceCol + colOffset < BOARD_SIZE) &&
                                         (board[pieceRow - 2][pieceCol + colOffset].getState() != Space.State.OCCUPIED) &&
                                         (board[pieceRow - 1][pieceCol + (colOffset / 2)].getState() == Space.State.OCCUPIED) &&
                                         (board[pieceRow - 1][pieceCol + (colOffset / 2)].getPieceColor() != activeColor);

                if(isPossibleJump)
                    return true;
            }
        } else { //Not a king and white
            for(int colOffset : new int[] {-2, 2})  {
                boolean isPossibleJump = (pieceRow + 2 < BOARD_SIZE && pieceCol + colOffset < BOARD_SIZE) &&
                        (board[pieceRow + 2][pieceCol + colOffset].getState() != Space.State.OCCUPIED) &&
                        (board[pieceRow + 1][pieceCol + (colOffset / 2)].getState() == Space.State.OCCUPIED) &&
                        (board[pieceRow + 1][pieceCol + (colOffset / 2)].getPieceColor() != activeColor);

                if(isPossibleJump)
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

        if(Math.abs(deltaRow) > 1) { //It was a jump move
            Space openSpace = new Space(start.getCell() + (deltaCol / 2), Space.State.OPEN);
            board[start.getRow() + (deltaRow / 2)][start.getCell() + (deltaCol / 2)] = openSpace;

            if(activeColor == Piece.Color.RED)
                numWhitePieces--;
            else
                numRedPieces--;
        }

        //We do this stuff whether or not it was a jump
        Piece jumpingPiece = board[start.getRow()][start.getCell()].getPiece();
        board[start.getRow()][start.getCell()] = new Space(start.getCell(), Space.State.OPEN);
        board[end.getRow()][end.getCell()] = new Space(end.getCell(), jumpingPiece);
    }
}
