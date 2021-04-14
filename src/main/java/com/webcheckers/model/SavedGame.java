package com.webcheckers.model;

import java.util.ArrayList;

public class SavedGame {

    private ArrayList<SavedMove> savedMoves;
    private int turnNum;
    private Player playerWatching;


    public SavedGame(ArrayList<SavedMove> moves, CheckersGame game ) {
        this.savedMoves = new ArrayList<>(moves);
        this.turnNum = 0;
        this.playerWatching = null;
    }

    public void nextTurn() {
        turnNum++;

    }

    public void previousTurn() {
        turnNum--;
    }

    public int getTurnNum() {
        return turnNum;
    }

    public void setTurnNum( int turn ) {
        turnNum += turn;
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
}
