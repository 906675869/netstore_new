package com.parent.xtgl.dubbo;

import com.parent.xtgl.entity.User;

public interface UserServiceI {
    User getUserInfoByLogin(String LoginName);
}
