package my.kata.bank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public abstract class Operation {

    @Getter
    protected final Amount amount;

    protected Operation(Amount amount) {
        if (amount.isNegative()) {
            throw new IllegalArgumentException("Amount of an operation should be positive, but was: " + amount);
        }

        this.amount = amount;
    }

    abstract double getSignedAmountValue();

}
