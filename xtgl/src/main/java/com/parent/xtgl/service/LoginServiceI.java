package com.parent.xtgl.service;

import com.parent.xtgl.entity.Login;

public interface LoginServiceI {
    public String doLogin(String loginName, String password);
    public boolean updateLogin(Login login);
}
