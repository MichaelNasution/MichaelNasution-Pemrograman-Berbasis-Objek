
package fintech.model;

public class DepositTransaction extends Transaction {

    public DepositTransaction(int id, String username, double amount, String timestamp, String description) {
        super(id, username, amount, timestamp, description);
    }

    public String getType() {
        return "deposit";
    }

}
