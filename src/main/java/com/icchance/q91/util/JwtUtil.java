package com.icchance.q91.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * JWT工具類
 * </p>
 * @author 6687353
 * @since 2023/8/18 15:32:20
 */
@Slf4j
@Component
@Data
public class JwtUtil {

    private static final String CLAIMS_KEY_USER_ROLES = "userRoles";
    @Value("${jwt.signKey}")
    private String jwtSignKey;
    @Value("${jwt.expireTimeAsSec}")
    private long jwtExpireTimeAsAsc;

    /**
     * <p>
     * 生成token
     * </p>
     * @param account 用戶帳號
     * @param userId  用戶ID
     * @return java.lang.String
     * @author 6687353
     * @since 2023/8/18 15:32:30
     */
    public String createToken(String account, Integer userId) {
        //Map<String, Object> claimMap = Collections.singletonMap(CLAIMS_KEY_USER_ROLES, userRoles);
        Map<String, Object> claimMap = Collections.singletonMap("userId", userId);
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

    /**
     * <p>
     * 解析token
     * </p>
     * @param token 令牌
     * @return io.jsonwebtoken.Claims
     * @author 6687353
     * @since 2023/8/18 15:32:50
     */
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

    /**
     * <p>
     * 取得用戶帳號
     * </p>
     * @param token 令牌
     * @return java.lang.String
     * @author 6687353
     * @since 2023/8/18 15:36:27
     */
    public String parseAccount(String token) {
        return parseToken(token).getSubject();
    }

    /**
     * <p>
     * 取得用戶id
     * </p>
     * @param token 令牌
     * @return java.lang.Integer
     * @author 6687353
     * @since 2023/8/18 15:36:53
     */
    public Integer parseUserId(String token) {
        return parseToken(token).get("userId", Integer.class);
    }

    public List<SimpleGrantedAuthority> parseUserAuthoritiesFromToken(String token) {
        List<String> userRoles = parseToken(token).get(CLAIMS_KEY_USER_ROLES, List.class);
        log.debug("userRoles : {}", userRoles);
        return userRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
