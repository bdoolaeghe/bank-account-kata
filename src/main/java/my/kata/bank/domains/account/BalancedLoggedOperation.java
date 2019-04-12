package my.kata.bank.domains.account;


import lombok.Getter;
import my.kata.bank.domains.amount.Amount;
import my.kata.bank.domains.operation.Operation;

import java.time.Instant;

public class BalancedLoggedOperation extends LoggedOperation {

    @Getter
    private final Amount currentBalance;

    public BalancedLoggedOperation(LoggedOperation loggedOperation, Amount currentBalance) {
        super(loggedOperation.getOperationDate(), loggedOperation.getOperation());
        this.currentBalance = currentBalance;
    }

    public static BalancedLoggedOperation aBalancedLoggedOperation(Operation operation, Instant operationDate, Amount currentBalance) {
        return  new BalancedLoggedOperation(new LoggedOperation(operationDate, operation), currentBalance);
    }
}
