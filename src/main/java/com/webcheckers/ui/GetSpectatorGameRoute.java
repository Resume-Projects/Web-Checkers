
package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;

public class GetSpectatorGameRoute implements Route {

    private final PlayerLobby playerLobby;
    private final GameManager gameManager;
    private final TemplateEngine templateEngine;

    public GetSpectatorGameRoute(PlayerLobby playerLobby, GameManager gameManager, TemplateEngine templateEngine) {
        this.playerLobby = playerLobby;
        this.gameManager = gameManager;
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        //request.queryParams("gameID") will give the game's ID
        HashMap<String, Object> vm = new HashMap<>();
        Player currentUser = request.session().attribute("currentUser");
        //int gameID = request.attribute("gameID");
        Player spectatedPlayer = playerLobby.getPlayerFromName(request.queryParams("playerSpectated"));
        CheckersGame spectatedGame = gameManager.getPlayersGame(spectatedPlayer);

        vm.put("title", "penis");
        vm.put("currentUser", currentUser);
        vm.put("viewMode", GetGameRoute.playMode.SPECTATOR);
        vm.put("redPlayer", new Player(spectatedGame.getRedPlayerName()));
        vm.put("whitePlayer", new Player(spectatedGame.getWhitePlayerName()));
        vm.put("activeColor", spectatedGame.getActiveColor());

        vm.put("board", spectatedGame.getRedBoardView());

        vm.put("message", Message.info("Cock and balls"));

        return new FreeMarkerEngine().render(new ModelAndView(vm, "game.ftl"));
    }
}
