package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostSubmitTurnRoute implements Route {

    private GameManager gameManager;

    public PostSubmitTurnRoute(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Player currentPlayer = request.session().attribute("currentUser");
        CheckersGame playersGame = gameManager.getPlayersGame(currentPlayer);
        return new Gson().toJson(playersGame.applyAttemptedMoves());
    }
}
