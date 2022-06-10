/**
 * 
 */
package it.aizoon.owasp1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

/**
 * @author Philippe
 *
 */
@Component
public class AccountDAO {

    private final DataSource dataSource;
    private final EntityManager em;

    public AccountDAO(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
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
     * Return all accounts owned by a given customer,given his/her external id
     * 
     * @param customerId
     * @return
     */
    public List<AccountDTO> safeFindAccountsByCustomerId(String customerId) {

        String sql = "select customer_id, branch_id,acc_number,balance from Accounts where customer_id = ?";

        try (Connection c = dataSource.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, customerId);
            ResultSet rs = p.executeQuery();
            List<AccountDTO> accounts = new ArrayList<>();
            while (rs.next()) {
                AccountDTO acc = AccountDTO.builder()
                    .customerId(rs.getString("customerId"))
                    .branchId(rs.getString("branch_id"))
                    .accNumber(rs.getString("acc_number"))
                    .balance(rs.getBigDecimal("balance"))
                    .build();

                accounts.add(acc);
            }
            return accounts;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
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
