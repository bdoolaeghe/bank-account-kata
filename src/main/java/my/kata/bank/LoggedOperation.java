package my.kata.bank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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

    public static LoggedOperation historized(Operation operation, Instant operationDate) {
        return new LoggedOperation(operationDate, operation);
    }

}
