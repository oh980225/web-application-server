package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.myutil.RequestUtil;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            DataOutputStream dos = new DataOutputStream(out);
            if (in == null) {
                System.out.println("NULL???");
            }
            RequestUtil requestUtil = new RequestUtil();
            Map<String, String> request = requestUtil.getRequest(bufferedReader);
            String method = request.get("method");
            String url = request.get("url");
            String requestBody = request.get("body");

            Map<String, Object> response = requestUtil.getResponseBody(method, url, requestBody);
//            byte[] body = requestUtil.getResponseBody(method, url, requestBody);
//            byte[] body = RequestUtil.getTest(bufferedReader);
            int statusCode = (int) response.get("status");
            byte [] content = (byte [])response.get("content");
            switch (statusCode) {
                case 302:
                    response302(dos, content);
                    break;
                default:
                    response200Header(dos, content.length);
                    responseBody(dos, content);
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302(DataOutputStream dos, byte[] location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: ");
            dos.write(location, 0, location.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
