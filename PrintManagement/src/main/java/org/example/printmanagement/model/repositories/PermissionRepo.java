package org.example.printmanagement.model.repositories;

import org.example.printmanagement.model.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepo extends JpaRepository<Permission, Integer> {
    Permission findPermissionByUserIdAndRoleId(int userId, int roleId);

    List<Permission> findAllByRoleId(int roleId);
}
