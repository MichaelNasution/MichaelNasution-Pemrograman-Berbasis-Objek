
package fintech.model;

public abstract class Transaction implements Comparable<Transaction> {

    protected int id;
    protected String username;
    protected double amount;
    protected String timestamp;
    protected String description;

    public Transaction(int id, String username, double amount, String timestamp, String description) {
        this.id = id;
        this.username = username;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description;
    }

    public abstract String getType();

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int compareTo(Transaction o) {
        return this.timestamp.compareTo(o.timestamp);
    }
}