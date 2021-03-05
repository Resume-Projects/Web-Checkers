
package com.webcheckers.model;

public class Player implements Comparable<Player> {

    private final String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Player: " + name;
    }

    @Override
    public int compareTo(Player other) {
        return this.name.compareTo(other.name);
    }
}
