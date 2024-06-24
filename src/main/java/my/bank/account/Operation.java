package my.bank.account;

import java.util.Date;

public interface Operation {

    Amount amount();

    Date date();

    default Currency getCurrency() {
        return amount().currency();
    }

    double signedAmountValue();
}
