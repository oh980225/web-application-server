package util.myutil;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RequestUtilTest {
    @Test
    public void getUrlFromRequest() throws IOException {
        BufferedReader bufferedReader =  new BufferedReader(new FileReader("C:\\Users\\ohseu\\Desktop\\Study\\SpringProject\\web-application-server\\src\\test\\java\\util\\myutil\\index.txt"));
        assertEquals("/index.html", RequestUtil.getUrlFromRequest(bufferedReader));
    }

    @Test(expected = RuntimeException.class)
    public void getUrlFromRequest_빈요청() throws IOException {
        BufferedReader bufferedReader =  new BufferedReader(new FileReader("C:\\Users\\ohseu\\Desktop\\Study\\SpringProject\\web-application-server\\src\\test\\java\\util\\myutil\\null.txt"));
        RequestUtil.getUrlFromRequest(bufferedReader);
    }

    @Test
    public void getResponseBody() throws IOException {
        String url = "/";
        assertEquals(new String(RequestUtil.getResponseBody(url)), "Hello My Playground!");
    }
}

