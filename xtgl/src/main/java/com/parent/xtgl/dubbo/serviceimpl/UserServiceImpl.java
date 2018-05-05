package com.parent.xtgl.dubbo.serviceimpl;

import com.parent.xtgl.entity.Login;
import com.parent.xtgl.entity.User;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements com.parent.xtgl.dubbo.UserServiceI {
    @Autowired
    private com.parent.xtgl.service.UserServiceI userServiceI;

    public com.parent.xtgl.service.UserServiceI getUserServiceI() {
        return userServiceI;
    }

    public void setUserServiceI(com.parent.xtgl.service.UserServiceI userServiceI) {
        this.userServiceI = userServiceI;
    }

    @Override
    public User getUserInfoByLogin(String LoginName) {
        Login login = new Login();
        login.setLoginname(LoginName);
        return   userServiceI.getUserByLoginName(login);
    }
}
