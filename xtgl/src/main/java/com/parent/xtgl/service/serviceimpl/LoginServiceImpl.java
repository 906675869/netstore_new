package com.parent.xtgl.service.serviceimpl;

import com.parent.xtgl.entity.Login;
import com.parent.xtgl.entity.User;
import com.parent.xtgl.mapper.LoginMapper;
import com.parent.xtgl.service.LoginServiceI;
import com.parent.xtgl.service.UserServiceI;
import com.parent.xtgl.utils.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class LoginServiceImpl implements LoginServiceI {
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private RedisUtils redisUtils;

    public RedisUtils getRedisUtils() {
        return redisUtils;
    }

    public void setRedisUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    public UserServiceI getUserServiceI() {
        return userServiceI;
    }

    public void setUserServiceI(UserServiceI userServiceI) {
        this.userServiceI = userServiceI;
    }

    public LoginMapper getLoginMapper() {
        return loginMapper;
    }

    public void setLoginMapper(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    @Override
    public String doLogin(String loginName, String password) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求
        HttpServletRequest request = attributes.getRequest();
        Login login = new Login();
        login.setLoginname(loginName);
        login.setPassword(password);
        //在redis中根据登录名获取login对象
        String redisKey = "Login:"+loginName;
        if( !redisUtils.exists(redisKey) ){
            login = loginMapper.selectByLoginName(login);
            redisUtils.set(redisKey,login);
        }else {
            login = (Login) redisUtils.get(redisKey);
        }
        if(StringUtils.isEmpty(login)){
            return "用户不存在";
        }
        if(! password.equals(login.getLoginname())){
            return "密码不正确";
        }
        User user = userServiceI.getUserByLoginName(login);
        request.getSession().setAttribute(UserServiceI.USERSESSION,user);

        return "登录成功";
    }

    @Override
    public boolean updateLogin(Login login) {
        String key = "Login:"+login.getLoginname();
        boolean redisResult = redisUtils.set(key,login);
        Integer mybatisResult = loginMapper.updateByPrimaryKey(login);
        if(!redisResult || mybatisResult == 0){
            return  false;
        }
        return true;
    }

}
