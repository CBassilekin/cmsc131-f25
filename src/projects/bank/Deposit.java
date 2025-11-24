package projects.bank;

public class Deposit extends Transaction {
    public Deposit(String accountID, double transactionAmt) {
        super(accountID, transactionAmt);
    }

    /**
     * Returns Deposit for a given transaction.
     */
    @Override
    public TransactionType getType() {
        return TransactionType.DEPOSIT;
    }

    /**
     * Returns boolean
     * true - to allow the transaction to proceed without hold.
     * Deposit should only always validate as a default value.
     */
    @Override
    public boolean validate(Account account, Audit audit) {
        return true;
    }

    /**
     * Trigger the execution of the deposit and
     * affects the account balance.
     */
    @Override
    public void execute(Account account, Audit audit) {
        account.credit(getAmount());
        audit.recordExecute(this, account);
    }
}
