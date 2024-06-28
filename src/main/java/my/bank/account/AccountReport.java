package my.bank.account;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;

record AccountReport(List<Row> rows) {

    record Row(Operation operation, Amount balance) {
        @Override
        public String toString() {
            return operation().toString() +
                   " (new balance: " +
                   balance() +
                   ")";
        }
    };

    public static AccountReport from(Account account) {
        var  accumulatingBalance = new AtomicReference<>(Amount.ZERO);
        return new AccountReport(
                account.getHistory()
                        .stream()
                        .map(operation -> toRow(operation, accumulatingBalance, operation.accumulator()))
                        .toList()
        );
    }

    private static Row toRow(Operation operation, AtomicReference<Amount> currentBalance, BinaryOperator<Amount> accumulate) {
        return new Row(
                operation,
                currentBalance.accumulateAndGet(
                        operation.amount(),
                        accumulate
                )
        );
    }

    @Override
    public String toString() {
        return String.join(
                "\n",
                rows.stream()
                        .map(Row::toString)
                        .toList()
        );
    }
}
