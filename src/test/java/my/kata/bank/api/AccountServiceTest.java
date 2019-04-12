package my.kata.bank.api;

import my.kata.bank.domains.account.Account;
import my.kata.bank.domains.account.BalancedLoggedOperation;
import my.kata.bank.domains.account.LoggedOperation;
import org.junit.Test;

import java.time.Instant;
import java.util.List;

import static my.kata.bank.domains.account.Account.anAccount;
import static my.kata.bank.domains.account.Account.anEmptyAccount;
import static my.kata.bank.domains.account.BalancedLoggedOperation.aBalancedLoggedOperation;
import static my.kata.bank.domains.account.LoggedOperation.aLoggedOperation;
import static my.kata.bank.domains.amount.Amount.amount;
import static my.kata.bank.domains.operation.Deposit.aDeposit;
import static my.kata.bank.domains.operation.Withrawal.aWithdrawal;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountServiceTest {

    AccountService accountService = new AccountServiceImpl();
    Instant now = Instant.now();

    @Test
    public void should_increase_balance_after_the_deposit() {
        // Given
        Account myAccount = anAccount(aDeposit(amount(0f)));
        // When
        accountService.deposit(amount(10), myAccount);
        // THen
        assertThat(myAccount.getBalance()).isEqualTo(amount(10));
    }

    @Test
    public void should_increase_balance_after_a_second_deposit() {
        // Given
        Account myAccount = anAccount(aDeposit(amount(100d)));
        // When
        accountService.deposit(amount(10), myAccount);
        // THen
        assertThat(myAccount.getBalance()).isEqualTo(amount(110d));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_support_negative_amount_withdrawal() {
        aWithdrawal(amount(-1f));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_support_negative_amount_deposit() {
        aDeposit(amount(-1f));
    }

    @Test
    public void should_decrease_balance_after_a_withdrawal() {
        // Given
        Account myAccount = anAccount(aDeposit(amount(100d)));
        // When
        accountService.withdraw(amount(10), myAccount);
        // THen
        assertThat(myAccount.getBalance()).isEqualTo(amount(90d));
    }

    @Test
    public void should_allow_to_withdraw_when_balance_becomes_negative() {
        // Given
        Account myAccount = anAccount(aDeposit(amount(100d)));
        // When
        accountService.withdraw(amount(200d), myAccount);
        // THen
        assertThat(myAccount.getBalance()).isEqualTo(amount(-100d));
    }

    @Test
    public void should_get_balance_after_few_operations() {
        // Given
        Account myAccount = anAccount(aDeposit(amount(100d)));
        // When
        accountService.withdraw(amount(200d), myAccount);
        accountService.deposit(amount(200d), myAccount);
        accountService.deposit(amount(50d), myAccount);
        accountService.withdraw(amount(10d), myAccount);
        // THen
        assertThat(myAccount.getBalance()).isEqualTo(amount(140d));
    }

    @Test
    public void should_get_empty_operations_list_after_no_operations() {
        // Given
        Account myAccount = anEmptyAccount();
        // When
        List<LoggedOperation> operations = myAccount.getOperationsLog();
        // Then
        assertThat(operations).isEmpty();
    }

    @Test
    public void should_get_operations_list_after_one_operation() {
        // Given
        Account myAccount = anEmptyAccount();
        accountService.deposit(amount(200d), myAccount, now);
        // When
        List<LoggedOperation> operations = myAccount.getOperationsLog();
        // THen
        assertThat(operations).containsExactly(
            aLoggedOperation(aDeposit(amount(200d)), now)
        );
    }

    @Test
    public void should_get_operations_list_after_few_operation() {
        // Given
        Account myAccount = Account.anAccount(aDeposit(amount(100d)), now);
        accountService.withdraw(amount(200d), myAccount, now);
        accountService.deposit(amount(200d), myAccount, now);
        accountService.deposit(amount(50d), myAccount, now);
        accountService.withdraw(amount(10d), myAccount, now);

        // When
        List<LoggedOperation> operations = myAccount.getOperationsLog();

        // THen
        assertThat(operations).containsExactly(
                aLoggedOperation(aDeposit(amount(100d)), now),
                aLoggedOperation(aWithdrawal(amount(200d)), now),
                aLoggedOperation(aDeposit(amount(200d)), now),
                aLoggedOperation(aDeposit(amount(50d)), now),
                aLoggedOperation(aWithdrawal(amount(10d)), now)
        );
    }

    @Test
    public void should_get_empty_history_when_no_operation_occured() {
        // Given
        Account myAccount = anEmptyAccount();
        // When
        List<BalancedLoggedOperation> history = accountService.getHistory(myAccount);
        // Then
        assertThat(history).isEmpty();
    }

    @Test
    public void should_get_history_after_one_operation() {
        // Given
        Account myAccount = anEmptyAccount();
        accountService.deposit(amount(200d), myAccount, now);
        // When
        List<BalancedLoggedOperation> history = accountService.getHistory(myAccount);
        // THen
        assertThat(history).containsExactly(
                aBalancedLoggedOperation(aDeposit(amount(200d)), now,  amount(200d))
        );
    }

    @Test
    public void should_get_history_after_few_operation() {
        // Given
        Account myAccount = Account.anAccount(aDeposit(amount(100d)), now);
        accountService.withdraw(amount(200d), myAccount, now);
        accountService.deposit(amount(200d), myAccount, now);
        accountService.deposit(amount(50d), myAccount, now);
        accountService.withdraw(amount(10d), myAccount, now);

        // When
        List<BalancedLoggedOperation> history = accountService.getHistory(myAccount);

        // THen
        assertThat(history).containsExactly(
                aBalancedLoggedOperation(aDeposit(amount(100d)), now, amount(100d)),
                aBalancedLoggedOperation(aWithdrawal(amount(200d)), now, amount(-100d)),
                aBalancedLoggedOperation(aDeposit(amount(200d)), now, amount(100d)),
                aBalancedLoggedOperation(aDeposit(amount(50d)), now, amount(150d)),
                aBalancedLoggedOperation(aWithdrawal(amount(10d)), now, amount(140d))
        );
    }

}
