package my.bank.account;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static my.bank.account.Currency.EUR;
import static my.bank.account.Currency.USD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {

    Date today = new Date();

    @Nested
    class AccountConsistency {

        @Test
        void should_get_currency_from_initial_funds() {
            // When
            var anUsdAccount = Account.withInitialFunds(Amount.of(10, USD), today);
            // Then
            assertThat(anUsdAccount.getCurrency()).isEqualTo(USD);
        }

        @Test
        void should_create_eur_account_by_default() {
            // When
            var anEuroAccount = Account.inEuro(today);
            // Then
            assertThat(anEuroAccount.getCurrency()).isEqualTo(EUR);
        }

    }

    @Nested
    class DepositTest {

        @Test
        void should_deposit_an_amount_on_empty_account() {
            // Given
            var anEmptyAccount = Account.inEuro(today);
            // When
            anEmptyAccount.deposit(Amount.of(20, EUR), today);
            // Then
            assertThat(anEmptyAccount.getBalance()).isEqualTo(Amount.of(20, EUR));
        }

        @Test
        void should_deposit_an_amount_on_non_empty_account() {
            // Given
            var anAccount = Account.withInitialFunds(Amount.of(10, EUR), today);
            // When
            anAccount.deposit(Amount.of(20, EUR), today);
            // Then
            assertThat(anAccount.getBalance()).isEqualTo(Amount.of(30, EUR));
        }

        @Test
        void should_refuse_deposit_in_another_currency() {
            // Given
            var anEurAccount = Account.withInitialFunds(Amount.of(10, EUR), today);
            // When/Then
            assertThatThrownBy(() ->
                    anEurAccount.deposit(Amount.of(20, USD), today))
                    .isInstanceOf(IllegalAccountOperationException.class);
        }
    }

    @Nested
    class WithdrawalTest {

        @Test
        void should_successfully_withdraw_on_covered_account() {
            // Given
            var anAccount = Account.withInitialFunds(Amount.of(10.15, EUR), today);
            // When
            anAccount.withdraw(Amount.of(1, EUR), today);
            // Then
            assertThat(anAccount.getBalance()).isEqualTo(Amount.of(9.15, EUR));
        }

        @Test
        void should_successfully_empty_the_whole_account() {
            // Given
            var anAccount = Account.withInitialFunds(Amount.of(10.15, EUR), today);
            // When
            anAccount.withdraw(Amount.of(10.15, EUR), today);
            // Then
            assertThat(anAccount.getBalance()).isEqualTo(Amount.of(0, EUR));
        }

        @Test
        void should_fail_to_withdraw_from_account_in_another_currency() {
            // Given
            var anAccount = Account.withInitialFunds(Amount.of(10.15, EUR), today);
            // When/Then
            assertThatThrownBy(() ->
                    anAccount.withdraw(Amount.of(1, USD), today)
            ).isInstanceOf(IllegalAccountOperationException.class);
        }

        @Test
        void should_fail_to_withdraw_when_account_get_overdrawn() {
            // Given
            var anAccount = Account.withInitialFunds(Amount.of(10.15, EUR), today);
            // When/Then
            assertThatThrownBy(() ->
                    anAccount.withdraw(Amount.of(1000, EUR), today)
            ).isInstanceOf(OverdrawnAccountException.class);
        }

    }

    @Nested
    class HistoryTest {

        @Test
        void should_record_and_retrieve_operations_history() {
            // Given
            var anAccount = Account.withInitialFunds(Amount.of(100, EUR), today);
            anAccount.deposit(Amount.of(10, EUR), today);
            anAccount.withdraw(Amount.of(1, EUR), today);
            anAccount.withdraw(Amount.of(2, EUR), today);

            // When
            var history = anAccount.getHistory();

            // Then
            assertThat(history).containsExactly(
                    Deposit.of(Amount.of(100, EUR), today),
                    Deposit.of(Amount.of(10, EUR), today),
                    Withdrawal.of(Amount.of(1, EUR), today),
                    Withdrawal.of(Amount.of(2, EUR), today)
            );
        }

        @Test
        void should_collapse_operations_to_compute_account_balance() {
            // Given
            var anAccount = Account.withInitialFunds(Amount.of(100, EUR), today);
            anAccount.deposit(Amount.of(10, EUR), today);
            anAccount.withdraw(Amount.of(1, EUR), today);
            anAccount.withdraw(Amount.of(2, EUR), today);

            // When
            var balance = anAccount.getBalance();

            // Then
            assertThat(balance).isEqualTo(Amount.of(107, EUR));
        }

        @Test
        void should_compute_balance_in_rows_history() {
            // Given
            var anAccount = Account.withInitialFunds(Amount.of(100, EUR), today);
            anAccount.deposit(Amount.of(10, EUR), today);
            anAccount.withdraw(Amount.of(1, EUR), today);
            anAccount.withdraw(Amount.of(2, EUR), today);

            // When
            var report = anAccount.getReport();

            // Then
            assertThat(report).isEqualTo(
                    new AccountReport(List.of(
                            new AccountReport.Row(Deposit.of(Amount.of(100, EUR), today), Amount.of(100, EUR)),
                            new AccountReport.Row(Deposit.of(Amount.of(10, EUR), today), Amount.of(110, EUR)),
                            new AccountReport.Row(Withdrawal.of(Amount.of(1, EUR), today), Amount.of(109, EUR)),
                            new AccountReport.Row(Withdrawal.of(Amount.of(2, EUR), today), Amount.of(107, EUR))
                    ))
            );
        }
    }

}