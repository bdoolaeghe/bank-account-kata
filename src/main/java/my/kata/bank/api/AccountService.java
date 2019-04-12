package my.kata.bank.api;

import my.kata.bank.domains.account.Account;
import my.kata.bank.domains.account.Statement;
import my.kata.bank.domains.amount.Amount;

import java.time.Instant;
import java.util.List;

//API
public interface AccountService {

    void deposit(Amount depositAmount, Account toAccount);

    void deposit(Amount depositAmount, Account toAccount, Instant operationDate);

    void withdraw(Amount withdrawalAmount, Account fromAccount);

    void withdraw(Amount withdrawalAmount, Account fromAccount, Instant operationDate);

    List<Statement> getStatements(Account account);

}
