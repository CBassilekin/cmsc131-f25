
package projects.bank;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {

        private Account svaccount;
        private double amount;

        /*
         * Setting up our account to be used or not
         * with each test.
         * Amount is set for credit() and debit() methods
         */
        @BeforeEach
        void setupAccount() {
                svaccount = new SavingsAccount(
                                "wz240833",
                                "Anna Gomez",
                                8111.00);
                amount = 100.00;
        }

        /*
         * Test confirms exceptions are thrown with invalid account paprameters.
         */
        @Test
        void testDataValidation() {
                Exception e = assertThrows(
                                IllegalArgumentException.class,
                                () -> {
                                        new CheckingAccount(null, "name", 0.0);
                                });
                assertEquals("account ID cannot be empty.", e.getMessage());

                e = assertThrows(
                                IllegalArgumentException.class,
                                () -> {
                                        new CheckingAccount("id", null, 0.0);
                                });
                assertEquals("account owner's name cannot be empty.", e.getMessage());
        }

        /*
         * Test confirms that make() method throws an exception
         * when a null argument string is passed.
         */
        @Test
        void testMakeThrowsOnNullInput() {
                Exception exception = assertThrows(
                                IllegalArgumentException.class,
                                () -> {
                                        Account.make(null);
                                });
                assertEquals(
                                "line must not be null.",
                                exception.getMessage());
        }

        /*
         * Test confirms that make preserves a given account data during the factory.
         */
        @Test
        void testMakePreservesData() {
                Account svaccount2 = Account.make(
                                "savings,wz240833,Anna Gomez,8111.00");
                assertEquals(
                                svaccount.getID(),
                                svaccount2.getID());
                assertEquals(
                                svaccount.getOwner(),
                                svaccount2.getOwner());
                assertEquals(
                                svaccount.getType(),
                                svaccount2.getType());
                assertEquals(
                                svaccount.getCurrentBalance(),
                                svaccount2.getCurrentBalance(),
                                1e-2);
        }

        /*
         * tests confirms that an account is successfully convert to a line
         * into the CSV file.
         */
        @Test
        void testToCSV() {
                assertEquals(
                                "savings,wz240833,Anna Gomez,8111.00",
                                svaccount.toCSV());
        }

        /*
         * Test confirms that credit() increases the account's balance successfully.
         */
        @Test
        void TestCredit() {
                svaccount.credit(amount);
                Double expectedResult = svaccount.setNewBalance();
                assertEquals(
                                8211.00, expectedResult);
        }

        /*
         * Test confirms that debit() reduces the account's balance successfully.
         */
        @Test
        void TestDebit() {
                svaccount.debit(amount);
                Double expectedResult = svaccount.setNewBalance();
                assertEquals(
                                8011.00, expectedResult);
        }

}// end: class AccountTest
