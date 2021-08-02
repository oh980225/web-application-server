package controller;

import service.UserService;
import util.IOUtils;
import util.myutil.RequestUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PostController {
    public static Map<String, Object> findPostMapping(String url, Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        RequestUtil requestUtil = new RequestUtil();
        Map<String, String> query = requestUtil.getQuery(request.get("body"));

        switch (request.get("path")) {
            case "/user/create":
                UserService.signUpUser(query);
                response.put("status", 302);
                response.put("content", "/index.html".getBytes(StandardCharsets.UTF_8));
                break;
            default:
                response.put("status", 404);
                response.put("content", "Null!".getBytes(StandardCharsets.UTF_8));
                break;
        }
        return response;
    }
}
