package com.icchance.q91.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@Data
public class JwtUtil {

    private static final String CLAIMS_KEY_USER_ROLES = "userRoles";
    @Value("${jwt.signKey}")
    private String jwtSignKey;
    @Value("${jwt.expireTimeAsSec}")
    private long jwtExpireTimeAsAsc;

    public String createToken(String account) {
        //Map<String, Object> claimMap = Collections.singletonMap(CLAIMS_KEY_USER_ROLES, userRoles);
        Map<String, Object> claimMap = Collections.singletonMap("account", account);
        String token = Jwts.builder()
                .setSubject(account)
                .addClaims(claimMap)
                .setIssuedAt(new Date())
                // 驗證消滅時間
                //.setExpiration(Date.from(Instant.now().plusSeconds(jwtExpireTimeAsAsc)))
                .signWith(Keys.hmacShaKeyFor(jwtSignKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS512)
                .compact();
        log.debug("token : {}", token);
        return token;
    }

    private Claims parseToken(String token) {
        Claims claims = Jwts.parserBuilder()
                //.setSigningKey(Keys.hmacShaKeyFor(jwtSignKey.getBytes(StandardCharsets.UTF_8)))
                .setSigningKey(Keys.hmacShaKeyFor(jwtSignKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
        log.debug("claims :{}", claims);
        return claims;
    }

    public String parseAccountFromToken(String token) {
        return parseToken(token).getSubject();
    }

    public List<SimpleGrantedAuthority> parseUserAuthoritiesFromToken(String token) {
        List<String> userRoles = parseToken(token).get(CLAIMS_KEY_USER_ROLES, List.class);
        log.debug("userRoles : {}", userRoles);
        return userRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
