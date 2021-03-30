
package com.webcheckers.application;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;

import java.util.ArrayList;

/**
 * This class will store all of the active checkers games. Only one should ever be created. Most classes will
 * receive the GameManager as a parameter.
 */
public class GameManager {

    private final ArrayList<CheckersGame> checkersGames;

    /**
     * Creates the GameManager object that just initialized the list of games. Only one should
     * ever be made
     */
    public GameManager() {
        checkersGames = new ArrayList<>();
    }

    /**
     * Finds what game the player is in, or returns null if the player is not in a game
     * @param player the player we are looking for
     * @return the game the player is in or null
     */
    public CheckersGame getPlayersGame(Player player) {
        for(CheckersGame checkersGame : checkersGames)
            if(player.equals(checkersGame.getRedPlayer()) || player.equals(checkersGame.getWhitePlayer()))
                return checkersGame;

        return null;
    }

    /**
     * Returns a new checkers game that gets added to the list
     * @param redPlayer the red player in the checkers game
     * @param whitePlayer the white player in the checkers game
     * @return the new Checkers game
     */
    public CheckersGame getNewGame(Player redPlayer, Player whitePlayer) {
        CheckersGame checkersGame = new CheckersGame(redPlayer, whitePlayer);
        checkersGames.add(checkersGame);
        return checkersGame;
    }

    public boolean resignGame(Player player) {
        CheckersGame game = getPlayersGame(player);
        return game.resignGame(player);
    }

    public void deleteGame(Player player){
        checkersGames.removeIf(game -> game.getRedPlayer() == player);
        checkersGames.removeIf(game -> game.getWhitePlayer() == player);
    }
}
