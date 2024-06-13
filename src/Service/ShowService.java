package Service;

import dto.User;
import repository.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Util.UserUtil.getUserByName;

public class ShowService {
    public List<User> userList = UserRepo.getUserList();
    public void SIMPLIFY(){
        for(int i=0;i<userList.size();i++){
            User user = userList.get(i);
            Map<String, Integer> owes = user.getOwes();
            for(Map.Entry<String,Integer> entry:owes.entrySet()){
                int amt = entry.getValue();
                String borrower = entry.getKey();
                User bUser = getUserByName(borrower);
                Map<String, Integer> bMap = bUser.getOwes();
                if(bMap.containsKey(user.getName())){
                    if(bMap.get(user.getName())>amt){
                        bMap.put(user.getName(), bMap.get(user.getName())-amt);
                        owes.remove(borrower);
                    }
                    else{
                        bMap.remove(user.getName());
                        owes.put(borrower, amt-bMap.get(user.getName()));
                    }
                }
            }
        }

    }
    public List<String[]> SHOW(){
        List<String[]> ans = new ArrayList<>();
        SIMPLIFY();
        for(int i=0;i<userList.size();i++){
            User user = userList.get(i);
            Map<String, Integer> owes = user.getOwes();
            if(!owes.isEmpty()){
                for(Map.Entry<String,Integer> entry:owes.entrySet()){
                    int amt = entry.getValue();
                    String borrower = entry.getKey();
                    String[] arr = new String[3];
                    arr[0] = borrower;
                    arr[1] = user.getName();
                    arr[2] = String.valueOf(amt);
                    ans.add(arr);
                }
            }
        }
        if(ans.size()==0){
            System.out.println("No balances");
        }
        return ans;
//        for(int i=0;i<ans.size();i++){
//            String[] temp = ans.get(i);
//            System.out.println(temp[0]+" OWES "+temp[1]+" "+temp[2]);
//        }
    }
}
