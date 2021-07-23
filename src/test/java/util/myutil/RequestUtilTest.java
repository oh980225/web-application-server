package util.myutil;

import db.DataBase;
import model.User;
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

    @Test
    public void splitUrl() throws IOException {
        String path = "/usr/create";
        String params = "userId=hello1&password=hello123&name=helloMan&email=hello@mail.com";
        String url = path + "?" + params;
        byte[] response = RequestUtil.getResponseBody(url);

        assertEquals("path is " + path + "\nparams is " + params, new String(response));
    }

    @Test
    public void getResponseBody_GET_회원가입() throws IOException {
        String path = "/usr/create";
        String params = "userId=hello1&password=hello123&name=helloMan&email=hello@mail.com";
        String url = path + "?" + params;
        User user = new User("hello1", "hello123", "helloMan", "hello@mail.com");
        byte[] response = RequestUtil.getResponseBody(url);

        assertEquals(DataBase.findUserById("hello1").getName(), user.getName());
        assertEquals(DataBase.findUserById("hello1").getPassword(), user.getPassword());
        assertEquals(DataBase.findUserById("hello1").getEmail(), user.getEmail());
    }

    @Test(expected = NullPointerException.class)
    public void 회원가입_params_empty() throws IOException {
        String path = "/usr/create";

        RequestUtil.getResponseBody(path);
    }

    @Test(expected = NullPointerException.class)
    public void 회원가입_NULL값() throws IOException {
        String path = "/usr/create";
        String params = "userId=hello1&password=hello123&name=&email=hello@mail.com";
        String url = path + "?" + params;
        User user = new User("hello1", "hello123", null, "hello@mail.com");

        RequestUtil.getResponseBody(url);
    }
}

