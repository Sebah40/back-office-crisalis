package com.orange.Crisalis.security.Service;

import com.orange.Crisalis.security.Entity.UserEntity;
import com.orange.Crisalis.security.Repository.iUserRepository;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    @Autowired
    iUserRepository iusuarioRepository;
    
    public Optional<UserEntity> getByUserName(String username){
        return iusuarioRepository.findByUsername(username);
    }
    
    public boolean existsByUsername(String username){
        return iusuarioRepository.existsByUsername(username);
    }

    public List<UserEntity> findAll(){
        return iusuarioRepository.findAll();
    }
    
    public boolean existsByEmail(String email){
        return iusuarioRepository.existsByEmail(email);
    }
    
    public void save(UserEntity userEntity){
        iusuarioRepository.save(userEntity);
    }
}
