package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostValidateMoveRoute implements Route {

    private final GameManager gameManager;

    public PostValidateMoveRoute(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        //*********************************************************************************
        //All of the code here will run once a player drops a piece on the board
        //request.queryParams("actionData") will return the attempted move in a json format
        //The attempted move will now be stored in the CheckersGame, and when the submit
        //button is hit, the move will take effect
        //*********************************************************************************
        Player currentPlayer = request.session().attribute("currentUser");
        Move attemptedMove = new Gson().fromJson(request.queryParams("actionData"), Move.class);
        CheckersGame playersGame = gameManager.getPlayersGame(currentPlayer);
        return new Gson().toJson(playersGame.saveAttemptedMove(attemptedMove));
    }
}
