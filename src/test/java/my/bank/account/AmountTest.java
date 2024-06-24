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
        void should_reject_amount_negative_value() {
            assertThatThrownBy(() -> new Amount(-1, EUR))
                    .isInstanceOf(IllegalArgumentException.class);
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