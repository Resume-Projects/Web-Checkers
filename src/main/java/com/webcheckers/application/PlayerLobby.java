
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

    public int getActivePlayersCount() {
        return this.activePlayers.size();
    }

    public void removePlayer(String name) {
        this.activePlayers.remove(name);
    }

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
