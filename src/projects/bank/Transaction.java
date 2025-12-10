
package projects.bank;

public abstract class Transaction {
    private double amount;
    private String accountID;

    /**
     * Set a transaction default parameters.
     * 
     * @param accountNumber  - corresponding unique account identifier where the
     *                       transaction applies, string value
     * @param transactionAmt - amount of the transaction, positive double
     * 
     *                       This method is only accessible by its subclasses.
     */
    protected Transaction(String accountNumber, double transactionAmt) {
        if (accountNumber == null) {
            throw new IllegalArgumentException("account number cannot be null.");
        }
        if (transactionAmt <= 0) {
            throw new IllegalArgumentException(
                    "transaction amount must be positive.");
        }

        amount = transactionAmt;
        accountID = accountNumber;

    }

    /**
     * This method gets a given transaction's amount
     * 
     * @return amount - transaction's amount to be applies onto the account balance.
     *         it should be a double value.
     * 
     */
    public double getAmount() {
        return amount;
    }

    /**
     * This method gets a given transaction's corresponding account number.
     * 
     * @return accountID - account unique identifier, string value
     * 
     */
    public String getAccountNumber() {
        return accountID;
    }

    /*
     * this method should return each Transaction type.
     * It will be further defined in the subclasses.
     */
    public abstract TransactionType getType();

    /**
     * This method confirm that a transaction can be operated on a given's account.
     * 
     * @param account - Account of the transaction
     * 
     * @return true - if transaction can be operated or
     *         false - when the transaction cannot be operated on the account.
     * 
     *         This method will be further defined in the subclasses.
     */
    public abstract boolean validate(Account account, Audit audit);

    /**
     * This method execute the transcation and affect the account's balance.
     * 
     * @param account - account to be modified by the transaction
     * 
     *                further implementation will occur in the subclasses.
     */
    public abstract void execute(Account account, Audit audit);

    /*
     * Factory method to create Transaction objects from a CSV line.
     * 
     * The line should be in the format:
     * 
     * "withdrawal,<accountID>,<amount>" or
     * "deposit,<accountID>,<amount>"
     * 
     * @param line - CSV formatted string
     * 
     * @return Transaction object (either Withdrawal or Deposit)
     * 
     * @throws IllegalArgumentException if the line is null or
     * improperly formatted
     * 
     * or if the transaction type is unknown
     * 
     */
    protected static Transaction make(String line) {
        if (line == null) {
            throw new IllegalArgumentException("line must not be null.");
        }

        String[] token = line.split(",");
        TransactionType type = TransactionType.valueOf(token[0].toUpperCase());
        String id = token[1];
        Double amount = Double.parseDouble(token[2]);
        Transaction dep = new Deposit(id, amount);
        if (type == TransactionType.WITHDRAWAL) {
            Transaction wdraw = new Withdrawal(id, amount);
            return wdraw;
        } else
            return dep;

    }

    /*
     * Return a string representation of this transaction.
     * 
     * @return String in CSV format: "type,accountID,amount"
     * 
     * eg, "withdrawal,wz240833,500.00"
     * 
     */
    @Override
    public String toString() {
        return String.format(
                "%s,%s,%.2f", // format double to 2 decimal places
                getType().name().toLowerCase(),
                getAccountNumber(),
                getAmount());
    }

    /*
     * Create a CSV line holding this transaction's data.
     * 
     * @return Eg, "savings,wz240833,Anna Gomez,8111.00"
     */
    public String toCSV() {
        return toString();
    }
}
