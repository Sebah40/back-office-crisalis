package com.orange.Crisalis.security.Repository;

import com.orange.Crisalis.security.Entity.RoleEntity;
import java.util.Optional;

import com.orange.Crisalis.security.Enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface iRoleRepository extends JpaRepository<RoleEntity, Integer>{
    Optional<RoleEntity> findByRoleName(RoleName roleName);
}