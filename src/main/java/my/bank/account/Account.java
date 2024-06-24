package my.bank.account;

import java.util.List;

public class Account {

    private Amount balance;

    public Account(Amount initialFunds) {
        this.balance = initialFunds;
    }

    public Account() {
        this(Amount.of(0, Currency.EUR));
    }

    public void deposit(Amount amount) {
        this.balance = balance.plus(amount);
    }

    public void withdraw(Amount amount) {
        try {
            this.balance = balance.minus(amount);
        } catch (IllegalArgumentException e) {
            throw new OverdrawnAccountException(e);
        }
    }

    public Amount getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return balance.currency();
    }

    public List<Operation> getHisotry() {
        throw new RuntimeException("implement me !");
    }
}
