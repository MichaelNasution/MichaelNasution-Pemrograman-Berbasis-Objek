
package fintech.model;

public class TransferTransaction extends Transaction {

    private String receiver;

    public TransferTransaction(int id, String username, String receiver, double amount, String timestamp, String description) {
        super(id, username, amount, timestamp, description);
        this.receiver = receiver;
    }

    public String getType() {
        return "transfer";
    }

    public String getReceiver() {
        return receiver;
    }

}
