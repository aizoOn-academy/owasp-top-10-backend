package it.aizoon.owasp1;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import it.aizoon.owasp1.sql.AccountDAO;
import it.aizoon.owasp1.sql.AccountDTO;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class Owasp1SqlTest {

    @Autowired
    private AccountDAO target;

    @Test
    void givenAVulnerableJpaMethod_whenHackedCustomerId_thenReturnAllAccounts() {

        List<AccountDTO> accounts = target.unsafeJpaFindAccountsByCustomerId("C1' or '1'='1");
        Assertions.assertThat(accounts).isNotNull();
        Assertions.assertThat(accounts).isNotEmpty();
        Assertions.assertThat(accounts).hasSize(3);
    }

    @Test
    void givenASafeJpaMethod_whenHackedCustomerId_thenReturnNoAccounts() {
        List<AccountDTO> accounts = target.safeJpaFindAccountsByCustomerId("C1' or '1'='1");
        Assertions.assertThat(accounts).isNotNull();
        Assertions.assertThat(accounts).isEmpty();

        accounts = target.safeJpaFindAccountsByCustomerId("C1");
        Assertions.assertThat(accounts).isNotEmpty();
        Assertions.assertThat(accounts).hasSize(1);
    }

    @Test
    void givenWrongJpaPlaceholderUsageMethod_whenNormalCall_thenThrowsException() {
        assertThrows(Exception.class, () -> target.wrongJpaCountRecordsByTableName("Accounts"));
    }
    
}
