package it.aizoon.owasp1;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class Owasp1Test {

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
    void givenASafeMethod_whenHackedCustomerId_thenReturnNoAccounts() {

        List<AccountDTO> accounts = target.safeFindAccountsByCustomerId("C1' or '1'='1");
        Assertions.assertThat(accounts).isNotNull();
        Assertions.assertThat(accounts).isEmpty();
    }

    @Test
    void givenASafeJpaMethod_whenHackedCustomerId_thenReturnNoAccounts() {

        List<AccountDTO> accounts = target.safeJpaFindAccountsByCustomerId("C1' or '1'='1");
        Assertions.assertThat(accounts).isNotNull();
        Assertions.assertThat(accounts).isEmpty();
    }

    @Test
    void givenWrongJpaPlaceholderUsageMethod_whenNormalCall_thenThrowsException() {
        target.wrongJpaCountRecordsByTableName("Accounts");
    }
    
}
