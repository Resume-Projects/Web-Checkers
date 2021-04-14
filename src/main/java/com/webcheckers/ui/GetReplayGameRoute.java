package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.model.SavedGame;
import com.webcheckers.util.Message;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;

public class GetReplayGameRoute implements Route {

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final GameManager gameManager;
    private final Gson gson;

    public GetReplayGameRoute(final GameManager gameManager, final PlayerLobby playerLobby, final TemplateEngine templateEngine, final Gson gson) {
        this.gameManager = gameManager;
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        HashMap<String, Object> vm = new HashMap<>();
        HashMap<String, Object> modeOptions = new HashMap<>(2);
        Player currentUser = request.session().attribute("currentUser");

        // mode options has next and has previous


        vm.put("title", "Game Replay");
        if( request.session().attribute("currentUser") != null ) {
            final Player player = request.session().attribute("currentUser");
            final String gameID = request.queryParams("gameID");
            vm.put("currentUser", player);
            vm.put("viewMode", GetGameRoute.playMode.REPLAY);
            SavedGame savedGame = gameManager.getSavedGame(gameID);
            if( savedGame.getPlayerWatching() == null ) {
                savedGame.setPlayerWatching(player);
            } else if( !(savedGame.getPlayerWatching().equals(player)) && savedGame.getPlayerWatching() != null ) {
                request.session().attribute("message", "Error: Game is already being replayed");
                response.redirect("/replay/game");
                return null;
            }
            // next and previous vm's
        }
        vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));

        // need:
        //vm.put redPlayer
        //vm.put whitePlayer
        //vm.put activeColor
        //vm.put board
        vm.put("message", "Viewing Replay of [gameID]"); //modify this to gameID eventually

        return null;
    }
}
