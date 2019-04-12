package my.kata.bank.api;

import my.kata.bank.domains.account.Account;
import my.kata.bank.domains.account.Statement;
import my.kata.bank.domains.amount.Amount;

import java.time.Instant;
import java.util.List;

import static java.time.Instant.now;
import static my.kata.bank.domains.operation.Deposit.aDeposit;
import static my.kata.bank.domains.operation.Withrawal.aWithdrawal;

public class AccountServiceImpl implements AccountService {

    @Override
    public void deposit(Amount depositAmount, Account account) {
        deposit(depositAmount, account, now());
    }

    @Override
    public void deposit(Amount amount, Account account, Instant operationDate) {
        account.apply(aDeposit(amount), operationDate);
    }

    @Override
    public void withdraw(Amount withdrawalAmount, Account account) {
        withdraw(withdrawalAmount, account, now());
    }

    @Override
    public void withdraw(Amount amount, Account account, Instant operationDate) {
        account.apply(aWithdrawal(amount), operationDate);
    }

    @Override
    public List<Statement> getOperationsHistory(Account account) {
        return account.getHistory();
    }
}
