package com.mylife.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class myAop {

    @Pointcut("execution(* com.mylife.service..*.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void before(){
        System.out.println("do before");//【2】
    }

    @After("pointCut()")
    public void after(){
        System.out.println("do after");//【5】
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint){
        System.out.println("do around.before"); //【1】
        Object result = null;
        try {
            result = joinPoint.proceed();//【3】
        }catch (Throwable e){
            e.printStackTrace();;
        }
        System.out.println("do around.after");//【4】
        return result;
    }
}
