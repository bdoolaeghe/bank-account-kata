package my.bank.account;

class Account {

    private final Currency currency;

    Account(Amount initialFunds) {
        this.currency = initialFunds.currency();
    }

    public Account() {
        this(new Amount(0, Currency.EUR));
    }

    void deposit(Amount amount) {
        throw new RuntimeException("implement me !");
    }

    Amount getBalance() {
        throw new RuntimeException("implement me !");
    }

    public Currency getCurrency() {
        throw new RuntimeException("implement me !");
    }
}
