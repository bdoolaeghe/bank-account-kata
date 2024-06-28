package my.bank.account;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
        var  balance = new AtomicReference<>(Amount.ZERO);
        return new AccountReport(
                account.getHistory()
                        .stream()
                        .map(operation ->
                                switch (operation) {
                                    case Deposit deposit -> appendDepositRow(deposit, balance);
                                    case Withdrawal withdrawal -> appendWithdrawalRow(withdrawal, balance);
                                    default -> throw new IllegalArgumentException("Unsupported operation: " + operation.getClass());
                                })
                        .toList()
        );
    }

    private static Row appendWithdrawalRow(Withdrawal withdrawal, AtomicReference<Amount> balance) {
        return new Row(
                withdrawal,
                balance.accumulateAndGet(
                        withdrawal.amount(),
                        Amount::subtract
                )
        );
    }

    private static Row appendDepositRow(Deposit deposit, AtomicReference<Amount> balance) {
        return new Row(
                deposit,
                balance.accumulateAndGet(
                        deposit.amount(),
                        Amount::sum
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
