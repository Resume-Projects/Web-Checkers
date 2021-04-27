package com.webcheckers.model;

import com.webcheckers.application.GameController;
import com.webcheckers.application.MoveChecker;
import com.webcheckers.util.Message;

import java.util.ArrayList;
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

    public enum State {
        PLAYING,
        RESIGNED,
        ENDED,
        OVER
    }

    private State state;
    private Player redPlayer;
    private Player whitePlayer;

    private Player winner;
    private Player loser;

    private Piece.Color activeColor;

    //I needed to make it a LinkedList so I can check the back and iterate through it
    //It is still mostly used like a queue
    private final LinkedList<Move> movesQueue;

    private int numRedPieces;
    private int numWhitePieces;

    private boolean justJumped;
    private boolean justKinged;

    private boolean playerLeft;

    private final String redPlayerName;
    private final String whitePlayerName;

    private final int gameID;

    private ArrayList<SavedMove> moves = new ArrayList<>();

    /**
     * The CheckersGame data type
     *
     * @param redPlayer   the player with the red pieces
     * @param whitePlayer the player with the white pieces
     */
    public CheckersGame(Player redPlayer, Player whitePlayer, int gameID) {
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
        this.gameID = gameID;
        GameController.initializeBoard(board);
        this.justJumped = false;
        this.justKinged = false;
        this.numRedPieces = 12;
        this.numWhitePieces = 12;
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.redPlayerName = redPlayer.getName();
        this.whitePlayerName = whitePlayer.getName();
        this.activeColor = Piece.Color.RED;
        this.winner = null;
        this.loser = null;
        this.state = State.PLAYING;
        this.playerLeft = false;
        addMove();
    }

    /**
     * Returns the current state of the game
     * @return the current state of the game
     */
    public State getGameState() {
        return state;
    }

    /**
     * Gets the unique ID of this checkers game
     * @return the unique ID of the checkers game
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * Returns whether or not a player has left the game
     * @return whether or not a player has left the game
     */
    public boolean hasPlayerLeft() {
        return playerLeft;
    }

    /**
     * Removes a player from the game
     * @param player the player to remove from the game
     */
    public void removePlayer(Player player) {
        if(redPlayer.equals(player)) {
            redPlayer = null;
        } else {
            whitePlayer = null;
        }
        playerLeft = true;
    }

    /**
     * Get the board of the game
     *
     * @return the game board
     */
    public Space[][] getBoard() {
        return board;
    }

    public LinkedList<Move> getMoves() {
        return movesQueue;
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
     * Returns the name of the red player. This will never return null, unlike getRedPlayer()
     * @return the name of the red player
     */
    public String getRedPlayerName() {
        return redPlayerName;
    }

    /**
     * Gets the player of the white pieces
     *
     * @return the player of the white pieces
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * Returns the name of the white player. This will never return null, unlike getWhitePlayer()
     * @return the name of the white player
     */
    public String getWhitePlayerName() {
        return whitePlayerName;
    }

    /**
     * Returns the color of the active player
     * @return the color of the active player
     */
    public Piece.Color getActiveColor() {
        return activeColor;
    }

    /**
     * Saves an attempted move whenever the player drops a checkers piece on the board.
     * This should get called from the PostValidateMoveRoute class
     * @param attemptedMove The move the player wants to make
     * @return A message (could be info or error) saying if saving the move was successful
     */
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
        } else if (MoveChecker.isValidJump(start, end, movedPieceType, board, activeColor)) {
            if (!movesQueue.isEmpty() && !justJumped) {
                return Message.error("You already made a simple move");
            } else {
                movesQueue.add(attemptedMove);
                justJumped = true;
                checkForKings(movedPieceType);
                return Message.info("Valid move");
            }
        } else if (MoveChecker.isValidSimpleMove(start, end, movedPieceType, activeColor)) {
            if (!movesQueue.isEmpty()) {
                return Message.error("You already made a move");
            } else if (MoveChecker.isJumpPossible(BOARD_SIZE, board, activeColor)) {
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

    /**
     * Applies all the attempted moves to change the state of the board
     * @return A message (info or error) saying if applying the move was successful
     */
    public Message applyAttemptedMoves() {
        if (justJumped && MoveChecker.jumpCanBeContinued(BOARD_SIZE, this)) {
            return Message.error("You must continue your jump");
        }

        while (!movesQueue.isEmpty()) {
            Move currentMove = movesQueue.remove();
            applyMove(currentMove, false);
        }

        justJumped = false;
        justKinged = false;
        if (activeColor == Piece.Color.RED)
            activeColor = Piece.Color.WHITE;
        else
            activeColor = Piece.Color.RED;

        if(!MoveChecker.playerCanMove(BOARD_SIZE, board, activeColor)) {
            state = State.ENDED;
            if(activeColor == Piece.Color.RED) {
                winner = whitePlayer;
                loser = redPlayer;
            } else {
                winner = redPlayer;
                loser = whitePlayer;
            }
        }
        addMove();

        return Message.info("Move applied");
    }

    /**
     * Removes an attempted move when the user hits the undo button
     * @return A message (info or error) saying if undoing the move was successful
     */
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

    /**
     * Moves a piece
     *
     * @param move the move to make
     * @param temporary This will be true when called from jumpCanBeContinued. If false,
     *                  the number of red and white pieces will not be decremented
     */
    public void applyMove(Move move, boolean temporary) {
        Position start = move.getStart();
        Position end = move.getEnd();
        int deltaRow = end.getRow() - start.getRow();
        int deltaCol = end.getCell() - start.getCell();

        if (Math.abs(deltaRow) > 1) { //It was a jump move
            Space openSpace = new Space(start.getCell() + (deltaCol / 2), Space.State.OPEN);
            board[start.getRow() + (deltaRow / 2)][start.getCell() + (deltaCol / 2)] = openSpace;

            if(!temporary) {
                if (activeColor == Piece.Color.RED)
                    numWhitePieces--;
                else
                    numRedPieces--;
            }
        }
        if(!temporary) {
            if(numRedPieces == 0) {
                state = State.ENDED;
                loser = redPlayer;
                winner = whitePlayer;
            }
            if(numWhitePieces == 0) {
                state = State.ENDED;
                loser = whitePlayer;
                winner = redPlayer;
            }
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

    /**
     * If the piece that just moved reached the end of the board, it must become a king.
     *
     * @param pieceMovedType The type of piece
     */
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

    /**
     * Get the players color
     *
     * @param player the player
     * @return the players color
     */
    public Piece.Color getPlayerColor(Player player) {
        if (player.equals(redPlayer)) {
            return Piece.Color.RED;
        } else if (player.equals(whitePlayer)) {
            return Piece.Color.WHITE;
        } else {
            return null;
        }
    }

    /**
     * Resigns a game
     *
     * @param player The player to resign
     * @return The player to resign
     */
    public boolean resignGame(Player player) {
        // Can only resign if it is their turn
        state = State.RESIGNED;
        if(player.equals(redPlayer)) {
            loser = redPlayer;
            winner = whitePlayer;
        } else {
            loser = whitePlayer;
            winner = redPlayer;
        }
        return true;
    }

    /**
     * See if a player is resigned.
     *
     * @return true if the player resigned, false otherwise
     */
    public boolean isResigned() {
        return state == State.RESIGNED;
    }

    /**
     * Gets the winner
     *
     * @return The winning player
     */
    public Player getWinner() {
        return this.winner;
    }

    /**
     * Gets the loser
     *
     * @return the losing player
     */
    public Player getLoser() {
        return this.loser;
    }

    /**
     * If a player captures all of the other player's pieces:
     * @return the gameOver state
     */
    public boolean gameEnded() {
        return state == State.ENDED;
    }

    /**A game is done if a player resigns or the game ends a normal way
     *
     * @return False if state is PLAYING, True otherwise
     */
    public boolean getIsGameDone() {
        return state == State.ENDED || state == State.RESIGNED || state == State.OVER;
    }

    /**
     * Adds a move to a player
     */
    public void addMove() {
        SavedMove move = new SavedMove(board, getPlayerColor(redPlayer));
        moves.add(move);
    }

    /**
     * Gets a list of moves
     *
     * @return the moves
     */
    public ArrayList<SavedMove> getSavedMoves() {
        return moves;
    }

    /**
     * For replay use specifically, this updates the current states of the board when
     * next/previous turn is clicked
     * @param newBoard - the state of the board of the turn being updated to
     */
    public void setBoard(Space[][] newBoard) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[i] = Arrays.copyOf(newBoard[i], BOARD_SIZE);
        }
    }
}
