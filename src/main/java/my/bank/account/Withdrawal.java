package my.bank.account;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.BinaryOperator;

public record Withdrawal(Amount amount, java.util.Date date) implements Operation {

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

    public static Withdrawal of(Amount amount, Date date) {
        return new Withdrawal(amount, date);
    }

    @Override
    public BinaryOperator<Amount> accumulator() {
        return Amount::subtract;
    }

    @Override
    public String toString() {
        return "[" + df.format(date()) + "] Withdrawal of "+ amount;
    }
}
