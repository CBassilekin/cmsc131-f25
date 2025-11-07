/** comments
 * you'll have to modify some of these tests once the execute methods also take an `Audit` object for input
 * because you're testing methods from the Transaction class in testConstructorDataValidation, you only need to use, eg, a Deposit. also testing a Withdrawal is redundant and should be removed
 * 
 */
package projects.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionTest {

    private Account ckgAccount;
    private Transaction defaultDeposit, defaultWithdrawal, overdraft;
    private Audit audit;
    /*
     * Setting up the transaction class with
     * a checking account,
     * a deposit,
     * and a withdrawal transaction.
     * they will be used for testing.
     */

    @BeforeEach
    void setUp() {
        ckgAccount = new CheckingAccount("id", "owner", 10.0);
        defaultDeposit = new Deposit("id", 2.51);
        defaultWithdrawal = new Withdrawal("id", 1.75);
        overdraft = new Withdrawal("id", 20.00);
        try {
            audit = new Audit("data/audittest.log");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Test to confirm that each transaction paramter is valid.
     * exceptions will be thrown on invalid parameter.
     */
    @Test
    void testConstructorDataValidation() {
        Exception e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    defaultDeposit = new Deposit(null, 2.51);
                    ;
                });
        assertEquals("account number cannot be null.", e.getMessage());

        e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    defaultDeposit = new Deposit("id", -2.51);
                    ;
                });
        assertEquals("transaction amount must be positive.", e.getMessage());

        e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    defaultWithdrawal = new Withdrawal(null, 1.75);
                    ;
                });
        assertEquals("account number cannot be null.", e.getMessage());

        e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    defaultWithdrawal = new Withdrawal("id", -1.75);
                    ;
                });
        assertEquals("transaction amount must be positive.", e.getMessage());
    }

    /*
     * Test confirms that make() will thrown an exception
     * when passed a null string input.
     */
    @Test
    void makeThrowsOnNullInputTest() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Transaction.make(null);
                });
        assertEquals(
                "line must not be null.",
                exception.getMessage());
    }

    /*
     * Test verifies that transaction data are preserved during the factory process.
     */
    @Test
    void makePreservesDataTest() {
        Transaction defaultDeposit2 = Transaction.make("deposit,id,2.51");
        assertEquals(
                defaultDeposit.getAccountNumber(),
                defaultDeposit2.getAccountNumber());
        assertEquals(
                defaultDeposit.getAmount(),
                defaultDeposit2.getAmount(), 1e-2);
        assertEquals(
                defaultDeposit.getType(),
                defaultDeposit2.getType());

        Transaction defaultWithdrawal2 = Transaction.make(
                "withdrawal,id,1.75");
        assertEquals(
                defaultWithdrawal.getAccountNumber(),
                defaultWithdrawal2.getAccountNumber());
        assertEquals(
                defaultWithdrawal.getAmount(),
                defaultWithdrawal2.getAmount(), 1e-2);
        assertEquals(
                defaultWithdrawal.getType(),
                defaultWithdrawal2.getType());

    }

    /*
     * Test confirms that validate() a deposit returns true.
     */
    @Test
    void validateAndExecuteCorrectnessTest() {
        // checking account is initialized with a $10 balance.

        assertEquals(// check deposit can execute on account
                true, // expected
                defaultDeposit.validate(ckgAccount, audit));// actual

        assertEquals( // check withdrawal of $20 can execute on account
                false, // expected
                overdraft.validate(ckgAccount, audit));// actual

        defaultDeposit.execute(ckgAccount, audit);
        // account was credited of $2.51
        assertEquals(// check if new balance can support the overdraft withdrawal
                false, // expected
                overdraft.validate(ckgAccount, audit));// actual

        defaultDeposit.execute(ckgAccount, audit); // a $2.51 deposit is now applied
        double endingBalance = ckgAccount.setNewBalance(); // getting new balance on account
        assertEquals( // checking if new balance reflects the credit operation
                12.51, // expected
                endingBalance); // actual

        ckgAccount.debit(defaultWithdrawal.getAmount()); // withdrawal is now applied
        endingBalance = ckgAccount.setNewBalance(); // getting new balance on account
        assertEquals(// checking if new balance reflects the credit operation
                8.25, // expected
                endingBalance); // actual

        defaultWithdrawal.execute(ckgAccount, audit);
        // this withdrawal is of $1.75 and should execute
        assertEquals(// Ckeck new balance is accurately reflected
                8.25, // expected
                ckgAccount.setNewBalance()// actual
        );

    }

}// end: class TransactionTest
