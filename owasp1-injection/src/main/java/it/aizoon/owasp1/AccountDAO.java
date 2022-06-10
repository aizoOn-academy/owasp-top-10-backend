/**
 * 
 */
package it.aizoon.owasp1;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

/**
 * @author Philippe
 *
 */
@Component
public class AccountDAO {

    private final EntityManager em;

    public AccountDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Return all accounts owned by a given customer,given his/her external id - JPA version
     * 
     * @param customerId
     * @return
     */
    public List<AccountDTO> unsafeJpaFindAccountsByCustomerId(String customerId) {
        String jql = "from Account where customerId = '" + customerId + "'";
        TypedQuery<Account> q = em.createQuery(jql, Account.class);
        return q.getResultList()
            .stream()
            .map(a -> AccountDTO.builder()
                .accNumber(a.getAccNumber())
                .balance(a.getBalance())
                .branchId(a.getAccNumber())
                .customerId(a.getCustomerId())
                .build())
            .collect(Collectors.toList());
    }

    /**
     * Return all accounts owned by a given customer,given his/her external id - JPA version
     * 
     * @param customerId
     * @return
     */
    public List<AccountDTO> safeJpaFindAccountsByCustomerId(String customerId) {

        String jql = "from Account where customerId = :customerId";
        TypedQuery<Account> q = em.createQuery(jql, Account.class)
            .setParameter("customerId", customerId);

        return q.getResultList()
            .stream()
            .map(a -> AccountDTO.builder()
                .accNumber(a.getAccNumber())
                .balance(a.getBalance())
                .branchId(a.getAccNumber())
                .customerId(a.getCustomerId())
                .build())
            .collect(Collectors.toList());
    }

    /**
     * Invalid placeholder usage example - JPA
     * 
     * @param tableName 
     * @return
     */
    public Long wrongJpaCountRecordsByTableName(String tableName) {

        String jql = "select count(*) from :tableName";
        TypedQuery<Long> q = em.createQuery(jql, Long.class)
            .setParameter("tableName", tableName);

        return q.getSingleResult();

    }

}
