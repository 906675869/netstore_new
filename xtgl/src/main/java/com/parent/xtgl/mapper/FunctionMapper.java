package com.parent.xtgl.mapper;

import com.parent.xtgl.entity.Function;
import com.parent.xtgl.entity.Role;
import com.parent.xtgl.entity.User;

import java.util.List;

public interface FunctionMapper {
    List<Function> getAll();

    List<Function> getFunctionsByRole(Role role);

    List<Function> getFunctionsByUser(User user);

    Function getOne(Integer id);

    void insert(Function function);

    void update(Function function);

    void delete(Integer id);


}
