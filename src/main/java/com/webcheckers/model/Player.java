
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

    /**
     * Gets the name of the player
     *
     * @return a string of the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Sees if the two players are the same player
     *
     * @param other the other player
     * @return true if both players are the same, false otherwise
     */
    public boolean equals(Player other) {
        return this.name.equals(other.name);
    }

    /**
     * Overrides toString to return the player name
     *
     * @return the player name
     */
    @Override
    public String toString() {
        return "Player: " + name;
    }

    /**
     * Identifies if two players are the same player
     *
     * @param other The other player
     * @return 0 if the players are the same player, 1 otherwise
     */
    @Override
    public int compareTo(Player other) {
        return this.name.compareTo(other.name);
    }
}
