package my.kata.bank;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Withrawal extends Operation {

    protected Withrawal(Amount amount) {
        super(amount);
    }

    @Override
    double getSignedAmountValue() {
        return -1 * amount.getValue();
    }

    public static Withrawal newWithdrawal(Amount amount) {
        return new Withrawal(amount);
    }

    @Override
    public String toString() {
        return "Withrawal{" +
                "amount=" + amount +
                '}';
    }
}
