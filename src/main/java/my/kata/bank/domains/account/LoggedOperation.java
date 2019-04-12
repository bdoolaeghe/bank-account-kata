package my.kata.bank.domains.account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import my.kata.bank.domains.operation.Operation;

import java.time.Instant;

@EqualsAndHashCode
@ToString
@Getter
public class LoggedOperation {

    private Instant operationDate;
    private Operation operation;

    public LoggedOperation(Instant operationDate, Operation operation) {
        this.operationDate = operationDate;
        this.operation = operation;
    }

    public static LoggedOperation aLoggedOperation(Operation operation, Instant operationDate) {
        return new LoggedOperation(operationDate, operation);
    }

}
