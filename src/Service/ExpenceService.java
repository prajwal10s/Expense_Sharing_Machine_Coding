package Service;

import dto.User;
import repository.UserRepo;
import static Util.UserUtil.UserExists;
import static Util.UserUtil.getUserByName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpenceService {
    public List<User> userList = UserRepo.getUserList();
    public void ADD_USERS(String payer, List<String> borrowers){
        if(!UserExists(payer)){
            User user = new User(payer);
            userList.add(user);
        }
        for(int i=0;i<borrowers.size();i++){
            if(!UserExists(borrowers.get(i))){
                User user = new User(borrowers.get(i));
                userList.add(user);
            }
        }
    }
    public void ADD_EXP_EQUAL(String payer, int amount, List<String> borrowers){
        //System.out.println("here1");
        ADD_USERS(payer, borrowers);
        //System.out.println("here");
        User Payer = getUserByName(payer);
        Map<String, Integer> owes = Payer.getOwes();
        for(int i=0;i<borrowers.size();i++){
            if(!borrowers.get(i).equals(payer)){
                if(owes.containsKey(borrowers.get(i))){
                    int oldAmt = owes.get(borrowers.get(i));
                    oldAmt = oldAmt + amount/borrowers.size();
                    owes.put(borrowers.get(i), oldAmt);
                }
                else{
                    owes.put(borrowers.get(i), amount/borrowers.size());
                }
            }
        }
    }
    public void ADD_EXP_EXACT(String payer, int amount, List<String> borrowers,List<Integer> values){
        ADD_USERS(payer, borrowers);
        User Payer = getUserByName(payer);
        Map<String, Integer> owes = Payer.getOwes();
        int count = 0;
        for(int i=0;i< values.size();i++){
            count+=values.get(i);
        }
        if(count!=amount)throw new RuntimeException("EXACT AMOUNTS DONT ADD UP");
        for(int i=0;i<borrowers.size();i++){
            if(!borrowers.get(i).equals(payer)){
                if(owes.containsKey(borrowers.get(i))){
                    int oldAmt = owes.get(borrowers.get(i));
                    int newAmt = values.get(i);
                    owes.put(borrowers.get(i), oldAmt+newAmt);
                }
                else{
                    owes.put(borrowers.get(i), values.get(i));
                }
            }
        }
    }
    public void ADD_EXP_PERCENT(String payer, int amount, List<String> borrowers,List<Integer> percents){
        ADD_USERS(payer, borrowers);
        User Payer = getUserByName(payer);
        Map<String, Integer> owes = Payer.getOwes();
        int count = 0;
        for(int i=0;i< percents.size();i++){
            count+=percents.get(i);
        }
        if(count!=100)throw new RuntimeException("PERCENT DONT ADD UP TO 100");
        for(int i=0;i<borrowers.size();i++){
            if(!borrowers.get(i).equals(payer)){
                if(owes.containsKey(borrowers.get(i))){
                    int oldAmt = owes.get(borrowers.get(i));
                     int newAmt = (amount*(percents.get(i)/10))/10;
                    owes.put(borrowers.get(i), oldAmt+newAmt);
                }
                else{
                    int newAmt = (amount*(percents.get(i)/10))/10;
                    owes.put(borrowers.get(i), newAmt);
                }
            }
        }
    }
}
