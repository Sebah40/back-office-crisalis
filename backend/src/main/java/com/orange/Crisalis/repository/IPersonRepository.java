package com.orange.Crisalis.repository;

import com.orange.Crisalis.model.EnterpriseEntity;
import com.orange.Crisalis.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IPersonRepository extends JpaRepository<PersonEntity, Integer> {

    Optional<PersonEntity> findById(int id);

    boolean existsById(int id);

    boolean existsByLastName(String lastName);

    boolean existsByFirstName(String firstName);

    boolean existsByDni(String dni);

    @Override
    List<PersonEntity> findAll();
}