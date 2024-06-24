package my.bank.account;

public interface Operation {

    Amount amount();

    default Currency getCurrency() {
        return amount().currency();
    }

    double signedAmountValue();
}
