package my.kata.bank.domains.account;

import lombok.Getter;
import my.kata.bank.domains.amount.Amount;

import java.util.ArrayList;
import java.util.List;

import static my.kata.bank.domains.amount.Amount.amount;
import static my.kata.bank.domains.account.BalancedLoggedOperation.aBalancedLoggedOperation;

class OperationsAccumulator {

    private Amount currentBalance = amount(0);

    @Getter
    private List<BalancedLoggedOperation> balancedOperations = new ArrayList<>();

    void accumulate(LoggedOperation loggedOperation) {
        currentBalance = currentBalance.apply(loggedOperation.getOperation());
        balancedOperations.add(aBalancedLoggedOperation(loggedOperation.getOperation(), loggedOperation.getOperationDate(), currentBalance));
    }

    void combine(OperationsAccumulator anotherAccumulator) {
        currentBalance.plus(anotherAccumulator.currentBalance);
        balancedOperations.addAll(anotherAccumulator.getBalancedOperations());
    }
}
