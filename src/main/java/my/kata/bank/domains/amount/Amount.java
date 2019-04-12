package my.kata.bank.domains.amount;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import my.kata.bank.domains.operation.Operation;

@EqualsAndHashCode
public class Amount {

    //FIXME double type is enough to start, but may need to care about currency and digits
    @Getter
    private final double value;

    protected Amount(double value) {
        this.value = value;
    }

    public Amount apply(Operation operation) {
        return amount(this.value + operation.getSignedAmountValue());
    }

    public Amount plus(Amount amountToAdd) {
        return amount(this.value + amountToAdd.value);
    }

    public static Amount amount(double value) {
        return new Amount(value);
    }

    @Override
    public String toString() {
        return "" + value;
    }

    public boolean isNegative() {
        return value < 0;
    }
}
