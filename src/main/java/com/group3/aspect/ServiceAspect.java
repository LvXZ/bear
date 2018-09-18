package com.group3.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName:
 * @Description:
 * @Author: lvxz
 * @Date: 2018-07-27  14:35
 */
@Order(2)
@Aspect
@Component
public class ServiceAspect {


    //选择logger日志进行输出
    private final static Logger logger = LoggerFactory.getLogger(ServiceAspect.class);


    @Pointcut("execution(public * com.group3.service.*.*(..))")
    public void serviceFlag() {
    }


    @Before("serviceFlag()")
    //对文件名下的函数进行调用Before拦截
    public void serviceBefore(JoinPoint jP) {
        // 类方法
        logger.info("service_class_method={}", jP.getSignature().getDeclaringType() + "." + jP.getSignature().getName());//获取类名
        // 参数
        logger.info("service_args={}", jP.getArgs());
    }


    @AfterReturning(value = "serviceFlag()",returning = "returnValue")
    public void serviceAfterReturning(Object returnValue){
        if(returnValue != null){
            logger.debug("Object={}", returnValue.toString());
        }else{
            logger.debug("null");
        }
    }
}
