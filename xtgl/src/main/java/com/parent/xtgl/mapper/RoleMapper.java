package com.parent.xtgl.mapper;

import com.parent.xtgl.entity.Role;
import com.parent.xtgl.entity.User;

import java.util.List;

public interface RoleMapper {
    List<Role> getAll();

    Role getOne(Integer id);

    int insert(Role role);

    void update(Role role);

    void delete(Integer id);

    List<Role> getRolesByUser(User user);
}
