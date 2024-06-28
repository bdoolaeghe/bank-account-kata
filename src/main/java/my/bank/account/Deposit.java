package my.bank.account;

import java.text.SimpleDateFormat;
import java.util.Date;

public record Deposit(Amount amount, java.util.Date date) implements Operation {

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

    public static Deposit of(Amount amount, Date date) {
        return new Deposit(amount, date);
    }

    @Override
    public double signedAmountValue() {
        return amount.value();
    }

    @Override
    public String toString() {
        return "[" + df.format(date()) + "] Deposit of "+ amount;
    }
}
