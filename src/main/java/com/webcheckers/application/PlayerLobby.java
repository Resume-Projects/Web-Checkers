
package com.webcheckers.application;

import com.webcheckers.model.Player;

import java.util.Set;
import java.util.TreeSet;

public class PlayerLobby {

    private Set<Player> activePlayers;

    public PlayerLobby() {
        activePlayers = new TreeSet<>();
    }

    public Player newPlayer(String name) {
        Player newPlayer = new Player(name);
        activePlayers.add(newPlayer);
        return newPlayer;
    }

    public boolean isValidName(String name) {
        return true;
    }

    public boolean isNameTaken(String name) {
        return activePlayers.contains(new Player(name));
    }

}
