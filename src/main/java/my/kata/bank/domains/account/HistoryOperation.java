package my.kata.bank.domains.account;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import my.kata.bank.domains.amount.Amount;
import my.kata.bank.domains.operation.Operation;

import java.time.Instant;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class HistoryOperation {

    private final Operation operation;
    private final Instant operationDate;
    private final Amount currentBalance;

    public static final class HistoryOperationBuilder {
        private Operation operation;
        private Instant operationDate;
        private Amount currentBalance;

        private HistoryOperationBuilder() {
        }

        public static HistoryOperationBuilder aHistoryOperation() {
            return new HistoryOperationBuilder();
        }

        public HistoryOperationBuilder withOperation(Operation operation) {
            this.operation = operation;
            return this;
        }

        public HistoryOperationBuilder withOperationDate(Instant operationDate) {
            this.operationDate = operationDate;
            return this;
        }

        public HistoryOperationBuilder withCurrentBalance(Amount currentBalance) {
            this.currentBalance = currentBalance;
            return this;
        }

        public HistoryOperation build() {
            return new HistoryOperation(operation, operationDate, currentBalance);
        }
    }
}
