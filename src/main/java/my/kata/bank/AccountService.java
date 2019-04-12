package my.kata.bank;

import java.time.Instant;
import java.util.List;

public interface AccountService {

    void deposit(Amount depositAmount, Account toAccount);

    void deposit(Amount depositAmount, Account toAccount, Instant operationDate);

    void withdraw(Amount withdrawalAmount, Account fromAccount);

    void withdraw(Amount withdrawalAmount, Account fromAccount, Instant operationDate);

    List<HistoryOperation> getHistory(Account account);

}
