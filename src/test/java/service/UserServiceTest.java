package service;

import db.DataBase;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {

    @Test
    public void 회원가입() throws IOException {
        Map<String, String> query = new HashMap<>();
        query.put("userId", "hello1");
        query.put("password", "hello123");
        query.put("name", "helloMan");
        query.put("email", "hello@mail.com");

        UserService.signUpUser(query);

        assertAll(
                () -> assertEquals(DataBase.findUserById("hello1").getUserId(), query.get("userId")),
                () -> assertEquals(DataBase.findUserById("hello1").getPassword(), query.get("password")),
                () -> assertEquals(DataBase.findUserById("hello1").getName(), query.get("name")),
                () -> assertEquals(DataBase.findUserById("hello1").getEmail(), query.get("email"))
        );
    }

    @Test
    public void 회원가입_params_empty() throws IOException {
        NullPointerException e = assertThrows(NullPointerException.class, () -> UserService.signUpUser(null));

        assertEquals("필요값이 없습니다.", e.getMessage());
    }

    @Test
    public void 회원가입_NULL값() throws IOException {
        Map<String, String> query = new HashMap<>();
        query.put("userId", "hello1");
        query.put("password", "hello123");
        query.put("name", "helloMan");

        NullPointerException e = assertThrows(NullPointerException.class, () -> UserService.signUpUser(query));

        assertEquals("필요값이 없습니다.", e.getMessage());
    }
}