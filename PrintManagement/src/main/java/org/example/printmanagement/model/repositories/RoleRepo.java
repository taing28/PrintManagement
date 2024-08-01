package org.example.printmanagement.model.repositories;

import org.example.printmanagement.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
    Role findRoleByRoleCodeEquals(String roleCode);
}
