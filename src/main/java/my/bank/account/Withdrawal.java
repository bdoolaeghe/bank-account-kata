package my.bank.account;

import java.text.SimpleDateFormat;
import java.util.Date;

public record Withdrawal(Amount amount, java.util.Date date) implements Operation {

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

    public static Withdrawal of(Amount amount, Date date) {
        return new Withdrawal(amount, date);
    }

    @Override
    public double signedAmountValue() {
        return amount.value() * -1;
    }

    @Override
    public String toString() {
        return "Withdrawal of "+ amount + " on " + df.format(date);
    }
}
