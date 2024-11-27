package io.victoriuso.better_integration_test.service.impl;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.victoriuso.better_integration_test.configuration.TokenProperties;
import io.victoriuso.better_integration_test.model.entity.User;
import io.victoriuso.better_integration_test.service.TokenHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenHelperServiceImpl implements TokenHelperService {

    private final TokenProperties tokenProperties;

    @Override
    public String generateToken(User user) {
        final Date expiration = Date.from(Instant.now().plus(tokenProperties.getExpirationTime()));
        final JwtBuilder jwtBuilder = Jwts.builder().issuedAt(new Date())
                .expiration(new Date())
                .issuedAt(new Date())
                .expiration(expiration)
                .claims(this.getClaims(user))
                .signWith(this.getSecretKey());
        return jwtBuilder.compact();
    }

    private Map<String, String> getClaims(User user) {
        final Map<String, String> claims = new HashMap<>();
        claims.put("name", user.getFullName());
        claims.put("email", user.getEmail());
        return claims;
    }

    private Key getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(tokenProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
