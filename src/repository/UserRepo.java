package repository;

import dto.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepo {
    public static List<User> userList;
    public UserRepo(){
        userList = new ArrayList<>();
    }
    public static List<User> getUserList(){
        return userList;
    }
}
