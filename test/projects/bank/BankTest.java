package projects.bank;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
        private Bank bank;
        private Account svacct;
        private Transaction transac;
        private Account[] activeAccounts = new Account[1];
        private Transaction[] trs;

        /*
         * Setting up the bank before each test
         * with a saving account
         * and a transaction
         */
        @BeforeEach
        void setup() {
                bank = new Bank();
                svacct = new SavingsAccount(
                                "id0",
                                "Owner Name",
                                1.0);

                transac = new Withdrawal("id0",
                                .25);

        }

        // tests for phase 1

        /*
         * Test confirms Add() throws an exception if an account argument is null.
         */
        @Test
        void addDataValidationTest() {
                Exception e = assertThrows(
                                IllegalArgumentException.class,
                                () -> {
                                        bank.add(null);
                                });
                assertEquals("account must not be null.", e.getMessage());
        }

        /*
         * Test confirms add() method is successful.
         */
        @Test
        void addAccountTest() {
                // add account that's absent from bank's accounts
                boolean addAccountResult = bank.add(svacct);
                assertEquals(
                                true,
                                addAccountResult,
                                "bank.add should return true");
                assertEquals(
                                1,
                                bank.getCount(),
                                "bank.getCount should be 1");

                // add account that's present in bank's accounts (no effect)
                addAccountResult = bank.add(svacct);
                assertEquals(
                                false,
                                addAccountResult,
                                "bank.add should return false");
                assertEquals(
                                1,
                                bank.getCount(),
                                "bank.getCount should still be 1");
        }

        /*
         * test confirms that bank array is able to add() accounts overflow.
         */
        @Test
        void addAccountOverflowTest() {
                for (int idx = 0; idx <= 100; idx++) {
                        Integer id = idx;
                        bank.add(
                                        new CheckingAccount(
                                                        id.toString(),
                                                        "Owner Name",
                                                        1.0));
                }

                // also serves as a test for getCount
                assertEquals(
                                101,
                                bank.getCount(),
                                "bank should hold 101 accounts");
        }

        /*
         * Test confirms find() throws an invalid argument exception on null account Id
         * argumment.
         */
        @Test
        void findDataValidationTest() {
                Exception e = assertThrows(
                                IllegalArgumentException.class,
                                () -> {
                                        bank.find(null);
                                });
                assertEquals("accountID must not be null.", e.getMessage());
        }

        /*
         * Test confirms find() outcomes return correct value.
         * if account is found, value index is returned
         * otherwise, value -1.
         */
        @Test
        void findTest() {
                bank.add(svacct);
                assertEquals(
                                0,
                                bank.find(svacct.getID()),
                                "acct should be at index 0");
                assertEquals(
                                -1,
                                bank.find("id1"),
                                "result should be -1 when finding absent account");
        }

        // tests for phase 2

        /*
         * Test loadAccounts() returns false when invalid file is passed.
         */
        @Test
        void loadAccountsFailTest() {
                assertEquals(false, bank.loadAccounts("not/a/real.file"));
        }

        /*
         * Test confirms accounts are successfully loaded onto the bank
         * Validate accounts data are correct.
         */
        @Test
        void loadAccountsTest() {
                String accountsFilename = "data/testaccounts.csv";
                boolean result = bank.loadAccounts(accountsFilename);
                assertEquals(
                                true,
                                result);
                Account[] accounts = bank.getAccounts();
                assertEquals(
                                2,
                                bank.getCount());

                // check validity of bank account
                Account account = accounts[0];
                assertEquals(
                                "wz240833",
                                account.getID());
                assertEquals(
                                "Anna Gomez",
                                account.getOwner());
                assertEquals(
                                AccountType.SAVINGS,
                                account.getType());
                assertEquals(
                                8111.00,
                                account.getCurrentBalance(),
                                1e-2);

                account = accounts[1];
                assertEquals(
                                "hr108256",
                                account.getID());
                assertEquals(
                                "Anna Gomez",
                                account.getOwner());
                assertEquals(
                                AccountType.CHECKING,
                                account.getType());
                assertEquals(
                                1715.18,
                                account.getCurrentBalance(),
                                1e-2);
        } // end: testLoadAccounts

        /*
         * Test writeAccounts() fails on inexistent file.
         */
        @Test
        void writeAccountsFailTest() {
                assertEquals(false, bank.writeAccounts("not/a/real.file"));
        }

        /*
         * Test verifies if writeAccounts() is successful
         * confirms bank account matches with written accounts.
         */
        @Test
        void writeAccountsTest() {
                // loading rental items already tested
                String accountsFilename = "data/testaccounts.csv";
                boolean result = bank.loadAccounts(accountsFilename);
                assertEquals(true, result);

                // write
                accountsFilename = "data/testaccounts-out.csv";
                bank.writeAccounts(accountsFilename);

                // reload and compare
                Bank bankReload = new Bank();
                bankReload.loadAccounts(accountsFilename);

                assertEquals(bank.getCount(), bankReload.getCount());

                Account[] bankAccounts = bank.getAccounts();
                Account[] bankReloadAccounts = bank.getAccounts();
                assertEquals(bankAccounts.length, bankReloadAccounts.length);
                for (int idx = 0; idx < bank.getCount(); idx++) {
                        assertEquals(
                                        bankAccounts[idx],
                                        bankReloadAccounts[idx],
                                        String.format("Bank accounts should match.", idx));
                }
        }

        // tests for phase 3

        /*
         * test verifies active accounts count in the bank
         * and validates integrity of the account data.
         */
        @Test
        void getAccountsTest() {
                bank.add(svacct);
                activeAccounts[0] = svacct;
                assertEquals("id0", activeAccounts[0].getID());
                assertEquals("Owner Name", activeAccounts[0].getOwner());
                assertEquals(1.0, activeAccounts[0].getCurrentBalance());
        }

        /*
         * test confirms load Transactions throws an exception
         * with the appropriate message if passed a null file.
         */
        @Test
        void loadTransactionsThrowsIllegalArgumentTest() {
                // What if we pass a null file to the method?
                Exception e = assertThrows(
                                IllegalArgumentException.class,
                                () -> {
                                        bank.loadTransactions(null);
                                });
                assertEquals("File cannot be null.", e.getMessage());
        }

        /*
         * test confirms that loadTransactions is successful
         * when correct file is passed
         * confirms number of transactions processed is exact.
         * validate transaction data is valid.
         */
        @Test
        void loadTransactionsSuccessTest() {
                // Our test file holds 3 transaction
                // Two transactions are valids but not the last one
                // Also serve as a test for the "getTransactionsCount()" method
                String transactionsFilename = "data/testtransactions.csv";
                trs = bank.loadTransactions(transactionsFilename);
                assertEquals(
                                3,
                                bank.getTransactionsCount());

                // check validity of stored transaction
                int i = 0;
                trs[i] = trs[0];
                assertEquals(
                                "rp332960",
                                trs[i].getAccountNumber());
                assertEquals(
                                267.57,
                                trs[i].getAmount(), 1e-2);
                assertEquals(
                                TransactionType.WITHDRAWAL,
                                trs[i].getType());

                trs[i] = trs[1];
                assertEquals(
                                "vc906780",
                                trs[i].getAccountNumber());
                assertEquals(
                                766.53,
                                trs[i].getAmount(), 1e-2);
                assertEquals(
                                TransactionType.DEPOSIT,
                                trs[i].getType());

                trs[i] = trs[2];
                assertEquals(
                                "id0",
                                trs[i].getAccountNumber());
                assertEquals(
                                .25,
                                trs[i].getAmount(), 1e-2);
                assertEquals(
                                TransactionType.WITHDRAWAL,
                                trs[i].getType());

        }// end: loadTransactionsTest

        /*
         * Test confirms that validateAcctExists is successful for
         * a valid transaction
         */
        @Test
        void validateAcctExistsSuccessTest() {
                bank.add(svacct);
                boolean expectedResult = bank.validateAcctExists(transac.getAccountNumber());
                assertEquals(true, expectedResult);
        }

        /*
         * Test confirms that validateAcctExist fails when transaction's related account
         * is not found in the bank database.
         * An invaid account is passed in the method and should return false.
         */
        void validateAcctExistsFailureTest() {
                boolean expectedResult = bank.validateAcctExists(
                                "invalidaccount");
                assertEquals(false, expectedResult);
        }

        /*
         * Test to confirm that ProcessTransactions is successful
         * only 1 transaction is valid in the transactions file so
         * we confirm that 1 transaction will be processed.
         */
        @Test
        void testProcessTransactionsSuccess() {
                bank.add(svacct);
                String transactionsFilename = "data/testtransactions.csv";
                trs = bank.loadTransactions(transactionsFilename);
                assertEquals(1, bank.processTransactions(trs));
        }

        /*
         * Test to confirm that failed ProcessTransactions should result in
         * no transaction being processed.
         */
        @Test
        void testProcessTransactionsFailureTest() {
                assertEquals(
                                0,
                                bank.processTransactions(null));
        } // end: processTransactionsTest

} // end: class BankTest
