/** TODO list
 * remove `amount` validation from credit and debit methods. prefer to not allow constructing a transaction with a negative amount.
 * 
 */
package projects.bank;

public abstract class Account {

    private final String accountID;
    private final String accountOwnerName;
    private double currentBalance;
    private double newBal;

    // Phase 1
    /**
     * Each account will hold the following values:
     * 
     * @param accountNumber   - alphanumeric Unique account identifier, definitive
     *                        value
     * 
     * @param name            - Name of the account owner, could be repeated across
     *                        accounts, definitive value
     * 
     * @param startingBalance - Current balance of the account, could be negative ,
     *                        variable value
     * 
     */
    public Account(
            String accountNumber,
            String name,
            double startingBalance) {
        if (accountNumber == null) {
            throw new IllegalArgumentException("account ID cannot be empty.");
        }
        if (name == null) {
            throw new IllegalArgumentException(
                    "account owner's name cannot be empty.");
        }

        accountID = accountNumber;
        accountOwnerName = name;
        currentBalance = startingBalance;
    }

    public String getID() {
        return accountID;
    }

    public String getOwner() {
        return accountOwnerName;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public abstract AccountType getType();

    // Phase 2
    /*
     * Parses a line from a CSV file and creates the appropriate Account object.
     *
     * @param line - A line from a CSV file representing an account.
     *
     * @return An Account object (either CheckingAccount or SavingsAccount).
     *
     * @throws IllegalArgumentException if the line is null or has an unknown
     * account type.
     */
    public static Account make(String line) {
        if (line == null) {
            throw new IllegalArgumentException("line must not be null.");
        }
        String[] token = line.split(",");
        AccountType type = AccountType.valueOf(token[0].toUpperCase());
        String id = token[1];
        String owner = token[2];
        double balance = Double.parseDouble(token[3]);
        if (type == AccountType.CHECKING) {
            Account chacct = new CheckingAccount(id, owner, balance);
            return chacct;
        } else if (type == AccountType.SAVINGS) {
            Account svacct = new SavingsAccount(id, owner, balance);
            return svacct;
        } else {
            throw new IllegalArgumentException("Invalid account type.");
        }
    }

    /*
     * Read an account parameters and format them into a String
     * in this order accounttype (lowercase), accountID, accountOwner,
     * accountBalance
     * accountBalance will be able to carry up to two decimals
     */
    @Override
    public String toString() {
        return String.format(
                "%s,%s,%s,%.2f", // format double to 2 decimal places
                getType().name().toLowerCase(),
                getID(),
                getOwner(),
                getCurrentBalance());
    }

    /**
     * return a CSV line holding this account's data.
     * 
     * @return Eg, "savings,wz240833,Anna Gomez,8111.00"
     */
    public String toCSV() {
        return toString();
    }

    // Phase 3
    /*
     * Credits (deposits) the given amount to the account.
     *
     * @param amount - The amount to deposit (must be positive).
     *
     * @throws IllegalArgumentException if the amount is negative.
     */
    public void credit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must be positive.");
        }
        newBal = currentBalance + amount;
    }

    /*
     * Debits (withdraws) the given amount from the account.
     * 
     * @param amount - The amount to withdraw (must be positive and within daily
     * limit).
     * 
     * @throws IllegalArgumentException if the amount is negative.
     * 
     */
    public void debit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must be positive.");
        } else {

            newBal = currentBalance - amount;
        }
    }

    /**
     * NEW!!
     * 
     * Method returns the new account balance
     * post-execution
     * 
     * @return newBalance = the account's new balance;
     */
    public double setNewBalance() {
        return newBal;
    }
}
