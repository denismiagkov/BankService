package com.dmiagkov.bank.application.repository;

import com.dmiagkov.bank.domain.Email;
import com.dmiagkov.bank.domain.Phone;
import com.dmiagkov.bank.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);

    User findUserByEmail(String email);

    User findUserByLogin(String login);

    User findUserById(Long id);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);
    boolean existsUserByPhonesContains(Phone phone);
    boolean existsUserByEmailContains(Email email);

}
