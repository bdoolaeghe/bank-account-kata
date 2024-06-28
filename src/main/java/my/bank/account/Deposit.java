package my.bank.account;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.BinaryOperator;

public record Deposit(Amount amount, java.util.Date date) implements Operation {

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

    public static Deposit of(Amount amount, Date date) {
        return new Deposit(amount, date);
    }

    @Override
    public BinaryOperator<Amount> accumulator() {
        return Amount::sum;
    }

    @Override
    public String toString() {
        return "[" + df.format(date()) + "] Deposit of "+ amount;
    }
}
