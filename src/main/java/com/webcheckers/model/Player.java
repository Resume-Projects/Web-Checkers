
package com.webcheckers.model;

public class Player implements Comparable<Player> {

    private String username;

    public Player(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public String toString() {
        return "Player: " + username;
    }

    @Override
    public int compareTo(Player other) {
        return this.username.compareTo(other.username);
    }
}
