package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import spark.*;

import static com.webcheckers.ui.WebServer.HOME_URL;

public class PostSignOutRoute implements Route {

    private final PlayerLobby playerLobby;
    private final GameManager gameManager;

    public PostSignOutRoute(PlayerLobby playerLobby, GameManager gameManager) {

        this.playerLobby = playerLobby;
        this.gameManager = gameManager;
    }


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
                // gameManager.quitGame(player);
            }
        }

        response.redirect(WebServer.HOME_URL);
        return null;

    }
}
