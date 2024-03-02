package com.dmiagkov.bank.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is mandatory")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "patronymic")
    private String patronymic;

    @NotBlank(message = "Last name is mandatory")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "Birth date is mandatory")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "user_phone_numbers", joinColumns = @JoinColumn(name = "user_id"))
    @AttributeOverride(name = "phoneNumber", column = @Column(name = "phone"))
    private List< Phone> phones = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "user_emails", joinColumns = @JoinColumn(name = "user_id"))
    @AttributeOverride(name = "emailAddress", column = @Column(name = "email"))
    private List<Email> email = new ArrayList<>();

    @NotBlank(message = "Login is mandatory")
    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @NotBlank(message = "Password is mandatory")
    @Column(name = "password")
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
