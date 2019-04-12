package my.kata.bank.domains.operation;

import lombok.EqualsAndHashCode;
import my.kata.bank.domains.amount.Amount;

@EqualsAndHashCode
public class Deposit extends Operation {

    protected Deposit(Amount amount) {
        super(amount);
    }

    public static Deposit aDeposit(Amount amount) {
        return new Deposit(amount);
    }

    @Override
    public Amount applyOn(Amount amountBefore) {
        return amountBefore.plus(getAmount());
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "amount=" + amount +
                '}';
    }
}
