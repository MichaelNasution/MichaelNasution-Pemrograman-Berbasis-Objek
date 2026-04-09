package fintech.driver;

import java.util.*;
import fintech.model.*;




/**
 * @author 12S24003 - Michael Pratama Nasution
 */
public class Driver1 {

    public static void main(String[] _args) {

        Scanner sc = new Scanner(System.in);
        List<Account> accounts = new ArrayList<>();

        while (true)    {

         String input = sc.nextLine();
         if (input.equals("---")) break;
            
        String[] parts = input.split("#");

        if (parts[0].equals("create-account")) {
                accounts.add(new Account(parts[1], parts[2]));
        }
    }

     for (Account acc : accounts) {
        System.out.println(acc.getUsername() + "|" + acc.getName() + "|" + acc.getBalance());
    }
    }
}

        
        


