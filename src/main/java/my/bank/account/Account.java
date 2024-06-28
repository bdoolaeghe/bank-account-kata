package my.bank.account;

import my.bank.account.AccountReport.Row;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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

    public String accountStatement() {
        return String.join(
                "\n",
                history.stream().map(Operation::toString).toList()
        );
    }

    public AccountReport getReport() {
        //FIXME refactor
        AtomicReference<Amount> balance = new AtomicReference<>(Amount.ZERO);
        return new AccountReport(
                history.stream()
                .map(operation ->
                        switch (operation) {
                            case Deposit deposit -> newDepositRow(deposit, balance);
                            case Withdrawal withdrawal -> newWithdrawalRow(withdrawal, balance);
                            default -> throw new IllegalArgumentException("Unsupported operation: " + operation.getClass());
                })
                .toList()
        );
    }

    private static Row newWithdrawalRow(Withdrawal withdrawal, AtomicReference<Amount> balance) {
        return new Row(
                withdrawal,
                balance.accumulateAndGet(
                        withdrawal.amount(),
                        (a1, a2) -> Amount.of(a1.value() - a2.value(), a1.currency())
                )
        );
    }

    private static Row newDepositRow(Deposit deposit, AtomicReference<Amount> balance) {
        return new Row(
                deposit,
                balance.accumulateAndGet(
                        deposit.amount(),
                        (a1, a2) -> Amount.of(a1.value() + a2.value(), a1.currency())
                )
        );
    }
}
