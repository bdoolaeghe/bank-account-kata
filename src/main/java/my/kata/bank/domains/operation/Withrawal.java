package my.kata.bank.domains.operation;

import lombok.EqualsAndHashCode;
import my.kata.bank.domains.amount.Amount;

@EqualsAndHashCode
public class Withrawal extends Operation {

    protected Withrawal(Amount amount) {
        super(amount);
    }

    @Override
    public Amount applyOn(Amount amountBefore) {
        return amountBefore.minus(getAmount());
    }

    public static Withrawal aWithdrawal(Amount amount) {
        return new Withrawal(amount);
    }

    @Override
    public String toString() {
        return "Withrawal{" +
                "amount=" + amount +
                '}';
    }
}
