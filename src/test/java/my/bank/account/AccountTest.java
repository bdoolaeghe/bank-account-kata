package my.bank.account;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static my.bank.account.Currency.EUR;
import static my.bank.account.Currency.USD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {

    @Nested
    class AccountConsistency {

        @Test
        void should_get_currency_from_initial_funds() {
            // When
            var anUsdAccount = new Account(Amount.of(10, USD));
            // Then
            assertThat(anUsdAccount.getCurrency()).isEqualTo(USD);
        }

        @Test
        void should_create_eur_account_by_default() {
            // When
            var anEuroAccount = new Account();
            // Then
            assertThat(anEuroAccount.getCurrency()).isEqualTo(EUR);
        }

    }

    @Nested
    class Deposit {

        @Test
        void should_deposit_an_amount_on_empty_account() {
            // Given
            var anEmptyAccount = new Account();
            // When
            anEmptyAccount.deposit(new Amount(20, EUR));
            // Then
            assertThat(anEmptyAccount.getBalance()).isEqualTo(new Amount(20, EUR));
        }

        @Test
        void should_deposit_an_amount_on_non_empty_account() {
            // Given
            var anAccount = new Account(new Amount(10, EUR));
            // When
            anAccount.deposit(new Amount(20, EUR));
            // Then
            assertThat(anAccount.getBalance()).isEqualTo(new Amount(30, EUR));
        }

        @Test
        void should_refuse_deposit_in_another_currency() {
            // Given
            var anEurAccount = new Account(new Amount(10, EUR));
            // When/Then
            assertThatThrownBy(() ->
                    anEurAccount.deposit(Amount.of(20, USD)))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class Withdrawal {

        @Test
        void should_successfully_withdraw_on_covered_account() {
            // Given
            var anAccount = new Account(Amount.of(10.15, EUR));
            // When
            anAccount.withdraw(Amount.of(1, EUR));
            // Then
            assertThat(anAccount.getBalance()).isEqualTo(Amount.of(9.15, EUR));
        }

        @Test
        void should_successfully_empty_the_whole_account() {
            // Given
            var anAccount = new Account(Amount.of(10.15, EUR));
            // When
            anAccount.withdraw(Amount.of(10.15, EUR));
            // Then
            assertThat(anAccount.getBalance()).isEqualTo(Amount.of(0, EUR));
        }

        @Test
        void should_fail_to_withdraw_when_account_get_overdrawn() {
            // Given
            var anAccount = new Account(Amount.of(10.15, EUR));
            // When/Then
            assertThatThrownBy(() ->
                    anAccount.withdraw(Amount.of(1000, EUR))
            ).isInstanceOf(OverdrawnAccountException.class);
        }

    }
}