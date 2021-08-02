package util.myutil;

import controller.GetController;
import controller.PostController;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {
    public Map<String, String> getRequest(BufferedReader bufferedReader) throws IOException {
        Map<String, String> result = new HashMap<>();
        Map<String, Object> readResult = readRequest(bufferedReader);
        System.out.println(readResult.get("content").toString());
        validateRequest(readResult.get("content").toString());
        String[] tokens = splitLine(readResult.get("content").toString());
        result.put("method", findMethod(tokens));
        result.put("url", findUrl(tokens));
        if (readResult.get("length") != null) {
            result.put("body", IOUtils.readData(bufferedReader, Integer.parseInt((String) readResult.get("length"))));
        }

        return result;
    }

    private String findContentLength(BufferedReader bufferedReader) throws IOException {
        String str = null;
        do {
            str = bufferedReader.readLine();
            System.out.println(str);
            if (str == null || str.equals("")) {
                return null;
            }

        } while (!str.contains("Content-Length"));

        String[] tokens = splitLine(str);

        return tokens[1];
    }

    public String getRequestBody(BufferedReader bufferedReader, String length) throws IOException {
        if (length == null)
            return null;

        for (int i = 0; i < 10; i++) {
            String str = bufferedReader.readLine();
        }

        return bufferedReader.readLine();
    }

    private String findMethod(String[] tokens) {
        return tokens[0];
    }

    private String findUrl(String[] tokens) {
        return tokens[1];
    }

    private String[] splitLine(String line) {
        return line.split(" ");
    }

    private void validateRequest(String line) {
        if ("".equals(line) || line == null) {
            throw new RuntimeException("요청이 잘못됬습니다.");
        }
    }

    public Map<String, Object> getResponseBody(String method, String url, String requestBody) throws IOException {
        Map<String, String> request = splitUrl(url);
        request.put("body", requestBody);
        return findSuitableResponse(method, url, request);
    }

    private Map<String, Object> findSuitableResponse(
            String method,
            String url,
            Map<String, String> request) throws IOException {
        Map<String, Object> response = new HashMap<>();
        switch (method) {
            case "GET":
                return GetController.findGetMapping(url, request);
            case "POST":
                return PostController.findPostMapping(url, request);
            default:
                response.put("status", 404);
                response.put("content", "Null!".getBytes(StandardCharsets.UTF_8));
                return response;
        }
    }

    public Map<String, String> splitUrl(String url) {
        Map<String, String> request = new HashMap<>();
        int index = url.indexOf("?");
        if (index != -1) {
            request.put("params", url.substring(index + 1));
            request.put("path", url.substring(0, index));
            return request;
        }
        request.put("path", url);
        return request;
    }

    public Map<String, String> getQuery(String content) {
        if (content != null) {
            return HttpRequestUtils.parseQueryString(content);
        }

        return null;
    }

    public void validateQuery(Map<String, String> query) {
        if (query == null)
            throw new NullPointerException("필요값이 없습니다.");
    }

    public Map<String, Object> readRequest(BufferedReader bufferedReader) throws IOException {
        Map<String, Object> result = new HashMap<>();
        StringBuilder str = new StringBuilder();

        String line = bufferedReader.readLine();
//        if (line == null) {
//            return null;
//        }
        while (line != null && !line.equals("")) {
            if (line.contains("Content-Length")) {
                String[] tokens = splitLine(line);
                result.put("length", tokens[1]);
            }
            str.append(line);
            line = bufferedReader.readLine();
        }
        result.put("content", str.toString());

        return result;
    }
}
