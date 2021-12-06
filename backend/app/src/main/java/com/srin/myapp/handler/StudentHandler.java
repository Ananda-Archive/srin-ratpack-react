package com.srin.myapp.handler;

import com.srin.myapp.dao.StudentDao;
import com.srin.myapp.dto.StudentDto;
import ratpack.handling.Context;
import ratpack.handling.InjectionHandler;
import ratpack.http.Response;
import ratpack.jackson.Jackson;

public class StudentHandler extends InjectionHandler {

    public void handle(Context ctx, StudentDao studentDao) throws Exception {
        Response res = ctx.getResponse();

        ctx.byMethod(byMethodSpec -> byMethodSpec
                .options(() -> {
                    res.getHeaders().set("Access-Control-Allow-Methods", "OPTIONS, GET, POST, DELETE, PUT");
                    res.send();
                })
                .get(() -> {
                    studentDao.findAllStudents().map(Jackson::json).then(ctx::render);
                })
                .post(() -> {
                    ctx.parse(Jackson.fromJson(StudentDto.class))
                        .flatMap(studentDao::save)
                        .map(Jackson::json)
                        .then(ctx::render);
                })
                .put(() -> {
                    ctx.parse(Jackson.fromJson(StudentDto.class))
                        .flatMap(studentDao::update)
                        .map(Jackson::json)
                        .then(ctx::render);
                })
        );
    }

}
