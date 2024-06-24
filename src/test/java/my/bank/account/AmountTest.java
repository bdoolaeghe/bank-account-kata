package my.bank.account;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static my.bank.account.Currency.EUR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

}