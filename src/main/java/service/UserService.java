package service;

import db.DataBase;
import model.User;
import util.myutil.RequestUtil;

import java.util.Map;

public class UserService {
    public static void signUpUser(Map<String, String> query) {
        RequestUtil requestUtil = new RequestUtil();
        requestUtil.validateQuery(query);
        User user = new User(query.get("userId"), query.get("password"), query.get("name"), query.get("email"));
        System.out.println("user = " + user);
        validateUser(user);
        DataBase.addUser(user);
    }

    private static void validateUser(User user) {
        if (
                isBlank(user.getUserId())
                        || isBlank(user.getPassword())
                        || isBlank(user.getName())
                        || isBlank(user.getEmail())
        ) {
            throw new NullPointerException("필요값이 없습니다.");
        }
    }

    private static boolean isBlank(String str) {
        return str == null || str.isBlank();
    }
}
