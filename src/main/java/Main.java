import my.bank.account.Account;
import my.bank.account.Amount;

import static my.bank.account.Currency.EUR;

public class Main {

    public static void main(String[] args) {
        var myAccount = Account.withInitialFunds(Amount.of(100, EUR));
        myAccount.deposit(Amount.of(10, EUR));
        myAccount.withdraw(Amount.of(5, EUR));
        System.out.println("my account: " + myAccount.getBalance());
    }

}
