package my.bank.account;

record Amount(double value, Currency currency) {

    static Amount of(double value, Currency currency) {
        return new Amount(value, currency);
    }

    public Amount plus(Amount added) {
        throw new RuntimeException("implement me !");
    }
}
