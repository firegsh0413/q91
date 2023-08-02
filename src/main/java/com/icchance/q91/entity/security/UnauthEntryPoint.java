package com.icchance.q91.entity.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 未授權的API
 * </p>
 * @author 6687353
 * @since 2023/7/28 10:47:47
 */
@Slf4j
public class UnauthEntryPoint implements AuthenticationEntryPoint {
    private static final ObjectMapper jsonObjectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authenticationException) throws IOException, ServletException {
        log.error(authenticationException.getMessage(), authenticationException);
    }

}
