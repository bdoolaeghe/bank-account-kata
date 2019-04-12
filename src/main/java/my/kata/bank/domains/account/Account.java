package my.kata.bank.domains.account;


import lombok.Getter;
import my.kata.bank.domains.amount.Amount;
import my.kata.bank.domains.operation.Deposit;
import my.kata.bank.domains.operation.Operation;
import my.kata.bank.domains.operation.Withrawal;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static java.time.Instant.now;
import static my.kata.bank.domains.amount.Amount.amount;
import static my.kata.bank.domains.account.LoggedOperation.aLoggedOperation;

public class Account {

    @Getter
    private List<LoggedOperation> operationsLog = new ArrayList<>();

    private Account() {}

    private Account(LoggedOperation firstDeposit) {
        this.operationsLog.add(firstDeposit);
    }

    public Amount getBalance() {
        return amount(operationsLog.stream()
                .map(LoggedOperation::getOperation)
                .mapToDouble(Operation::getSignedAmountValue)
                .sum());
    }

    public void apply(Deposit deposit, Instant operationDate) {
        operationsLog.add(aLoggedOperation(deposit, operationDate));
    }

    public void apply(Withrawal withrawal, Instant operationDate) {
        operationsLog.add(aLoggedOperation(withrawal, operationDate));
    }

    public List<BalancedLoggedOperation> getHistory() {
        return operationsLog.stream()
                .collect(OperationsAccumulator::new,
                        OperationsAccumulator::accumulate,
                        OperationsAccumulator::combine)
                .getBalancedOperations();
    }

    public static Account anAccount(Deposit firstDeposit) {
        return anAccount(firstDeposit, now());
    }

    public static Account anAccount(Deposit firstDeposit, Instant creationDate) {
        return new Account(aLoggedOperation(firstDeposit, creationDate));
    }

    public static Account anEmptyAccount() {
        return new Account();
    }

}
