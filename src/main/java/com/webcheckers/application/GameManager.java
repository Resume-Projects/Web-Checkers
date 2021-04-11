
package com.webcheckers.application;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class will store all of the active checkers games. Only one should ever be created. Most classes will
 * receive the GameManager as a parameter.
 */
public class GameManager {

    private static int staticGameID = 0;

    //Changed to a HashMap so game ID's can be used
    private final HashMap<Integer, CheckersGame> checkersGames;

    /**
     * Creates the GameManager object that just initialized the list of games. Only one should
     * ever be made
     */
    public GameManager() {
        checkersGames = new HashMap<>();
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
        CheckersGame checkersGame = new CheckersGame(redPlayer, whitePlayer);
        checkersGames.put(staticGameID, checkersGame);
        staticGameID++;
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
