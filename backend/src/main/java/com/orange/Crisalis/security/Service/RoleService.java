package com.orange.Crisalis.security.Service;

import com.orange.Crisalis.security.Entity.RoleEntity;
import com.orange.Crisalis.security.Enums.RoleName;
import com.orange.Crisalis.security.Repository.iRoleRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sebastián
 */
@Service
@Transactional
public class RoleService {
    @Autowired
    iRoleRepository irolRepository;
    
    public Optional<RoleEntity> getByRoleName(RoleName roleName){
        return irolRepository.findByRoleName(roleName);
    }
    
    public void save(RoleEntity roleEntity){
        irolRepository.save(roleEntity);
    }
}
