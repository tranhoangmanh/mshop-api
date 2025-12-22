package dev.manhtran.mshop_api.auth.service;

import dev.manhtran.mshop_api.auth.dto.AuthResponse;
import dev.manhtran.mshop_api.auth.dto.LoginRequest;
import dev.manhtran.mshop_api.auth.dto.SignupRequest;
import dev.manhtran.mshop_api.common.security.JwtUtil;
import dev.manhtran.mshop_api.user.entity.User;
import dev.manhtran.mshop_api.user.repository.UserRepository;
import dev.manhtran.mshop_api.user.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        User user = userDetailsService.getUserByEmail(loginRequest.getEmail());
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getFullName(),
                user.getRole().name()
        );
    }

    public AuthResponse signup(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setFullName(signupRequest.getFullName());
        user.setPhone(signupRequest.getPhone());
        user.setAddress(signupRequest.getAddress());
        user.setRole(User.Role.CUSTOMER);
        user.setActive(true);

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getFullName(),
                user.getRole().name()
        );
    }

    public User getCurrentUser(String email) {
        return userDetailsService.getUserByEmail(email);
    }
}
