package com.webcheckers.model;

import java.util.ArrayList;

public class SavedGame {

    private ArrayList<SavedMove> savedGame;
    private int turnNum;
    private Player playerWatching;


    public SavedGame(ArrayList<SavedMove> moves, CheckersGame game ) {
        this.savedGame = new ArrayList<>(moves);
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
}
