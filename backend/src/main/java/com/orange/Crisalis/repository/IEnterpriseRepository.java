package com.orange.Crisalis.repository;

import com.orange.Crisalis.model.EnterpriseEntity;
import com.orange.Crisalis.security.Entity.UserEntity;
import com.orange.Crisalis.service.EnterpriseService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEnterpriseRepository extends JpaRepository<EnterpriseEntity, Integer> {

    Optional<EnterpriseEntity> findById(int id);

    boolean existsById(int id);

    boolean existsByCuit(String cuit);

    boolean existsByBusinessName(String businessName);

    @Override
    List<EnterpriseEntity> findAll();
}