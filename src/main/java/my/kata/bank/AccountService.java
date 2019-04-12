package my.kata.bank;

import java.util.List;

public interface AccountService {

    void deposit(Deposit deposit, Account toAccount);

    void withdraw(Withrawal withdrawal, Account fromAccount);

    List<Operation> getHistory(Account account);

}
