
package fintech.model;
import java.util.ArrayList;
import java.util.List;


public class Account {

    private String username;
    private String name;
    private double balance;
    private List<Transaction> transactions;

    public Account(String name, String username) {
        this.name = name;
        this.username = username;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public String getUsername() { return username; }
    public String getName() { return name; }
    public double getBalance() { return balance; }
    public List<Transaction> getTransactions() { return transactions; }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) throws NegativeBalanceException {
        if(balance - amount < 0) {
            throw new NegativeBalanceException("Insufficient balance");
        }
        balance -= amount;
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }
}
