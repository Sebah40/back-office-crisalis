package com.orange.Crisalis.security.Service;

import com.orange.Crisalis.dto.ChangePasswordRequestDTO;
import com.orange.Crisalis.dto.UserProfileDTO;
import com.orange.Crisalis.dto.UserProfileEditDTO;
import com.orange.Crisalis.security.Entity.UserEntity;
import com.orange.Crisalis.security.Repository.IUserRepository;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    private final IUserRepository iuserRepository;




    public UserService(IUserRepository iuserRepository) {
        this.iuserRepository = iuserRepository;

    }


    public Optional<UserEntity> getByUserName(String username){
        return iuserRepository.findByUsername(username);
    }

    public Optional<UserEntity> getByEmail(String email){
        return iuserRepository.findByEmail(email);
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

    public UserProfileDTO getProfileByUsername(String username){
        UserEntity userEntity = this.getByUserName(username).orElseThrow(null);

        return new UserProfileDTO(userEntity);

    }
    public void editProfileByUsername(String username, UserProfileEditDTO userEdited){
        UserEntity existingUser = this.getByUserName(username).orElseThrow(null);
        existingUser.setName(userEdited.getName());
        existingUser.setEmail(userEdited.getEmail());
        existingUser.setBirthdate((userEdited.getBirthdate()));
        iuserRepository.save(existingUser);
    }



}
