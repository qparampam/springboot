package hello.service;


import hello.model.Role;

import java.util.List;

public interface RoleService {
    Role getById(int id);
    Role getByRole(String role);
    List<Role> getAllRoles();
}
