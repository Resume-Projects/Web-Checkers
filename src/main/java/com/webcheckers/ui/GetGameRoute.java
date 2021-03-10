package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;
import static spark.Spark.halt;

/**
 * The UI Controller to GET the Game page.
 */
public class GetGameRoute implements Route {
    static final String TITLE = "Web Checkers";
    static final String VIEW_NAME = "game.ftl";

    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetGameRoute (final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * Render the WebCheckers Game page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Game page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Map<String, Object> viewModel = new HashMap<>();
        return templateEngine.render(new ModelAndView(viewModel, "game.ftl"));
    }
}
