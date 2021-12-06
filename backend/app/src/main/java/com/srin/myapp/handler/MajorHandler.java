package com.srin.myapp.handler;

import com.srin.myapp.dao.MajorDao;
import ratpack.handling.Context;
import ratpack.handling.InjectionHandler;
import ratpack.http.Response;
import ratpack.jackson.Jackson;

public class MajorHandler extends InjectionHandler {

    public void handle(Context ctx, MajorDao majorDao) throws Exception {
        Response res = ctx.getResponse();

        ctx.byMethod(byMethodSpec -> byMethodSpec
                .options(() -> {
                    res.getHeaders().set("Access-Control-Allow-Methods", "OPTIONS, GET, POST, DELETE");
                    res.send();
                })
                .get(() -> {
                    majorDao.findAllMajor().map(Jackson::json).then(ctx::render);
                })
        );
    }

}
