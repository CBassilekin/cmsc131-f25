package projects.bank;

import java.io.IOException;
import java.io.FileWriter;

public class Audit {

    // Phase 4
    private FileWriter writer;

    /**
     * Create a new output stream. Stays open during this instance's lifepan.
     * 
     * Output file is TXT with lines like this:
     * Timestamp Level Account ID Transaction Type Amount Balance Forward
     * 
     * @param fileName Points to desired log file. Overwrites existing file.
     * @throws IllegalArgumentException If fileName is null.
     * @throws IOException              If the default file is not usable.
     */
    public Audit(String fileName) throws IOException {
        if (fileName == null) {
            throw new IllegalArgumentException("fileName cannot be null.");
        }

        writer = new FileWriter(fileName); // throws IOException
    }

    /**
     * Close this audit's output file stream.
     */
    public void close() {
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write a line to the audit file.
     * 
     * @param s - Content to be written to output file.
     */
    private void write(String s) {
        try {
            writer.write(s + System.lineSeparator());
        } catch (IOException e) { // fall back to console output
            e.printStackTrace();
        }
    }

    /**
     * 
     * Write audit line for No Such Account warning.
     * 
     * @param t - Transaction causing the warning.
     * 
     */
    public void recordNoSuchAccount(Transaction t) {
        write(
            String.format(
                "%s [WARN]: no such account: %s",
                lib.Utils.timestamp(),
                t.toString()
            )
        );
    }

    /**
     * Write audit line for Nonsufficient Funds warning.
     * 
     * @param t - Transaction causing the warming.
     * @param a - Target account item.
     */
    public void recordNonSufficientFunds(Transaction t, Account a) {
        write(
            String.format(
                "%s [WARN]: nonsufficient funds: %s, but account balance is %.2f",
                lib.Utils.timestamp(),
                t.toString(),
                a.getCurrentBalance()
            )
        );
    }

    /**
     * Write audit line for transforming Balance after transaction execution.
     * 
     * @param t - Transaction being executed.
     * @param a - Target account.
     */
    public void recordExecute(Transaction t, Account a) {
        write(
            String.format(
                "%s [INFO]: %s, ending account balance is now %.2f",
                lib.Utils.timestamp(),
                t.toString(),
                a.setNewBalance()
            )
        );
    }
}