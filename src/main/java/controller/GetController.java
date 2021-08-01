package controller;

import db.DataBase;
import service.UserService;
import util.myutil.RequestUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

public class GetController {
    public static byte[] findGetMapping(String url, Map<String, String> request) throws IOException {
        RequestUtil requestUtil = new RequestUtil();
        Map<String, String> query = requestUtil.getQuery(request.get("params"));

        switch (request.get("path")) {
            case "/":
                return getResponseByRootRequest();
//            case "/user/create":
//                UserService.signUpUser(query);
//                return getResponseSignUp(request);
            case "/users":
                return getResponseUserList();
            default:
                return Files.readAllBytes(new File("./webapp" + url).toPath());
        }
    }

    private static byte[] getResponseByRootRequest() {
        return "Hello My Playground!".getBytes();
    }

    private static byte[] getResponseSignUp(Map<String, String> request) {
        String body = "path is " + request.get("path") + "\n" +
                "params is " + request.get("params");
        return body.getBytes(StandardCharsets.UTF_8);
    }

    private static byte[] getResponseUserList() {
        return DataBase.findAll().toString().getBytes(StandardCharsets.UTF_8);
    }
}
