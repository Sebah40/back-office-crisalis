package com.orange.Crisalis.security.Service;

import com.orange.Crisalis.security.Entity.UserEntity;
import com.orange.Crisalis.security.Repository.IUserRepository;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    @Autowired
    IUserRepository iuserRepository;
    
    public Optional<UserEntity> getByUserName(String username){
        return iuserRepository.findByUsername(username);
    }
    
    public boolean existsByUsername(String username){
        return iuserRepository.existsByUsername(username);
    }

    public List<UserEntity> findAll(){
        return iuserRepository.findAll();
    }
    
    public boolean existsByEmail(String email){
        return iuserRepository.existsByEmail(email);
    }
    
        public void save(UserEntity userEntity){
        iuserRepository.save(userEntity);
    }
}
