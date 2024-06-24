package my.bank.account;

import java.util.List;

public record AccountReport(List<Row> rows) {

    record Row(Operation operation, Amount balance) {
        @Override
        public String toString() {
            return operation().toString() +
                   " (new balance: " +
                   balance() +
                   ") ";
        }
    };

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
