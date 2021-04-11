
package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostSpectatorCheckTurnRoute implements Route {

    private final GameManager gameManager;

    public PostSpectatorCheckTurnRoute(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        //request.queryParams("gameID") will give the game's ID
        Player currentUser = request.session().attribute("currentUser");
        return new Gson().toJson(Message.info("" + gameManager.hasBoardBeenUpdated(currentUser)));
    }
}
