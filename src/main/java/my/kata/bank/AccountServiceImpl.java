package my.kata.bank;

import java.time.Instant;
import java.util.List;

import static java.time.Instant.now;
import static my.kata.bank.Deposit.newDeposit;
import static my.kata.bank.Withrawal.newWithdrawal;

public class AccountServiceImpl implements AccountService {

    @Override
    public void deposit(Amount depositAmount, Account account) {
        deposit(depositAmount, account, now());
    }

    @Override
    public void deposit(Amount depositAmount, Account account, Instant operationDate) {
        account.apply(newDeposit(depositAmount), operationDate);
    }

    @Override
    public void withdraw(Amount withdrawalAmount, Account account) {
        withdraw(withdrawalAmount, account, now());
    }

    @Override
    public void withdraw(Amount withdrawalAmount, Account account, Instant operationDate) {
        account.apply(newWithdrawal(withdrawalAmount), operationDate);
    }

    @Override
    public List<HistoryOperation> getHistory(Account account) {
        return account.getHistory();
    }
}
