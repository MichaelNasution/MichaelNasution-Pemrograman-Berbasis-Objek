package fintech.driver;

import java.util.*;
import fintech.model.*;


/**
 * @author 12S24003 - Michael Pratama Nasution
 */
public class Driver4 {
    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, Account> map = new HashMap<>();
        int id = 1;

    while (true) {
            String input = sc.nextLine();
            if (input.equals("---")) break;

            String[] p = input.split("#");

            if (p[0].equals("create-account")) {
                map.put(p[2], new Account(p[1], p[2]));
            }

            else if (p[0].equals("deposit")) {
                Account acc = map.get(p[1]);
                double amount = Double.parseDouble(p[2]);
                acc.deposit(amount);
                acc.addTransaction(new DepositTransaction(id++, p[1], amount, p[3], p[4]));
            }

            else if (p[0].equals("withdraw")) {
                Account acc = map.get(p[1]);
                double amount = Double.parseDouble(p[2]);

                try {
                    acc.withdraw(amount);
                    acc.addTransaction(new WithdrawTransaction(id++, p[1], -amount, p[3], p[4]));
                } catch (NegativeBalanceException e) {}
            }

            else if (p[0].equals("transfer")) {
                Account sender = map.get(p[1]);
                Account receiver = map.get(p[2]);
                double amount = Double.parseDouble(p[3]);

                try {
                    sender.withdraw(amount);
                    receiver.deposit(amount);

                    sender.addTransaction(new TransferTransaction(id++, p[1], p[2], -amount, p[4], p[5]));
                    receiver.addTransaction(new TransferTransaction(id++, p[2], p[1], amount, p[4], p[5]));

                } catch (NegativeBalanceException e) {}
            }

            else if (p[0].equals("show-account")) {
                Account acc = map.get(p[1]);
                System.out.println(acc.getUsername() + "|" + acc.getName() + "|" + acc.getBalance());

                List<Transaction> list = acc.getTransactions();
                Collections.sort(list);

                for (Transaction t : list) {
                    System.out.println(
                        t.getId() + "|" +
                        t.getType() + "|" +
                        t.getAmount() + "|" +
                        t.getTimestamp() + "|" +
                        t.getDescription()
                    );
                }
            }
        }
    }
}

