package com.orange.Crisalis.repository;

import com.orange.Crisalis.model.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IClientRepository extends JpaRepository <ClientEntity, Integer>{

    Optional<ClientEntity> findById(int username);

    boolean existsById(int id);

    @Override
    List<ClientEntity> findAll();
}