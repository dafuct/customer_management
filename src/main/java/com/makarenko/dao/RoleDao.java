package com.makarenko.dao;

import com.makarenko.entity.Role;
import java.util.List;

public interface RoleDao {
    void create(Role role);

    void update(Role role);

    void remove(Role role);

    Role findByName(String name);

    List<Role> findAll();
}
