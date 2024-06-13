package Service;

import repository.UserRepo;

public class InMemory {
    public static UserRepo userRepo;
    public InMemory(){
        userRepo = new UserRepo();
    }
}
