package Util;

import dto.User;
import repository.UserRepo;

import java.util.List;

public class UserUtil {
    public static User getUserByName(String username){
        List<User> userList = UserRepo.getUserList();
        for(int i=0;i<userList.size();i++){
            if(userList.get(i).getName().equals(username))return userList.get(i);
        }
        throw new RuntimeException("No User Found");
    }
    public static boolean UserExists(String username){
        boolean flag = false;
        List<User> userList = UserRepo.getUserList();
        if(userList.size()==0)return false;
        for(int i=0;i<userList.size();i++){
            if(userList.get(i).getName().equals(username)){
                flag = true;
            }
        }
        return flag;
    }
}
