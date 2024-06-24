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

    public Amount plus(Amount added) {
        if (added.currency != currency) {
            throw new IllegalArgumentException("Can't add amount in " + added.currency + " to current amount " + this);
        } else {
            return new Amount(value + added.value, currency);
        }
    }

    @Override
    public String toString() {
        return value + " " + currency;
    }
}
