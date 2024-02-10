package org.ieeervce.api.siterearnouveau.service;

import org.ieeervce.api.siterearnouveau.entity.AuthToken;
import org.ieeervce.api.siterearnouveau.entity.User;
import org.ieeervce.api.siterearnouveau.jwt.JWTUtil;
import org.ieeervce.api.siterearnouveau.repository.AuthTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthTokenService {

    private final AuthTokenRepository authTokenRepository;
    private final JWTUtil jwtUtil;

    public AuthTokenService(AuthTokenRepository authTokenRepository, JWTUtil jwtUtil) {
        this.authTokenRepository = authTokenRepository;
        this.jwtUtil = jwtUtil;
    }

    public AuthToken create(User user) {
        AuthToken authToken = new AuthToken();
        authToken.setUser(user);
        String jwtToken = jwtUtil.create(user);
        authToken.setToken(jwtToken);
        return authTokenRepository.save(authToken);
    }

    public Optional<String> validateAndGetUserId(String authToken) {
        Optional<Integer> userIdOptional = jwtUtil.verifyAndGetUserId(authToken).map(Integer::parseInt);

        return userIdOptional
                .filter(userId -> authTokenRepository.existsByUidAndToken(userId, authToken))
                .map(Object::toString);
    }

    public void remove(String authTokenString) {
        authTokenRepository.findById(authTokenString).ifPresent(authTokenRepository::delete);
    }

}
