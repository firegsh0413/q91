package com.icchance.q91.filter;

import com.icchance.q91.util.HttpContextUtil;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * HttpServletRequest 過濾器
 * </p>
 * @author 6687353
 * @since 2023/9/1 09:46:10
 */
@Component
@WebFilter(filterName = "ParamsRequestFilter", urlPatterns = "/")
public class ParamsRequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (servletRequest instanceof HttpServletRequest) {
            requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
        }
        // 獲取request中的流，取出字符串再次轉換成流，然後放入新的request
        if (null == requestWrapper) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * <p>
     * HttpServletRequest包裝器
     * </p>
     * @author 6687353
     * @since 2023/9/1 09:48:38
     */
    public static class RequestWrapper extends HttpServletRequestWrapper {
        private String mBody;
        public RequestWrapper(HttpServletRequest request) {
            super(request);
            mBody = getBody(request);
        }

        private String getBody(HttpServletRequest request) {
            return HttpContextUtil.getBodyString(request);
        }

        public String getBody() {
            return mBody;
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {

            final ByteArrayInputStream bais = new ByteArrayInputStream(mBody.getBytes(StandardCharsets.UTF_8));
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return bais.read();
                }
            };
        }
    }


}
