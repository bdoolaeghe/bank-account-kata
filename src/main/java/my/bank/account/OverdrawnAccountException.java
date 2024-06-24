package my.bank.account;

public class OverdrawnAccountException extends RuntimeException {
    public OverdrawnAccountException(IllegalArgumentException e) {
        super(e);
    }
}
