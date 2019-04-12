package my.kata.bank;


import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static java.time.Instant.now;
import static my.kata.bank.Amount.amount;
import static my.kata.bank.HistoryOperation.historyOperation;
import static my.kata.bank.LoggedOperation.historized;

public class Account {

    @Getter
    private List<LoggedOperation> operations = new ArrayList<>();

    private Account() {}

    private Account(LoggedOperation firstDeposit) {
        this.operations.add(firstDeposit);
    }

    public Amount getBalance() {
        return amount(operations.stream()
                .map(LoggedOperation::getOperation)
                .mapToDouble(Operation::getSignedAmountValue)
                .sum());
    }

    void apply(Deposit deposit, Instant operationDate) {
        operations.add(historized(deposit, operationDate));
    }

    void apply(Withrawal withrawal, Instant operationDate) {
        operations.add(historized(withrawal, operationDate));
    }

    static class OperationAccumulator {
        Amount currentBalance = amount(0);
        @Getter
        List<HistoryOperation> historyOperations = new ArrayList<>();

        void accept(LoggedOperation operation) {
            currentBalance = currentBalance.apply(operation.getOperation());
            historyOperations.add(historyOperation(operation.getOperation(), operation.getOperationDate(), currentBalance));
        }

        void combine(OperationAccumulator anotherAccumulator) {
            currentBalance.plus(anotherAccumulator.currentBalance);
            historyOperations.addAll(anotherAccumulator.getHistoryOperations());
        }
    }

    public List<HistoryOperation> getHistory() {
        return operations.parallelStream()
                .collect(OperationAccumulator::new,
                        OperationAccumulator::accept,
                        OperationAccumulator::combine)
                .getHistoryOperations();
    }

    public static Account newAccount(Deposit firstDeposit) {
        return newAccount(firstDeposit, now());
    }

    public static Account newAccount(Deposit firstDeposit, Instant creationDate) {
        return new Account(historized(firstDeposit, creationDate));
    }

    public static Account newEmptyAccount() {
        return new Account();
    }

}
