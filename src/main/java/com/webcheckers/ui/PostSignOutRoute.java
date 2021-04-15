package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import spark.*;

import static com.webcheckers.ui.WebServer.HOME_URL;

/**
 * The UI Controller to POST the Sign Out
 */
public class PostSignOutRoute implements Route {

    private final PlayerLobby playerLobby;
    private final GameManager gameManager;

    /**
     * Create the Spark Route (UI Controller) to handle all {@code POST /signout} HTTP requests
     *
     * @param playerLobby
     *     the Player Lobby object storing info about the active players, games, etc.
     * @param gameManager
     *     Keeps track of all of the active games and what players are included in that game
     */
    public PostSignOutRoute(PlayerLobby playerLobby, GameManager gameManager) {

        this.playerLobby = playerLobby;
        this.gameManager = gameManager;
    }


    /**
     * Removes the user from the playerLobby and removes the attribute in the session
     *
     * @param request
     *     the HTTP request
     * @param response
     *     the HTTP response
     * @return
     *     null, with a redirect back to the home page
     */
    @Override
    public Object handle(Request request, Response response) {

        Player player = request.session().attribute("currentUser");
        String playerName = player.getName();
        CheckersGame game = gameManager.getPlayersGame(player);
        playerLobby.removePlayer(playerName);
        if(!playerLobby.isPlayerInLobby(player)) {
            request.session().removeAttribute(playerName);
            request.session().attribute("currentUser", null);
            if(game != null) {
                game.resignGame(player);
                gameManager.saveGame(game.getGameID(), player);
            }
        }

        response.redirect(WebServer.HOME_URL);
        return null;

    }
}
