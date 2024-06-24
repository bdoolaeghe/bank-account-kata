package my.bank.account;

record Amount(double value, Currency currency) {

    Amount {
        if (value < 0) {
            throw new IllegalArgumentException("Amount cannot be negative (was: " + value + ")");
        }
    }

    static Amount of(double value, Currency currency) {
        return new Amount(value, currency);
    }

    Amount plus(Amount added) {
        if (added.currency != currency) {
            throw new IllegalArgumentException("Can't add amount in " + added.currency + " to current amount " + this);
        } else {
            return new Amount(value + added.value, currency);
        }
    }

    Amount minus(Amount subtracted) {
        if (subtracted.currency != currency) {
            throw new IllegalArgumentException("Can't subtract amount in " + subtracted.currency + " to current amount " + this);
        } else if (subtracted.gt(this)) {
            throw new IllegalArgumentException("Can't subtract " + subtracted + " because it's greater than current amount (" + this + ")");
        } else {
            return new Amount(value - subtracted.value, currency);
        }
    }

    private boolean gt(Amount another) {
        return currency == another.currency &&
               value > another.value;
    }

    @Override
    public String toString() {
        return value + " " + currency;
    }

}
