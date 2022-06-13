package it.aizoon.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.aizoon.model.EmailVerification;

@Repository
public interface EmailVerificationRepository extends CrudRepository<EmailVerification, String> {

    EmailVerification findByUsername(String userName);
    boolean existsByUsername(String userName);
}
