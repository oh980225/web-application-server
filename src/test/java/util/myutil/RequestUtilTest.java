package util.myutil;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

public class RequestUtilTest {

    RequestUtil requestUtil;

    @Before
    public void init() {
        requestUtil = new RequestUtil();
    }

    @Test
    public void getRequest() throws IOException {
        BufferedReader bufferedReader =  new BufferedReader(new FileReader("src/test/java/util/myutil/index.txt"));

        Map<String, String> request = requestUtil.getRequest(bufferedReader);

        assertEquals("/index.html", request.get("url"));
        assertEquals("GET", request.get("method"));
    }

    @Test
    public void getRequest_본문길이() throws IOException {
        BufferedReader bufferedReader =  new BufferedReader(new FileReader("src/test/java/util/myutil/post.txt"));

        Map<String, String> request = requestUtil.getRequest(bufferedReader);

        assertEquals(59, request.get("body").length());
    }

    @Test
    public void getRequest_본문바디() throws IOException {
        BufferedReader bufferedReader =  new BufferedReader(new FileReader("src/test/java/util/myutil/post.txt"));

        Map<String, String> request = requestUtil.getRequest(bufferedReader);

        assertEquals("userId=hello&password=hello123&name=helloMan&hello@mail.com", request.get("body"));
    }

    @Test(expected = RuntimeException.class)
    public void getRequest_빈요청() throws IOException {
        BufferedReader bufferedReader =  new BufferedReader(new FileReader("C:\\Users\\ohseu\\Desktop\\Study\\SpringProject\\web-application-server\\src\\test\\java\\util\\myutil\\null.txt"));

        requestUtil.getRequest(bufferedReader);
    }

    @Test
    public void getResponseBody() throws IOException {
        String url = "/";
        String method = "GET";
        assertEquals("Hello My Playground!", new String(requestUtil.getResponseBody(method, url, null)));
    }

    @Test
    public void splitUrl() throws IOException {
        String path = "/user/create";
        String params = "userId=hello1&password=hello123&name=helloMan&email=hello@mail.com";
        String url = path + "?" + params;

        Map<String, String> result = requestUtil.splitUrl(url);

        assertAll(
                () -> assertEquals(path, result.get("path")),
                () -> assertEquals(params,result.get("params"))
        );
    }
}

