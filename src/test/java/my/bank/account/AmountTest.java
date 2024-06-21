package my.bank.account;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static my.bank.account.Currency.EUR;
import static my.bank.account.Currency.USD;
import static org.assertj.core.api.Assertions.*;

class AmountTest {

    @Nested
    class AmountConsistency {

        @ParameterizedTest
        @ValueSource(doubles = {0, 10.15})
        void amount_should_support_positive_value(double value) {
            var amount = new Amount(value, EUR);
            assertThat(amount).isNotNull();
        }

        @Test
        void amount_should_support_negative_value() {
            var amount = new Amount(-10, EUR);
            assertThat(amount).isNotNull();
        }

    }

    @Nested
    class Plus {

        @Test
        void should_add_amounts_in_same_currency() {
            // Given
            var anEurAmount = new Amount(10, EUR);
            // When
            var newAmount = anEurAmount.plus(Amount.of(21.01, EUR));
            // Then
            assertThat(newAmount).isEqualTo(Amount.of(31.01, EUR));
        }

        @Test
        void should_add_negative_amounts_in_same_currency() {
            // Given
            var anEurAmount = new Amount(10, EUR);
            // When
            var newAmount = anEurAmount.plus(Amount.of(-5, EUR));
            // Then
            assertThat(newAmount).isEqualTo(Amount.of(5, EUR));
        }

        @Test
        void should_reject_adding_amounts_in_different_currencies() {
            // Given
            var anEurAmount = new Amount(10, EUR);
            // When/Then
            assertThatThrownBy(() ->
                    anEurAmount.plus(Amount.of(21.01, USD))
            ).isInstanceOf(IllegalArgumentException.class);
        }

    }
}