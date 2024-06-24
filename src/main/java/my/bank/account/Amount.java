package my.bank.account;

public record Amount(double value, Currency currency) {

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
        checkRemainPositiveAmount(subtracted);
        return Amount.of(value - subtracted.value, currency);
    }

    private void checkRemainPositiveAmount(Amount subtracted) {
        if (subtracted.gt(this)) {
            throw new IllegalArgumentException("Can't subtract " + subtracted + " because it's greater than current amount (" + this + ")");
        }
    }

    private void checkCurrencyIsSame(Amount anotherAmount) {
        if (anotherAmount.currency != currency) {
            throw new IllegalArgumentException("Amount " + anotherAmount + " has not same currency as " + this);
        }
    }

    private boolean gt(Amount another) {
        checkCurrencyIsSame(another);
        return value > another.value;
    }

    @Override
    public String toString() {
        return value + " " + currency;
    }

}
