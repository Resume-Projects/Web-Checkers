
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

    public Player newPlayer(String name) {
        Player newPlayer = new Player(name);
        activePlayers.put(name, new Player(name));
        return newPlayer;
    }

    public Map<String, Player> getActivePlayers() {
        return activePlayers;
    }

    public boolean isValidName(String name) {
        //Must be letters, numbers, and spaces, but not only spaces
        return name.matches("[a-zA-Z0-9 ]+") && !name.matches(" +");
    }

    public boolean isNameTaken(String name) {
        return activePlayers.containsKey(name);
    }

    public Player getPlayerFromName(String name) {
        return activePlayers.get(name);
    }

}
