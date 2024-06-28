package my.bank.account;

import java.util.Date;
import java.util.function.BinaryOperator;

public interface Operation {

    static Amount accumulate(Amount amount, Operation operation) {
        return operation
                .accumulator()
                .apply(amount, operation.amount());
    }

    Amount amount();

    Date date();

    default Currency getCurrency() {
        return amount().currency();
    }

    BinaryOperator<Amount> accumulator();
}
