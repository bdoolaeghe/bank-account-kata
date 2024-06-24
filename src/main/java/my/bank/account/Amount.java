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

    Amount plus(Amount added) {
        checkCurrencyIsSame(added);
        return Amount.of(value + added.value, currency);
    }

    Amount minus(Amount subtracted) {
        checkCurrencyIsSame(subtracted);
        checkGreaterThanOrEquals(subtracted);
        return Amount.of(value - subtracted.value, currency);
    }

    private void checkGreaterThanOrEquals(Amount another) {
        if (another.gt(this)) {
            throw new IllegalArgumentException("Can't subtract " + another + " because it's greater than current amount (" + this + ")");
        }
    }

    private void checkCurrencyIsSame(Amount anotherAmount) {
        if (anotherAmount.currency != currency) {
            throw new IllegalArgumentException("Amount " + anotherAmount + " has not same currency as " + this);
        }
    }

    public boolean gt(Amount another) {
        checkCurrencyIsSame(another);
        return value > another.value;
    }

    @Override
    public String toString() {
        return value + " " + currency;
    }

}
