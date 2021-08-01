package controller;

import service.UserService;
import util.IOUtils;
import util.myutil.RequestUtil;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class PostController {
    public static byte[] findPostMapping(String url, Map<String, String> request) {
        RequestUtil requestUtil = new RequestUtil();
        Map<String, String> query = requestUtil.getQuery(request.get("body"));

        switch (request.get("path")) {
            case "/user/create":
                UserService.signUpUser(query);
                return "Create Success".getBytes(StandardCharsets.UTF_8);
            default:
                return "Null!".getBytes(StandardCharsets.UTF_8);
        }
    }
}
