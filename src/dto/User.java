package dto;

import java.util.HashMap;
import java.util.Map;

public class User {
    String name;
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    Map<String, Integer> owes;
    public Map<String, Integer> getOwes(){
        return owes;
    }
    public void setOwes(Map<String, Integer> owes){
        this.owes = owes;
    }
    public User(String name){
        this.name = name;
        owes = new HashMap<>();
    }
}
