package com.icchance.q91.aspect;

import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.entity.model.User;
import com.icchance.q91.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

//@Aspect
@Component
public class UserValidationAspect {

    private final UserService userService;

    @Autowired
    public UserValidationAspect(UserService userService) {
        this.userService = userService;
    }

    @Pointcut("execution(* com.icchance.q91.service.*.*(..)) && args(userToken, ..)")
    public void pointcut(String userToken) {}

    @Around(value = "pointcut(userToken)", argNames = "joinPoint, userToken")
    public Object validateUser(ProceedingJoinPoint joinPoint, String userToken) throws Throwable {
        User user = userService.getUserByToken(userToken);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
        // 調用被切入的方法 並返回獲取到的用戶對象
        return joinPoint.proceed();
    }

}
