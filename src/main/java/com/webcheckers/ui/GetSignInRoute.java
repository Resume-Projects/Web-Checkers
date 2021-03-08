
package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;

public class GetSignInRoute implements Route {

    private final TemplateEngine templateEngine;

    public GetSignInRoute(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Map<String, Object> viewModel = new HashMap<>();
        return templateEngine.render(new ModelAndView(viewModel, "signin.ftl"));
    }
}
