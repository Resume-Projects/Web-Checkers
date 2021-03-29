package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostCheckTurnRoute implements Route {

    private final GameManager gameManager;

    public PostCheckTurnRoute(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Player currentPlayer = request.session().attribute("currentUser");
        CheckersGame playersGame = gameManager.getPlayersGame(currentPlayer);
        Piece.Color activeColor = playersGame.getActiveColor();
        boolean isPlayersTurn = (currentPlayer.equals(playersGame.getRedPlayer()) && activeColor == Piece.Color.RED) ||
                                (currentPlayer.equals(playersGame.getWhitePlayer()) && activeColor == Piece.Color.WHITE);
        return new Gson().toJson(Message.info("" + isPlayersTurn));
    }
}
