package com.webcheckers.model;

import java.util.ArrayList;

public class SavedGame {

    private ArrayList<SavedMove> savedMoves;
    private int turnNum;
    private Player playerWatching;
    private CheckersGame game;


    public SavedGame(ArrayList<SavedMove> moves, Player red, Player white, int gameID ) {
        this.savedMoves = new ArrayList<>(moves);
        this.turnNum = 0;
        this.playerWatching = null;
        this.game = new CheckersGame(red, white, gameID);
    }


    public int getTurnNum() {
        return turnNum;
    }

    public void setTurnNum( int turn ) {
        turnNum = turn;
    }

    public Player getPlayerWatching() {
        return playerWatching;
    }

    public void setPlayerWatching(Player player) {
        playerWatching = player;
    }

    public boolean hasNext() {
        return turnNum != savedMoves.size() - 1;
    }

    public boolean hasPrevious() {
        return turnNum > 0;
    }

    public ArrayList<SavedMove> getSavedMoves() {
        return savedMoves;
    }

    public CheckersGame getGame() {
        return game;
    }

    public void updateBoard() {
        this.game.setBoard(savedMoves.get(this.turnNum).getBoard());
    }
}
