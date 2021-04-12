
package com.webcheckers.application;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class will store all of the active checkers games. Only one should ever be created. Most classes will
 * receive the GameManager as a parameter.
 */
public class GameManager {

    //Used to assign ID's to games. Get's incremented every time a new game is made
    private int currentID = 0;

    //Changed to a HashMap so game ID's can be used
    //The key is the gameID, the value is the game
    private final HashMap<Integer, CheckersGame> checkersGames;

    //The key is the gameID, and the value is the list of people spectating
    private final HashMap<Integer, ArrayList<Player>> spectators;

    //The key is a spectator and the value is whether or not the board has been updated
    private final HashMap<Player, Boolean> newBoardState;

    /**
     * Creates the GameManager object that just initialized the list of games. Only one should
     * ever be made
     */
    public GameManager() {
        checkersGames = new HashMap<>();
        spectators = new HashMap<>();
        newBoardState = new HashMap<>();
    }

    public boolean isPlayerSpectating(Player player) {
        return newBoardState.containsKey(player);
    }

    public void addSpectator(int gameID, Player player) {
        spectators.get(gameID).add(player);
        newBoardState.put(player, false);
    }

    public boolean hasBoardBeenUpdated(Player player) {
        if(newBoardState.get(player)) {
            newBoardState.put(player, false);
            return true;
        }
        return false;
    }

    public void removeSpectator(Player player) {
        for(int num : spectators.keySet()) {
            if(spectators.get(num).contains(player)) {
                spectators.get(num).remove(player);
                break;
            }
        }
        newBoardState.remove(player);
    }

    public Piece.Color getPlayersColor(Player player) {
        for(CheckersGame game : checkersGames.values()) {
            if(game.getRedPlayer() != null && game.getRedPlayer().equals(player))
                return Piece.Color.RED;
            else if(game.getWhitePlayer() != null && game.getWhitePlayer().equals(player))
                return Piece.Color.WHITE;
        }
        return null;
    }

    public void gameHasBeenUpdated(CheckersGame checkersGame) {
        int gamesID = -1;
        for(int i = 0; i < currentID; i++) {
            if(checkersGames.get(i) == checkersGame) {
                gamesID = i;
                break;
            }
        }
        if(gamesID == -1)
            return; //This should not happen
        for(Player player : spectators.get(gamesID)) {
            newBoardState.put(player, true);
        }
    }

    /**
     * Finds what game the player is in, or returns null if the player is not in a game
     *
     * @param player the player we are looking for
     * @return the game the player is in or null
     */
    public CheckersGame getPlayersGame(Player player) {
        for(CheckersGame checkersGame : checkersGames.values())
            if(player.equals(checkersGame.getRedPlayer()) || player.equals(checkersGame.getWhitePlayer()))
                return checkersGame;

        return null;
    }

    /**
     * Returns a new checkers game that gets added to the list
     *
     * @param redPlayer the red player in the checkers game
     * @param whitePlayer the white player in the checkers game
     * @return the new Checkers game
     */
    public CheckersGame getNewGame(Player redPlayer, Player whitePlayer) {
        CheckersGame checkersGame = new CheckersGame(redPlayer, whitePlayer, currentID);
        spectators.put(currentID, new ArrayList<>());
        checkersGames.put(currentID, checkersGame);
        currentID++;
        return checkersGame;
    }

    /**
     * resigns a player form the game
     *
     * @param player the player to resign
     * @return the player to resign.
     */
    public boolean resignGame(Player player) {
        CheckersGame game = getPlayersGame(player);
        return game.resignGame(player);
    }

    /**
     * Deletes a (finished) game by removing a player
     * @param player The player to remove from the game
     */
    public void deleteGame(Player player){
        for(int gameID : checkersGames.keySet()) {
            CheckersGame game = checkersGames.get(gameID);
            Player redPlayer = game.getRedPlayer();
            Player whitePlayer = game.getWhitePlayer();
            if((redPlayer != null && redPlayer.equals(player)) || (whitePlayer != null && whitePlayer.equals(player))) {
                checkersGames.remove(gameID);
                break; //Stop the loop once we remove the game
            }
        }
    }
}
