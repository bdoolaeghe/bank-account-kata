package my.bank.account;

import static my.bank.account.Currency.EUR;

public record Amount(double value, Currency currency) {

    static final Amount ZERO = Amount.of(0, EUR);

    public Amount {
        if (value < 0) {
            throw new IllegalArgumentException("Amount cannot be negative (was: " + value + ")");
        }
    }

    public static Amount of(double value, Currency currency) {
        return new Amount(value, currency);
    }

    public static Amount sum(Amount a1, Amount a2) {
        if (a1.currency != a2.currency)
            throw new IllegalArgumentException("currencies mismatch: " + a1 + " / " + a2);
        return Amount.of(a1.value() + a2.value(), a1.currency());
    }

    public static Amount subtract(Amount initialAmount, Amount subtractedAmount) {
        if (initialAmount.currency != subtractedAmount.currency)
            throw new IllegalArgumentException("currencies mismatch: " + initialAmount + " / " + subtractedAmount);
        return Amount.of(initialAmount.value() - subtractedAmount.value(), initialAmount.currency());
    }

    public boolean gt(Amount another) {
        checkCurrencyIsSame(another);
        return value > another.value;
    }

    private void checkCurrencyIsSame(Amount anotherAmount) {
        if (anotherAmount.currency != currency) {
            throw new IllegalArgumentException("Amount " + anotherAmount + " has not same currency as " + this);
        }
    }

    @Override
    public String toString() {
        return value + " " + currency;
    }

}
