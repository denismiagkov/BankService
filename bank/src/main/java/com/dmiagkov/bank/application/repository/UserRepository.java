package com.dmiagkov.bank.application.repository;

import com.dmiagkov.bank.domain.Email;
import com.dmiagkov.bank.domain.Phone;
import com.dmiagkov.bank.domain.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);

    User findUserByLogin(String login);

    User findUserById(Long id);

    boolean existsByLogin(String login);

    boolean existsById(Long id);

    boolean existsUserByPhonesContains(Phone phone);

    boolean existsUserByEmailContains(Email email);

    User findUserByEmailContains(Email email);

    User findUserByPhonesContains(Phone phone);

    List<User> findUsersByBirthDateAfter(LocalDate date, PageRequest pageRequest);

    @Query("""
            select u
            from User u
            where u.firstName like :firstname% and u.lastName like :lastname%
            """)
    List<User> findAllByName(String firstname, String lastname, PageRequest pageable);


}
