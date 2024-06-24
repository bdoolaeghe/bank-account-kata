package my.bank.account;

public record Deposit() implements Operation {
    public static Deposit of(Amount amount) {
        throw new RuntimeException("implement me !");
    }
}
