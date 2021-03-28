
package com.webcheckers.application;

import com.webcheckers.model.Player;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Holds all the active players in a TreeSet. There should only ever be one created (in the WebServer class). All the
 * classes that need to access it will likely have it passed as a parameter.
 */
public class PlayerLobby {

    private final Map<String, Player> activePlayers;

    public PlayerLobby() {
        activePlayers = new TreeMap<>();
    }

    /**
     * Creates a new player
     *
     * @param name the name of the player
     * @return The new player
     */
    public Player newPlayer(String name) {
        Player newPlayer = new Player(name);
        activePlayers.put(name, new Player(name));
        return newPlayer;
    }

    /**
     * Get a list of active players
     *
     * @return the list of active players
     */
    public Map<String, Player> getActivePlayers() {
        return activePlayers;
    }

    /**
     * Determines if a username can be used for the program
     *
     * @param name the user name
     * @return true if it satisfies both true statements below, false otherwise
     */
    public boolean isValidName(String name) {
        //Must be letters, numbers, and spaces, but not only spaces
        return name.matches("[a-zA-Z0-9 ]+") && !name.matches(" +");
    }

    /**
     * Determines if a name is already taken
     * @param name the name of the user
     * @return true if name is taken, false otherwise
     */
    public boolean isNameTaken(String name) {
        return activePlayers.containsKey(name);
    }

    /**
     * Gets a players name
     * @param name the name of the player
     * @return the name of the player
     */
    public Player getPlayerFromName(String name) {
        return activePlayers.get(name);
    }

    /**
     * Gets the amount of players in the active players list
     * @return - count of the players in activePlayers
     */
    public int getActivePlayersCount() {
        return this.activePlayers.size();
    }

    /**
     * Removes a player from the list of active players in the lobby
     * @param name - name of the player to be removed
     */
    public void removePlayer(String name) {
        this.activePlayers.remove(name);
    }

    /**
     * Check if the specified Player object is in the PlayerLobby
     *
     * @param player
     *     the player in question
     * @return
     *     true if they are in the lobby, false otherwise
     */
    public boolean isPlayerInLobby(Player player) {
        if(player == null) {
            return false;
        }
        if(getActivePlayersCount() == 0) {
            return false;
        }
        return activePlayers.containsKey(player.getName());
    }

}
