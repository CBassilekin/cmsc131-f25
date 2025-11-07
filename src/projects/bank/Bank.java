/** comments/TODO
 * because you've loaded the transactions into this bank's transactions attribute, you don't need to return them. but the code works so it's fine
 * getTransactionsCount is a more descriptive name than getTransactions
 * 
 */
package projects.bank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Bank {
    private static Account[] accounts;
    private static int accountCount;
    private static Scanner scanner;
    private static FileWriter writer;
    private static Transaction[] transactions;
    private static int transactionsCount;
    private static Account[] activeAccounts;

    // Phase 1
    public Bank() {
        accounts = new Account[392];
        accountCount = 0;
        transactions = new Transaction[1000];
        transactionsCount = 0;
    }

    /**
     * Confirm that an account is successfully added to the bank database.
     * 
     * @param account the valid account to be added.
     * 
     * @return true if the account was added successfully, false if an account with
     *         the same ID already exists.
     * 
     * @throws ArrayIndexOutOfBoundsException if the bank has reached its maximum
     *                                        capacity.
     */
    public boolean add(Account account) {
        // Check if account ID already exist
        if (account == null) {
            throw new IllegalArgumentException("account must not be null.");
        }
        if (find(account.getID()) != -1) {
            // Account with same ID already exists
            return false;
        }
        // Otherwise, add the new account
        // what if accountCount is already at max capacity?
        if (accountCount >= accounts.length) {
            // to increase capacity by a fixed quantity each time (say add 100 new slots)
            Account[] newAccounts = new Account[accounts.length * 2];
            System.arraycopy(accounts, 0, newAccounts, 0, accounts.length);
            accounts = newAccounts;
            accountCount++;
            accounts[accountCount++] = account;
            return true;
        }
        // The bank database has space, add the account
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] == null) {
                accounts[i] = account;
                accountCount++;
                break;
            }
        }
        return true;
    }

    /**
     * Find an account by its ID.
     * 
     * @param accountID the ID of the account to find.
     * 
     * @return returns the index of the account in the array or -1 if not found.
     */
    public int find(String accountID) {
        if (accountID == null) {
            throw new IllegalArgumentException("accountID must not be null.");
        }
        for (int i = 0; i < accountCount; i++) {
            // so you can simplify the condition on line 62
            if (accounts[i].getID().equals(accountID)) {
                return i;
            }
        }
        return -1; // Account not found
    }

    /**
     * Get the number of active accounts in the bank.
     * 
     * @return the number of active accounts.
     */
    public int getCount() {
        return accountCount;
    }

    // Phase 2
    /**
     * Read the file containing the bank accounts and fill the datatbase
     * 
     * @param file the file containing the bank accounts.
     * 
     * @return true if the file was read successfully, false otherwise.
     * 
     * @throws Exception if there was an error reading the file.
     * 
     */
    public boolean loadAccounts(String fileName) {
        // Load accounts one by one into the bank

        File fileToLoad = new File(fileName);
        try {
            scanner = new Scanner(fileToLoad);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line == null || line.isEmpty()) {
                    throw new IllegalArgumentException("line must not be null.");
                } else {
                    Account account = Account.make(line);
                    add(account);
                }
            }
            scanner.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("file not found. " + e.getMessage());
            return false;
        }
    }

    /**
     * Write all accounts in the bank to a file.
     * 
     * @param filename the name of the file to write the accounts to.
     * 
     * @return true if the accounts were written successfully, false otherwise.
     * 
     * @throws Exception if there was an error writing to the file.
     * 
     */
    public boolean writeAccounts(String filename) {
        try {
            writer = new FileWriter(filename);
            for (int i = 0; i < accountCount; i++) {
                Account account = accounts[i];
                String line = account.toCSV();
                writer.write(line + "\n");
            }
            writer.close();
            return true;
        } catch (IOException | IllegalArgumentException e) {
            return false;
        }
    }

    // Phase 3

    /**
     * Get all active accounts in the bank.
     * 
     * * @return an array of all active accounts in the bank.
     */
    public Account[] getAccounts() {
        activeAccounts = new Account[accountCount];
        int j = 0;
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i] != null) {
                activeAccounts[j] = accounts[i];
                j++;
            }
        }
        return activeAccounts;
    }

    /*
     * Process a file containing transactions and apply them to the appropriate
     * accounts.
     * 
     * @param transactionFile - The file containing the transactions.
     * 
     * @return transactions - an array of all the transactions listed in the file.
     * 
     * @throws FileNotFoundException if the transaction file is not found.
     */
    public Transaction[] loadTransactions(String transactionFile) {
        if (transactionFile == null) {
            throw new IllegalArgumentException("File cannot be null.");
        } else {
            try (
                Scanner scanner = new Scanner(new File(transactionFile))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    Transaction trs = Transaction.make(line);
                    transactions[transactionsCount] = trs;
                    transactionsCount++;
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return transactions;
    } 

    /*
     * This method holds the count of transactions in an array.
     * 
     * @return transactionsCount - count of all transactions
     * loaded from the csv transactions file.
     * 
     */
    public int getTransactions() {
        return transactionsCount;
    }

    /*
     * This method checks if an account exists in the bank
     * 
     * @param accountID - Unique account number
     * 
     * @return boolean
     * true - if account exists
     * false - if account does not exist
     */
    public boolean validateAcctExists(String accountID) {
        int locateAccount = find(accountID);
        if (locateAccount == -1) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Make process transactions return an int
     * so it can count how many transactions were processed
     * 
     * Method processes all transactions and changes accounts
     * balances in the bank.
     * 
     * @param Transaction [] transations - array of all the transactions
     * to be processed.
     * 
     * @return int count of processed transactions.
     *
     */
    public int processTransactions(Transaction[] transactions) {
        int transactionsProcessed = 0;
        try {
            Audit audit = new Audit("data/audit.log");
            for (int i = 0; i < transactionsCount; i++) {
                if (validateAcctExists(transactions[i].getAccountNumber())) {
                    Account target = accounts[(find(transactions[i].getAccountNumber()))];
                    if (transactions[i].validate(target, audit)) {
                        transactions[i].execute(target, audit);
                        transactionsProcessed++;
                    }
                } else {
                    // target account absent
                    audit.recordNoSuchAccount(transactions[i]);

                }

            }
            audit.close();
        } catch (IOException e) {
            // problem with Audit constructor
            e.printStackTrace();
        }
        return transactionsProcessed;
    }
}