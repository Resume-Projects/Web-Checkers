package com.webcheckers.model;

import java.util.ArrayList;

/**
 * A save of a game turn by turn to be used for Replay mode
 *
 * @author Corey Urbanke
 */
public class SavedGame {

    /**
     * Attributes
     */
    private ArrayList<SavedMove> savedMoves;
    private int turnNum;
    private Player playerWatching;
    private CheckersGame game;

    /**
     * SavedGame data type that holds all information about that saved game
     * @param moves - stores the game move by move
     * @param red - the red player
     * @param white - the white player
     * @param gameID - ID of the game being saved
     */
    public SavedGame(ArrayList<SavedMove> moves, Player red, Player white, int gameID ) {
        this.savedMoves = new ArrayList<>(moves);
        this.turnNum = 0;
        this.playerWatching = null;
        this.game = new CheckersGame(red, white, gameID);
    }

    /**
     * get the current turn number
     * @return - int of current turn
     */
    public int getTurnNum() {
        return turnNum;
    }

    /**
     * Sets the current turn counter to the next or previous turn
     * @param turn - the turn to be set to
     */
    public void setTurnNum( int turn ) {
        turnNum = turn;
    }

    /**
     * Retrives the current player watching the replay
     * @return - the player watching
     */
    public Player getPlayerWatching() {
        return playerWatching;
    }

    /**
     * Sets the current player watching the replay
     * @param player - the player watching
     */
    public void setPlayerWatching(Player player) {
        playerWatching = player;
    }

    /**
     * Is there a turn after the current one?
     * @return - true if yes, false if not
     */
    public boolean hasNext() {
        return turnNum != savedMoves.size() - 1;
    }

    /**
     * Is there a turn before the current one?
     * @return - true if yes, false if not
     */
    public boolean hasPrevious() {
        return turnNum > 0;
    }

    /**
     * Retrives the list of saved moves for the saved game
     * @return - the list of saved moves
     */
    public ArrayList<SavedMove> getSavedMoves() {
        return savedMoves;
    }

    /**
     * Gets the instance of CheckersGame as a means of storing more information about the game
     * @return - the saved CheckersGame instance
     */
    public CheckersGame getGame() {
        return game;
    }

    /**
     * Method that updates the display of the board when a new turn is being viewed
     */
    public void updateBoard() {
        this.game.setBoard(savedMoves.get(this.turnNum).getBoard());
    }
}
