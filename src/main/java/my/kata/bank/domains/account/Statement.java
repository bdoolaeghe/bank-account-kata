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
public class Statement {

    private final Operation operation;
    private final Instant operationDate;
    private final Amount currentBalance;

    public static final class StatementBuilder {
        private Operation operation;
        private Instant operationDate;
        private Amount currentBalance;

        private StatementBuilder() {
        }

        public static StatementBuilder aStatement() {
            return new StatementBuilder();
        }

        public StatementBuilder withOperation(Operation operation) {
            this.operation = operation;
            return this;
        }

        public StatementBuilder withOperationDate(Instant operationDate) {
            this.operationDate = operationDate;
            return this;
        }

        public StatementBuilder withCurrentBalance(Amount currentBalance) {
            this.currentBalance = currentBalance;
            return this;
        }

        public Statement build() {
            return new Statement(operation, operationDate, currentBalance);
        }
    }
}
