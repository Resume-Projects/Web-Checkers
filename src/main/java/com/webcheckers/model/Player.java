
package com.webcheckers.model;

/**
 * The class for a player that just holds the players name currently. Implements Comparable so it can be used
 * in a TreeSet
 */
public class Player implements Comparable<Player> {

    private final String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Player other) {
        return this.name.equals(other.name);
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
