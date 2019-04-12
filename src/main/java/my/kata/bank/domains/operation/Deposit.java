package my.kata.bank.domains.operation;

import lombok.EqualsAndHashCode;
import my.kata.bank.domains.amount.Amount;

@EqualsAndHashCode
public class Deposit extends Operation {

    protected Deposit(Amount amount) {
        super(amount);
    }

    @Override
    public double getSignedAmountValue() {
        return amount.getValue();
    }

    public static Deposit aDeposit(Amount amount) {
        return new Deposit(amount);
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "amount=" + amount +
                '}';
    }
}
