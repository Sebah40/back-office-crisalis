package com.orange.Crisalis.security.Repository;

import com.orange.Crisalis.security.Entity.UserEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Integer>{
    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Override
    List<UserEntity> findAll();
}
