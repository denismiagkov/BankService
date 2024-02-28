package com.dmiagkov.bank.security.service;

import com.dmiagkov.bank.application.dto.incoming.UserRegisterDto;
import com.dmiagkov.bank.application.dto.outgoing.UserDto;
import com.dmiagkov.bank.application.mapper.UserMapper;
import com.dmiagkov.bank.application.repository.UserRepository;
import com.dmiagkov.bank.security.JwtProvider;
import com.dmiagkov.bank.security.http.JwtRequest;
import com.dmiagkov.bank.security.http.JwtResponse;
import com.dmiagkov.bank.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;


    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    @Override
    public UserDto signUp(UserRegisterDto userRegisterDto) {
        var encodedPassword = passwordEncoder.encode(userRegisterDto.getPassword());
        userRegisterDto.setPassword(encodedPassword);
        User toRegisterUser = userMapper.userRegisterDtoToUser(userRegisterDto);
        User registeredUser = userRepository.save(toRegisterUser);
        return userMapper.userToUserDto(registeredUser);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    @Override
    public JwtResponse signIn(JwtRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(),
                request.getPassword()
        ));
//        var user = userService
//                .loadUserByUsername(request.getLogin());

        User user1 = new User(request.getLogin(), request.getPassword());
        var jwt = jwtProvider.createToken(user1);
        return new JwtResponse(jwt);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(login);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .build();
    }
}