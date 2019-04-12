package my.kata.bank.api;

import my.kata.bank.domains.account.Account;
import my.kata.bank.domains.account.HistoryOperation;
import org.junit.Test;

import java.time.Instant;
import java.util.List;

import static my.kata.bank.domains.account.Account.anAccount;
import static my.kata.bank.domains.account.Account.anEmptyAccount;
import static my.kata.bank.domains.account.HistoryOperation.HistoryOperationBuilder.aHistoryOperation;
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
        Account myAccount = anAccount(aDeposit(amount(0)));
        // When
        accountService.deposit(amount(10), myAccount);
        // THen
        assertThat(myAccount.getBalance()).isEqualTo(amount(10));
    }

    @Test
    public void should_increase_balance_after_a_second_deposit() {
        // Given
        Account myAccount = anAccount(aDeposit(amount(100)));
        // When
        accountService.deposit(amount(10), myAccount);
        // THen
        assertThat(myAccount.getBalance()).isEqualTo(amount(110));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_support_negative_amount_withdrawal() {
        aWithdrawal(amount(-1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_support_negative_amount_deposit() {
        aDeposit(amount(-1));
    }

    @Test
    public void should_decrease_balance_after_a_withdrawal() {
        // Given
        Account myAccount = anAccount(aDeposit(amount(100)));
        // When
        accountService.withdraw(amount(10), myAccount);
        // THen
        assertThat(myAccount.getBalance()).isEqualTo(amount(90));
    }

    @Test
    public void should_allow_to_withdraw_when_balance_becomes_negative() {
        // Given
        Account myAccount = anAccount(aDeposit(amount(100)));
        // When
        accountService.withdraw(amount(200), myAccount);
        // THen
        assertThat(myAccount.getBalance()).isEqualTo(amount(-100));
    }

    @Test
    public void should_get_balance_after_few_operations() {
        // Given
        Account myAccount = anAccount(aDeposit(amount(100)));
        // When
        accountService.withdraw(amount(200), myAccount);
        accountService.deposit(amount(200), myAccount);
        accountService.deposit(amount(50), myAccount);
        accountService.withdraw(amount(10), myAccount);
        // THen
        assertThat(myAccount.getBalance()).isEqualTo(amount(140));
    }

    @Test
    public void should_get_empty_history_when_no_operation_occured() {
        // Given
        Account myAccount = anEmptyAccount();
        // When
        List<HistoryOperation> history = accountService.getHistory(myAccount);
        // Then
        assertThat(history).isEmpty();
    }

    @Test
    public void should_get_history_after_one_operation() {
        // Given
        Account myAccount = anEmptyAccount();
        accountService.deposit(amount(200), myAccount, now);
        // When
        List<HistoryOperation> history = accountService.getHistory(myAccount);
        // THen
        assertThat(history).containsExactly(
                aHistoryOperation()
                        .withOperation(aDeposit(amount(200)))
                        .withCurrentBalance(amount(200))
                        .withOperationDate(now)
                        .build()
        );
    }

    @Test
    public void should_get_history_after_few_operation() {
        // Given
        Account myAccount = Account.anAccount(aDeposit(amount(100)), now);
        accountService.withdraw(amount(200), myAccount, now);
        accountService.deposit(amount(200), myAccount, now);
        accountService.deposit(amount(50), myAccount, now);
        accountService.withdraw(amount(10), myAccount, now);

        // When
        List<HistoryOperation> history = accountService.getHistory(myAccount);

        // THen
        assertThat(history).containsExactly(
                aHistoryOperation().withOperation(aDeposit(amount(100))).withOperationDate(now).withCurrentBalance(amount(100)).build(),
                aHistoryOperation().withOperation(aWithdrawal(amount(200))).withOperationDate(now).withCurrentBalance(amount(-100)).build(),
                aHistoryOperation().withOperation(aDeposit(amount(200))).withOperationDate(now).withCurrentBalance(amount(100)).build(),
                aHistoryOperation().withOperation(aDeposit(amount(50))).withOperationDate(now).withCurrentBalance(amount(150)).build(),
                aHistoryOperation().withOperation(aWithdrawal(amount(10))).withOperationDate(now).withCurrentBalance(amount(140)).build()
        );
    }

}
