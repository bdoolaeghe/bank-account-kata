package my.bank.account;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static my.bank.account.Currency.EUR;
import static org.assertj.core.api.Assertions.assertThat;

class AccountReportTest {

    Date today = new Date();

    @Test
    void should_compute_account_operations_report() {
        // Given
        var anAccount = Account.withInitialFunds(Amount.of(100, EUR), today);
        anAccount.deposit(Amount.of(10, EUR), today);
        anAccount.withdraw(Amount.of(1, EUR), today);
        anAccount.withdraw(Amount.of(2, EUR), today);

        // When
        var report = AccountReport.from(anAccount);

        // Then
        assertThat(report.toString()).isEqualToIgnoringWhitespace(
                """
                        [2024/06/28] Deposit of 100.0 EUR (new balance: 100.0 EUR)
                        [2024/06/28] Deposit of 10.0 EUR (new balance: 110.0 EUR)
                        [2024/06/28] Withdrawal of 1.0 EUR (new balance: 109.0 EUR)
                        [2024/06/28] Withdrawal of 2.0 EUR (new balance: 107.0 EUR)
                        """
        );
    }
}
