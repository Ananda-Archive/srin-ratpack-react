package com.srin.myapp.handler;

import com.srin.myapp.dao.StudentDao;
import ratpack.handling.Context;
import ratpack.handling.InjectionHandler;
import ratpack.http.Response;
import ratpack.jackson.Jackson;

public class StudentHandlerWithId  extends InjectionHandler {

    public void handle(Context ctx, StudentDao student) throws Exception {
        Long id = Long.parseLong(ctx.getPathTokens().get("id"));
        Response res = ctx.getResponse();

        ctx.byMethod(byMethodSpec -> byMethodSpec
                .options(() -> {
                    res.getHeaders().set("Access-Control-Allow-Methods", "OPTIONS, GET, POST, DELETE");
                    res.send();
                })
                .delete(() -> student.delete(id).map(Jackson::json).then(ctx::render))
                .get(() -> student.findStudentById(id).map(Jackson::json).then(ctx::render))
        );
    }

}
