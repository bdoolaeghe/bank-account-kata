package my.bank.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    private final List<Operation> history = new ArrayList<>();

    private Account(Amount initialFunds, Date creationDate) {
        this.history.add(Deposit.of(initialFunds, creationDate));
    }

    public static Account inEuro(Date creationDate) {
        return new Account(Amount.of(0, Currency.EUR), creationDate);
    }

    public static Account withInitialFunds(Amount initialFunds, Date creationDate) {
        return new Account(initialFunds, creationDate);
    }

    public void deposit(Amount depositAmount, Date date) {
        if (depositAmount.currency() != getCurrency()) {
            throw new IllegalAccountOperationException("Can not deposit " + depositAmount + " onto account in currency " + getCurrency());
        } else {
            this.history.add(Deposit.of(depositAmount, date));
        }
    }

    public void withdraw(Amount withdrawalAmount, Date date) {
        if (withdrawalAmount.currency() != getCurrency()) {
            throw new IllegalAccountOperationException("Can not withdraw " + withdrawalAmount + " from account in currency " + getCurrency());
        } else if (withdrawalAmount.gt(getBalance())) {
            throw new OverdrawnAccountException("Can not withdraw an withdrawalAmount of " + withdrawalAmount + ". Insufficient funds: " + getBalance());
        } else {
            this.history.add(Withdrawal.of(withdrawalAmount, date));
        }
    }

    List<Operation> getHistory() {
        return history;
    }

    public Currency getCurrency() {
        return history.getFirst().getCurrency();
    }

    public Amount getBalance() {
        return  history.stream()
                .reduce(Amount.ZERO,
                        Operation::accumulate,
                        Amount::sum
                );
    }

    public String createReport() {
        return AccountReport.from(this).toString();
    }
}
