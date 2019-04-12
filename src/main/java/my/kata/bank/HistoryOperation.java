package my.kata.bank;


import lombok.Getter;

import java.time.Instant;

public class HistoryOperation extends LoggedOperation {

    @Getter
    private final Amount currentBalance;

    public HistoryOperation(LoggedOperation loggedOperation, Amount currentBalance) {
        super(loggedOperation.getOperationDate(), loggedOperation.getOperation());
        this.currentBalance = currentBalance;
    }

    public static HistoryOperation historyOperation(Operation operation, Instant operationDate, Amount currentBalance) {
        return  new HistoryOperation(new LoggedOperation(operationDate, operation), currentBalance);
    }
}
