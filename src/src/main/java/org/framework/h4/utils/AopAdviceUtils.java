/*  
 * @(#)AopAdviceUtils.java V1.0 2013-11-15 上午09:20:06
 * @ org.framework.util
 *
 * Copyright (c) 2013, Wafersystems All rights reserved.
 * Wafer PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 

package org.framework.h4.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

*//**
 * <p>Title: AOP 通知类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012 Wafer (XA'AN) Systems Co., Ltd. All rights reserved.</p>
 *
 * <p>Company: Wafersystems</p>
 *
 * @author Mou Lu
 * @Date：2013-11-15 上午09:20:06
 * @version 1.0
 *//*

@Service
@Aspect
public class AopAdviceUtils {

protected final Log log = LogFactory.getLog(getClass());
	
	*//**
     *  切点, 匹配任何位于com.wafersystems.lbs.action包及其子包下的方法
     *//*
	@Pointcut("execution(* com.wafersystems.lbs.action.*.*(..))")
	public void actionMethod(){}
	
	*//**
     *  切点, 匹配任何位于com.wafersystems.lbs.action包及其子包下的方法
     *//*
	@Pointcut("execution(* com.wafersystems.lbs.service.*.*(..))")
	public void serviceMethod(){
		
	}
	
	*//**
     *  切点, 匹配任何有@RequestMapping()注解的方法
     *//*
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void anyRequestMappingMethod() {
 
    }
    
    *//**
     *  切点, 匹配任何public的方法
     *//*
    @Pointcut("execution(public * *(..))")
    public void anyPublicMethod() {
 
    }
    
    *//**
     *  切点, 综合以上切点
     *//*
    @Pointcut("actionMethod() && anyRequestMappingMethod() && anyRequestMappingMethod() && serviceMethod()")
    public void anyInteractionMethod() {
 
    }

 // 前置通知
    @Before("actionMethod()")
    public void beforeAdvice() {
        //java.lang.Object[] getArgs()：获取连接点方法运行时的入参列表
        //Signature getSignature() ：获取连接点的方法签名对象
        //java.lang.Object getTarget() ：获取连接点所在的目标对象
        //java.lang.Object getThis() ：获取代理对象本身
    	System.out.println("before");
//        log.debug("XXXXXXXXXXXXX=====>getArgs() " + pjp.getArgs());
//        log.debug("XXXXXXXXXXXXX=====>getSignature() " + pjp.getSignature());
//        log.debug("XXXXXXXXXXXXX=====>getTarget() " + pjp.getTarget());
//        log.debug("XXXXXXXXXXXXX=====>getThis() " + pjp.getThis());
// 
//        log.debug("XXXXXXXXXXXXX=====>pjp.toLongString() " + pjp.toLongString());
//        log.debug("XXXXXXXXXXXXX=====>pjp.getTarget().getClass().getName() " + pjp.getTarget().getClass().getName());
        // 取得controller名称, 查询匹配模块名称, 向访问次数统计表更新访问次数字段 + 1
    }
    
 // 环绕通知
    @Around("actionMethod()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("before invoke method:" + joinPoint.getSignature().getName());
        Object object = joinPoint.proceed();
        log.debug("after invoke method:" + joinPoint.getSignature().getName());
        return object;
    }
    
    //方法执行返回后执行此方法
    @AfterReturning(pointcut="actionMethod()",returning="retVal")
    public void afterReturning(Object obj){
    	
    } 

    //方法执行最终执行的方法
    @After("actionMethod()")
    public void after(JoinPoint pjp) {
        log.debug("XXXXXXXXXXXXX=====>test anyControllerMethod() ");
    }
    
    //出现异常之后执行
    @AfterThrowing(pointcut="actionMethod()",throwing="ex")
    public void throwException(Exception ex){
    	
    }
}
*/