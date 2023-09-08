package com.icchance.q91.aspect;

import com.anji.captcha.util.StringUtils;
import com.icchance.q91.common.constant.RequestConstant;
import com.icchance.q91.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 請求切面處理
 * </p>
 * @author 6687353
 * @since 2023/9/8 11:30:10
 */
@Component
@Aspect
@Slf4j
public class RequestAspect {

    @Pointcut("execution(* com.icchance.q91.controller..*(..))")
    public void requestAspectPointCut() {
    }

    @Around("requestAspectPointCut()")
    public Object loggerAround(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        // 取得request
        Optional<ServletRequestAttributes> servletRequestAttributesOptional = Optional
                .ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(obj1 -> true)
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast);
        /**
         * 具有HttpServletRequset屬性的請求一定來自HTTP，其他地方呼叫的接口數據該屬性為空
         * 直接執行方法並不打印請求訊息與追蹤API效能分析
         * */
        if (servletRequestAttributesOptional.isPresent()) {
            HttpServletRequest request = servletRequestAttributesOptional.get().getRequest();
            // 是否為文件上傳
            boolean isFileUpload = Optional
                    .ofNullable(request.getContentType())
                    .filter(RequestConstant.Head.MULTIPART::startsWith)
                    .isPresent();
            // 參數處理
            String args = StringUtils.EMPTY;
            if (!isFileUpload) {
                Map<String, String[]> parameterMap;
                if (MapUtils.isNotEmpty(parameterMap = request.getParameterMap())) {
                    args = JacksonUtil.toJson(parameterMap);
                }
            } else {
                args = "文件上傳";
            }

            Object obj = point.proceed();
            String param = StringUtils.EMPTY;
            if (StringUtils.isNotBlank(args)) {
                param = "參數:[" + args + "],";
            }
            long endTime = System.currentTimeMillis();
            LoggerFactory.getLogger(point.getSignature().getDeclaringType())
                    .info("請求:[{}],{}耗時:[{}ms]", request.getRequestURI(), param, endTime - startTime);
            return obj;
        }
        return point.proceed();
    }
}
