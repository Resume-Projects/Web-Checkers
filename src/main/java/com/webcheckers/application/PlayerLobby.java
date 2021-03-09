
package com.webcheckers.application;

import com.webcheckers.model.Player;

import java.util.Set;
import java.util.TreeSet;

/**
 * Holds all the active players in a TreeSet. There should only ever be one created (in the WebServer class). All the
 * classes that need to access it will likely have it passed as a parameter.
 */
public class PlayerLobby {

    private final Set<Player> activePlayers;

    public PlayerLobby() {
        activePlayers = new TreeSet<>();
    }

    public Player newPlayer(String name) {
        Player newPlayer = new Player(name);
        activePlayers.add(newPlayer);
        return newPlayer;
    }

    public Set<Player> getActivePlayers() {
        return activePlayers;
    }

    public boolean isValidName(String name) {
        //Must be letters, numbers, and spaces, but not only spaces
        return name.matches("[a-zA-Z0-9 ]+") && !name.matches(" +");
    }

    public boolean isNameTaken(String name) {
        return activePlayers.contains(new Player(name));
    }

}
