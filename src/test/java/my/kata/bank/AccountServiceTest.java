package my.kata.bank;

import org.junit.Test;

import java.time.Instant;
import java.util.List;

import static my.kata.bank.Account.newAccount;
import static my.kata.bank.Account.newEmptyAccount;
import static my.kata.bank.Amount.amount;
import static my.kata.bank.Deposit.newDeposit;
import static my.kata.bank.LoggedOperation.historized;
import static my.kata.bank.Withrawal.newWithdrawal;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountServiceTest {

    AccountService accountService = new AccountServiceImpl();
    Instant now = Instant.now();

    @Test
    public void should_increase_balance_after_the_deposit() {
        // Given
        Account myAccount = newAccount(newDeposit(amount(0f)));
        // When
        accountService.deposit(amount(10), myAccount);
        // THen
        assertThat(myAccount.getBalance()).isEqualTo(amount(10));
    }

    @Test
    public void should_increase_balance_after_a_second_deposit() {
        // Given
        Account myAccount = newAccount(newDeposit(amount(100d)));
        // When
        accountService.deposit(amount(10), myAccount);
        // THen
        assertThat(myAccount.getBalance()).isEqualTo(amount(110d));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_support_negative_amount_withdrawal() {
        newWithdrawal(new Amount(-1f));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_support_negative_amount_deposit() {
        newDeposit(new Amount(-1f));
    }

    @Test
    public void should_decrease_balance_after_a_withdrawal() {
        // Given
        Account myAccount = newAccount(newDeposit(amount(100d)));
        // When
        accountService.withdraw(amount(10), myAccount);
        // THen
        assertThat(myAccount.getBalance()).isEqualTo(amount(90d));
    }

    @Test
    public void should_allow_to_withdraw_when_balance_becomes_negative() {
        // Given
        Account myAccount = newAccount(newDeposit(amount(100d)));
        // When
        accountService.withdraw(amount(200d), myAccount);
        // THen
        assertThat(myAccount.getBalance()).isEqualTo(amount(-100d));
    }

    @Test
    public void should_get_balance_after_few_operations() {
        // Given
        Account myAccount = newAccount(newDeposit(amount(100d)));
        // When
        accountService.withdraw(amount(200d), myAccount);
        accountService.deposit(amount(200d), myAccount);
        accountService.deposit(amount(50d), myAccount);
        accountService.withdraw(amount(10d), myAccount);
        // THen
        assertThat(myAccount.getBalance()).isEqualTo(amount(140d));
    }

    @Test
    public void should_show_empty_history_after_no_operations() {
        // Given
        Account myAccount = newEmptyAccount();
        // When
        List<LoggedOperation> history = accountService.getHistory(myAccount);
        // Then
        assertThat(history).isEmpty();
    }

    @Test
    public void should_show_history_after_one_operation() {
        // Given
        Account myAccount = newEmptyAccount();
        accountService.deposit(amount(200d), myAccount, now);
        // When
        List<LoggedOperation> history = accountService.getHistory(myAccount);
        // THen
        assertThat(history).containsExactly(
            historized(newDeposit(amount(200d)), now)
        );
    }

    @Test
    public void should_show_history_after_few_operation() {
        // Given
        Account myAccount = newAccount(newDeposit(amount(100d)), now);
        accountService.withdraw(amount(200d), myAccount, now);
        accountService.deposit(amount(200d), myAccount, now);
        accountService.deposit(amount(50d), myAccount, now);
        accountService.withdraw(amount(10d), myAccount, now);

        // When
        List<LoggedOperation> history = accountService.getHistory(myAccount);

        // THen
        assertThat(history).containsExactly(
                historized(newDeposit(amount(100d)), now),
                historized(newWithdrawal(amount(200d)), now),
                historized(newDeposit(amount(200d)), now),
                historized(newDeposit(amount(50d)), now),
                historized(newWithdrawal(amount(10d)), now)
        );

    }

    // many ? (paginaation ?)

    // balance after each operation ?
}
