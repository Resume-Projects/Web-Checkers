package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostResignGameRoute implements Route {

    private final GameManager gameManager;

    public PostResignGameRoute(GameManager gameManager) {

        this.gameManager = gameManager;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Player player = request.session().attribute("currentUser");
        boolean resign = gameManager.resignGame(player);
        if(resign){
            return new Gson().toJson(Message.info(player.getName() + "has resigned."));
        }
        return new Gson().toJson(Message.error("Resign failed"));
    }
}
