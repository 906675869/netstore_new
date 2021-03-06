package com.parent.xtgl.mapper;

import com.parent.xtgl.entity.Login;

public interface LoginMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Login record);

    int insertSelective(Login record);

    Login selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Login record);

    int updateByPrimaryKey(Login record);

    Login selectByLoginName(Login record);
}