package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.Player;
import com.webcheckers.model.SavedGame;
import com.webcheckers.util.Message;
import spark.*;

public class PostPreviousTurnRoute implements Route {

    private GameManager gameManager;
    private Gson gson;

    public PostPreviousTurnRoute(GameManager gameManager, Gson gson) {
        this.gameManager = gameManager;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        Player currentUser = request.session().attribute("playerWatching");
        SavedGame savedGame = gameManager.getSavedGame(request.queryParams("gameID"));
        int currentTurn = savedGame.getTurnNum();
        savedGame.setTurnNum(currentTurn - 1);
        savedGame.updateBoard();
        if( savedGame.getTurnNum() < 0) {
            savedGame.setTurnNum(0);
            return gson.toJson(Message.error("Error in going to previous turn"));
        }
        else {
            return gson.toJson(Message.info("true"));
        }
    }
}
