package com.dmiagkov.bank.application.repository;

import com.dmiagkov.bank.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);

    User findUserByEmail(String email);
    User findUserByLogin(String login);
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);

}
