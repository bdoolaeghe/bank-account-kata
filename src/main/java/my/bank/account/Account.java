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

    public void deposit(Amount depositAmount) {
        if (depositAmount.currency() != getCurrency()) {
            throw new IllegalAccountOperationException("Can not deposit " + depositAmount + " onto account in currency " + getCurrency());
        } else {
            this.history.add(Deposit.of(depositAmount));
        }
    }

    public void withdraw(Amount withdrawalAmount) {
        if (withdrawalAmount.currency() != getCurrency()) {
            throw new IllegalAccountOperationException("Can not withdraw " + withdrawalAmount + " from account in currency " + getCurrency());
        } else if (withdrawalAmount.gt(getBalance())) {
            throw new OverdrawnAccountException("Can not withdraw an withdrawalAmount of " + withdrawalAmount + ". Insufficient funds: " + getBalance());
        } else {
            this.history.add(Withdrawal.of(withdrawalAmount));
        }
    }

    public List<Operation> getHistory() {
        return history;
    }

    public Currency getCurrency() {
        return history.getFirst().getCurrency();
    }

    public Amount getBalance() {
        return Amount.of(
                history.stream()
                        .mapToDouble(Operation::signedAmountValue)
                        .sum(),
                getCurrency());
    }
}
