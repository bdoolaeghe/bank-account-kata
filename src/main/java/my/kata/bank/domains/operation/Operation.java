package my.kata.bank.domains.operation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import my.kata.bank.domains.amount.Amount;

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

    public abstract double getSignedAmountValue();

}
