package com.icchance.q91.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Http內容獲取工具類
 * </p>
 * @author 6687353
 * @since 2023/9/1 09:55:30
 */
@Slf4j
public class HttpContextUtil {

    /**
     * <p>
     * 獲取query參數
     * </p>
     * @param request  HttpServletRequest
     * @return java.util.Map<java.lang.String,java.lang.String>
     * @author 6687353
     * @since 2023/9/1 09:56:06
     */
    public static Map<String, String> getParameterMapAll(HttpServletRequest request) {
        Enumeration<String> parameters = request.getParameterNames();

        Map<String, String> params = new HashMap<>();
        while (parameters.hasMoreElements()) {
            String parameter = parameters.nextElement();
            String value = request.getParameter(parameter);
            params.put(parameter, value);
        }

        return params;
    }

    /**
     * <p>
     * 獲取請求body
     * </p>
     * @param request  ServletRequest
     * @return java.lang.String
     * @author 6687353
     * @since 2023/9/1 09:56:26
     */
    public static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
