package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostResignGameRoute implements Route {

    public PostResignGameRoute() {

    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return new Gson().toJson(Message.info("Good"));
    }
}
