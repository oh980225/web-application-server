package util.myutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
        if(url.equals("/")) {
            return getResponseByRootRequest();
        }
        return Files.readAllBytes(new File("./webapp" + url).toPath());
    }

    private static byte[] getResponseByRootRequest() {
        return "Hello My Playground!".getBytes();
    }
}
