package com.webcheckers.application;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
public class PlayerLobbyTest {

    private PlayerLobby CuT;

    /**
     * Sets up an Lobby to preform tests on
     */
    @BeforeEach
    public void setup(){
        CuT = new PlayerLobby();
    }

    /**
     * Preform tests on mock lobby
     */
    @Test
    public void testNewPlayer(){
        assertNotNull(CuT.newPlayer("Player"));
    }
    @Test
    public void testGetActivePlayers(){
        CuT.newPlayer("player1");
        CuT.newPlayer("player2");
        assertNotNull(CuT.getActivePlayers());
    }
    @Test
    public void testIsValidName(){
        assertTrue(CuT.isValidName("foobar123"));
        assertTrue(CuT.isValidName("lorem ipsum"));
        assertFalse(CuT.isValidName("something-new!"));
        assertFalse(CuT.isValidName("   "));
    }
    @Test
    public void testisNameTaken(){
        CuT.newPlayer("abc");
        assertTrue(CuT.isNameTaken("abc"));
        assertFalse(CuT.isNameTaken("def"));
    }
    @Test
    public void testGetPlayerFromName(){
        CuT.newPlayer("mockPlayer");
        assertEquals(CuT.getPlayerFromName("mockPlayer"), new Player("mockPlayer"));
    }
}
