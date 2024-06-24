package my.bank.account;

class Account {

    private Amount balance;

    Account(Amount initialFunds) {
        this.balance = initialFunds;
    }

    public Account() {
        this(new Amount(0, Currency.EUR));
    }

    void deposit(Amount amount) {
        this.balance = balance.plus(amount);
    }

    void withdraw(Amount amount) {
        throw new RuntimeException("implement me !");
    }

    Amount getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return balance.currency();
    }
}
