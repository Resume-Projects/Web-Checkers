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
        final Player player = request.session().attribute("playerWatching");
        final int gameID = Integer.parseInt(request.queryParams("gameID"));
        vm.put("title", "Game Replay");
        if( request.session().attribute("currentUser") != null ) {
            // final Player player = request.session().attribute("playerWatching");
            // final int gameID = gameManager.getPlayersGame(player).getGameID();
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
            modeOptions.put("hasNext", savedGame.hasNext());
            modeOptions.put("hasPrevious", savedGame.hasPrevious());

            if( !savedGame.hasNext()) {
                modeOptions.put("isGameOver", true);
                modeOptions.put("gameOverMessage", "Game Over");
            }
            CheckersGame game = savedGame.getGame();
            vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
            vm.put("redPlayer", game.getRedPlayer());
            vm.put("whitePlayer", game.getWhitePlayer());
            vm.put("activeColor", game.getActiveColor());
            vm.put("board", game.getBoard());
            vm.put("message", "Viewing Replay of " + gameID);
        } else {
            // final int gameID = gameManager.getPlayersGame(player).getGameID();
            SavedGame savedGame = gameManager.getSavedGame(gameID);
            savedGame.setPlayerWatching(null);
            response.redirect(WebServer.HOME_URL);
            return null;
        }



        return null;
    }
}
