import Service.ExpenceService;
import Service.InMemory;
import Service.ShowService;
import dto.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Util.UserUtil.UserExists;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InMemory inm = new InMemory();
        ExpenceService expenceService = new ExpenceService();
        ShowService showService = new ShowService();

        while (true) {
            String inp = sc.nextLine();
            if (inp.trim().isEmpty()) {
                continue; // skip empty input
            }
            String[] inpt = inp.split(" ");

            try {
                if (inpt.length == 0) {
                    System.out.println("Invalid Command");
                    continue;
                }

                switch (inpt[0]) {
                    case "SHOW":
                        handleShowCommand(inpt, showService);
                        break;

                    case "EXPENSE":
                        handleExpenseCommand(inpt, expenceService);
                        break;

                    default:
                        System.out.println("Invalid Command");
                }
            } catch (RuntimeException rne) {
                System.out.println("Error: " + rne.getMessage());
            }
        }
    }

    private static void handleShowCommand(String[] inpt, ShowService showService) {
        if (inpt.length == 1) {
            //System.out.println("Executing SHOW command...");
            List<String[]> ans = showService.SHOW();
            for(int i=0;i<ans.size();i++){
                String[] temp = ans.get(i);
                System.out.println(temp[0]+" OWES "+temp[1]+" "+temp[2]);
            }
        } else if (inpt.length == 2) {

            String username = inpt[1];
            if(!UserExists(username)){
                System.out.println("No Balance");
            }
            else{
                List<String[]> ans = showService.SHOW();
                for(int i=0;i<ans.size();i++){
                    String[] temp = ans.get(i);
                    if(temp[0].equals(username) || temp[1].equals(username)){
                        System.out.println(temp[0]+" OWES "+temp[1]+" "+temp[2]);
                    }
                }
            }
        } else {
            throw new RuntimeException("Too many arguments in show");
        }
    }

    private static void handleExpenseCommand(String[] inpt, ExpenceService expenceService) {
        int n = Integer.parseInt(inpt[3]);
        String mode = inpt[3 + n + 1];

        List<String> borrowers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            borrowers.add(inpt[3 + i]);
        }

        switch (mode) {
            case "EQUAL":
                expenceService.ADD_EXP_EQUAL(inpt[1], Integer.parseInt(inpt[2]), borrowers);
                break;

            case "EXACT":
                List<Integer> exactValues = new ArrayList<>();
                for (int i = 1; i <= n; i++) {
                    exactValues.add(Integer.parseInt(inpt[4 + n + i]));
                }
                expenceService.ADD_EXP_EXACT(inpt[1], Integer.parseInt(inpt[2]), borrowers, exactValues);
                break;

            case "PERCENT":
                List<Integer> percentValues = new ArrayList<>();
                for (int i = 1; i <= n; i++) {
                    percentValues.add(Integer.parseInt(inpt[4 + n + i]));
                }
                expenceService.ADD_EXP_PERCENT(inpt[1], Integer.parseInt(inpt[2]), borrowers, percentValues);
                break;

            default:
                System.out.println("Invalid expense mode");
        }

    }
}
