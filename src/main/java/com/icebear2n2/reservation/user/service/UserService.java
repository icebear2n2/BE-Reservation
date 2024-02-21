package com.icebear2n2.reservation.user.service;

import com.icebear2n2.reservation.domain.entity.User;
import com.icebear2n2.reservation.domain.repository.UserRepository;
import com.icebear2n2.reservation.domain.request.LoginRequest;
import com.icebear2n2.reservation.domain.request.SignupRequest;

import com.icebear2n2.reservation.domain.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z]).{8,}$");
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final Random random;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    public void signup(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Duplicated user email.");
        }

        validatePassword(signupRequest.getPassword());
        String encodedPassword = new BCryptPasswordEncoder().encode(signupRequest.getPassword());
        User user = signupRequest.toEntity(signupRequest.getUsername(), signupRequest.getEmail(), encodedPassword);

        try {
            user.setNickname(generateNickname());
            userRepository.save(user);
        } catch (Exception e) {
            LOGGER.error("ERROR OCCURRED WHILE SIGNING UP", e);
            throw new RuntimeException("Internal server error.");
        }
    }

    public void validatePassword(String password) {
        if (!password.matches(String.valueOf(PASSWORD_PATTERN))) {
            throw new RuntimeException("Invalid password format.");
        }
    }

    public Map<String, String> authenticateUser(LoginRequest loginRequest) {
        User user = Optional.ofNullable(userRepository.findByEmail(loginRequest.getEmail()))
                .orElseThrow(() -> new RuntimeException("User is unauthorized."));

        if (!new BCryptPasswordEncoder().matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Failed login.");
        }

        return tokenService.generateAndSaveTokens(user);
    }


    public Page<UserResponse> getAll(PageRequest pageRequest) {
        Page<User> all = userRepository.findAll(pageRequest);
        return all.map(UserResponse::new);
    }

    public void removeUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        user.setDeletedAt(new Timestamp(System.currentTimeMillis()));
        user.setRole(null);
        userRepository.save(user);
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void purgeDeletedUsers() {
        Timestamp threshold = new Timestamp(System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000));
        userRepository.deleteByDeletedAtBefore(threshold);
    }


    public String generateNickname() {
        int nickname;
        do {
            nickname = random.nextInt(900000) + 100000;
        } while (userRepository.findByNickname(String.valueOf(nickname)) != null);
        return String.valueOf(nickname);
    }
}

