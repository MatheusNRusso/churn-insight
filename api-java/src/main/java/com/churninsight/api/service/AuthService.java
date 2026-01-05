package com.churninsight.api.service;

import com.churninsight.api.dto.LoginRequestDto;
import com.churninsight.api.dto.RegisterRequestDto;
import com.churninsight.api.dto.RegisterResponseDto;
import com.churninsight.api.dto.TokenResponseDto;
import com.churninsight.api.error.EmailAlreadyRegisteredException;
import com.churninsight.api.error.InvalidCredentialsException;
import com.churninsight.api.model.Account;
import com.churninsight.api.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public RegisterResponseDto register(RegisterRequestDto dto) {

        if (accountRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyRegisteredException(dto.email());
        }

        var account = Account.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .name(dto.name())
                .active(true)
                .build();

        accountRepository.save(account);

        // padrão: sem token no register
        return new RegisterResponseDto("Account created successfully. Please login.");
    }

    public TokenResponseDto login(LoginRequestDto dto) {

        try {
            var authToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
            var authentication = authenticationManager.authenticate(authToken);

            Account account = (Account) authentication.getPrincipal();

            String token = tokenService.generateToken(account);
            return new TokenResponseDto(token, "Bearer");

        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException();
        }
    }
}
