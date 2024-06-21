package my.bank.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class AmountTest {

    @ParameterizedTest
    @ValueSource(doubles = {0, 10.15})
    void amount_should_support_positive_value(double value) {
        var amount = new Amount(value, Currency.EUR);
        assertThat(amount).isNotNull();
    }

    @Test
    void amount_should_support_negative_value() {
        var amount = new Amount(-10, Currency.EUR);
        assertThat(amount).isNotNull();
    }

}