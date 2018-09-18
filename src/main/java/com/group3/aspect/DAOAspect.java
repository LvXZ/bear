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
 * @Date: 2018-07-27  15:08
 */
@Order(3)
@Aspect
@Component
public class DAOAspect {

    //选择logger日志进行输出
    private final static Logger logger = LoggerFactory.getLogger(DAOAspect.class);

    @Pointcut("execution(public * com.group3.dao.*.*(..))")
    public void daoFlag() {
    }


    @Before("daoFlag()")
    //对文件名下的函数进行调用Before拦截
    public void daoBefore(JoinPoint jP) {
        // 类方法
        logger.debug("dao_class_method={}", jP.getSignature().getDeclaringType() + "." + jP.getSignature().getName());
        // 参数
        logger.debug("dao_args={}", jP.getArgs());
    }


    @AfterReturning(value = "daoFlag()",returning = "returnValue")
    public void daoAfterReturning(Object returnValue){
        if(returnValue != null){
            logger.debug("Object={}", returnValue.toString());
        }else{
            logger.debug("null");
        }

    }
}
