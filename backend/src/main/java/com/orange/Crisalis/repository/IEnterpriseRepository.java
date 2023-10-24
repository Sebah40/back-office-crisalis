package com.orange.Crisalis.repository;

import com.orange.Crisalis.model.EnterpriseEntity;
import com.orange.Crisalis.security.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IEnterpriseRepository extends JpaRepository<EnterpriseEntity, Integer> {

    Optional<EnterpriseEntity> findById(int id);

    boolean existsById(int id);

    @Override
    List<EnterpriseEntity> findAll();
}
