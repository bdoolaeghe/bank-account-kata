package my.kata.bank;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Amount {

    //FIXME double type is enough to start, but may need to care about currency, digits...
    @Getter
    private final double value;

    protected Amount(double value) {
        this.value = value;
    }

    public Amount apply(Operation operation) {
        return amount(this.value + operation.getSignedAmountValue());
    }

//    public Amount plus(Amount amountToAdd) {
//        return amount(this.value + amountToAdd.value);
//    }
//
//    public Amount minus(Amount amountToSubstract) {
//        return amount(this.value - amountToSubstract.value);
//    }

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
