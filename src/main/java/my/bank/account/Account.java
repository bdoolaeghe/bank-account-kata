package my.bank.account;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private final List<Operation> history = new ArrayList<>();

    private Account(Amount initialFunds) {
        this.history.add(Deposit.of(initialFunds));
    }

    public static Account inEuro() {
        return new Account(Amount.of(0, Currency.EUR));
    }

    public static Account withInitialFunds(Amount initialFunds) {
        return new Account(initialFunds);
    }

    public void deposit(Amount amount) {
        if (amount.currency() != getCurrency()) {
            throw new IllegalAccountOperationException("Can not deposit " + amount + " onto account in currency " + getCurrency());
        } else {
            this.history.add(Deposit.of(amount));
        }
    }

    public void withdraw(Amount amount) {
        if (amount.currency() != getCurrency()) {
            throw new IllegalAccountOperationException("Can not withdraw " + amount + " from account in currency " + getCurrency());
        } else if (amount.gt(getBalance())) {
            throw new OverdrawnAccountException("Can not withdraw an amount of " + amount + ". Insufficient funds: " + getBalance());
        } else {
            this.history.add(Withdrawal.of(amount));
        }
    }

    public Amount getBalance() {
        return Amount.of(
                history.stream()
                        .mapToDouble(Operation::signedAmountValue)
                        .sum(),
                getCurrency());
    }

    public Currency getCurrency() {
        return history.getFirst().getCurrency();
    }

    public List<Operation> getHistory() {
        return history;
    }
}
