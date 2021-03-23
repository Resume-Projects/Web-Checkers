package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  Coverage for the Player class
 */
@Tag("Model-Tier")
public class PlayerTest {

    private Player CuT;
    private Player CuT2;
    private String name;

    /**
     * Sets up a single player to preform tests on
     */
    @BeforeEach
    public void setup(){
        name = "foobar";
        CuT = new Player(name);
        CuT2 = new Player("p2");
    }

    /**
     * Preform tests on mock player
     */
    @Test
    public void testPlayer(){
        assertNotNull(CuT.getName());
        assertNotNull(CuT.toString());
    }
    @Test
    public void comparePlayer(){
        assertNotEquals(CuT.compareTo(CuT2),0);
        assertEquals(CuT.equals(CuT2),false);

    }
}
