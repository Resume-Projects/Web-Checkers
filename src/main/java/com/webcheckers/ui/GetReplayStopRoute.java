package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.model.Player;
import com.webcheckers.model.SavedGame;
import spark.*;

import java.util.HashMap;

public class GetReplayStopRoute implements Route {

    private GameManager gameManager;

    public GetReplayStopRoute(GameManager gameManager) {

        this.gameManager = gameManager;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        HashMap<String, Object> vm = new HashMap<>();
        vm.put("title", "Exit Replay Mode");
        final Player player = request.session().attribute("currentUser");
        final SavedGame savedGame = gameManager.getSavedGame(request.queryParams("gameID"));
        savedGame.setTurnNum(0);
        savedGame.setPlayerWatching(null);
        vm.put("currentUser", player);
        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
