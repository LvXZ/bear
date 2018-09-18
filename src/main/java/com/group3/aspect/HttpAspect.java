package com.group3.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理文件
 * 显示访问请求信息
 */
@Order(1)
@Aspect
@Component
public class HttpAspect {

    //选择logger日志进行输出
    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    private String IP;

    long startTime;

    @Pointcut("execution(public * com.group3.controller.*.*(..))")
    public void flag() {
    }

    @Before("flag()")
    //对文件名下的函数进行调用Before拦截
    public void httpBefore(JoinPoint jP) {
        // 获取开始时间
        startTime = System.currentTimeMillis();
        //记录http请求//URL//method//IP//类方法//参数
        //强制转换
        ServletRequestAttributes SRattributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest HSrequest = SRattributes.getRequest();
        logger.info("-------------------------------------------------------------------------------");
        //URL
        logger.info("URL={}", HSrequest.getRequestURL());//在括号中输出
        //method
        logger.info("method={}", HSrequest.getMethod());
        // IP
        IP = HSrequest.getRemoteAddr();
        logger.info("IP={}", HSrequest.getRemoteAddr());
        // 类方法
        logger.info("controller_class_method={}", jP.getSignature().getDeclaringType() + "." + jP.getSignature().getName());//获取类名
        // 参数
        logger.info("controller_args={}", jP.getArgs());
    }

    //对文件名下的函数进行结束After拦截
    @After("flag()")
    public void httpAfter() {
        // 获取结束时间
        long runTime = System.currentTimeMillis();
        logger.info(" End of the [{}] visit, the running time:[{}ms]",IP,runTime-startTime);
    }



}
