package my.bank.account;

class Account {

    private Amount balance;

    Account(Amount initialFunds) {
        this.balance = initialFunds;
    }

    Account() {
        this(new Amount(0, Currency.EUR));
    }

    void deposit(Amount amount) {
        this.balance = balance.plus(amount);
    }

    void withdraw(Amount amount) {
        try {
            this.balance = balance.minus(amount);
        } catch (IllegalArgumentException e) {
            throw new OverdrawnAccountException(e);
        }
    }

    Amount getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return balance.currency();
    }

}
