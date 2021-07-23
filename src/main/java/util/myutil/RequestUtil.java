package util.myutil;

import db.DataBase;
import model.User;
import util.HttpRequestUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {
    public static String getUrlFromRequest(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        validateRequest(line);
        String[] tokens = splitLine(line);
        return findUrl(tokens);
    }

    private static String findUrl(String[] tokens) {
        return tokens[1];
    }

    private static String[] splitLine(String line) {
        return line.split(" ");
    }

    private static void validateRequest(String line) {
        if ("".equals(line) || line == null) {
            throw new RuntimeException("요청이 잘못됬습니다.");
        }
    }

    public static byte[] getResponseBody(String url) throws IOException {
        Map<String, String> request = splitUrl(url);
        Map<String, String> query = getQuerys(request);

        switch (request.get("path")) {
            case "/":
                return getResponseByRootRequest();

            case "/usr/create":
                signUpUser(query);
                return getResponseSignUp(request);

            default:
                return Files.readAllBytes(new File("./webapp" + url).toPath());
        }
    }

    private static void signUpUser(Map<String, String> query) {
        validateQuery(query);
        User user = new User(query.get("userId"), query.get("password"), query.get("name"), query.get("email"));
        validateUser(user);
        DataBase.addUser(user);
    }

    private static void validateQuery(Map<String, String> query) {
        if(query == null)
            throw new  NullPointerException("필요값이 없습니다.");
    }

    private static Map<String, String> getQuerys(Map<String, String> request) {
        if(request.get("params") != null) {
            return HttpRequestUtils.parseQueryString(request.get("params"));
        }

        return null;
    }

    private static void validateUser(User user) {
        if(
                user.getUserId().isBlank()
                || user.getEmail().isBlank()
                || user.getName().isBlank()
                || user.getPassword().isBlank()
        ) {
            throw new NullPointerException("필요값이 없습니다.");
        }
    }

    private static byte[] getResponseSignUp(Map<String, String> request) {
        String body = "path is " + request.get("path") + "\n" +
                "params is " + request.get("params");
        return body.getBytes(StandardCharsets.UTF_8);
    }

    private static Map<String, String> splitUrl(String url) {
        Map<String, String> request = new HashMap<>();
        int index = url.indexOf("?");
        if(index != -1) {
            request.put("params", url.substring(index+1));
            request.put("path", url.substring(0, index));
            return request;
        }
        request.put("path", url);
        return request;
    }

    private static byte[] getResponseByRootRequest() {
        return "Hello My Playground!".getBytes();
    }
}
