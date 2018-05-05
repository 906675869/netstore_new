package com.parent.xtgl.config.aop;

import com.parent.xtgl.entity.Function;
import com.parent.xtgl.entity.User;
import com.parent.xtgl.service.UserServiceI;
import com.parent.xtgl.utils.utilimpl.CommonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by qianxp on 17/4/27.
 * 登录切面
 */
@Aspect
@Component
public class LoginAspect {
    @Autowired
    private UserServiceI userServiceI;

    public UserServiceI getUserServiceI() {
        return userServiceI;
    }

    public void setUserServiceI(UserServiceI userServiceI) {
        this.userServiceI = userServiceI;
    }

    @Pointcut("execution(public * com.example.demo.controller.afterlogin.*.*(..))")
    public void webLog(){}

    /**
     * 执行方法前 对用户身份做验证
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求
        HttpServletRequest request = attributes.getRequest();
        //获取响应
        HttpServletResponse response = attributes.getResponse();
        //usersession的判断
        User userSession  = userServiceI.getCurrentUser();
        if(StringUtils.isEmpty(userSession)){
            response.sendRedirect("/index");
        }
        //权限的判断
        String url = request.getRequestURL().toString();
        List<Function> functionList = userServiceI.getCurrentUserFunctions();
        if(! CommonUtils.toMatchList(url,functionList,"url")){
            response.sendRedirect("/no_function");
        }
    }
/*
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        System.out.println("方法的返回值 : " + ret);
    }

    //后置异常通知
    @AfterThrowing("webLog()")
    public void throwss(JoinPoint jp){
        System.out.println("方法异常时执行.....");
    }

    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
    @After("webLog()")
    public void after(JoinPoint jp){
        System.out.println("方法最后执行.....");
    }

  //环绕通知,环绕增强，相当于MethodInterceptor
    @Around("webLog()")
    public Object arround(ProceedingJoinPoint pjp) {
        System.out.println("方法环绕start.....");
        try {
            Object o =  pjp.proceed();
            System.out.println("方法环绕proceed，结果是 :" + o);
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }*/
}