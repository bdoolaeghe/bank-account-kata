package my.bank.account;

public record Withdrawal() implements Operation {
    public static Withdrawal of(Amount amount) {
        throw new RuntimeException("implement me !");
    }
}
