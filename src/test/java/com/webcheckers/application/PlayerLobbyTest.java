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

    @Test
    public void getPlayerCount_test() {
        CuT.newPlayer("mockPlayer");
        assertEquals(CuT.getActivePlayersCount(), 1);
    }

    @Test
    public void removePlayer_test() {
        CuT.newPlayer("mockPlayer");
        CuT.removePlayer("mockPlayer");
        assertNull(CuT.getPlayerFromName("mockPlayer"));
    }

    @Test
    public void playerInLobby_test() {
        CuT.newPlayer("mockPlayer");
        assertTrue(CuT.isPlayerInLobby(CuT.getPlayerFromName("mockPlayer")));
    }

    @Test
    public void playerNotInLobby_test() {
        CuT.newPlayer("mockPlayer");
        assertFalse(CuT.isPlayerInLobby(CuT.getPlayerFromName("mockPlayer2")));
    }

    @Test
    public void noPlayersInLobby_test() {
        assertFalse(CuT.isPlayerInLobby(new Player("mockPlayer")));
    }
}
