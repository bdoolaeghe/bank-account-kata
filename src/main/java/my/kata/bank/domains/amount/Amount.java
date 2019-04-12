package my.kata.bank.domains.amount;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Amount {

    //FIXME double type is enough to start, but may need to care about currency and digits
    @Getter
    private final double value;

    protected Amount(double value) {
        this.value = value;
    }

    public Amount plus(Amount amountToAdd) {
        return amount(this.value + amountToAdd.value);
    }

    public Amount minus(Amount amountToSubscribe) {
        return amount(this.value - amountToSubscribe.value);
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
