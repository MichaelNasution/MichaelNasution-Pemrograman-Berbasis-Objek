
package fintech.model;

public class WithdrawTransaction extends Transaction {

    public WithdrawTransaction(int id, String username, double amount, String timestamp, String description) {
        super(id, username, amount, timestamp, description);
    }

    public String getType() {
        return "withdraw";
    }

}
