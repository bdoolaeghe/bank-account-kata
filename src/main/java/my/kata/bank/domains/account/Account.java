package my.kata.bank.domains.account;


import lombok.Getter;
import my.kata.bank.domains.amount.Amount;
import my.kata.bank.domains.operation.Deposit;
import my.kata.bank.domains.operation.Operation;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static java.time.Instant.now;
import static my.kata.bank.domains.account.HistoryOperation.HistoryOperationBuilder.aHistoryOperation;
import static my.kata.bank.domains.amount.Amount.amount;

@Getter
public class Account {

    private Amount balance = amount(0);
    private List<HistoryOperation> history = new ArrayList<>();

    private Account() {}

    public void apply(Operation operation, Instant operationDate) {
        balance = operation.applyOn(balance);
        history.add(aHistoryOperation()
                .withOperation(operation)
                .withOperationDate(operationDate)
                .withCurrentBalance(balance)
                .build());
    }

    public static Account anAccount(Deposit firstDeposit) {
        return anAccount(firstDeposit, now());
    }

    public static Account anAccount(Deposit firstDeposit, Instant creationDate) {
        Account account = new Account();
        account.apply(firstDeposit, creationDate);
        return account;
    }

    public static Account anEmptyAccount() {
        return new Account();
    }

}
