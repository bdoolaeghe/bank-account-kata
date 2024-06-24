import my.bank.account.Account;
import my.bank.account.Amount;

import java.util.Date;

import static my.bank.account.Currency.EUR;

public class Main {

    public static void main(String[] args) {
        var today = new Date();
        var myAccount = Account.withInitialFunds(Amount.of(100, EUR), today);
        myAccount.deposit(Amount.of(10, EUR), today);
        myAccount.withdraw(Amount.of(5, EUR), today);
        System.out.println("my account balance: " + myAccount.getBalance());
        System.out.println("my account history:\n" + myAccount.accountStatement());
    }

}
