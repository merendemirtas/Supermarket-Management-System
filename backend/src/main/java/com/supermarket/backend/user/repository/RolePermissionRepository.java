package com.supermarket.backend.user.repository;

import com.supermarket.backend.user.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    @Query("""
        select rp.permission.permissionCode
        from RolePermission rp
        where rp.role.id = :roleId
    """)
    List<String> findPermissionCodesByRoleId(@Param("roleId") Long roleId);
}
