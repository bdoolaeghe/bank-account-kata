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

    Amount getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return balance.currency();
    }
}
