package com.nixsolutions.dao;

import com.nixsolutions.entity.Role;
import java.util.List;

public interface RoleDao {
    void create(Role role);

    void update(Role role);

    void remove(Role role);

    Role findByName(String name);

    List<Role> findAll();
}
