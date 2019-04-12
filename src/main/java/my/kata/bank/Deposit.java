package my.kata.bank;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Deposit extends Operation {

    protected Deposit(Amount amount) {
        super(amount);
    }

    @Override
    double getSignedAmountValue() {
        return amount.getValue();
    }

    public static Deposit newDeposit(Amount amount) {
        return new Deposit(amount);
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "amount=" + amount +
                '}';
    }
}
