package my.bank.account;

public record Withdrawal(Amount amount) implements Operation {

    public static Withdrawal of(Amount amount) {
        return new Withdrawal(amount);
    }

    @Override
    public double signedAmountValue() {
        return amount.value() * -1;
    }

    @Override
    public String toString() {
        return "Withdrawal of "+ amount;
    }
}
