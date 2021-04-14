package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.SavedGame;
import com.webcheckers.util.Message;
import spark.*;

public class PostPreviousTurnRoute implements Route {

    private GameManager gameManager;
    private Gson gson;

    public PostPreviousTurnRoute(GameManager gameManager, Gson gson) {

    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        SavedGame savedGame = gameManager.getSavedGame(request.queryParams("gameID"));
        int currentTurn = savedGame.getTurnNum();
        savedGame.nextTurn();
        if( savedGame.getTurnNum() < 0) {
            savedGame.setTurnNum(0);
            return gson.toJson(Message.error("Error in going to previous turn"));
        }
        else {
            return gson.toJson(Message.info("true"));
        }
    }
}
