package my.bank.account;

public record Deposit(Amount amount) implements Operation {

    public static Deposit of(Amount amount) {
        return new Deposit(amount);
    }

    @Override
    public double signedAmountValue() {
        return amount.value();
    }

    @Override
    public String toString() {
        return "Deposit of "+ amount;
    }
}
