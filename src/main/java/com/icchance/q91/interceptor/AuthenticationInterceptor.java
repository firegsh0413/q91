package com.icchance.q91.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icchance.q91.annotation.PassToken;
import com.icchance.q91.annotation.UserCertificateAnnotation;
import com.icchance.q91.annotation.UserLoginToken;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.error.ServiceException;
import com.icchance.q91.entity.model.User;
import com.icchance.q91.filter.ParamsRequestFilter;
import com.icchance.q91.service.AuthUserService;
import com.icchance.q91.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <p>
 * 自定義接口攔截器
 * </p>
 * @author 6687353
 * @since 2023/8/15 18:06:46
 */
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    JwtUtil jwtUtil;
    
    @Autowired
    AuthUserService authUserService;

    // 執行Handler方法之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = StringUtils.EMPTY;
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 檢查是否有@PassToken，有則跳過認證
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        // TODO 應該改為login時才去DB確認user資料
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            // 用過濾器獲取requestBody中json格式傳遞的內容，這裡只獲取token做為jwt檢核使用
            if (request instanceof ParamsRequestFilter.RequestWrapper) {
                ParamsRequestFilter.RequestWrapper restoreRequest = (ParamsRequestFilter.RequestWrapper) request;
                JSONObject jsonObject = JSON.parseObject(restoreRequest.getBody());
                if (Objects.isNull(jsonObject)) {
                    // 攔截
                    throw new ServiceException(ResultCode.PARAM_LOSS);
                }
                log.info("從request取得的JSON內容 = " + JSON.toJSONString(jsonObject));
                if (Objects.isNull(jsonObject.get("token"))) {
                    throw new ServiceException(ResultCode.PARAM_LOSS);
                }
                token = jsonObject.get("token").toString();
                if (StringUtils.isBlank(token)) {
                    throw new ServiceException(ResultCode.PARAM_LOSS);
                }
            }
            String account;
            try {
                account = jwtUtil.parseAccount(token);
            } catch (JWTDecodeException je) {
                throw new ServiceException(ResultCode.AWT_PARSE_ERROR);
            }
            User user = authUserService.getByAccount(account);
            if (Objects.isNull(user)) {
                throw new ServiceException(ResultCode.ACCOUNT_NOT_EXIST);
            }
            // TODO 在通過用戶存在判別後進行用戶憑證判別
            if (method.isAnnotationPresent(UserCertificateAnnotation.class)) {
                if (Objects.isNull(user.getCertified()) || !user.getCertified()) {
                    throw new ServiceException(ResultCode.NOT_CERTIFICATE);
                }
            }
        }
        return true;
    }

    // 執行Handler之後、返回ModelAndView之前執行
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    // 執行完Handler之後
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
