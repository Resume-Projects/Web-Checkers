package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;


public class GetSignOutRoute implements Route {

    private final PlayerLobby playerLobby;
    private final GameManager gameManager;
    private final TemplateEngine templateEngine;

    public GetSignOutRoute(TemplateEngine templateEngine, PlayerLobby playerLobby, GameManager gameManager) {

        this.playerLobby = playerLobby;
        this.gameManager = gameManager;
        this.templateEngine = templateEngine;
    }


    @Override
    public Object handle(Request request, Response response) throws Exception {
        Map<String, Object> viewModel = new HashMap<>();

        Player player = request.session().attribute("Player");
        String playerName = player.getName();
        CheckersGame game = gameManager.getPlayersGame(player);
        playerLobby.removePlayer(playerName);
        if(!playerLobby.isPlayerInLobby(player)) {
            request.session().removeAttribute(playerName);

        }

        return templateEngine.render(new ModelAndView(viewModel, "home.ftl"));
    }
}
