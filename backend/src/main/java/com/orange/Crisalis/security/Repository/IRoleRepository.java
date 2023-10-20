package com.orange.Crisalis.security.Repository;

import com.orange.Crisalis.security.Entity.RoleEntity;
import java.util.Optional;

import com.orange.Crisalis.security.Enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Integer>{
    Optional<RoleEntity> findByRoleName(RoleName roleName);
}
