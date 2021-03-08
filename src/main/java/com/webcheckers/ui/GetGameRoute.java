package com.webcheckers.ui;

import spark.TemplateEngine;

public class GetGameRoute {
    static final String TITLE = "Web Checkers";
    static final String VIEW_NAME = "game.ftl";

    private final TemplateEngine templateEngine;

    public GetGameRoute (final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
}
