package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostValidateMoveRoute implements Route {

    private GameManager gameManager;
    private Gson gson;

    public PostValidateMoveRoute(GameManager gameManager, Gson gson) {
        this.gameManager = gameManager;
        this.gson = gson;
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
        Move attemptedMove = gson.fromJson(request.queryParams("actionData"), Move.class);
        CheckersGame playersGame = gameManager.getPlayersGame(currentPlayer);
        playersGame.saveAttemptedMove(attemptedMove);
        return gson.toJson(Message.info("Good"));
    }
}
