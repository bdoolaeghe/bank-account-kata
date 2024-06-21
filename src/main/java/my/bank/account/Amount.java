package my.bank.account;

record Amount(double value, Currency currency) {

    static Amount of(double value, Currency currency) {
        return new Amount(value, currency);
    }

}
