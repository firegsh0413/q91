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
//@Component
public class UserValidationAspect {

    private final UserService userService;

    //@Autowired
    public UserValidationAspect(UserService userService) {
        this.userService = userService;
    }

    @Pointcut("execution(* com.icchance.q91.service.*.*(..)) && args(token, ..)")
    public void pointcut(String token) {}

    @Around(value = "pointcut(token)", argNames = "joinPoint, token")
    public Object validateUser(ProceedingJoinPoint joinPoint, String token) throws Throwable {
/*        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().repCode(ResultCode.ACCOUNT_NOT_EXIST.code).repMsg(ResultCode.ACCOUNT_NOT_EXIST.msg).build();
        }*/
        // 調用被切入的方法 並返回獲取到的用戶對象
        return joinPoint.proceed();
    }

}
