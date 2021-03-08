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

public class GetGameRoute implements Route {
    static final String TITLE = "Web Checkers";
    static final String VIEW_NAME = "game.ftl";

    private final TemplateEngine templateEngine;

    public GetGameRoute (final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Map<String, Object> viewModel = new HashMap<>();
        return templateEngine.render(new ModelAndView(viewModel, "game.ftl"));
    }
}
