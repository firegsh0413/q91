package com.icchance.q91.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icchance.q91.service.impl.UserDetailsServiceImpl;
import com.icchance.q91.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
//@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final ObjectMapper jsonObjectMapper = new ObjectMapper();

    private final JwtUtil jwtUtil;
    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
/*            String token = retriveJwt(request);
            if (token != null) {
                String account = jwtUtil.parseAccountFromToken(token);
                List<SimpleGrantedAuthority> userAuthorities = jwtUtil.parseUserAuthoritiesFromToken(token);
                UserDetails userDetails = new UserDetailImpl(account, null, userAuthorities);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }*/
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * <p>
     * 從Http header 取出JWT
     * </p>
     * @param request HttpServletRequest
     * @return java.lang.String
     * @author 6687353
     * @since 2023/7/28 09:47:30
     */
    private String retriveJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
