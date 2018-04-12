package com.aop.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by shaobo on 2018/4/12.
 */
@Component
@Aspect
public class AopIntercept {

    /**
     * 只有around拦截可以使用joinPoint。我们就练习这个相对复杂的
     *
     * @param joinPoint
     * @return
     */
    //execution 拦截点介绍：第一个* 表示所有返回值,第二个*表示所有方法，后面的（..）表示这个方法中所有入参。即拦截ApiInterface下所有方法
    @Around("execution(* com.aop.api.ApiInterface.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) {
        before();
        //获得所有请求参数
        Object[] args = joinPoint.getArgs();
        for (Object obj : args) {
            System.out.println("拦截中请求参数:" + obj.toString());
        }
        Object obj = null;
        try {
            obj = joinPoint.proceed();
            System.out.println("拦截中方法返回值:" + obj.toString());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        after();

        return obj;
    }

    private void before() {
        //流水号重复校验
        System.out.println("拦截方法前执行!");
    }

    private void after() {
        System.out.println("拦截方法后执行!");
    }
}
